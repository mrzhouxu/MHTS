package com.mhts.bean;

import java.sql.Timestamp;

public class Record {
	private String id;
	private String id_card;
	private String type;
	private String window;
	private Timestamp time;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWindow() {
		return window;
	}
	public void setWindow(String window) {
		this.window = window;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Record [id=" + id + ", id_card=" + id_card + ", type=" + type + ", window=" + window + ", time=" + time
				+ ", status=" + status + "]";
	}
	
	
	
	
}
