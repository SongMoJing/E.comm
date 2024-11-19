package com.funcablaze;

import com.funcablaze.frame.ChatWindow;
import com.funcablaze.frame.Frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	public static final String title = "E.communication";
	private static Frame main;
	public static Map<String, ChatWindow> chatWindow = new HashMap<>();
	private static TrayIcon trayIcon;
	private static boolean allowNotifications = false;
	
	public static void main(String[] args) {
		SystemTrayInitial();
		main = new Frame(300, 700, 0, 0);
		showMainWindow();

//        showTrayMessage(title, "弹出消息", TrayIcon.MessageType.INFO);
	}
	
	/**
	 * 显示主窗口
	 */
	public static void showMainWindow() {
		if (main != null) {
			main.setVisible(true);
			main.setAlwaysOnTop(true);
			main.toFront();
		}
	}
	
	/**
	 * 弹出信息
	 */
	public static void showTrayMessage(String title, String message, TrayIcon.MessageType messageType) {
		if (trayIcon != null && allowNotifications) {
			trayIcon.displayMessage(title, message, messageType);
		}
	}
	
	/**
	 * 系统托盘
	 */
	private static void SystemTrayInitial() {
		//判断系统是否支持托盘
		if (!SystemTray.isSupported()) {
			return;
		}
		try {
			SystemTray systemTray = SystemTray.getSystemTray();//获取系统默认托盘
			Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/img/icon.png"));//系统栏图标
			trayIcon = new TrayIcon(image, title, createMenu());//添加图标,标题,内容,菜单
			trayIcon.setImageAutoSize(true);//设置图像自适应
			trayIcon.addActionListener(new SysActionListener());//双击打开窗口
			systemTray.add(trayIcon);//添加托盘
		} catch (AWTException ignored) {
		}
	}
	
	//托盘中的菜单
	private static PopupMenu createMenu() {
		PopupMenu menu = new PopupMenu();//创建弹出式菜单
		MenuItem exitItem = new MenuItem("exit EC");//创建菜单项
		//给菜单项添加事件监听器，单击时退出系统
		exitItem.addActionListener(e -> System.exit(0));
		CheckboxMenuItem notificationsItem = new CheckboxMenuItem("allow Notifications");//创建菜单项
		// 可勾选的ItemItem
		notificationsItem.setState(allowNotifications);
		notificationsItem.addActionListener(e -> allowNotifications = !allowNotifications);
		//给菜单项添加事件监听器，单击时退出系统
		exitItem.addActionListener(e -> System.exit(0));
		menu.add(notificationsItem);//添加打开系统菜单
		menu.addSeparator();//菜单分割符
		menu.add(exitItem);//添加退出系统菜单
		return menu;
	}

	//双击托盘弹出主窗体
	static class SysActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			showMainWindow();
		}
	}
}