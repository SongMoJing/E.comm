package com.funcablaze.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.funcablaze.frame.Window.*;

public class ChatWindow extends JFrame {
	
	private int mouseX, mouseY;
	
	public ChatWindow(String title, String ip) {
		setSize(400, 600);
		setTitle(title);
		setUndecorated(true);
		setLocationRelativeTo(null);
		addTop(title, this, null);
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
		toolBar.setLayout(new GridLayout(1, 3));
		toolBar.setBackground(toolBackColor);
//		toolBar.add();
		bottom.add(toolBar, BorderLayout.NORTH);
		bottom.setBackground(toolBackColor);
		add(bottom, BorderLayout.SOUTH);
	}
}
