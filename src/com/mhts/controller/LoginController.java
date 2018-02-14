package com.mhts.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Admin;
import com.mhts.bean.Ticketer;
import com.mhts.model.LoginModel;

public class LoginController {
	
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	public boolean adminLogin(Admin admin) {
		LoginModel login = new LoginModel();
		try {
			ArrayList<Admin> arr = login.adminLogin(admin);
			if(arr.size()>0) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 售票员登录
	 * @param ticketer
	 * @return
	 */
	public Ticketer ticketerLogin(Ticketer ticketer) {
		LoginModel login = new LoginModel();
		try {
			ArrayList<Ticketer> arr = login.ticketerLogin(ticketer);
			if(arr.size()>0) {
				return arr.get(0);
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
