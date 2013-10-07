package com.weixin.domain;

public class TB_User {
	private Integer UserID;
	private String UserAccount;
	private String Password;
	private Integer Role;
	private TB_Unit Unit;

	public Integer getUserID() {
		return this.UserID;
	}

	public void setUserID(Integer userID) {
		this.UserID = userID;
	}

	public String getUserAccount() {
		return this.UserAccount;
	}

	public void setUserAccount(String userAccount) {
		this.UserAccount = userAccount;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public Integer getRole() {
		return this.Role;
	}

	public void setRole(Integer role) {
		this.Role = role;
	}

	public TB_Unit getUnit() {
		return Unit;
	}

	public void setUnit(TB_Unit unit) {
		Unit = unit;
	}
	
}