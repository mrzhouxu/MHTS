package com.mhts.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
import com.mhts.bean.Ticketer;
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
		String sql = "select * from record where status = 0 and time > ? order by time desc limit ?,?";
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
		String sql = "select count(*) from record where time > ? and status = 0";
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
				"WHERE time > ? and record.status = 0";
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
	public ArrayList<Window> getWindow(int status) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if(status==-1) {
			String sql = "select * from window ";
			preparedStatement = con.prepareStatement(sql);
		}else {
			String sql = "select * from window where status = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, status);
		}
		
		resultSet = preparedStatement.executeQuery();
		ArrayList<Window> arr = new ArrayList<Window>();
		while(resultSet.next()) {
			Window temp = new Window();
			temp.setId(resultSet.getString("id"));
			temp.setName(resultSet.getString("name"));
			temp.setStatus(resultSet.getString("status"));
			arr.add(temp);
		}
		return arr;
	}
	
	/**
	 * 得到窗口中的工作人数
	 * 窗口id    人数
	 * window   num
	 *    1     116
	 *    2     1
	 *    3     1
	 *    4     2
	 *    6     3
	 * @return
	 * 返回一个集合 如下
	 * 1  116   2   1   3   1   4   2   6   3
	 * @throws SQLException
	 */
	public ArrayList getWindowNum() throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select window,count(*) as num from ticketer GROUP BY window";
		preparedStatement = con.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		ArrayList arr = new ArrayList();
		while(resultSet.next()) {
			arr.add(resultSet.getString("window"));
			arr.add(resultSet.getString("num"));
		}
		return arr;
	}
	
	/**
	 * 批量查询售票员信息
	 * @param key
	 * @param val
	 * @param start
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Ticketer> getTicketer(String key,String val,String start,int num) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from ticketer where status=0 and "+key+" like ? order by window,id desc  limit ?,?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, val);
		preparedStatement.setInt(2, Integer.valueOf(start));
		preparedStatement.setInt(3, num);
		resultSet = preparedStatement.executeQuery();
		ArrayList<Ticketer> arr = new ArrayList<Ticketer>();
		while(resultSet.next()) {
			Ticketer temp = new Ticketer();
			temp.setId(resultSet.getString("id"));
			temp.setName(resultSet.getString("name"));
			temp.setId_card(resultSet.getString("id_card"));
			temp.setPhone(resultSet.getString("phone"));
			temp.setAccount(resultSet.getString("account"));
			temp.setWindow(resultSet.getInt("window")+"");
			temp.setStatus(resultSet.getInt("status")+"");
			arr.add(temp);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return arr;
	}
	
	/**
	 * 售票员 共多少条记录
	 * @param key
	 * @param val
	 * @return
	 * @throws SQLException
	 */
	public int getTicketerCount(String key,String val) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from ticketer where "+key+" like ? and status = 0";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, val);
		resultSet = preparedStatement.executeQuery();
		int count = 0;
		while(resultSet.next()) {
			count = resultSet.getInt(1);
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return count;
	}
	
	/**
	 * 重置密码
	 * @param selectTicketer
	 * @return
	 * @throws SQLException
	 */
	public boolean resetPassword(ArrayList<Ticketer> selectTicketer) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		
		con.setAutoCommit(false);//将自动提交关闭
		
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		for(int i=0,len=selectTicketer.size();i<len;i++) {
			String sql = "update ticketer set password=md5(md5(666666)) where id = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, selectTicketer.get(i).getId());
			preparedStatement.executeUpdate();
		}

		con.commit();//执行完后，手动提交事务
		con.setAutoCommit(true);//在把自动提交打开
		
		return true;
	}
	
	/**
	 * 批量删除售票员
	 * @param selectTicketer
	 * @return
	 * @throws SQLException
	 */
	public boolean delTicketer(ArrayList<Ticketer> selectTicketer) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		
		con.setAutoCommit(false);//将自动提交关闭
		
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		for(int i=0,len=selectTicketer.size();i<len;i++) {
			String sql = "update ticketer set status = 1  where id = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, selectTicketer.get(i).getId());
			preparedStatement.executeUpdate();
		}

		con.commit();//执行完后，手动提交事务
		con.setAutoCommit(true);//在把自动提交打开
		
		return true;
	}
	
	/**
	 * 判断账号是否存在
	 * @param account
	 * @return
	 * @throws SQLException
	 */
	public boolean getAccount(String account) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from ticketer where account = ? and status = 0";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, account);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			JDBCUtils.release(con,preparedStatement,resultSet);
			return true;
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return false;
	}
	
	/**
	 * 添加售票员记录
	 * @param ticketer
	 * @return
	 * @throws SQLException
	 */
	public boolean addTicketer(Ticketer ticketer) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "insert into ticketer(name,id_card,phone,account,password,window) values(?,?,?,?,md5(md5(?)),?)";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, ticketer.getName());
		preparedStatement.setString(2, ticketer.getId_card());
		preparedStatement.setString(3, ticketer.getPhone());
		preparedStatement.setString(4, ticketer.getAccount());
		preparedStatement.setString(5, "666666");
		preparedStatement.setString(6, ticketer.getWindow());
		result = preparedStatement.executeUpdate();
		return result == 0 ? false: true;
	}
	
	/**
	 * 根据id获得售票员信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Ticketer getTicketer(int id) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select * from ticketer where id = ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();
		Ticketer ticketer = new Ticketer();
		while(resultSet.next()) {
			ticketer.setId(resultSet.getString("id"));
			ticketer.setName(resultSet.getString("name"));
			ticketer.setId_card(resultSet.getString("id_card"));
			ticketer.setPhone(resultSet.getString("phone"));
			ticketer.setAccount(resultSet.getString("account"));
			ticketer.setWindow(resultSet.getInt("window")+"");
			ticketer.setStatus(resultSet.getInt("status")+"");
		}
		JDBCUtils.release(con,preparedStatement,resultSet);
		return ticketer;
	}
	
	/**
	 * 更新售票员信息
	 * 报错为更新失败 否则为更新成功
	 * @param ticketer
	 * @throws SQLException
	 */
	public void editTicketer(Ticketer ticketer) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "update ticketer set name = ? , id_card = ? , phone = ? , window = ?  where id = ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, ticketer.getName());
		preparedStatement.setString(2, ticketer.getId_card());
		preparedStatement.setString(3, ticketer.getPhone());
		preparedStatement.setString(4, ticketer.getWindow());
		preparedStatement.setString(5, ticketer.getId());
		preparedStatement.executeUpdate();
	}
	
	/**
	 * 批量删除窗口信息
	 * @param selectWindow
	 * @return
	 * @throws SQLException
	 */
	public boolean delWindow(ArrayList<Window> selectWindow) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		
		con.setAutoCommit(false);//将自动提交关闭
		
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		for(int i=0,len=selectWindow.size();i<len;i++) {
			String sql = "update window set status = 1  where id = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, selectWindow.get(i).getId());
			preparedStatement.executeUpdate();
		}

		con.commit();//执行完后，手动提交事务
		con.setAutoCommit(true);//在把自动提交打开
		
		return true;
	}
	
	/**
	 * 添加窗口信息
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean addWindow(String name) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "insert into window(name) values(?)";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, name);
		result = preparedStatement.executeUpdate();
		return result == 0 ? false: true;
	}
	
	/**
	 * 更新窗口信息
	 * 报错为更新失败 否则为更新成功
	 * @param ticketer
	 * @throws SQLException
	 */
	public void editWindow(Window window) throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "update window set name = ? where id = ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, window.getName());
		preparedStatement.setString(2, window.getId());
		preparedStatement.executeUpdate();
	}
	
}
