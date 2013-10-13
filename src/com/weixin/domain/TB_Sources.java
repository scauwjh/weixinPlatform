package com.weixin.domain;

import java.util.Date;

public class TB_Sources {
	private Integer ID;
	private String PicMessage = "";
	private Date UpdateTime = new Date();
	private TB_Unit Unit;
	
	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer iD) {
		this.ID = iD;
	}

	public String getPicMessage() {
		return this.PicMessage;
	}

	public void setPicMessage(String picMessage) {
		this.PicMessage = picMessage;
	}

	public Date getUpdateTime() {
		return this.UpdateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.UpdateTime = updateTime;
	}

	public TB_Unit getUnit() {
		return Unit;
	}

	public void setUnit(TB_Unit unit) {
		Unit = unit;
	}
}