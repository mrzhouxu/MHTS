package com.mhts.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mhts.bean.Admin;
import com.mhts.bean.Ticketer;
import com.mhts.controller.LoginController;

public class LoginView extends JFrame {
	
	final int WIDTH = 600,
			HEIGHT = 500,
			FONT_SIZE = 30,
			TITLE_HEIGHT = 100,
			FOOTER_FONT_SIZE = 12,
			FOOTER_HEIGHT = 30,
			JL_WIDTH = 60,
			JL_HEIGHT = 20,
			JT_WIDTH = 150,
			JT_HEIGHT = 30;
	final String TITLE = "猴山售票系统登录窗口",
			FONT_NAME = "宋体",
			FOOTER_TITLE = "版权所有 @李诗莹 2018.2.10";
	JLabel errMsg = null;
	
	/**
	 * 管理员窗体构造方法
	 */
	LoginView(){
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
//		setLocationRelativeTo(owner);
//		这种方法是设定一个窗口的相对于另外一个窗口的位置（一般是居中于父窗口的中间），如果owner==null则窗口就居于屏幕的中央
		
		JLabel title = new JLabel(TITLE,JLabel.CENTER);//第二个参数 表示  字体居中对齐方式
		title.setFont(new Font(FONT_NAME,Font.PLAIN,FONT_SIZE));
		title.setPreferredSize(new Dimension(WIDTH, TITLE_HEIGHT));//这里setSize不能改变标题大小，只能使用setPreferredSize
		
		
		JLabel footer = new JLabel(FOOTER_TITLE,JLabel.CENTER);//第二个参数 表示  字体居中对齐方式
		footer.setFont(new Font(FONT_NAME,Font.PLAIN,FOOTER_FONT_SIZE));
		footer.setPreferredSize(new Dimension(WIDTH, FOOTER_HEIGHT));//这里setSize不能改变标题大小，只能使用setPreferredSize
		
		JPanel main = new JPanel();
		main.setLayout(null);
		
		errMsg = new JLabel("",JLabel.CENTER);
		errMsg.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
		errMsg.setForeground(Color.red);
		errMsg.setLocation(0, -5);
		errMsg.setSize( WIDTH,30);
		
		JLabel jlStatus = new JLabel("身份：");
		jlStatus.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
		jlStatus.setBounds(200,25,JL_WIDTH,JL_HEIGHT);
		ButtonGroup bgStatus = new ButtonGroup();
		JRadioButton jrAdmin = new JRadioButton("管理员",true);
		jrAdmin.setBounds(250, 25, 80, JL_HEIGHT);
		JRadioButton jrTicketer = new JRadioButton("售票员");
		jrTicketer.setBounds(340, 25, 80, JL_HEIGHT);
		bgStatus.add(jrAdmin);
		bgStatus.add(jrTicketer);
		//System.out.println(jrAdmin.isSelected());
		
		JLabel jlAccount = new JLabel("账号：");
		jlAccount.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
		jlAccount.setBounds(200,75,JL_WIDTH,JL_HEIGHT);
		JTextField jtAccount = new JTextField();
		jtAccount.setBounds(250,70,JT_WIDTH,JT_HEIGHT);
		
		JLabel jlPassWord = new JLabel("密码：");
		jlPassWord.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
		jlPassWord.setBounds(200,130,JL_WIDTH,JL_HEIGHT);
		JPasswordField jtPassWord = new JPasswordField();
		jtPassWord.setBounds(250,125,JT_WIDTH,JT_HEIGHT);
		
		JButton btLogin = new JButton("登录");
		btLogin.setBounds(200, 180, 200, JT_HEIGHT);
		
		Admin admin = new Admin();
		Ticketer ticketer = new Ticketer();
		KeyAdapter login = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode()==10) {
					if(jrAdmin.isSelected()) {
						admin.setAccount(jtAccount.getText().replaceAll(" ", ""));
						admin.setPassword(jtPassWord.getText().replaceAll(" ", ""));
						submit(1,admin,ticketer);
					}else if(jrTicketer.isSelected()) {
						ticketer.setAccount(jtAccount.getText().replaceAll(" ", ""));
						ticketer.setPassword(jtPassWord.getText().replaceAll(" ", ""));
						submit(2,admin,ticketer);
					}
				}
			}
		};
		
		jtAccount.addKeyListener(login);
		jtPassWord.addKeyListener(login);
		btLogin.addKeyListener(login);
		btLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getButton() == MouseEvent.BUTTON1) {//MouseEvent.BUTTON1表示鼠标左键
					if(jrAdmin.isSelected()) {
						admin.setAccount(jtAccount.getText().replaceAll(" ", ""));
						admin.setPassword(jtPassWord.getText().replaceAll(" ", ""));
						submit(1,admin,ticketer);
					}else if(jrTicketer.isSelected()) {
						ticketer.setAccount(jtAccount.getText().replaceAll(" ", ""));
						ticketer.setPassword(jtPassWord.getText().replaceAll(" ", ""));
						submit(2,admin,ticketer);
					}
				}
			}
		});
		
		main.add(errMsg);
		main.add(jlStatus);
		main.add(jrAdmin);
		main.add(jrTicketer);
		main.add(jlAccount);
		main.add(jtAccount);
		main.add(jlPassWord);
		main.add(jtPassWord);
		main.add(btLogin);
		
		this.add(BorderLayout.CENTER,main);
		this.add(BorderLayout.NORTH,title);
		this.add(BorderLayout.SOUTH,footer);
//		BorderLayout（边界布局）
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	/**
	 * 登陆操作
	 * @param status
	 * @param admin
	 * @param ticketer
	 */
	public void submit(int status,Admin admin,Ticketer ticketer) {
		boolean suc = false;
		if(status == 1) {
			if(admin.getAccount().equals("")||admin.getPassword().equals("")) {
				errMsg.setText("账号和密码不能为空！");
				return;
			}else {
				LoginController login = new LoginController();
				suc = login.adminLogin(admin);
			}
		}else if(status == 2) {
			if(ticketer.getAccount().equals("")||ticketer.getPassword().equals("")) {
				errMsg.setText("账号和密码不能为空！");
				return;
			}else {
				LoginController login = new LoginController();
				suc = login.ticketerLogin(ticketer);
			}
		}
		
		if(suc) {
			errMsg.setText("登陆成功，正在跳转...");
			if(status == 1) {
				this.dispose();
				new AdminView(admin);
			}else if(status == 2) {
				this.dispose();
			}
			//进入界面
		}else {
			errMsg.setText("账号或密码错误！");
		}
		
	}
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		/** 改变按钮样式 **/
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		new LoginView();
	}

}
