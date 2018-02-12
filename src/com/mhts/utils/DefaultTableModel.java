package com.mhts.utils;

import java.util.Vector;

/**
 * @author zhouxu
 * 重写DefaultTableModel 继承 JAVA的DefaultTableModel
 */
public class DefaultTableModel extends javax.swing.table.DefaultTableModel{
	
	public DefaultTableModel(Vector select, Vector tableHeader) {
		// TODO 自动生成的构造函数存根
		super(select, tableHeader);
	}

	@Override
	public boolean isCellEditable(int row, int column) {//重写DefaultTableModel的isCellEditable方法  设置表格不可编辑
		// TODO 自动生成的方法存根
		return false;
	} 
}
