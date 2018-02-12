package com.mhts.utils;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TableHideColumn {
	
	/**
	 * 隐藏表格指定列
	 * @param table
	 * @param index
	 */
	public static void hideColumn(JTable table, int index) {
		TableColumn tc = table.getColumnModel().getColumn(index);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setMinWidth(0);
		tc.setWidth(0);
		table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
	}
}
