package com.mhts.bean;

public class Ticketer {
	private String id;
	private String name;
	private String id_card;
	private String phone;
	private String account;
	private String password;
	private String window;
	private String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWindow() {
		return window;
	}
	public void setWindow(String window) {
		this.window = window;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Ticketer [id=" + id + ", name=" + name + ", id_card=" + id_card + ", phone=" + phone + ", account="
				+ account + ", password=" + password + ", status=" + status + "]";
	}
	
	
	
}
