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
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.TableHideColumn;

/**
 * @author zhouxu
 * 下面用的DefaultTableModel都是自己写的类
 */
public class WindowManagementView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	JTable table = null;
	boolean isClick = true;
	DefaultTableModel tableModel = null;
	Vector tableHeader = null;
	Vector datas = null;
	String[] tableHeaderStr = {"序号","id","窗口名称","工作人数"};
	
	WindowManagementView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("管理员界面 > 窗口管理");
		jlTitle.setBounds(30, 10, 150, 30);
		jlTitle.setFont(font);
		
		/** 操作 **/
		JButton btAdd = new JButton("新增");
		btAdd.setBounds(350, 50, 80, 30);

		JButton btUpdate = new JButton("修改");
		btUpdate.setBounds(450, 50, 80, 30);
		
		JButton btDel = new JButton("删除");
		btDel.setBounds(550, 50, 80, 30);
		
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
		
		/** button add Listener **/
		btAdd.addActionListener(this);
		btUpdate.addActionListener(this);
		btDel.addActionListener(this);
		
		/** add jpanel **/
		this.add(jlTitle);
		this.add(jscrollPane);
		this.add(btAdd);
		this.add(btUpdate);
		this.add(btDel);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
	public void select () {
		AdminController adminController = new AdminController();
		datas = adminController.getWindow(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("新增")) {
			this.removeAll();
			this.add(new AddWindowView());
			this.repaint();
			this.validate();
		}else if(e.getActionCommand().equals("修改")) {
			int selLen = table.getSelectedRowCount();
			if(selLen < 1) {//不能删
				//请选择要修改的记录
				JOptionPane.showMessageDialog(this, "亲爱的，别着急呀，先选择要修改的记录吧！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
			}else if(selLen > 1) {
				//老板拖欠工资，多条修改功能不打算开发，请暂先一条一条修改
				JOptionPane.showMessageDialog(this, "老板拖欠工资，多条修改功能不打算开发，请暂先一条一条修改！", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				this.removeAll();
				this.add(new AddWindowView(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 1).toString())));
				this.repaint();
				this.validate();
			}
		}else if(e.getActionCommand().equals("删除")) {
			ArrayList<Window> selectTicketer = new ArrayList<Window>();
			int selLen = table.getSelectedRowCount();
			if(selLen < 1) {//不能删
				
			}else if(JOptionPane.showConfirmDialog(this, "将要删除选中的"+selLen+"条记录", "是否继续", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				boolean isDel = true;
				int [] arr = new int[selLen];
				for(int i=0;i<selLen;i++) {
					int row = table.getSelectedRows()[i];
					Window tTemp = new Window();
					tTemp.setId(table.getValueAt(row, 1).toString());
					tTemp.setName(table.getValueAt(row, 2).toString());
					if(Integer.valueOf(table.getValueAt(row, 3).toString())>0) {
						isDel = false;
					}
					selectTicketer.add(tTemp);
				}
				if(isDel) {
					AdminController adminController = new AdminController();
					boolean result = adminController.delWindow(selectTicketer);
					if(result) {
						select();
						tableModel=new DefaultTableModel(datas, tableHeader);
						table.setModel(tableModel);
						TableHideColumn.hideColumn(table,1);
						JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(this, "删除失败！", "失败", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "所选窗口还存在工作人员，不允许删除！", "失败", JOptionPane.ERROR_MESSAGE);
				}
				
			}; 
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
