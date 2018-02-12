package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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

import com.mhts.bean.Ticketer;
import com.mhts.controller.AdminController;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.TableHideColumn;

/**
 * @author zhouxu
 * 下面用的DefaultTableModel都是自己写的类
 */
public class StaffManagementView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	JTable table = null;
	boolean isClick = true;
	DefaultTableModel tableModel = null;
	Vector tableHeader = null;
	Vector datas = null;
	int count = 0;
	double money = 0;
	JButton homePage = null,previous = null,next = null,endPage = null;
	JLabel jlCount = null;
	JComboBox key = null;
	JTextField val = null;
	JTextField jtSkip = new JTextField();
	int page = 1,num = 20;
	String[] tableHeaderStr = {"序号","id","姓名","身份证号","手机号","账号","所属窗口"};
	
	StaffManagementView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("管理员界面 > 员工管理");
		jlTitle.setBounds(30, 10, 150, 30);
		jlTitle.setFont(font);
		
		/** select **/
		JLabel jlDate = new JLabel("查询条件");
		jlDate.setBounds(30, 50, 100, 30);
		jlDate.setFont(font);
		
		key = new JComboBox();
		key.addItem("全部");
		key.addItem("姓名");
		key.addItem("身份证号");
		key.addItem("手机号");
		key.setBounds(100, 50, 90, 30);
		
		val = new JTextField();
		val.setBounds(200, 50, 150, 30);
		
		JButton btSelect = new JButton("查询");
		btSelect.setBounds(360, 50, 80, 30);
		
		JButton btReset = new JButton("重置");
		btReset.setBounds(450, 50, 80, 30);
		
		JButton btAdd = new JButton("新增");
		btAdd.setBounds(540, 50, 80, 30);

		JButton btUpdate = new JButton("修改");
		btUpdate.setBounds(630, 50, 80, 30);
		
		JButton btDel = new JButton("删除");
		btDel.setBounds(720, 50, 80, 30);
		
		JButton btResetPassword = new JButton("密码重置");
		btResetPassword.setBounds(810, 50, 150, 30);
		
		
		/** 得到数据  **/
		select();//初始化表格数据
		
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
		
		if(page == Math.ceil(Double.valueOf(count)/Double.valueOf(num))) {
			next.setEnabled(false);
			endPage.setEnabled(false);
		}
		
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
		btAdd.addActionListener(this);
		btUpdate.addActionListener(this);
		btDel.addActionListener(this);
		btResetPassword.addActionListener(this);
		val.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				super.keyPressed(e);
				if(e.getKeyCode() == 10) {
					btSelect.doClick();
				}
			}
		});
		
		/** add jpanel **/
		this.add(jlTitle);
		this.add(jlDate);
		this.add(key);
		this.add(val);
		this.add(btSelect);
		this.add(btReset);
		this.add(jscrollPane);
		this.add(jlCount);
		this.add(homePage);
		this.add(previous);
		this.add(next);
		this.add(endPage);
		this.add(jtSkip);
		this.add(btSkip);
		this.add(btAdd);
		this.add(btUpdate);
		this.add(btDel);
		this.add(btResetPassword);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
	public void select () {
		String key = this.key.getSelectedItem().toString();
		String val = this.val.getText();
		switch (key) {
			case "姓名":
				key = "name";
				break;
			case "身份证号":
				key = "id_card";
				break;
			case "手机号":
				key = "phone";
				break;
			case "全部":
				key = "id";
				val = "";
				break;
		}
		String pageSkip = !jtSkip.getText().replaceAll(" ", "").equals("")?jtSkip.getText().replaceAll(" ", ""):page+"";
		int num = this.num;
		AdminController adminController = new AdminController();
		datas = adminController.getTicketer(key, val, pageSkip, num+"");
		count = adminController.getTicketerCount(key, val);
		if(count == 0)page=0;
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
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("重置")) {
			key.setSelectedIndex(0);
			val.setText(null);
			jtSkip.setText(null);
			page = 1;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("首页")) {
			jtSkip.setText(null);
			page = 1;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("上一页")) {
			jtSkip.setText(null);
			page--;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("下一页")) {
			jtSkip.setText(null);
			page++;
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
			jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
		}else if(e.getActionCommand().equals("尾页")) {
			jtSkip.setText(null);
			page = (int) Math.ceil(Double.valueOf(count)/Double.valueOf(num));
			select();
			tableModel=new DefaultTableModel(datas, tableHeader);
			table.setModel(tableModel);
			TableHideColumn.hideColumn(table,1);
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
				jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
			}
			
		}else if(e.getActionCommand().equals("新增")) {
			this.removeAll();
			this.add(new AddTicketerView());
			this.repaint();
			this.validate();
		}else if(e.getActionCommand().equals("修改")) {
			System.out.println("修改");
		}else if(e.getActionCommand().equals("删除")) {
			ArrayList<Ticketer> selectTicketer = new ArrayList<Ticketer>();
			int selLen = table.getSelectedRowCount();
			if(JOptionPane.showConfirmDialog(this, "将要删除选中的"+selLen+"条记录", "是否继续", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				int [] arr = new int[selLen];
				for(int i=0;i<selLen;i++) {
					int row = table.getSelectedRows()[i];
					Ticketer tTemp = new Ticketer();
					tTemp.setId(table.getValueAt(row, 1).toString());
					tTemp.setName(table.getValueAt(row, 2).toString());
					tTemp.setId_card(table.getValueAt(row, 3).toString());
					tTemp.setPhone(table.getValueAt(row, 4).toString());
					tTemp.setAccount(table.getValueAt(row, 5).toString());
					tTemp.setWindow(table.getValueAt(row, 6).toString());
					selectTicketer.add(tTemp);
				}
				AdminController adminController = new AdminController();
				boolean result = adminController.delTicketer(selectTicketer);
				if(result) {
					key.setSelectedIndex(0);
					val.setText(null);
					jtSkip.setText(null);
					page = 1;
					select();
					tableModel=new DefaultTableModel(datas, tableHeader);
					table.setModel(tableModel);
					TableHideColumn.hideColumn(table,1);
					jlCount.setText("共 "+count+" 条记录       第 "+page+" 页 / 共 "+(int)Math.ceil(Double.valueOf(count)/Double.valueOf(num))+" 页");
				
					JOptionPane.showMessageDialog(this, "成功", "删除成功！", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "失败", "删除失败！", JOptionPane.ERROR_MESSAGE);
				}
			}; 
		}else if(e.getActionCommand().equals("密码重置")) {
			ArrayList<Ticketer> selectTicketer = new ArrayList<Ticketer>();
			int selLen = table.getSelectedRowCount();
			if(JOptionPane.showConfirmDialog(this, "为选中的"+selLen+"条记录密码重置为666666", "是否继续", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				int [] arr = new int[selLen];
				for(int i=0;i<selLen;i++) {
					int row = table.getSelectedRows()[i];
					Ticketer tTemp = new Ticketer();
					tTemp.setId(table.getValueAt(row, 1).toString());
					tTemp.setName(table.getValueAt(row, 2).toString());
					tTemp.setId_card(table.getValueAt(row, 3).toString());
					tTemp.setPhone(table.getValueAt(row, 4).toString());
					tTemp.setAccount(table.getValueAt(row, 5).toString());
					tTemp.setWindow(table.getValueAt(row, 6).toString());
					selectTicketer.add(tTemp);
				}
				AdminController adminController = new AdminController();
				boolean result = adminController.resetPassword(selectTicketer);
				if(result) {
					JOptionPane.showMessageDialog(this, "成功", "重置密码成功！", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "失败", "重置密码失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		homePage.setEnabled(true);
		previous.setEnabled(true);
		next.setEnabled(true);
		endPage.setEnabled(true);
		if(page <= 1) {
			homePage.setEnabled(false);
			previous.setEnabled(false);
		}
		if(page >= Math.ceil(Double.valueOf(count)/Double.valueOf(num))) {
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
