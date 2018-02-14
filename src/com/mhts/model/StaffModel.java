package com.mhts.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
import com.mhts.bean.Ticketer;
import com.mhts.utils.JDBCUtils;

public class StaffModel {
	/**
	 * 添加售票记录
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	public boolean addRecord(Record record) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "insert into record(id_card,type,window,time,status) values(?,?,?,?,0)";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, record.getId_card());
		preparedStatement.setString(2, record.getType());
		preparedStatement.setString(3, record.getWindow());
		preparedStatement.setTimestamp(4, record.getTime());
		result = preparedStatement.executeUpdate();
		return result == 0 ? false: true;
	}
	
	/**
	 * 判断原密码是否正确
	 * @param account
	 * @return
	 * @throws SQLException
	 */
	public boolean judgePassword(String id,String password) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from ticketer where id = ? and password = md5(md5(?)) and status = 0";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, password);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			JDBCUtils.release(con,preparedStatement,resultSet);
			return true;
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return false;
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean editPassword(String id,String password) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		String sql = "update ticketer set password=md5(md5(?)) where id = ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, password);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		return true;
	}
}
