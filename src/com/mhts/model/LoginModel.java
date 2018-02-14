package com.mhts.model;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Admin;
import com.mhts.bean.Ticketer;
import com.mhts.utils.JDBCUtils;

public class LoginModel {
	
	/**
	 * 管理员登录
	 * @param admin
	 * @return 
	 * @throws SQLException
	 */
	public ArrayList<Admin> adminLogin(Admin admin) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM admin where account=? and password = md5(md5(?))";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, admin.getAccount());
		preparedStatement.setString(2, admin.getPassword());
		resultSet = preparedStatement.executeQuery();
		ArrayList<Admin> arr = new ArrayList<Admin>();
		while(resultSet.next()) {
			Admin temp = new Admin();
			temp.setId(resultSet.getString("id"));
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}
	
	/**
	 * 售票员登录
	 * @param ticketer
	 * @return 
	 * @throws SQLException
	 */
	public ArrayList<Ticketer> ticketerLogin(Ticketer ticketer) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM ticketer where account=? and password = md5(md5(?)) and status = 0";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, ticketer.getAccount());
		preparedStatement.setString(2, ticketer.getPassword());
		resultSet = preparedStatement.executeQuery();
		ArrayList<Ticketer> arr = new ArrayList<Ticketer>();
		while(resultSet.next()) {
			Ticketer temp = new Ticketer();
			temp.setId(resultSet.getString("id"));
			temp.setName(resultSet.getString("name"));
			temp.setId_card(resultSet.getString("id_card"));
			temp.setPhone(resultSet.getString("phone"));
			temp.setAccount(resultSet.getString("account"));
			temp.setWindow(resultSet.getString("window"));
			temp.setStatus(resultSet.getString("status"));
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}

}
