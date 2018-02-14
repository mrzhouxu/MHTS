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
import com.mhts.bean.Ticketer;
import com.mhts.controller.AdminController;
import com.mhts.controller.LoginController;

public class StaffView extends JFrame implements ActionListener{
	
	Ticketer ticketer = null ;
	boolean isClick = true;
	JPanel main = null;
	AdminController adminController = new AdminController();

	/**
	 * ����Ա����
	 * @param admin
	 */
	StaffView(Ticketer ticketer) {
		this.ticketer = ticketer;
		this.setTitle("��ӭʹ�ú�ɽ��Ʊϵͳ��");
		this.setSize(1200, 900);
		this.setFocusable(true);
		this.setLayout(new BorderLayout());
		
		/** header **/
		JPanel header = new JPanel(null);//null �� ������ʽ
		header.setPreferredSize(new Dimension(1200, 100));//����setSize���ܸı�����С��ֻ��ʹ��setPreferredSize
//		header.setBorder(BorderFactory.createTitledBorder(""));//���ñ߽�
		header.setBorder(new EtchedBorder(EtchedBorder.RAISED));//���ñ߽�
		
		JLabel title = new JLabel(adminController.idWindow(this.ticketer.getWindow(), this.ticketer.getWindow()),JLabel.CENTER);//�ڶ������� ��ʾ  ������ж��뷽ʽ
		title.setFont(new Font("����",Font.PLAIN,30));
		title.setSize(1200, 100);
		
		JLabel name = new JLabel("��ӭ����"+this.ticketer.getName());
		name.setFont(new Font("����",Font.PLAIN,14));
		name.setBounds(700, 70, 200, 20);
		
		JButton btLogout = new JButton("ע��");
		btLogout.setBounds(920, 65, 60, 25);
//		btLogout.setContentAreaFilled(false);//��ť����Ϊ͸���������Ͳ��ᵲ�ź���ı��� 
		
		JButton btExit = new JButton("�˳�");
		btExit.setBounds(1000, 65, 60, 25);
//		btExit.setContentAreaFilled(false);//��ť����Ϊ͸���������Ͳ��ᵲ�ź���ı��� 
		
		header.add(title);
		header.add(name);
		header.add(btLogout);
		header.add(btExit);
		
		/** menu **/
		JPanel menu = new JPanel(null);//null �� ������ʽ
		menu.setPreferredSize(new Dimension(200, 770));//����setSize���ܸı�����С��ֻ��ʹ��setPreferredSize
		menu.setBorder(new EtchedBorder(EtchedBorder.RAISED));//���ñ߽�
		menu.setLayout(new FlowLayout(FlowLayout.CENTER,0,20));//��ʽ����
		
		JButton businessStatus = new JButton("��Ʊ����");
		businessStatus.setPreferredSize(new Dimension(150, 40));
		businessStatus.setFont(new Font("����", Font.PLAIN, 14));
		
//		JButton staffManagement = new JButton("��Ʊ��¼");
//		staffManagement.setPreferredSize(new Dimension(150, 40));
//		staffManagement.setFont(new Font("����", Font.PLAIN, 14));
		
		JButton windowManagement = new JButton("�޸�����");
		windowManagement.setPreferredSize(new Dimension(150, 40));
		windowManagement.setFont(new Font("����", Font.PLAIN, 14));
		
		
		menu.add(businessStatus);
//		menu.add(staffManagement);
		menu.add(windowManagement);
		
		/** main **/
		main = new JPanel(null);
		main.setPreferredSize(new Dimension(1000, 770));//����setSize���ܸı�����С��ֻ��ʹ��setPreferredSize
		main.setBorder(new EtchedBorder(EtchedBorder.RAISED));//���ñ߽�
		
		
		main.add(new AddRecordView(ticketer)); // todo  ��
		
		/** footer **/
		JPanel footer = new JPanel(null);
		footer.setPreferredSize(new Dimension(1200, 30));//����setSize���ܸı�����С��ֻ��ʹ��setPreferredSize
		footer.setBorder(new EtchedBorder(EtchedBorder.RAISED));//���ñ߽�
		
		JLabel copyright = new JLabel("��Ȩ���� @��ʫӨ 2018.2.10",JLabel.CENTER);//�ڶ������� ��ʾ  ������ж��뷽ʽ
		copyright.setFont(new Font("����",Font.PLAIN,12));
		copyright.setSize(1200, 30);
		
		footer.add(copyright);
		
		/** ��ť���� **/
		btExit.addActionListener(this);
		btLogout.addActionListener(this);
		businessStatus.addActionListener(this);
//		staffManagement.addActionListener(this);
		windowManagement.addActionListener(this);
		
		/** ���岼�� **/
		this.add(BorderLayout.NORTH,header);
		this.add(BorderLayout.WEST,menu);
		this.add(BorderLayout.CENTER,main);
		this.add(BorderLayout.SOUTH,footer);
		
		/** �������� **/
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		LoginController login = new LoginController();
		Ticketer ticketer = new Ticketer();
		ticketer.setId("105");;
		ticketer.setAccount("999");
		ticketer.setPassword("666666");
		Ticketer ticketerTemp = login.ticketerLogin(ticketer);
		new StaffView(ticketerTemp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("ע��")) {
			if(JOptionPane.showConfirmDialog(this, "����Ҫע����¼,�Ƿ������", "ע��", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
				new LoginView();
			}
		}else if(e.getActionCommand().equals("�˳�")) {
			if(JOptionPane.showConfirmDialog(this, "����Ҫ�˳�ϵͳ,�Ƿ������", "�˳�", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		}else if(e.getActionCommand().equals("��Ʊ����")) {
			this.main.removeAll();
			this.main.add(new AddRecordView(this.ticketer));
			this.main.repaint();
			this.main.validate();
		}else if(e.getActionCommand().equals("��Ʊ��¼")) {
			this.main.removeAll();
			this.main.add(new BusinessStatusView());
			this.main.repaint();
			this.main.validate();
		}else if(e.getActionCommand().equals("�޸�����")) {
			this.main.removeAll();
			this.main.add(new ChangePasswordView(this.ticketer));
			this.main.repaint();
			this.main.validate();
		}
		
		/** �߳� ��ֹ��ť��� **/
		new Thread() { 
			@Override
			public void run() {
				super.run();
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				isClick = !isClick;
				this.stop();
			}
		}.start();
	}
}