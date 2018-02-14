package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.controller.StaffController;
import com.mhts.model.AdminModel;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.ReDocumentListener;
import com.mhts.utils.TableHideColumn;

/**
 * 添加售票员面板
 * @author zhouxu
 *
 */
public class AddRecordView extends JPanel implements ActionListener{
	
	Font font = new Font("宋体", Font.PLAIN, 12);
	boolean isClick = true;
	JComboBox jcTicket = new JComboBox();
	JTextField jtIdCard = null,jtPrice = new JTextField();
	JLabel jlIdCardErr = null;
	boolean bIdCard = false;
	ArrayList<Ticket> aTickets = null;
	boolean result = false;//出票成功
	Ticketer ticketer = null;
	JButton btEnter = null;
	JLabel jlBreadCut = null;
	
	/** 初始化 窗口下拉列表框  **/
	{
		jcTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtPrice.setText(getTicketPrice(jcTicket.getSelectedItem().toString()));
			}
		});
		AdminModel adminModel = new AdminModel();
		try {
			aTickets = adminModel.getTicket(0);
			for(int i=0,len = aTickets.size();i<len;i++) {
				jcTicket.addItem(aTickets.get(i).getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 添加售票员构造方法
	 */
	AddRecordView(Ticketer ticketer) {
		this.ticketer = ticketer;
		
		/** breadCut **/
		jlBreadCut = new JLabel("售票员界面 > 售票功能 ");
		jlBreadCut.setBounds(30, 10, 200, 30);
		jlBreadCut.setFont(font);
		
		/** content **/
		JLabel jlIdCard = new JLabel("身份证号：");
		jlIdCard.setFont(font);
		jlIdCard.setBounds(320, 60, 100, 30);
		
		jtIdCard = new JTextField();
		jtIdCard.setBounds(400, 60, 200, 30);
		
		jlIdCardErr = new JLabel();
		jlIdCardErr.setFont(font);
		jlIdCardErr.setForeground(Color.red);
		jlIdCardErr.setBounds(610, 60, 400, 30);
		
		JLabel jlTicket = new JLabel("票类型：");
		jlTicket.setFont(font);
		jlTicket.setBounds(320, 110, 100, 30);
		
		jcTicket.setBounds(400, 110, 200, 30);
		
		
		JLabel jlPrice = new JLabel("票价：");
		jlPrice.setFont(font);
		jlPrice.setBounds(320, 160, 100, 30);
		
		jtPrice.setBounds(400, 160, 200, 30);
		jtPrice.setEditable(false);
		
		
		
		/** button **/
		
		btEnter = new JButton("出票");
		btEnter.setFont(font);
		btEnter.setBounds(320, 230, 130, 30);
		
		JButton btReset = new JButton("重置");
		btReset.setFont(font);
		btReset.setBounds(470, 230, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btReset.addActionListener(this);
		jtIdCard.addFocusListener(new LsyFocusListener(jtIdCard, "^\\d{17}[0-9Xx]$", jlIdCardErr, "* 请输入正确的证件信息，长度为18位","身份证号"));
		
		/** add jpanel **/
		this.add(jlBreadCut);
		this.add(jlIdCard);
		this.add(jtIdCard);
		this.add(jlIdCardErr);
		this.add(jlTicket);
		this.add(jcTicket);
		this.add(jlPrice);
		this.add(jtPrice);
		this.add(btEnter);
		this.add(btReset);
		
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
	
	public String getTicketPrice(String name) {
		
		for(int i=0,len=aTickets.size();i<len;i++) {
			if(aTickets.get(i).getName().equals(name)) {
				return aTickets.get(i).getPrice()+" 元";
			}
		}
		return "Not Find";
	}
	
	public String getTicketId(String name) {
		
		for(int i=0,len=aTickets.size();i<len;i++) {
			if(aTickets.get(i).getName().equals(name)) {
				return aTickets.get(i).getId();
			}
		}
		return "Not Find";
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("出票")) {
			if(bIdCard) {
				long currTime = System.currentTimeMillis();  
				Timestamp timeObj = new Timestamp(currTime);
				
				Record record = new Record();
				record.setId_card(jtIdCard.getText().replaceAll(" ", ""));
				record.setType(getTicketId(jcTicket.getSelectedItem().toString()));
				record.setWindow(ticketer.getWindow());
				record.setTime(timeObj);
				StaffController staffController = new StaffController();
				boolean result = staffController.addRecord(record);
				if(result) {
					JOptionPane.showMessageDialog(this, "成功", "出票成功！", JOptionPane.INFORMATION_MESSAGE);
					jtIdCard.setText("");
					jlIdCardErr.setText("");
				}else {
					JOptionPane.showMessageDialog(this, "失败", "出票失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		}else if(e.getActionCommand().equals("重置")) {
			jtIdCard.setText("");
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
				case "身份证号":
					bIdCard = false;
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
	        
	        switch (this.name) {
				case "身份证号":
					bIdCard = true;
					break;
			}
	        
		}
		
	}
	
}
