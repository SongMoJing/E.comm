package com.funcablaze.frame;

import com.funcablaze.Main;
import com.funcablaze.net.Client;
import com.funcablaze.net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import static com.funcablaze.Main.showTrayMessage;

public class Frame extends JFrame {
	
	private final JList<User> userList;
	private final JPanel tool;
	public boolean alwaysOnTop = false;
	public Client client;
	
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
		tool = createTool();
		add(tool, BorderLayout.SOUTH);
		link2Server();
		add(userList);
	}
	
	private JPanel createTool() {
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new GridLayout(1, 5));
		toolBar.setPreferredSize(new Dimension(getWidth(), 40));
		JButton newContact = new JButton(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/icon/New contact"))).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		newContact.addActionListener(e -> new JFrame() {
			public JFrame JFrame() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						 UnsupportedLookAndFeelException ignored) {
				}
				setTitle("添加联系人");
				setSize(new Dimension(300, 145));
				setLocationRelativeTo(null);
				setResizable(false);
				setUndecorated(true);
				Window.addTop("添加联系人", this, this::dispose);
				addView();
				return this;
			}
			
			private void addView() {
				// 创建一个主面板，使用 GridLayout
				JPanel mainPanel = new JPanel();
				Dimension input = new Dimension(240, 30);
				mainPanel.add(new JLabel("用户名"));
				JTextField inputNameTextField = new JTextField();
				inputNameTextField.setPreferredSize(input);
				mainPanel.add(inputNameTextField);
				mainPanel.add(new JLabel("IP地址"));
				JTextField inputIpTextField = new JTextField();
				inputIpTextField.setPreferredSize(input);
				mainPanel.add(inputIpTextField);
				mainPanel.setPreferredSize(new Dimension(300, 80));
				JButton button = new JButton("申请添加为好友");
				button.setPreferredSize(new Dimension(300, 40));
				button.addActionListener(e -> {
					// TODO 申请添加为好友
//					Client.
					dispose();
				});
				mainPanel.add(button, BorderLayout.SOUTH);
				add(mainPanel, BorderLayout.CENTER);
			}
		}.JFrame().setVisible(true));
		toolBar.add(newContact);
		JButton Contacts = new JButton(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/icon/Contacts"))).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		Contacts.addActionListener(e -> new JFrame() {
			public JFrame JFrame() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						 UnsupportedLookAndFeelException ignored) {
				}
				setTitle("添加联系人");
				setSize(new Dimension(300, 145));
				setLocationRelativeTo(null);
				setResizable(false);
				setUndecorated(true);
				Window.addTop("添加联系人", this, null);
				addView();
				return this;
			}
			
			private void addView() {
				// 创建一个主面板，使用 GridLayout
				JPanel mainPanel = new JPanel();
				Dimension input = new Dimension(240, 30);
				mainPanel.add(new JLabel("用户名"));
				JTextField inputNameTextField = new JTextField();
				inputNameTextField.setPreferredSize(input);
				mainPanel.add(inputNameTextField);
				mainPanel.add(new JLabel("IP地址"));
				JTextField inputIpTextField = new JTextField();
				inputIpTextField.setPreferredSize(input);
				mainPanel.add(inputIpTextField);
				mainPanel.setPreferredSize(new Dimension(300, 80));
				JButton button = new JButton("申请添加为好友");
				button.setPreferredSize(new Dimension(300, 40));
				button.addActionListener(e -> {
					// TODO 申请添加为好友
					System.out.println("申请添加为好友");
					System.out.println("ip: " + inputIpTextField.getText());
					System.out.println("name: " + inputNameTextField.getText());
					dispose();
				});
				mainPanel.add(button, BorderLayout.SOUTH);
				add(mainPanel, BorderLayout.CENTER);
			}
		}.JFrame().setVisible(true));
		toolBar.add(Contacts);
		JButton setting = new JButton(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/icon/setting"))).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		setting.addActionListener(e -> new JFrame() {
			public JFrame JFrame() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						 UnsupportedLookAndFeelException ignored) {
				}
				setTitle("设置");
				setSize(new Dimension(300, 145));
				setLocationRelativeTo(null);
				setResizable(false);
				setUndecorated(true);
				Window.addTop("设置", this, null);
				addView();
				return this;
			}
			
			private void addView() {
				// TODO Setting
			}
		}.JFrame().setVisible(true));
		toolBar.add(setting);
		// TODO 添加工具
		return toolBar;
	}
	
	private void link2Server() {
		// TODO 连接服务器
		client = new Client();
		client.linkServer("192.168.12.138", 9109, (from, type, message) -> {
			switch (type) {
				case TEXT, CARD, MD, FILE -> showTrayMessage(from, message, TrayIcon.MessageType.INFO);
				case SERVER -> {
					// TODO 处理服务器消息
					Main.getMessage(from, type, message);
				}
				default -> {}
			}
		});
	}
	
	private JList<User> createList() {
		// create List 模型
		DefaultListModel<User> model = new DefaultListModel<>();
		// 将项添加到模型
		model.addElement(new User("张三", "一二三四五", "192.168.12.100", false, true, 5));
		model.addElement(new User("李四", "上山打老虎", "192.168.12.101", false, false, -3));
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
	
	public record User(String name, String message, String ip, boolean isOnline, boolean isTop,
					   int letter) {
	}
	
	public static class UserRenderer extends JPanel implements ListCellRenderer<User> {
		
		private final JLabel Icon = new JLabel(), Name = new JLabel(), Info = new JLabel(), letter = new JLabel();
		
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
		JButton topButton = new JButton("Top");
		Font LetterFont = new Font("微软雅黑", Font.PLAIN, 15);
		topButton.setFont(LetterFont);
		topButton.setFocusPainted(false);
		topButton.setBorderPainted(false);
		topButton.setForeground(Color.WHITE);
		topButton.setBackground(backgroundColor);
		topButton.setHorizontalAlignment(SwingConstants.CENTER);
		topButton.setPreferredSize(new Dimension(100, 30));
		topButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				alwaysOnTop = !alwaysOnTop;
				setAlwaysOnTop(alwaysOnTop);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				topButton.setBackground(topfocusColor);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (alwaysOnTop) {
					topButton.setBackground(noneTopfocusColor);
				} else {
					topButton.setBackground(backgroundColor);
				}
			}
		});
		titleBar.add(topButton, BorderLayout.WEST);
		
		// 关闭按钮
		JButton closeButton = new JButton("X");
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
			topfocusColor = new Color(155, 155, 155),
			noneTopfocusColor = new Color(121, 121, 121),
			selectColor = new Color(201, 196, 196);
}
