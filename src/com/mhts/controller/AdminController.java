package com.mhts.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.mhts.bean.Record;
import com.mhts.bean.Ticket;
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
			ArrayList<Ticket> aTicket = adminModel.getTicket();
			ArrayList<Window> aWindow = adminModel.getWindow();
			int ticketLen = aTicket.size();
			int windowLen = aWindow.size();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int len = temp.size();
			for(int i=0;i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(Integer.valueOf(start)+i+1);
				vTemp.add(temp.get(i).getId());
//				idCard.replaceAll("(\\d{4})\\d{10}(\\d{4})","$1****$2")
				vTemp.add(temp.get(i).getId_card().replaceAll("(\\d{4})\\d{10}([0-9]{3}[0-9Xx]{1})","$1****$2"));
				for(int j=0;j<ticketLen;j++) {
					if(aTicket.get(j).getId().equals(temp.get(i).getType())) {
						vTemp.add(aTicket.get(j).getName());
						break;
					}else {
						vTemp.add(temp.get(i).getType());
					}
				}
//				vTemp.add(temp.get(i).getType());
				for(int j=0;j<windowLen;j++) {
					if(aWindow.get(j).getId().equals(temp.get(i).getWindow())) {
						vTemp.add(aWindow.get(j).getName());
						break;
					}else {
						vTemp.add(temp.get(i).getWindow());
					}
				}
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
	public Vector getTicket() {
		AdminModel adminModel = new AdminModel();
		Vector result = new Vector();
		ArrayList<Ticket> arr = new ArrayList<Ticket>();
		try {
			arr = adminModel.getTicket();
			for(int i=0,len=arr.size();i<len;i++) {
				Vector vTemp = new Vector();
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
	public Vector getWindow() {
		AdminModel adminModel = new AdminModel();
		Vector result = new Vector();
		ArrayList<Window> arr = new ArrayList<Window>();
		try {
			arr = adminModel.getWindow();
			for(int i=0,len=arr.size();i<len;i++) {
				Vector vTemp = new Vector();
				vTemp.add(arr.get(i).getId());
				vTemp.add(arr.get(i).getName());
				vTemp.add(arr.get(i).getStatus());
				result.add(vTemp);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
}
