package com.funcablaze.frame;

import com.funcablaze.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Frame extends JFrame {
	
	private final JList<User> userList;
	
	public Frame(int width, int height, int x, int y) {
		// 设置 JFrame 无边框
		setTitle(Main.title);
		setUndecorated(true);
		setSize(width, height);
		setLocation(x, y);
		addTop();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException ignored) {
		}
		userList = createList();
		add(userList);
	}
	
	private JList<User> createList() {
		// create List 模型
		DefaultListModel<User> model = new DefaultListModel<>();
		// 将项添加到模型
		model.addElement(new User("张三", "一二三四五", "192.168.12.100", null, false, true, 5));
		model.addElement(new User("李四", "上山打老虎", "192.168.12.101", null, false, false, -3));
		// 使用 model 创建 JList
		JList<User> list = new JList<>(model);
		// 设置单元格渲染器
		list.setCellRenderer(new UserRenderer());
		// 双击监听
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					User user = list.getSelectedValue();
					if (user != null) {
						if (Main.chatWindow.containsKey(user.ip)) {
							Main.chatWindow.get(user.ip).setVisible(true);
						} else {
							Main.chatWindow.put(user.ip, new ChatWindow(user.name(), user.ip));
						}
					}
				}
			}
		});
		return list;
	}
	
	public record User(String name, String message, String ip, ImageIcon icon, boolean isOnline, boolean isTop,
					   int letter) {
	}
	
	public static class UserRenderer extends JPanel implements ListCellRenderer<User> {
		
		private JLabel Icon = new JLabel(), Name = new JLabel(), Info = new JLabel(), letter = new JLabel();
		
		public UserRenderer() {
			setLayout(new BorderLayout(5, 5));
			
			JPanel panelText = new JPanel(new GridLayout(0, 1));
			panelText.add(Name);
			panelText.add(Info);
			add(Icon, BorderLayout.WEST);
			add(panelText, BorderLayout.CENTER);
			add(letter, BorderLayout.EAST);
		}
		
		@Override
		public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
			ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Users/icon/" + user.ip))).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			Icon.setIcon(icon);
			Name.setText(user.name());
			Font NameFont = new Font("微软雅黑", Font.BOLD, 14);
			Name.setFont(NameFont);
			Info.setText(user.message);
			Info.setForeground(Color.GRAY);
			letter.setForeground(Color.RED);
			letter.setPreferredSize(new Dimension(40, 50));
			letter.setOpaque(true);
			letter.setHorizontalAlignment(SwingConstants.CENTER);
			Font LetterFont = new Font("consolas", Font.PLAIN, 15);
			letter.setFont(LetterFont);
			if (user.letter > 0) {
				letter.setText(user.letter + "");
			} else if (user.letter == 0) {
				letter.setText("");
			} else {
				letter.setText("•" + Math.abs(user.letter));
				letter.setForeground(Color.DARK_GRAY);
			}
			// 设置 Opaque 以更改 JLabel 的背景颜色
			Name.setOpaque(true);
			Info.setOpaque(true);
			Icon.setOpaque(true);
			// 约束大小
			setPreferredSize(new Dimension(getWidth(), 50));
			
			if (isSelected) {
				// 被选中
				Name.setBackground(selectColor);
				Info.setBackground(selectColor);
				Icon.setBackground(selectColor);
				letter.setBackground(selectColor);
				setBackground(selectColor);
			} else {
				Name.setBackground(list.getBackground());
				Info.setBackground(list.getBackground());
				Icon.setBackground(list.getBackground());
				letter.setBackground(list.getBackground());
				setBackground(list.getBackground());
			}
			return this;
		}
	}
	
	private void addTop() {
		// 自定义标题栏
		JPanel titleBar = new JPanel();
		titleBar.setLayout(new BorderLayout());
		titleBar.setBackground(backgroundColor);
		titleBar.setPreferredSize(new Dimension(getWidth(), 30));
		
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
		add(titleBar, BorderLayout.NORTH);
	}
	
	private static final Color
			backgroundColor = new Color(30, 31, 34),
			exitfocusColor = new Color(255, 0, 0),
			selectColor = new Color(201, 196, 196);
}
