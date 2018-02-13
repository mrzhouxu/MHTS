package com.mhts.view;

import javax.swing.JFrame;

public class EditTicketerInfoView extends JFrame {
	EditTicketerInfoView() {
		this.setSize(600, 800);
		this.setLayout(null);
		
		
		/** ¥∞ÃÂ…Ë÷√ **/
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new EditTicketerInfoView();
	}
}
