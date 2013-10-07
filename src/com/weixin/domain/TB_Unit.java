package com.weixin.domain;

import java.util.Date;

public class TB_Unit {
	private Integer UnitID;
	private String UnitName = "";
	private String Appid = "";
	private String Secret = "";
	private String Menu = "";
	private String UnitMemo = "";
	private Date CreateTime = new Date();
	
	public Integer getUnitID() {
		return UnitID;
	}
	public void setUnitID(Integer unitID) {
		UnitID = unitID;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}
	public String getAppid() {
		return Appid;
	}
	public void setAppid(String appid) {
		Appid = appid;
	}
	public String getSecret() {
		return Secret;
	}
	public void setSecret(String secret) {
		Secret = secret;
	}
	public String getMenu() {
		return Menu;
	}
	public void setMenu(String menu) {
		Menu = menu;
	}
	public String getUnitMemo() {
		return UnitMemo;
	}
	public void setUnitMemo(String unitMemo) {
		UnitMemo = unitMemo;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	
}