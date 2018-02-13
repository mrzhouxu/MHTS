package com.mhts.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
import com.mhts.bean.Ticketer;
import com.mhts.bean.Window;
import com.mhts.model.AdminModel;

/**
 * @author zhouxu
 * 管理员控制器
 */
public class AdminController {
	/**
	 * 营业状况 表格数据
	 * @param year
	 * @param mouth
	 * @param skip 跳转页数
	 * @param num  每页条数
	 * @return 
	 */
	public Vector selRecord(String year,String month,String skip,int num) {
		String time = "";
		if(!month.replaceAll("\\D", "").equals("")) {
			time = year.replaceAll("\\D", "")+"-"+String.format("%02d", Integer.valueOf(month.replaceAll("\\D", "")));
		}else {
			time = year.replaceAll("\\D", "")+"-"+month.replaceAll("\\D", "");
		}
		String start = ((Integer.valueOf(skip)-1)*Integer.valueOf(num))+"";
		Vector arr = new Vector();
		AdminModel adminModel = new AdminModel();
		try {
			ArrayList<Record> temp = adminModel.selRecord(time, start, num);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int len = temp.size();
			for(int i=0;i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(Integer.valueOf(start)+i+1);
				vTemp.add(temp.get(i).getId());
				vTemp.add(temp.get(i).getId_card().replaceAll("(\\d{4})\\d{10}([0-9]{3}[0-9Xx]{1})","$1****$2"));
				vTemp.add(idTicket(temp.get(i).getType(),temp.get(i).getType()));
//				vTemp.add(temp.get(i).getType());
				vTemp.add(idWindow(temp.get(i).getWindow(), temp.get(i).getWindow()));
//				vTemp.add(temp.get(i).getWindow());
				vTemp.add(sdf.format(temp.get(i).getTime()));
				vTemp.add(temp.get(i).getStatus());
				arr.add(vTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return arr;
	}
	
	/**
	 * 营业状况 共多少条记录
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public int selRecordCount(String year,String month) {
		String time = "";
		if(!month.replaceAll("\\D", "").equals("")) {
			time = year.replaceAll("\\D", "")+"-"+String.format("%02d", Integer.valueOf(month.replaceAll("\\D", "")));
		}else {
			time = year.replaceAll("\\D", "")+"-"+month.replaceAll("\\D", "");
		}
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.selRecordCount(time);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	/**
	 * 营业状况 营业额
	 * @param year
	 * @param month
	 * @return
	 */
	public double selRecordMoney(String year,String month) {
		String time = "";
		if(!month.replaceAll("\\D", "").equals("")) {
			time = year.replaceAll("\\D", "")+"-"+String.format("%02d", Integer.valueOf(month.replaceAll("\\D", "")));
		}else {
			time = year.replaceAll("\\D", "")+"-"+month.replaceAll("\\D", "");
		}
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.selRecordMoney(time);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 得到所有票类型
	 * @return
	 */
	public Vector getTicket(int status) {
		AdminModel adminModel = new AdminModel();
		Vector result = new Vector();
		ArrayList<Ticket> arr = new ArrayList<Ticket>();
		try {
			arr = adminModel.getTicket(status);
			for(int i=0,len=arr.size();i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(i+1);
				vTemp.add(arr.get(i).getId());
				vTemp.add(arr.get(i).getName());
				vTemp.add(arr.get(i).getPrice());
				vTemp.add(arr.get(i).getStatus());
				result.add(vTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 得到所有窗口
	 * @return
	 */
	public Vector getWindow(int status) {
		AdminModel adminModel = new AdminModel();
		Vector result = new Vector();
		ArrayList<Window> arr = new ArrayList<Window>();
		try {
			arr = adminModel.getWindow(status);
			ArrayList num = adminModel.getWindowNum();
			for(int i=0,len=arr.size();i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(i+1);
				vTemp.add(arr.get(i).getId());
				vTemp.add(arr.get(i).getName());
				for(int j=0,numLen=num.size();j<numLen;j=j+2) {
					if(arr.get(i).getId().equals(num.get(j))) {
						vTemp.add(num.get(j+1));
						break;
					}
					if(j==numLen-2) {
						vTemp.add(0);
					}
				}
				vTemp.add(arr.get(i).getStatus());
				result.add(vTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据id得到票名称
	 * @param id 票id
	 * @param val 如果此id不存在返回val 在这里val算是个默认值
	 * @return
	 */
	public String idTicket(String id,String val) {
		AdminModel adminModel = new AdminModel();
		try {
			ArrayList<Ticket> aTicket = adminModel.getTicket(-1);
			int ticketLen = aTicket.size();
			for(int j=0;j<ticketLen;j++) {
				if(aTicket.get(j).getId().equals(id)) {
					return aTicket.get(j).getName();
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return val;
	}
	
	/**
	 * 根据id得到窗口名称
	 * @param id 窗口id
	 * @param val 默认值
	 * @return
	 */
	public String idWindow(String id,String val) {
		AdminModel adminModel = new AdminModel();
		try {
			ArrayList<Window> aWindow = adminModel.getWindow(-1);
			int windowLen = aWindow.size();
			for(int j=0;j<windowLen;j++) {
				if(aWindow.get(j).getId().equals(id)) {
					return aWindow.get(j).getName();
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return val;
	}
	
	/**
	 * 按条件查询售票员
	 * @param key
	 * @param val
	 * @param skip
	 * @param num
	 * @return 
	 */
	public Vector getTicketer(String key,String val,String skip,String num) {
		val = "%"+val+"%";
		String start = ((Integer.valueOf(skip)-1)*Integer.valueOf(num))+"";
		Vector arr = new Vector();
		AdminModel adminModel = new AdminModel();
		try {
			ArrayList<Ticketer> temp = adminModel.getTicketer(key, val, start,Integer.valueOf(num));
			int len = temp.size();
			for(int i=0;i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(Integer.valueOf(start)+i+1);
				vTemp.add(temp.get(i).getId());
				vTemp.add(temp.get(i).getName());
				vTemp.add(temp.get(i).getId_card().replaceAll("(\\d{4})\\d{10}([0-9]{3}[0-9Xx]{1})","$1****$2"));
//				vTemp.add(temp.get(i).getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
				vTemp.add(temp.get(i).getPhone());
				vTemp.add(temp.get(i).getAccount());
				vTemp.add(idWindow(temp.get(i).getWindow(), temp.get(i).getWindow()));
				vTemp.add(temp.get(i).getStatus());
				arr.add(vTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return arr;
	}
	
	/**
	 * 售票员 共多少条记录
	 * @param year
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public int getTicketerCount(String key,String val) {
		val = "%"+val+"%";
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.getTicketerCount(key,val);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	/**
	 * 批量重置密码
	 * @param selectTicketer
	 * @return
	 */
	public boolean resetPassword(ArrayList<Ticketer> selectTicketer) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.resetPassword(selectTicketer);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 批量删除售票员
	 * @param selectTicketer
	 * @return
	 */
	public boolean delTicketer(ArrayList<Ticketer> selectTicketer) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.delTicketer(selectTicketer);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断账号是否存在
	 * 如果存在返回 true 不存在则返回 false
	 * 报错按存在处理
	 * @param account
	 * @return
	 */
	public boolean getAccount(String account) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.getAccount(account);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * 添加售票员记录
	 * @param ticketer
	 * @return
	 */
	public boolean addTicketer(Ticketer ticketer) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.addTicketer(ticketer);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据id获得售票员记录
	 * @param id
	 * @return
	 */
	public Ticketer getTicketer(int id) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.getTicketer(id);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改售票员信息
	 * @param ticketer
	 * @return
	 */
	public boolean editTicketer(Ticketer ticketer) {
		AdminModel adminModel = new AdminModel();
		try {
			adminModel.editTicketer(ticketer);
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 批量删除窗口信息
	 * @param selectWindow
	 * @return
	 */
	public boolean delWindow(ArrayList<Window> selectWindow) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.delWindow(selectWindow);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加窗口信息
	 * @param name
	 * @return
	 */
	public boolean addWindow(String name) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.addWindow(name);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改窗口信息
	 * @param ticketer
	 * @return
	 */
	public boolean editWindow(Window window) {
		AdminModel adminModel = new AdminModel();
		try {
			adminModel.editWindow(window);
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 批量删除票类型信息
	 * @param selectWindow
	 * @return
	 */
	public boolean delTicket(ArrayList<Ticket> selectTicket) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.delTicket(selectTicket);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 添加票类型信息
	 * @param name
	 * @return
	 */
	public boolean addTicket(String name,String price) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.addTicket(name,price);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改票类型信息
	 * @param ticketer
	 * @return
	 */
	public boolean editTicket(Ticket ticket) {
		AdminModel adminModel = new AdminModel();
		try {
			adminModel.editTicket(ticket);
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}
	
	public Ticket idTicket(String id) {
		AdminModel adminModel = new AdminModel();
		try {
			return adminModel.idTicket(id);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	
	
}
