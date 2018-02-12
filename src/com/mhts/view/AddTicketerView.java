package com.mhts.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.model.AdminModel;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.TableHideColumn;

public class AddTicketerView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	boolean isClick = true;
	JComboBox jcWindow = new JComboBox();
	JTextField jtName = null,jtIdCard = null,jtPhone = null,jtAccount = null;
	
	{
		AdminModel adminModel = new AdminModel();
		try {
			ArrayList<Window> aWindows = adminModel.getWindow();
			for(int i=0,len = aWindows.size();i<len;i++) {
				jcWindow.addItem(aWindows.get(i).getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	AddTicketerView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("管理员界面 > 员工管理 > 新增员工");
		jlTitle.setBounds(30, 10, 200, 30);
		jlTitle.setFont(font);
		
		/** content **/
		JLabel jlName = new JLabel("姓名：");
		jlName.setFont(font);
		jlName.setBounds(320, 60, 100, 30);
		
		JTextField jtName = new JTextField();
		jtName.setBounds(400, 60, 200, 30);
		
		JLabel jlIdCard = new JLabel("身份证号：");
		jlIdCard.setFont(font);
		jlIdCard.setBounds(320, 110, 100, 30);
		
		JTextField jtIdCard = new JTextField();
		jtIdCard.setBounds(400, 110, 200, 30);
		
		JLabel jlPhone = new JLabel("手机号：");
		jlPhone.setFont(font);
		jlPhone.setBounds(320, 160, 100, 30);
		
		JTextField jtPhone = new JTextField();
		jtPhone.setBounds(400, 160, 200, 30);
		
		JLabel jlAccount = new JLabel("账号：");
		jlAccount.setFont(font);
		jlAccount.setBounds(320, 210, 100, 30);
		
		JTextField jtAccount = new JTextField();
		jtAccount.setBounds(400, 210, 200, 30);
		
		JLabel jlWindow = new JLabel("分配窗口：");
		jlWindow.setFont(font);
		jlWindow.setBounds(320, 260, 100, 30);
		
		jcWindow.setBounds(400, 260, 200, 30);
		
		/** button **/
		
		JButton btEnter = new JButton("确认");
		btEnter.setFont(font);
		btEnter.setBounds(320, 330, 130, 30);
		
		JButton btBack = new JButton("返回");
		btBack.setFont(font);
		btBack.setBounds(470, 330, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btBack.addActionListener(this);
		
		
		/** add jpanel **/
		this.add(jlTitle);
		this.add(jlName);
		this.add(jtName);
		this.add(jlIdCard);
		this.add(jtIdCard);
		this.add(jlPhone);
		this.add(jtPhone);
		this.add(jlAccount);
		this.add(jtAccount);
		this.add(jlWindow);
		this.add(jcWindow);
		this.add(btEnter);
		this.add(btBack);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
//	public boolean judge() {
		/*String name = jtName.getText();
		String idCard = jtIdCard.getText();
		String phone = jtPhone.getText();
		String account = jtAccount.getText();
		
		if(name.replaceAll(" ", "").equals("")) {
			
		}
		
		
		return true;*/
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("确认")) {
			
		}else if(e.getActionCommand().equals("返回")) {
			this.removeAll();
			this.add(new StaffManagementView());
			this.repaint();
			this.validate();
		}
		
		
		/** 线程 防止按钮多点 **/
		new Thread() { 
			@Override
			public void run() {
				super.run();
				try {
					sleep(1000);
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
