package com.funcablaze.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame extends JFrame {

    public Frame(int width, int height, int x, int y) {
        // 设置 JFrame 无边框
        setUndecorated(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLocation(x, y);
        JPanel main = addTop();
    }

    private JPanel addTop() {
        JPanel back = new JPanel();
        back.setBackground(new Color(30, 31, 34));
        // 自定义标题栏
        JPanel titleBar = new JPanel();
        {
            titleBar.setBackground(new Color(0, 0, 0, 0));
            titleBar.setPreferredSize(new Dimension(getWidth(), 25));
            titleBar.setLayout(new BorderLayout());
        }
        // 关闭按钮
        JLabel closeButton = new JLabel("X");
        {
            closeButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
            closeButton.setPreferredSize(new Dimension(40, 30));
            closeButton.setHorizontalAlignment(SwingConstants.CENTER);
            closeButton.setForeground(Color.WHITE);
            closeButton.setBackground(new Color(255, 0, 0, 255));
//            closeButton.setBackground(new Color(0, 0, 0, 0));
            closeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setVisible(false);
                }
            });
            titleBar.add(closeButton, BorderLayout.EAST);
        }
        // 添加自定义标题栏和主面板
        back.add(titleBar, BorderLayout.WEST);
        add(back, BorderLayout.CENTER);
        return back;
    }
}
