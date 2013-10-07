package com.weixin.domain;

import java.util.Date;

public class TB_WeixinMessage {
	private Integer ID;
	private String Introduction = "";
	private String Message = "";
	private Integer Score = 0;
	private Integer Term = 0;
	private Integer WelcomeMessage = 1;
	private Date UpdateTime = new Date();
	private TB_Unit unit;
	
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