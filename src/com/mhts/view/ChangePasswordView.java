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
 * ������ƱԱ���
 * @author zhouxu
 *
 */
public class ChangePasswordView extends JPanel implements ActionListener{
	
	int id = 0;
	
	Font font = new Font("����", Font.PLAIN, 12);
	boolean isClick = true;
	JTextField jtOldPassword = null,jtNewPassword = null,jtAgainPassword = null;
	JLabel jlOldPasswordErr = null,jlNewPasswordErr = null,jlAgainPasswordErr = null;
	boolean bOldPassword = false,bNewPassword = false,bAgainPassword = false;
	JButton btEnter = null;
	JLabel jlBreadCut = null;
	Ticketer ticketer = new Ticketer();
	
	
	/**
	 * �޸����빹�췽��
	 */
	ChangePasswordView(Ticketer ticketer) {
		this.ticketer = ticketer;
		
		/** breadCut **/
		jlBreadCut = new JLabel("��ƱԱ���� > �޸�����");
		jlBreadCut.setBounds(30, 10, 200, 30);
		jlBreadCut.setFont(font);
		
		/** content **/
		JLabel jlOldPassword = new JLabel("ԭ���룺");
		jlOldPassword.setFont(font);
		jlOldPassword.setBounds(320, 60, 100, 30);
		
		jtOldPassword = new JTextField();
		jtOldPassword.setBounds(400, 60, 200, 30);
		
		jlOldPasswordErr = new JLabel();
		jlOldPasswordErr.setFont(font);
		jlOldPasswordErr.setForeground(Color.red);
		jlOldPasswordErr.setBounds(610, 60, 400, 30);
		
		JLabel jlNewPassword = new JLabel("�����룺");
		jlNewPassword.setFont(font);
		jlNewPassword.setBounds(320, 110, 100, 30);
		
		jtNewPassword = new JTextField();
		jtNewPassword.setBounds(400, 110, 200, 30);
		
		jlNewPasswordErr = new JLabel();
		jlNewPasswordErr.setFont(font);
		jlNewPasswordErr.setForeground(Color.red);
		jlNewPasswordErr.setBounds(610, 110, 400, 30);
		
		JLabel jlAgainPassword = new JLabel("�ٴ������");
		jlAgainPassword.setFont(font);
		jlAgainPassword.setBounds(320, 160, 100, 30);
		
		jtAgainPassword = new JTextField();
		jtAgainPassword.setBounds(400, 160, 200, 30);
		
		jlAgainPasswordErr = new JLabel();
		jlAgainPasswordErr.setFont(font);
		jlAgainPasswordErr.setForeground(Color.red);
		jlAgainPasswordErr.setBounds(610, 160, 400, 30);
		
		/** button **/
		
		btEnter = new JButton("ȷ��");
		btEnter.setFont(font);
		btEnter.setBounds(320, 330, 130, 30);
		
		JButton btReset = new JButton("����");
		btReset.setFont(font);
		btReset.setBounds(470, 330, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btReset.addActionListener(this);
		jtOldPassword.addFocusListener(new LsyFocusListener(jtOldPassword, "^[a-z0-9_]{6,18}$", jlOldPasswordErr, "* ����ֻ�ܰ�����ĸ�����ֺ��»��ߣ��ҳ���������6~18λ","ԭ����"));
		jtNewPassword.addFocusListener(new LsyFocusListener(jtNewPassword, "^[a-z0-9_]{6,18}$", jlNewPasswordErr, "* ����ֻ�ܰ�����ĸ�����ֺ��»��ߣ��ҳ���������6~18λ","������"));
		jtAgainPassword.addFocusListener(new LsyFocusListener(jtAgainPassword, "^[a-z0-9_]{6,18}$", jlAgainPasswordErr, "* ����ֻ�ܰ�����ĸ�����ֺ��»��ߣ��ҳ���������6~18λ","�ٴ�����"));

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
	 * �ж������Ƿ���ȷ
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
		// TODO �Զ����ɵķ������
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("ȷ��")) {
			jtOldPassword.requestFocus();
			jtNewPassword.requestFocus();
			jtAgainPassword.requestFocus();
			btEnter.requestFocus();
			
			if(bOldPassword&&bNewPassword&&bAgainPassword&&jtNewPassword.getText().equals(jtAgainPassword.getText())) {
				StaffController staffController = new StaffController();
				boolean result = staffController.resetPassword(this.ticketer.getId(), jtNewPassword.getText().replaceAll(" ", ""));
				if(result) {
					JOptionPane.showMessageDialog(this, "�ɹ�", "�޸ĳɹ���", JOptionPane.INFORMATION_MESSAGE);
					jtOldPassword.setText("");
					jtNewPassword.setText("");
					jtAgainPassword.setText("");
				}else {
					JOptionPane.showMessageDialog(this, "ʧ��", "�޸�ʧ�ܣ�", JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getActionCommand().equals("����")) {
			jtOldPassword.setText("");
			jtNewPassword.setText("");
			jtAgainPassword.setText("");
		}
		
		/** �߳� ��ֹ��ť��� **/
		new Thread() { 
			@Override
			public void run() {
				super.run();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				isClick = !isClick;
				this.stop();
			}
		}.start();
	}
	
	/**
	 * 
	 * �ı���ʧȥ��ý������
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
				case "ԭ����":
					bOldPassword = false;
					break;
				case "������":
					bNewPassword = false;
					break;
				case "�ٴ�����":
					bAgainPassword = false;
					break;
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			this.label.setForeground(Color.red);
			
			if(this.input.getText().replaceAll(" ", "").equals("")) {
				this.label.setText("* ����Ϊ��");
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
	        	this.label.setText("��");
	        }
//	        System.out.println(jtOldPassword.getText()+" "+jtNewPassword.getText()+" "+jtAgainPassword.getText());
	        if(this.name.equals("ԭ����")&&!judgePassword()) {
	        	this.label.setForeground(Color.red);
	        	this.label.setText("* �������");
	        	return;
	        }else if(this.name.equals("������")) {
	        	if(jtNewPassword.getText().equals(jtOldPassword.getText())) {
	        		this.label.setForeground(Color.red);
		        	this.label.setText("* ��������ԭ������ͬ�������޸�");
		        	return;
	        	}
	        }else if(this.name.equals("�ٴ�����")) {
	        	if(!jtAgainPassword.getText().equals(jtNewPassword.getText())) {
	        		this.label.setForeground(Color.red);
		        	this.label.setText("* �����������벻һ�£�����������");
		        	return;
	        	}
	        }
	        
	        switch (this.name) {
		        case "ԭ����":
					bOldPassword = true;
					break;
				case "������":
					bNewPassword = true;
					break;
				case "�ٴ�����":
					bAgainPassword = true;
					break;
			}
	        
		}
		
	}
	
}