package com.funcablaze.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatWindow extends JFrame {
	
	private int mouseX, mouseY;
	
	public ChatWindow(String title, String ip) {
		setSize(400, 600);
		setTitle(title);
		setUndecorated(true);
		setLocationRelativeTo(null);
		addTop(title);
		addBottom();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException ignored) {
		}
		setVisible(true);
	}
	
	private void addBottom() {
		// 底部面板
		JPanel bottom = new JPanel();
		
		// 工具栏
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(1, 5));
//		toolBar
		bottom.add(toolBar, BorderLayout.NORTH);
		bottom.setBackground(toolBackColor);
		add(bottom, BorderLayout.SOUTH);
	}
	
	private void addTop(String title) {
		// 自定义标题栏
		JPanel titleBar = new JPanel();
		titleBar.setBackground(backgroundColor);
		titleBar.setPreferredSize(new Dimension(getWidth(), 30));
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
				setVisible(false);
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
		add(titleBar, BorderLayout.NORTH);
	}

	private static final Color
			backgroundColor = new Color(30, 31, 34),
			exitfocusColor = new Color(255, 0, 0),
			toolBackColor = new Color(190, 190, 190);
}
