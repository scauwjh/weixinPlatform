package com.weixin.domain;

import java.util.Date;

public class TB_WeixinKey {
	private Integer ID;
	private String KeyValue;
	private String Message;
	private Date UpdateTime = new Date();
	private TB_Unit unit;
	
	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public String getKeyValue() {
		return this.KeyValue;
	}

	public void setKeyValue(String key) {
		this.KeyValue = key;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String message) {
		this.Message = message;
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