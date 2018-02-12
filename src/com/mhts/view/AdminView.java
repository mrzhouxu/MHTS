package com.mhts.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

import com.mhts.bean.Admin;

public class AdminView extends JFrame implements ActionListener{
	
	Admin admin = null ;
	boolean isClick = true;
	JPanel main = null;

	/**
	 * 管理员界面
	 * @param admin
	 */
	AdminView(Admin admin) {
		this.admin = admin;
		this.setTitle("欢迎使用猴山售票系统！");
		this.setSize(1200, 900);
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		
		/** header **/
		JPanel header = new JPanel(null);//null 是 布局样式
		header.setPreferredSize(new Dimension(1200, 100));//这里setSize不能改变标题大小，只能使用setPreferredSize
//		header.setBorder(BorderFactory.createTitledBorder(""));//设置边界
		header.setBorder(new EtchedBorder(EtchedBorder.RAISED));//设置边界
		
		JLabel title = new JLabel("管理员界面",JLabel.CENTER);//第二个参数 表示  字体居中对齐方式
		title.setFont(new Font("宋体",Font.PLAIN,30));
		title.setSize(1200, 100);
		
		JLabel name = new JLabel("欢迎您，管理员");
		name.setFont(new Font("宋体",Font.PLAIN,14));
		name.setBounds(800, 70, 100, 20);
		
		JButton btLogout = new JButton("注销");
		btLogout.setBounds(920, 65, 60, 25);
//		btLogout.setContentAreaFilled(false);//按钮设置为透明，这样就不会挡着后面的背景 
		
		JButton btExit = new JButton("退出");
		btExit.setBounds(1000, 65, 60, 25);
//		btExit.setContentAreaFilled(false);//按钮设置为透明，这样就不会挡着后面的背景 
		
		header.add(title);
		header.add(name);
		header.add(btLogout);
		header.add(btExit);
		
		/** menu **/
		JPanel menu = new JPanel(null);//null 是 布局样式
		menu.setPreferredSize(new Dimension(200, 770));//这里setSize不能改变标题大小，只能使用setPreferredSize
		menu.setBorder(new EtchedBorder(EtchedBorder.RAISED));//设置边界
		menu.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));//流式布局
		
		JButton businessStatus = new JButton("营业状况");
		businessStatus.setPreferredSize(new Dimension(150, 40));
		businessStatus.setFont(new Font("宋体", Font.PLAIN, 14));
		
		JButton staffManagement = new JButton("员工管理");
		staffManagement.setPreferredSize(new Dimension(150, 40));
		staffManagement.setFont(new Font("宋体", Font.PLAIN, 14));
		
		JButton windowManagement = new JButton("窗口管理");
		windowManagement.setPreferredSize(new Dimension(150, 40));
		windowManagement.setFont(new Font("宋体", Font.PLAIN, 14));
		
		JButton ticketManagement = new JButton("票型管理");
		ticketManagement.setPreferredSize(new Dimension(150, 40));
		ticketManagement.setFont(new Font("宋体", Font.PLAIN, 14));
		
		menu.add(businessStatus);
		menu.add(staffManagement);
		menu.add(windowManagement);
		menu.add(ticketManagement);
		
		/** main **/
		main = new JPanel(null);
//		main.setBackground(Color.red);
		main.setPreferredSize(new Dimension(1000, 770));//这里setSize不能改变标题大小，只能使用setPreferredSize
		main.setBorder(new EtchedBorder(EtchedBorder.RAISED));//设置边界
		
//		JPanel temp = new BusinessStatusView();
//		temp.setBounds(0, 0, 100, 100);
//		temp.setBackground(Color.yellow);
//		JButton jbutton = new JButton("123");
//		jbutton.setBounds(0, 0, 100, 20);
//		temp.add(jbutton);
		
//		main.add(new BusinessStatusView());  todo  打开
		main.add(new AddTicketerView());
		
		/** footer **/
		JPanel footer = new JPanel(null);
		footer.setPreferredSize(new Dimension(1200, 30));//这里setSize不能改变标题大小，只能使用setPreferredSize
		footer.setBorder(new EtchedBorder(EtchedBorder.RAISED));//设置边界
		
		JLabel copyright = new JLabel("版权所有 @李诗莹 2018.2.10",JLabel.CENTER);//第二个参数 表示  字体居中对齐方式
		copyright.setFont(new Font("宋体",Font.PLAIN,12));
		copyright.setSize(1200, 30);
		
		footer.add(copyright);
		
		/** 按钮监听 **/
		btExit.addActionListener(this);
		btLogout.addActionListener(this);
		businessStatus.addActionListener(this);
		staffManagement.addActionListener(this);
		windowManagement.addActionListener(this);
		ticketManagement.addActionListener(this);
		
		/** 窗体布局 **/
		this.add(BorderLayout.NORTH,header);
		this.add(BorderLayout.WEST,menu);
		this.add(BorderLayout.CENTER,main);
		this.add(BorderLayout.SOUTH,footer);
		
		/** 窗体设置 **/
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		new AdminView(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("注销")) {
			if(JOptionPane.showConfirmDialog(this, "您将要注销登录,是否继续？", "注销", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
				new LoginView();
			}
		}else if(e.getActionCommand().equals("退出")) {
			if(JOptionPane.showConfirmDialog(this, "您将要退出系统,是否继续？", "退出", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		}else if(e.getActionCommand().equals("营业状况")) {
			this.main.removeAll();
			this.main.add(new BusinessStatusView());
			this.main.repaint();
			this.main.validate();
		}else if(e.getActionCommand().equals("员工管理")) {
			this.main.removeAll();
			this.main.add(new StaffManagementView());
			this.main.repaint();
			this.main.validate();
		}else if(e.getActionCommand().equals("窗口管理")) {
			System.out.println("窗口管理");
		}else if(e.getActionCommand().equals("票型管理")) {
			System.out.println("票型管理");
		}
		
		/** 线程 防止按钮多点 **/
		new Thread() { 
			@Override
			public void run() {
				super.run();
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				isClick = !isClick;
				this.stop();
			}
		}.start();
	}
}
