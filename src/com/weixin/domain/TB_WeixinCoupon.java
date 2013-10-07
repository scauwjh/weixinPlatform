package com.weixin.domain;

import java.util.Date;

public class TB_WeixinCoupon {
	private Integer ID;
	private String CouponID;
	private String CouponName;
	private String Memo;
	private Date CreateTime = new Date();
	private Date StartedDate;
	private Integer ExpiredDays;//有效期，多少天
	private TB_Unit unit;
	
	public String getCouponName() {
		return this.CouponName;
	}

	public void setCouponName(String couponName) {
		this.CouponName = couponName;
	}

	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer iD) {
		this.ID = iD;
	}

	public String getCouponID() {
		return this.CouponID;
	}

	public void setCouponID(String couponID) {
		this.CouponID = couponID;
	}

	public String getMemo() {
		return this.Memo;
	}

	public void setMemo(String memo) {
		this.Memo = memo;
	}

	public Date getCreateTime() {
		return this.CreateTime;
	}

	public void setCreateTime(Date createTime) {
		this.CreateTime = createTime;
	}

	public Date getStartedDate() {
		return this.StartedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.StartedDate = startedDate;
	}

	public Integer getExpiredDays() {
		return ExpiredDays;
	}

	public void setExpiredDays(Integer expiredDays) {
		ExpiredDays = expiredDays;
	}

	public TB_Unit getUnit() {
		return unit;
	}

	public void setUnit(TB_Unit unit) {
		this.unit = unit;
	}
}