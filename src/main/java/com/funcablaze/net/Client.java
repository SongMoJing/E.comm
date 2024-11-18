package com.funcablaze.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private MessageListener messageListener;

    public enum MessageType {
        /** 用户发送的信息 */
        TEXT,
        /** 用户发送的文件 */
        FILE,
        /** 用户发送的名片 */
        CARD,
        /** 机器人发送的Markdown信息 */
        MD,
        /** 系统发送的信息 */
        INFO,
        /** 服务器发送的信息 */
        SERVER
    }

    public static String MessageType(MessageType type) {
        return switch (type) {
            case TEXT -> "text";
            case FILE -> "file";
            case CARD -> "card";
            case MD -> "md";
            case INFO -> "info";
            case SERVER -> "server";
        };
    }

    public static MessageType MessageType(String type) {
        return switch (type) {
            case "text" -> MessageType.TEXT;
            case "file" -> MessageType.FILE;
            case "card" -> MessageType.CARD;
            case "md" -> MessageType.MD;
            case "info" -> MessageType.INFO;
            case "server" -> MessageType.SERVER;
            default -> null;
        };
    }

    public boolean linkServer(String serverAddress, int serverPort, MessageListener messageListener) {
        boolean Res;
        try {
            this.messageListener = messageListener;
            clientSocket = new Socket(serverAddress, serverPort);
            Res = true;
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            // 读取服务器响应的数据
            new Thread(() -> {
                while (true) {
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    try {
                        bytesRead = inputStream.read(buffer);
                    } catch (IOException ignored) {
                    }
                    String info = new String(buffer, 0, bytesRead);
                    switch (info) {
                        case "BREAK_LINKS" -> close();
                        default -> {
                            String[] res = info.split(":");
                            if (res.length == 4) {
                                this.messageListener.onMessageReceived(res[1], MessageType(res[2]), res[3]);
                            }
                        }
                    }
                }
            }).start();
        } catch (Exception ignored) {
            Res = false;
        }
        return Res;
    }

    public boolean sendMessage(String ip, MessageType type, String message) {
        try {
            message = message.replace(":", "：");
            outputStream.write(("Send:" + ip + ":" + MessageType(type) + ":" + message).getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public interface MessageListener {
        void onMessageReceived(String from, MessageType type, String message);
    }

    public void close() {
        try {
            outputStream.write("close".getBytes());
            clientSocket.close();
        } catch (Exception ignored) {
        }
    }
}
