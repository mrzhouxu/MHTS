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

import com.mhts.bean.Ticket;
import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.controller.AdminController;
import com.mhts.utils.DefaultTableModel;
import com.mhts.utils.TableHideColumn;

/**
 * @author zhouxu
 * �����õ�DefaultTableModel�����Լ�д����
 */
public class TicketManagementView extends JPanel implements ActionListener{
	
	Font font = new Font("����", Font.PLAIN, 12);
	JTable table = null;
	boolean isClick = true;
	DefaultTableModel tableModel = null;
	Vector tableHeader = null;
	Vector datas = null;
	String[] tableHeaderStr = {"���","id","Ʊ����","Ʊ��"};
	
	TicketManagementView() {
		/** breadCut **/
		JLabel jlTitle = new JLabel("����Ա���� > Ʊ�͹���");
		jlTitle.setBounds(30, 10, 150, 30);
		jlTitle.setFont(font);
		
		/** ���� **/
		JButton btAdd = new JButton("����");
		btAdd.setBounds(350, 50, 80, 30);

		JButton btUpdate = new JButton("�޸�");
		btUpdate.setBounds(450, 50, 80, 30);
		
		JButton btDel = new JButton("ɾ��");
		btDel.setBounds(550, 50, 80, 30);
		
		/** �õ�����  **/
		select();//��ʼ����������
		
		/** table **/
		tableHeader = new Vector();
		for(int i=0,len=tableHeaderStr.length;i<len;i++) {
			tableHeader.add(tableHeaderStr[i]);
		}
        
		tableModel=new DefaultTableModel(datas, tableHeader);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);//��ͷ�����϶�
		table.getTableHeader().setResizingAllowed(false);//�д�С���ɸı�
		table.setRowHeight(26);//ָ��ÿһ�е��и�50
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
		datas = adminController.getTicket(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isClick) return;
		isClick = !isClick;

		if(e.getActionCommand().equals("����")) {
			this.removeAll();
			this.add(new AddTicketView());
			this.repaint();
			this.validate();
		}else if(e.getActionCommand().equals("�޸�")) {
			int selLen = table.getSelectedRowCount();
			if(selLen < 1) {//����ɾ
				//��ѡ��Ҫ�޸ĵļ�¼
				JOptionPane.showMessageDialog(this, "�װ��ģ����ż�ѽ����ѡ��Ҫ�޸ĵļ�¼�ɣ�", "��ܰ��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}else if(selLen > 1) {
				//�ϰ���Ƿ���ʣ������޸Ĺ��ܲ����㿪����������һ��һ���޸�
				JOptionPane.showMessageDialog(this, "�ϰ���Ƿ���ʣ������޸Ĺ��ܲ����㿪����������һ��һ���޸ģ�", "��ܰ��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}else {
				this.removeAll();
				this.add(new AddTicketView(Integer.valueOf(table.getValueAt(table.getSelectedRow(), 1).toString())));
				this.repaint();
				this.validate();
			}
		}else if(e.getActionCommand().equals("ɾ��")) {
			ArrayList<Ticket> selectTicketer = new ArrayList<Ticket>();
			int selLen = table.getSelectedRowCount();
			if(selLen < 1) {//����ɾ
				
			}else if(JOptionPane.showConfirmDialog(this, "��Ҫɾ��ѡ�е�"+selLen+"����¼", "�Ƿ����", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				int [] arr = new int[selLen];
				for(int i=0;i<selLen;i++) {
					int row = table.getSelectedRows()[i];
					Ticket tTemp = new Ticket();
					tTemp.setId(table.getValueAt(row, 1).toString());
					tTemp.setName(table.getValueAt(row, 2).toString());
					selectTicketer.add(tTemp);
				}
				AdminController adminController = new AdminController();
				boolean result = adminController.delTicket(selectTicketer);
				if(result) {
					select();
					tableModel=new DefaultTableModel(datas, tableHeader);
					table.setModel(tableModel);
					TableHideColumn.hideColumn(table,1);
					JOptionPane.showMessageDialog(this, "ɾ���ɹ���", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "ɾ��ʧ�ܣ�", "ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				
			}; 
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