package com.weixin.domain;

import java.util.Date;

/** 
 * @author wjh
 *
 */
public class TB_UnitMessage {
	private Integer ID;//主键ID
	private String Introduction = "";//单位介绍
	private String Message = "";//自动回复信息
	private Integer Score = 0;//当前发布的积分
	private Integer Term = 0;//积分的期数
	private Integer WelcomeMessage = 1;//欢迎页面的图文信息
	private Date UpdateTime = new Date();//更新时间
	private TB_Unit unit;//所属单位
	
	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer iD) {
		this.ID = iD;
	}

	public String getIntroduction() {
		return this.Introduction;
	}

	public void setIntroduction(String introduction) {
		this.Introduction = introduction;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String message) {
		this.Message = message;
	}

	public Integer getScore() {
		return this.Score;
	}

	public void setScore(Integer score) {
		this.Score = score;
	}

	public Integer getTerm() {
		return this.Term;
	}

	public void setTerm(Integer term) {
		this.Term = term;
	}

	public Integer getWelcomeMessage() {
		return this.WelcomeMessage;
	}

	public void setWelcomeMessage(Integer welcomeMessage) {
		this.WelcomeMessage = welcomeMessage;
	}

	public Date getUpdateTime() {
		return this.UpdateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.UpdateTime = updateTime;
	}

	public TB_Unit getUnit() {
		return unit;
	}

	public void setUnit(TB_Unit unit) {
		this.unit = unit;
	}
	
}