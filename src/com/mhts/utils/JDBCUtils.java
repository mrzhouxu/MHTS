package com.mhts.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;



/*
 * JDBC工具类
*/

public class JDBCUtils {
	
	public static final String URL = "jdbc:mysql://127.0.0.1/lsy_mhts";
	public static final String NAME = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "zhouxu";
	
	private static Connection conn;
	
	/**
	 * 数据库连接
	 * @return
	 */
	public static Connection  getConnect() {
		try {
			Class.forName(NAME);
			if (conn==null||conn.isClosed()) {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
//				System.out.println("数据库连接成功！");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 释放资源
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	public static void release(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet) {
		try {
			if(connection!=null || connection.isClosed()) {
				connection.close();
//				System.out.println("connection.close();");
			}
			if(preparedStatement!=null || preparedStatement.isClosed()) {
				preparedStatement.close();
//				System.out.println("preparedStatement.close();");
			}
			if(resultSet!=null || resultSet.isClosed()) {
				resultSet.close();
//				System.out.println("resultSet.close();");
			}
		} catch (Exception e) {
			System.out.println("释放资源失败！");
		}
	}
	
}
