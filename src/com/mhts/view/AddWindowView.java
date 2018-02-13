package com.mhts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.view.AddTicketerView.LsyFocusListener;

public class AddWindowView extends JPanel implements ActionListener{
	
	int id = 0;
	
	Font font = new Font("����", Font.PLAIN, 12);
	JLabel jlBreadCut = null,jlNameErr = null;
	JTextField jtName = null;
	JButton btEnter = null;
	boolean isClick = true;
	
	/**
	 * �޸���ƱԱ��Ϣ���췽��
	 * @param id
	 */
	AddWindowView(int id){
		this();
		this.id = id;
		jlBreadCut.setText("����Ա���� > ���ڹ��� > �޸���Ϣ");
		
		/** ��ֵ **/
		this.jtName.setText(this.getWindowInfo(id));
		
	}
	
	AddWindowView() {
		/** breadCut **/
		jlBreadCut = new JLabel("����Ա���� > ���ڹ��� > ��������");
		jlBreadCut.setBounds(30, 10, 200, 30);
		jlBreadCut.setFont(font);
		
		JLabel jlName = new JLabel("������");
		jlName.setFont(font);
		jlName.setBounds(320, 60, 100, 30);
		
		jtName = new JTextField();
		jtName.setBounds(400, 60, 200, 30);
		
		jlNameErr = new JLabel();
		jlNameErr.setFont(font);
		jlNameErr.setForeground(Color.red);
		jlNameErr.setBounds(610, 60, 400, 30);
		
		/** button **/
		btEnter = new JButton("ȷ��");
		btEnter.setFont(font);
		btEnter.setBounds(320, 230, 130, 30);
		
		JButton btBack = new JButton("����");
		btBack.setFont(font);
		btBack.setBounds(470, 230, 130, 30);
		
		/** button add Listener **/
		btEnter.addActionListener(this);
		btBack.addActionListener(this);
		
		
		this.add(jlBreadCut);
		this.add(jlName);
		this.add(jtName);
		this.add(jlNameErr);
		this.add(btEnter);
		this.add(btBack);
		
		
		this.setLayout(null);
		this.setSize(1000,770);
	}
	
	/**
	 * ��ô�����Ϣ
	 * @param id
	 * @return
	 */
	public String getWindowInfo(int id) {
		AdminController adminController = new AdminController();
		return adminController.idWindow(id+"","");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
				if(!isClick) return;
				isClick = !isClick;

				if(e.getActionCommand().equals("ȷ��")) {
					String name = jtName.getText().replaceAll(" ", "");
					if(id==0) {
						AdminController adminController = new AdminController();
						boolean result = adminController.addWindow(name);
						if(result) {
							int isEnter = JOptionPane.showConfirmDialog(this, "���ӳɹ�,�Ƿ�������ӣ�", "�ɹ�", JOptionPane.YES_NO_OPTION);
							if(isEnter==JOptionPane.YES_OPTION) {
							}else if(isEnter==JOptionPane.NO_OPTION){
								this.removeAll();
								this.add(new WindowManagementView());
								this.repaint();
								this.validate();
							}; 
						}else {
							JOptionPane.showMessageDialog(this, "ʧ��", "����ʧ�ܣ�", JOptionPane.ERROR_MESSAGE);
						}
					}else if(id!=0){
						System.out.println(id);
						AdminController adminController = new AdminController();
						Window window = new Window();
						window.setId(id+"");
						window.setName(name);
						boolean result = adminController.editWindow(window);
						if(result) {
							//���³ɹ�
							JOptionPane.showMessageDialog(this, "�ɹ�", "���³ɹ���", JOptionPane.INFORMATION_MESSAGE);
							this.removeAll();
							this.add(new WindowManagementView());
							this.repaint();
							this.validate();
						}else {
							//����ʧ��
							JOptionPane.showMessageDialog(this, "ʧ��", "����ʧ�ܣ�", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else if(e.getActionCommand().equals("����")) {
					this.removeAll();
					this.add(new WindowManagementView());
					this.repaint();
					this.validate();
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
}