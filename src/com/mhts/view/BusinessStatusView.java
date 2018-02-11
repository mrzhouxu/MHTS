package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class BusinessStatusView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	JTable table = null;
	boolean isClick = true;
	
	BusinessStatusView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("管理员界面 > 营业状况");
		jlTitle.setBounds(30, 10, 150, 30);
		jlTitle.setFont(font);
		
		/** select **/
		JLabel jlDate = new JLabel("请选择日期：");
		jlDate.setBounds(30, 50, 100, 30);
		jlDate.setFont(font);
		
		JComboBox years = new JComboBox();
		for(int i=Calendar.getInstance().get(Calendar.YEAR);i>=2000;i--) {
			years.addItem(i+"年");
		}
		years.setBounds(110, 50, 80, 30);
		
		JComboBox months = new JComboBox();
		for(int i=1;i<=12;i++) {
			months.addItem(i+"月");
		}
		months.setBounds(200, 50, 80, 30);
		
		JButton btSelect = new JButton("查询");
		btSelect.setBounds(300, 50, 80, 30);
		
		JButton btReset = new JButton("重置");
		btReset.setBounds(390, 50, 80, 30);
		
		/** income **/
		JLabel jlIncome = new JLabel("收入(单位元):");
		jlIncome.setFont(font);
		jlIncome.setBounds(700, 50, 100, 30);
		
		JLabel jlMoney = new JLabel("888888888.00");
		jlMoney.setFont(new Font("宋体", Font.PLAIN, 30));
		jlMoney.setBounds(780, 50, 200, 30);
		
		/** table **/
		Vector tableHeader = new Vector();
		String[] tableHeaderStr = {"序号","身份证号","票类型","窗口号","购票时间"};
		for(int i=0,len=tableHeaderStr.length;i<len;i++) {
			tableHeader.add(tableHeaderStr[i]);
		}
        
		Vector datas = new Vector();
		for(int i=0;i<20;i++) {
			Vector temp = new Vector();
			temp.add(i+1);
			temp.add("10001");
			temp.add("成人票");
			temp.add("壹号窗口");
			temp.add("2017-10-12 20：21：10");
			datas.add(temp);
		}
		
		DefaultTableModel tableModel=new DefaultTableModel(datas, tableHeader);
		table = new JTable();
		table.setRowHeight(26);//指定每一行的行高50
		table.setModel(tableModel);
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setBounds(30, 100, 930, 548);
		
		/** paging **/
		JLabel jlCount = new JLabel("共 888888 条记录");
		jlCount.setFont(font);
		jlCount.setBounds(30, 666, 200, 30);
		
		JButton homePage = new JButton("首页");
		homePage.setFont(font);
		homePage.setBounds(400, 666, 100, 30);
		
		JButton previous = new JButton("上一页");
		previous.setFont(font);
		previous.setBounds(510, 666, 100, 30);
		
		JButton next = new JButton("下一页");
		next.setFont(font);
		next.setBounds(620, 666, 100, 30);
		
		JButton endPage = new JButton("尾页");
		endPage.setFont(font);
		endPage.setBounds(730, 666, 100, 30);
		
		JTextField jtSkip = new JTextField();
		jtSkip.setFont(font);
		jtSkip.setBounds(840, 666, 50, 30);
		
		JButton btSkip = new JButton("跳转");
		btSkip.setFont(font);
		btSkip.setBounds(900, 666, 60, 30);
		
		/** button add Listener **/
		btSelect.addActionListener(this);
		btReset.addActionListener(this);
		homePage.addActionListener(this);
		previous.addActionListener(this);
		next.addActionListener(this);
		endPage.addActionListener(this);
		btSkip.addActionListener(this);
		jtSkip.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				super.keyPressed(e);
				btSkip.doClick();
			}
		});
		
		/** add jpanel **/
		this.add(jlTitle);
		this.add(jlDate);
		this.add(years);
		this.add(months);
		this.add(btSelect);
		this.add(btReset);
		this.add(jlIncome);
		this.add(jlMoney);
		this.add(jscrollPane);
		this.add(jlCount);
		this.add(homePage);
		this.add(previous);
		this.add(next);
		this.add(endPage);
		this.add(jtSkip);
		this.add(btSkip);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("查询")) {
			System.out.println("查询");
		}else if(e.getActionCommand().equals("重置")) {
			System.out.println("重置");
		}else if(e.getActionCommand().equals("首页")) {
			System.out.println("首页");
		}else if(e.getActionCommand().equals("上一页")) {
			System.out.println("上一页");
		}else if(e.getActionCommand().equals("下一页")) {
			System.out.println("下一页");
		}else if(e.getActionCommand().equals("尾页")) {
			System.out.println("尾页");
		}else if(e.getActionCommand().equals("跳转")) {
			System.out.println("跳转");
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
