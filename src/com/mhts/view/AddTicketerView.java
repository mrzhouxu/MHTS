package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.model.AdminModel;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.ReDocumentListener;
import com.mhts.utils.TableHideColumn;

/**
 * 添加售票员面板
 * @author zhouxu
 *
 */
public class AddTicketerView extends JPanel implements ActionListener{
	
	int id = 0;
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	boolean isClick = true;
	JComboBox jcWindow = new JComboBox();
	JTextField jtName = null,jtIdCard = null,jtPhone = null,jtAccount = null;
	JLabel jlNameErr = null,jlIdCardErr = null,jlPhoneErr = null,jlAccountErr = null;
	boolean bName = false,bIdCard = false,bPhone = false,bAccount = false;
	ArrayList<Window> aWindows = null;
	boolean result = false;//注册成功
	Ticketer ticketer = null;
	JButton btEnter = null;
	JLabel jlBreadCut = null;
	
	/** 初始化 窗口下拉列表框  **/
	{
		AdminModel adminModel = new AdminModel();
		try {
			aWindows = adminModel.getWindow(0);
			for(int i=0,len = aWindows.size();i<len;i++) {
				jcWindow.addItem(aWindows.get(i).getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改售票员信息构造方法
	 * @param id
	 */
	AddTicketerView(int id){
		this();
		this.id = id;
		this.jtAccount.setEditable(false);
		this.jlAccountErr.setVisible(false);
		jlBreadCut.setText("管理员界面 > 员工管理 > 修改信息");
		
		/** 赋值 **/
		this.ticketer = this.getTicketerInfo(id);
		this.jtName.setText(this.ticketer.getName());
		this.jtIdCard.setText(this.ticketer.getId_card());
		this.jtPhone.setText(this.ticketer.getPhone());
		this.jtAccount.setText(this.ticketer.getAccount());
		for(int i=0,len = aWindows.size();i<len;i++) {
			if(aWindows.get(i).getId().equals(this.ticketer.getWindow())) {
				this.jcWindow.setSelectedIndex(i);
			}
		}
		
	}
	
	/**
	 * 添加售票员构造方法
	 */
	AddTicketerView() {
		/** breadCut **/
		jlBreadCut = new JLabel("管理员界面 > 员工管理 > 新增员工");
		jlBreadCut.setBounds(30, 10, 200, 30);
		jlBreadCut.setFont(font);
		
		/** content **/
		JLabel jlName = new JLabel("姓名：");
		jlName.setFont(font);
		jlName.setBounds(320, 60, 100, 30);
		
		jtName = new JTextField();
		jtName.setBounds(400, 60, 200, 30);
		
		jlNameErr = new JLabel();
		jlNameErr.setFont(font);
		jlNameErr.setForeground(Color.red);
		jlNameErr.setBounds(610, 60, 400, 30);
		
//		judge(jtName, jlNameErr);
		
		JLabel jlIdCard = new JLabel("身份证号：");
		jlIdCard.setFont(font);
		jlIdCard.setBounds(320, 110, 100, 30);
		
		jtIdCard = new JTextField();
		jtIdCard.setBounds(400, 110, 200, 30);
		
		jlIdCardErr = new JLabel();
		jlIdCardErr.setFont(font);
		jlIdCardErr.setForeground(Color.red);
		jlIdCardErr.setBounds(610, 110, 400, 30);
		
		JLabel jlPhone = new JLabel("手机号：");
		jlPhone.setFont(font);
		jlPhone.setBounds(320, 160, 100, 30);
		
		jtPhone = new JTextField();
		jtPhone.setBounds(400, 160, 200, 30);
		
		jlPhoneErr = new JLabel();
		jlPhoneErr.setFont(font);
		jlPhoneErr.setForeground(Color.red);
		jlPhoneErr.setBounds(610, 160, 400, 30);
		
		JLabel jlAccount = new JLabel("账号：");
		jlAccount.setFont(font);
		jlAccount.setBounds(320, 210, 100, 30);
		
		jtAccount = new JTextField();
		jtAccount.setBounds(400, 210, 200, 30);
		
		jlAccountErr = new JLabel();
		jlAccountErr.setFont(font);
		jlAccountErr.setForeground(Color.red);
		jlAccountErr.setBounds(610, 210, 400, 30);
		
		JLabel jlWindow = new JLabel("分配窗口：");
		jlWindow.setFont(font);
		jlWindow.setBounds(320, 260, 100, 30);
		
		jcWindow.setBounds(400, 260, 200, 30);
		
		/** button **/
		
		btEnter = new JButton("确认");
		btEnter.setFont(font);
		btEnter.setBounds(320, 330, 130, 30);
		
		JButton btBack = new JButton("返回");
		btBack.setFont(font);
		btBack.setBounds(470, 330, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btBack.addActionListener(this);
		jtName.addFocusListener(new LsyFocusListener(jtName, "^[\\u4e00-\\u9fa5]{2,10}$", jlNameErr, "* 只允许输入2~10位中文字符","姓名"));
		jtIdCard.addFocusListener(new LsyFocusListener(jtIdCard, "^\\d{17}[0-9Xx]$", jlIdCardErr, "* 请输入正确的证件信息，长度为18位","身份证号"));
		jtPhone.addFocusListener(new LsyFocusListener(jtPhone, "^1[3|4|5|7|8][0-9]\\d{8}$", jlPhoneErr, "* 请输入正确的手机号码，长度为11位","手机号"));
		jtAccount.addFocusListener(new LsyFocusListener(jtAccount, "^[a-z0-9_]{6,18}$", jlAccountErr, "* 账号只能包含字母、数字和下划线，且长度限制在6~18位","账号"));
		jtAccount.getDocument().addDocumentListener(new ReDocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO 自动生成的方法存根
				super.insertUpdate(e);
				jlAccountErr.setForeground(Color.red);
				Pattern pattern = Pattern.compile("^[a-z0-9_]{0,18}$");
		        if(!pattern.matcher(jtAccount.getText().replaceAll(" ", "")).matches()) {
		        	jlAccountErr.setText("* 账号只能包含字母、数字和下划线，且长度限制在6~18位");
		        	return;
		        }
				if(isExist()&&jtAccount.getText().length()>5) {
		        	jlAccountErr.setText("* 该账号已注册");
		        	return;
		        }else if(!isExist()&&jtAccount.getText().length()>5){
		        	jlAccountErr.setForeground(Color.green);
		        	jlAccountErr.setText("√");
		        }else if(jtAccount.getText().length()<=5){
		        	jlAccountErr.setText("");
		        }
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO 自动生成的方法存根
				super.removeUpdate(e);
				jlAccountErr.setForeground(Color.red);
				Pattern pattern = Pattern.compile("^[a-z0-9_]{0,18}$");
		        if(!pattern.matcher(jtAccount.getText().replaceAll(" ", "")).matches()) {
		        	jlAccountErr.setText("* 账号只能包含字母、数字和下划线，且长度限制在6~18位");
		        	return;
		        }
				if(isExist()&&jtAccount.getText().length()>5) {
		        	jlAccountErr.setText("* 该账号已注册");
		        	return;
		        }else if(!isExist()&&jtAccount.getText().length()>5){
		        	jlAccountErr.setForeground(Color.green);
		        	jlAccountErr.setText("√");
		        }else if(jtAccount.getText().length()<=5){
		        	jlAccountErr.setText("* 账号只能包含字母、数字和下划线，且长度限制在6~18位");
		        }
			}
		});
		
		/** add jpanel **/
		this.add(jlBreadCut);
		this.add(jlName);
		this.add(jtName);
		this.add(jlNameErr);
		this.add(jlIdCard);
		this.add(jtIdCard);
		this.add(jlIdCardErr);
		this.add(jlPhone);
		this.add(jtPhone);
		this.add(jlPhoneErr);
		this.add(jlAccount);
		this.add(jtAccount);
		this.add(jlAccountErr);
		this.add(jlWindow);
		this.add(jcWindow);
		this.add(btEnter);
		this.add(btBack);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
	/**
	 * 获得售票员信息
	 * @param id
	 * @return
	 */
	public Ticketer getTicketerInfo(int id) {
		Ticketer ticketer = new Ticketer();
		AdminController adminModel = new AdminController();
		return adminModel.getTicketer(id);
	}
	
	/**
	 * 添加售票员的时候判断账号是否存在
	 * @return
	 */
	public boolean isExist() {
		String account = jtAccount.getText().replaceAll(" ", "");
		AdminController adminController = new AdminController();
		return adminController.getAccount(account);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("确认")) {
			jtName.requestFocus();
			jtIdCard.requestFocus();
			jtPhone.requestFocus();
			jtAccount.requestFocus();
			btEnter.requestFocus();
			System.out.println(bName+" "+bIdCard+" "+bPhone+" "+bAccount+" "+id);
			
			if(bName&&bIdCard&&bPhone&&bAccount&&id==0) {
				ticketer = new Ticketer();
				ticketer.setName(jtName.getText().replaceAll(" ", ""));
				ticketer.setId_card(jtIdCard.getText().replaceAll(" ", ""));
				ticketer.setPhone(jtPhone.getText().replaceAll(" ", ""));
				ticketer.setAccount(jtAccount.getText().replaceAll(" ", ""));
				for(int i=0,len = aWindows.size();i<len;i++) {
					if(aWindows.get(i).getName().equals(jcWindow.getSelectedItem())) {
						ticketer.setWindow(aWindows.get(i).getId());
						break;
					};
				}
				AdminController adminController = new AdminController();
				result = adminController.addTicketer(ticketer);
				if(result) {
					int isEnter = JOptionPane.showConfirmDialog(this, "添加成功,是否继续添加？", "成功", JOptionPane.YES_NO_OPTION);
					if(isEnter==JOptionPane.YES_OPTION) {
						bName = false;bIdCard=false;bPhone=false;bAccount=false;
						jtName.setText("");
						jtIdCard.setText("");
						jtPhone.setText("");
						jtAccount.setText("");
						jlNameErr.setText("");
						jlIdCardErr.setText("");
						jlPhoneErr.setText("");
						jlAccountErr.setText("");
						jcWindow.setSelectedIndex(0);
					}else if(isEnter==JOptionPane.NO_OPTION){
						this.removeAll();
						this.add(new StaffManagementView());
						this.repaint();
						this.validate();
					}; 
				}else {
					JOptionPane.showMessageDialog(this, "失败", "添加失败！", JOptionPane.ERROR_MESSAGE);
				}
			}else if(bName&&bIdCard&&bPhone&&id!=0){
				ticketer.setName(jtName.getText().replaceAll(" ", ""));
				ticketer.setId_card(jtIdCard.getText().replaceAll(" ", ""));
				ticketer.setPhone(jtPhone.getText().replaceAll(" ", ""));
				for(int i=0,len = aWindows.size();i<len;i++) {
					if(aWindows.get(i).getName().equals(jcWindow.getSelectedItem())) {
						ticketer.setWindow(aWindows.get(i).getId());
						break;
					};
				}
				AdminController adminController = new AdminController();
				result = adminController.editTicketer(ticketer);
				if(result) {
					//更新成功
					JOptionPane.showMessageDialog(this, "成功", "更新成功！", JOptionPane.INFORMATION_MESSAGE);
					this.removeAll();
					this.add(new StaffManagementView());
					this.repaint();
					this.validate();
				}else {
					//更新失败
					JOptionPane.showMessageDialog(this, "失败", "更新失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
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
	
	/**
	 * 
	 * 文本框失去获得焦点监听
	 *
	 */
	class LsyFocusListener implements FocusListener{
		JTextField input = null ;
		String regex = null;
		JLabel label = null;
		String errMsg = null;
		String name = null;
		
		LsyFocusListener(JTextField input,String regex,JLabel label,String errMsg,String name) {
			this.input = input;
			this.regex = regex;
			this.label = label;
			this.errMsg = errMsg;
			this.name = name;
		}

		@Override
		public void focusGained(FocusEvent e) {
			System.out.println("2");
			// TODO 自动生成的方法存根
			switch (this.name) {
				case "姓名":
					bName = false;
					break;
				case "身份证号":
					bIdCard = false;
					break;
				case "手机号":
					bPhone = false;
					break;
				case "账号":
					bAccount = false;
					break;
			}
//			this.label.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			System.out.println("1");
			
			this.label.setForeground(Color.red);
			
			if(this.input.getText().replaceAll(" ", "").equals("")) {
				this.label.setText("* 不能为空");
				return;
			}
			Pattern pattern = Pattern.compile(this.regex);
	        String str = "";
	        if(!pattern.matcher(this.input.getText().replaceAll(" ", "")).matches()) {
	        	str = this.errMsg;
	        	this.label.setText(str);
	        	return;
	        }else {
	        	this.label.setForeground(Color.green);
	        	this.label.setText("√");
	        }
	        if(this.name.equals("账号")&&isExist()&&!result) {
	        	this.label.setForeground(Color.red);
	        	this.label.setText("* 该账号已注册");
	        	return;
	        }
	        
	        switch (this.name) {
				case "姓名":
					bName = true;
					break;
				case "身份证号":
					bIdCard = true;
					break;
				case "手机号":
					bPhone = true;
					break;
				case "账号":
					bAccount = true;
					break;
			}
	        
		}
		
	}
	
}
