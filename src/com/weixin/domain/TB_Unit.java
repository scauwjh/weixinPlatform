package com.weixin.domain;

import java.util.Date;

public class TB_Unit {
	private Integer UnitID;//主键，单位ID
	private String UnitName = "";//单位名称
	private String Appid = "";//appid，从微信公众平台获取
	private String Secret = "";//secret，从公众平台获取
	private String Introduction = "";//单位介绍
	private String AutoReply = "";//自动回复信息
	private Integer Score = 0;//当前发布的积分
	private Integer Term = 0;//积分的期数
	private Integer WelcomePage = 1;//欢迎页面的图文信息ID
	private String Menu = "";//自定菜单
	private Date UpdateTime = new Date();//更新时间
	private Date CreateTime = new Date();//创建时间
	
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
	public String getIntroduction() {
		return Introduction;
	}
	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}
	public String getAutoReply() {
		return AutoReply;
	}
	public void setAutoReply(String autoReply) {
		AutoReply = autoReply;
	}
	public Integer getScore() {
		return Score;
	}
	public void setScore(Integer score) {
		Score = score;
	}
	public Integer getTerm() {
		return Term;
	}
	public void setTerm(Integer term) {
		Term = term;
	}
	public Integer getWelcomePage() {
		return WelcomePage;
	}
	public void setWelcomePage(Integer welcomePage) {
		WelcomePage = welcomePage;
	}
	public String getMenu() {
		return Menu;
	}
	public void setMenu(String menu) {
		Menu = menu;
	}
	public Date getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(Date updateTime) {
		UpdateTime = updateTime;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
}