package com.funcablaze.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
	public static void addTop(String title, JFrame window, Listener onExitListener) {
		final int[] mouseX = new int[1];
		final int[] mouseY = new int[1];
		// 自定义标题栏
		JPanel titleBar = new JPanel();
		titleBar.setBackground(backgroundColor);
		titleBar.setPreferredSize(new Dimension(window.getWidth(), 30));
		titleBar.setLayout(new BorderLayout());
		
		// 标题文本
		JLabel name = new JLabel(title, SwingConstants.CENTER);
		name.setForeground(Color.WHITE);
		titleBar.add(name, BorderLayout.CENTER);
		
		// 关闭按钮
		JButton closeButton = new JButton("X");
		Font LetterFont = new Font("微软雅黑", Font.PLAIN, 15);
		closeButton.setFont(LetterFont);
		closeButton.setFocusPainted(false);
		closeButton.setBorderPainted(false);
		closeButton.setForeground(Color.WHITE);
		closeButton.setBackground(backgroundColor);
		closeButton.setHorizontalAlignment(SwingConstants.CENTER);
		closeButton.setPreferredSize(new Dimension(45, 30));
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				window.setVisible(false);
				if (onExitListener != null) onExitListener.onExit();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				closeButton.setBackground(exitfocusColor);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				closeButton.setBackground(backgroundColor);
			}
		});
		
		titleBar.add(closeButton, BorderLayout.EAST);
		
		// 鼠标拖动实现窗口移动
		titleBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX[0] = e.getX();
				mouseY[0] = e.getY();
			}
		});
		
		titleBar.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				window.setLocation(x - mouseX[0], y - mouseY[0]);
			}
		});
		
		// 添加自定义标题栏和主面板
		window.add(titleBar, BorderLayout.NORTH);
	}
	
	public interface Listener {
		void onExit();
	}
	
	public static final Color
			backgroundColor = new Color(30, 31, 34),
			exitfocusColor = new Color(255, 0, 0),
			toolBackColor = new Color(190, 190, 190);
}
