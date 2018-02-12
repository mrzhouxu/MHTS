package com.mhts.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
import com.mhts.bean.Window;
import com.mhts.utils.JDBCUtils;

/**
 * @author zhouxu
 * 管理员Model
 */
public class AdminModel {
	/**
	 * 营业状况 表格数据
	 * @param time		时间
	 * @param start		开始条数
	 * @param num		每页条数
	 * @return 
	 * @throws SQLException 
	 */
	public ArrayList<Record> selRecord(String time,String start,int num) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from record where time > ? order by time desc limit ?,?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, time);
		preparedStatement.setInt(2, Integer.valueOf(start));
		preparedStatement.setInt(3, num);
		resultSet = preparedStatement.executeQuery();
		ArrayList<Record> arr = new ArrayList<Record>();
		while(resultSet.next()) {
			Record temp = new Record();
			temp.setId(resultSet.getString("id"));
			temp.setId_card(resultSet.getString("id_card"));
			temp.setType(resultSet.getString("type"));
			temp.setWindow(resultSet.getString("window"));
			temp.setTime(resultSet.getTimestamp("time"));
			temp.setStatus(resultSet.getString("status"));
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}
	
	/**
	 * 营业状况 共多少条记录
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	public int selRecordCount(String time) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from record where time > ? ";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, time);
		resultSet = preparedStatement.executeQuery();
		int count = 0;
		while(resultSet.next()) {
			count = resultSet.getInt(1);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return count;
	}
	
	/**
	 * 营业状况 营业额
	 * @param time
	 * @return
	 * @throws SQLException 
	 */
	public double selRecordMoney(String time) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT SUM(price) FROM record " + 
				"INNER JOIN ticket " + 
				"ON record.type = ticket.id " +
				"WHERE time > ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, time);
		resultSet = preparedStatement.executeQuery();
		double count = 0;
		while(resultSet.next()) {
			count = resultSet.getInt(1)/100;
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return count;
	}
	
	/**
	 * 查询 票类型
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Ticket> getTicket() throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from ticket";
		preparedStatement = con.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		ArrayList<Ticket> arr = new ArrayList<Ticket>();
		while(resultSet.next()) {
			Ticket temp = new Ticket();
			temp.setId(resultSet.getString("id"));
			temp.setName(resultSet.getString("name"));
			temp.setPrice(resultSet.getInt("price")/100+"");
			temp.setStatus(resultSet.getString("status"));
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}
	
	/**
	 * 得到所有窗口
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Window> getWindow() throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from window";
		preparedStatement = con.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		ArrayList<Window> arr = new ArrayList<Window>();
		while(resultSet.next()) {
			Window temp = new Window();
			temp.setId(resultSet.getString("id"));
			temp.setName(resultSet.getString("name"));
			temp.setStatus(resultSet.getString("status"));
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}
	
}
