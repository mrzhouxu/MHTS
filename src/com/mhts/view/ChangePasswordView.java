package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mhts.bean.Ticketer;
import com.mhts.controller.StaffController;

/**
 * 添加售票员面板
 * @author zhouxu
 *
 */
public class ChangePasswordView extends JPanel implements ActionListener{
	
	int id = 0;
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	boolean isClick = true;
	JTextField jtOldPassword = null,jtNewPassword = null,jtAgainPassword = null;
	JLabel jlOldPasswordErr = null,jlNewPasswordErr = null,jlAgainPasswordErr = null;
	boolean bOldPassword = false,bNewPassword = false,bAgainPassword = false;
	JButton btEnter = null;
	JLabel jlBreadCut = null;
	Ticketer ticketer = new Ticketer();
	
	
	/**
	 * 修改密码构造方法
	 */
	ChangePasswordView(Ticketer ticketer) {
		this.ticketer = ticketer;
		
		/** breadCut **/
		jlBreadCut = new JLabel("售票员界面 > 修改密码");
		jlBreadCut.setBounds(30, 10, 200, 30);
		jlBreadCut.setFont(font);
		
		/** content **/
		JLabel jlOldPassword = new JLabel("原密码：");
		jlOldPassword.setFont(font);
		jlOldPassword.setBounds(320, 60, 100, 30);
		
		jtOldPassword = new JTextField();
		jtOldPassword.setBounds(400, 60, 200, 30);
		
		jlOldPasswordErr = new JLabel();
		jlOldPasswordErr.setFont(font);
		jlOldPasswordErr.setForeground(Color.red);
		jlOldPasswordErr.setBounds(610, 60, 400, 30);
		
		JLabel jlNewPassword = new JLabel("新密码：");
		jlNewPassword.setFont(font);
		jlNewPassword.setBounds(320, 110, 100, 30);
		
		jtNewPassword = new JTextField();
		jtNewPassword.setBounds(400, 110, 200, 30);
		
		jlNewPasswordErr = new JLabel();
		jlNewPasswordErr.setFont(font);
		jlNewPasswordErr.setForeground(Color.red);
		jlNewPasswordErr.setBounds(610, 110, 400, 30);
		
		JLabel jlAgainPassword = new JLabel("再次输出：");
		jlAgainPassword.setFont(font);
		jlAgainPassword.setBounds(320, 160, 100, 30);
		
		jtAgainPassword = new JTextField();
		jtAgainPassword.setBounds(400, 160, 200, 30);
		
		jlAgainPasswordErr = new JLabel();
		jlAgainPasswordErr.setFont(font);
		jlAgainPasswordErr.setForeground(Color.red);
		jlAgainPasswordErr.setBounds(610, 160, 400, 30);
		
		/** button **/
		
		btEnter = new JButton("确认");
		btEnter.setFont(font);
		btEnter.setBounds(320, 330, 130, 30);
		
		JButton btReset = new JButton("重置");
		btReset.setFont(font);
		btReset.setBounds(470, 330, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btReset.addActionListener(this);
		jtOldPassword.addFocusListener(new LsyFocusListener(jtOldPassword, "^[a-z0-9_]{6,18}$", jlOldPasswordErr, "* 密码只能包含字母、数字和下划线，且长度限制在6~18位","原密码"));
		jtNewPassword.addFocusListener(new LsyFocusListener(jtNewPassword, "^[a-z0-9_]{6,18}$", jlNewPasswordErr, "* 密码只能包含字母、数字和下划线，且长度限制在6~18位","新密码"));
		jtAgainPassword.addFocusListener(new LsyFocusListener(jtAgainPassword, "^[a-z0-9_]{6,18}$", jlAgainPasswordErr, "* 密码只能包含字母、数字和下划线，且长度限制在6~18位","再次输入"));

		/** add jpanel **/
		this.add(jlBreadCut);
		this.add(jlOldPassword);
		this.add(jtOldPassword);
		this.add(jlOldPasswordErr);
		this.add(jlNewPassword);
		this.add(jtNewPassword);
		this.add(jlNewPasswordErr);
		this.add(jlAgainPassword);
		this.add(jtAgainPassword);
		this.add(jlAgainPasswordErr);
		this.add(btEnter);
		this.add(btReset);
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
	/**
	 * 判断密码是否正确
	 * @return
	 */
	public boolean judgePassword() {
		String id = this.ticketer.getId();
		String password = jtOldPassword.getText().replaceAll(" ", "");
		StaffController staffController = new StaffController();
		return staffController.judgePassword(id, password);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("确认")) {
			jtOldPassword.requestFocus();
			jtNewPassword.requestFocus();
			jtAgainPassword.requestFocus();
			btEnter.requestFocus();
			
			if(bOldPassword&&bNewPassword&&bAgainPassword&&jtNewPassword.getText().equals(jtAgainPassword.getText())) {
				StaffController staffController = new StaffController();
				boolean result = staffController.resetPassword(this.ticketer.getId(), jtNewPassword.getText().replaceAll(" ", ""));
				if(result) {
					JOptionPane.showMessageDialog(this, "成功", "修改成功！", JOptionPane.INFORMATION_MESSAGE);
					jtOldPassword.setText("");
					jtNewPassword.setText("");
					jtAgainPassword.setText("");
				}else {
					JOptionPane.showMessageDialog(this, "失败", "修改失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getActionCommand().equals("重置")) {
			jtOldPassword.setText("");
			jtNewPassword.setText("");
			jtAgainPassword.setText("");
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
			switch (this.name) {
				case "原密码":
					bOldPassword = false;
					break;
				case "新密码":
					bNewPassword = false;
					break;
				case "再次输入":
					bAgainPassword = false;
					break;
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
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
//	        System.out.println(jtOldPassword.getText()+" "+jtNewPassword.getText()+" "+jtAgainPassword.getText());
	        if(this.name.equals("原密码")&&!judgePassword()) {
	        	this.label.setForeground(Color.red);
	        	this.label.setText("* 密码错误");
	        	return;
	        }else if(this.name.equals("新密码")) {
	        	if(jtNewPassword.getText().equals(jtOldPassword.getText())) {
	        		this.label.setForeground(Color.red);
		        	this.label.setText("* 新密码与原密码相同，无需修改");
		        	return;
	        	}
	        }else if(this.name.equals("再次输入")) {
	        	if(!jtAgainPassword.getText().equals(jtNewPassword.getText())) {
	        		this.label.setForeground(Color.red);
		        	this.label.setText("* 两次密码输入不一致，请重新输入");
		        	return;
	        	}
	        }
	        
	        switch (this.name) {
		        case "原密码":
					bOldPassword = true;
					break;
				case "新密码":
					bNewPassword = true;
					break;
				case "再次输入":
					bAgainPassword = true;
					break;
			}
	        
		}
		
	}
	
}
