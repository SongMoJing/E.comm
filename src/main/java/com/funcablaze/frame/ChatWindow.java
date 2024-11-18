package com.funcablaze.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatWindow extends JFrame {

    private int mouseX, mouseY;

    private void addTop(String title) {
        // 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 自定义标题栏
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.DARK_GRAY);
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));
        titleBar.setLayout(new BorderLayout());

        // 标题文本
        JLabel name = new JLabel(title, SwingConstants.CENTER);
        name.setForeground(Color.WHITE);
        titleBar.add(name, BorderLayout.CENTER);

        // 关闭按钮
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.addActionListener(e -> System.exit(0));
        titleBar.add(closeButton, BorderLayout.EAST);

        // 鼠标拖动实现窗口移动
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });

        // 添加自定义标题栏和主面板
        mainPanel.add(titleBar, BorderLayout.NORTH);
        add(mainPanel);
    }
}
