package com.funcablaze.net;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Server {

    private final Map<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) {
        new Server(9109, 45);
    }

    public Server(int port, int maxClient) {
        try {
            ServerSocket serverSocket = new ServerSocket(port, maxClient);
            new Thread(() -> {
                while (true) {
                    try {
                        // 等待客户端连接
                        Socket clientSocket = serverSocket.accept();
                        // 连接成功：
                        this.clients.put(clientSocket.getInetAddress().getHostAddress(), clientSocket);
                        System.out.println("客户端已连接：" + clientSocket.getInetAddress().getHostAddress());
                        InputStream inputStream = clientSocket.getInputStream();
                        new Thread(() -> {
                            while (true) {
                                try {
                                    // 等待消息
                                    if (clientSocket.isClosed()) break;
                                    byte[] buffer = inputStream.readAllBytes();
                                    int bytesRead = buffer.length;
                                    receiveFrom(clientSocket, new String(buffer, 0, bytesRead));
                                } catch (IOException e) {
                                    break;
                                }
                            }
                        }).start();
                    } catch (IOException ignored) {
                    }
                }
            }).start();
        } catch (IOException ignored) {}
    }

    /**
     * 系统转发
     * @param client  来源端
     * @param message 内容
     */
    private void receiveFrom(Socket client, String message) {
        Builder builder = new Builder(message);
        Map<String, String> map = builder.getMap();
        Client.MessageType type = Client.MessageType(map.get("type"));
        if (type != null) {
            switch (type) {
                case TEXT, FILE, CARD, MD, INFO -> sendTo(builder);
                case SERVER -> {
                    JSONObject json = new JSONObject(map.get("content"));

                }
            }
        }
    }

    /**
     * 发送信息
     * @param builder 信息构建器
     */
    public void sendTo(Builder builder) {
        try {
            Map<String, String> map = builder.getMap();
            Socket clientSocket = this.clients.get(map.get("ip"));
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(builder.getString().getBytes());
        } catch (IOException ignored) {
        }
    }

    /**
     * 断开连接
     * @param ip 客户端ip
     * @return 成功断开
     */
    public boolean closeAt(String ip) {
        try {
            Socket clientSocket = this.clients.get(ip);
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(("BREAK_LINKS").getBytes());
            System.out.println("客户端已断开：" + ip);
            clientSocket.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}