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
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.mhts.controller.AdminController;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.TableHideColumn;

/**
 * @author zhouxu
 * 下面用的DefaultTableModel都是自己写的类
 */
public class BusinessStatusView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	JTable table = null;
	boolean isClick = true;
	DefaultTableModel tableModel = null;
	Vector tableHeader = null;
	Vector datas = null;
	int count = 0;
	double money = 0;
	JButton homePage = null,previous = null,next = null,endPage = null;
	JLabel jlMoney = null;
	JLabel jlCount = null;
	JComboBox years=null,months=null;
	JTextField jtSkip = new JTextField();
	int page = 1,num = 20;
	String[] tableHeaderStr = {"序号","id","身份证号","票类型","窗口号","购票时间"};
	
	BusinessStatusView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("管理员界面 > 营业状况");
		jlTitle.setBounds(30, 10, 150, 30);
		jlTitle.setFont(font);
		
		/** select **/
		JLabel jlDate = new JLabel("请选择日期：");
		jlDate.setBounds(30, 50, 100, 30);
		jlDate.setFont(font);
		
		years = new JComboBox();
		years.addItem("全部");
		for(int i=Calendar.getInstance().get(Calendar.YEAR);i>=2000;i--) {
			years.addItem(i+"年");
		}
		years.setBounds(110, 50, 80, 30);
		
		months = new JComboBox();
		months.addItem("全部");
		for(int i=1;i<=12;i++) {
			months.addItem(i+"月");
		}
		months.setBounds(200, 50, 80, 30);
		
		JButton btSelect = new JButton("查询");
		btSelect.setBounds(300, 50, 80, 30);
		
		JButton btReset = new JButton("重置");
		btReset.setBounds(390, 50, 80, 30);
		
		/** 得到数据  **/
		select();//初始化表格数据
		
		/** income **/
		JLabel jlIncome = new JLabel("收入(单位元):");
		jlIncome.setFont(font);
		jlIncome.setBounds(700, 50, 100, 30);
		
		jlMoney = new JLabel(money+" 元");
		jlMoney.setFont(new Font("宋体", Font.PLAIN, 30));
		jlMoney.setBounds(780, 50, 200, 30);
		
		/** table **/
		tableHeader = new Vector();
		for(int i=0,len=tableHeaderStr.length;i<len;i++) {
			tableHeader.add(tableHeaderStr[i]);
		}
        
		tableModel=new DefaultTableModel(datas, tableHeader);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);//表头不可拖动
		table.getTableHeader().setResizingAllowed(false);//列大小不可改变
		table.setRowHeight(26);//指定每一行的行高50
		table.setModel(tableModel);
		TableHideColumn.hideColumn(table,1);
		JScrollPane jscrollPane = new JScrollPane(table);
		jscrollPane.setBounds(30, 100, 930, 548);
		
		/** paging **/
		jlCount = new JLabel("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		jlCount.setFont(font);
		jlCount.setBounds(30, 666, 300, 30);

		
		homePage = new JButton("首页");
		homePage.setFont(font);
		homePage.setBounds(400, 666, 100, 30);
		homePage.setEnabled(false);
		
		previous = new JButton("上一页");
		previous.setFont(font);
		previous.setBounds(510, 666, 100, 30);
		previous.setEnabled(false);
		
		next = new JButton("下一页");
		next.setFont(font);
		next.setBounds(620, 666, 100, 30);
		
		endPage = new JButton("尾页");
		endPage.setFont(font);
		endPage.setBounds(730, 666, 100, 30);
		
//		jtSkip = new JTextField();
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
				if(e.getKeyCode() == 10) {
					btSkip.doClick();
				}
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
	
	public void select () {
		String year = years.getSelectedItem().toString();
		String month = months.getSelectedItem().toString();
		String pageSkip = !jtSkip.getText().replaceAll(" ", "").equals("")?jtSkip.getText().replaceAll(" ", ""):page+"";
		int num = this.num;
		AdminController adminController = new AdminController();
		datas = adminController.selRecord(year, month, pageSkip, num);
		count = adminController.selRecordCount(year, month);
		money = adminController.selRecordMoney(year, month);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("查询")) {
			jtSkip.setText(null);
			page = 1;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("重置")) {
			years.setSelectedIndex(0);
			months.setSelectedIndex(0);
			jtSkip.setText(null);
			page = 1;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("首页")) {
			jtSkip.setText(null);
			page = 1;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("上一页")) {
//			if(page<=1) {
//				return;
//			}
			jtSkip.setText(null);
			page--;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("下一页")) {
//			if(page==Math.ceil(Double.valueOf(count)/Double.valueOf(num))) {
//				return;
//			}
			jtSkip.setText(null);
			page++;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("尾页")) {
			jtSkip.setText(null);
			page = (int) Math.ceil(Double.valueOf(count)/Double.valueOf(num));
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlMoney.setText(money+" 元");
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("跳转")) {
			if(jtSkip.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "请输入正确的页码！", "错误", JOptionPane.ERROR_MESSAGE);
			}else if(Integer.valueOf(jtSkip.getText())<1 || Integer.valueOf(jtSkip.getText())>(int) Math.ceil(Double.valueOf(count)/Double.valueOf(num))) {
				JOptionPane.showMessageDialog(this, "请输入正确的页码！", "错误", JOptionPane.ERROR_MESSAGE);
			}else {
				page = Integer.valueOf(jtSkip.getText());
				select();
				tableModel=new DefaultTableModel(datas, tableHeader);
				table.setModel(tableModel);
				TableHideColumn.hideColumn(table,1);
				jlMoney.setText(money+" 元");
				jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
			}
			
		}
		
		homePage.setEnabled(true);
		previous.setEnabled(true);
		next.setEnabled(true);
		endPage.setEnabled(true);
		if(page == 1) {
			homePage.setEnabled(false);
			previous.setEnabled(false);
		}else if(page == Math.ceil(Double.valueOf(count)/Double.valueOf(num))) {
			next.setEnabled(false);
			endPage.setEnabled(false);
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
