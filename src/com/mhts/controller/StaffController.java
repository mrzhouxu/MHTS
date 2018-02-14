package com.mhts.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Record;
import com.mhts.bean.Ticketer;
import com.mhts.model.AdminModel;
import com.mhts.model.StaffModel;

public class StaffController {

	/**
	 * 添加售票记录
	 * @param ticketer
	 * @return
	 */
	public boolean addRecord(Record record) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.addRecord(record);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断原密码是否正确
	 * 如果存在返回 true 不存在则返回 false
	 * 报错按bu存在处理
	 * @param account
	 * @return
	 */
	public boolean judgePassword(String id,String password) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.judgePassword(id,password);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String id,String password) {
		StaffModel staffModel = new StaffModel();
		try {
			return staffModel.editPassword(id, password);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
}
