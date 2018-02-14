package com.mhts.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Record;
import com.mhts.bean.Ticketer;
import com.mhts.model.AdminModel;
import com.mhts.model.StaffModel;

public class StaffController {

	/**
	 * ������Ʊ��¼
	 * @param ticketer
	 * @return
	 */
	public boolean addRecord(Record record) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.addRecord(record);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * �ж�ԭ�����Ƿ���ȷ
	 * ������ڷ��� true �������򷵻� false
	 * ������bu���ڴ���
	 * @param account
	 * @return
	 */
	public boolean judgePassword(String id,String password) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.judgePassword(id,password);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * �޸�����
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String id,String password) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.editPassword(id, password);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return false;
	}
}