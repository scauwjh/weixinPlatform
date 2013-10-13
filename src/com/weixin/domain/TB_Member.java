package com.weixin.domain;

import java.util.Date;

public class TB_Member {
	private Integer ID;
	private String MemberID = "";//会员ID
	private Date CreateTime = new Date();//创建时间
	private String OpenID = "";//微信加密的串，微信用户的唯一标识
	private Integer Score = 0;//积分
	private Integer Term = 0;//最后获取积分的期数
	private String Coupon = "";//优惠券JSON数组，含优惠券ID和获取时间
	private String Telephone = "";//绑定的电话号码
	private String LastInput;//记忆用户上一次输入的值
	private Date LastTime;//记忆用户上一次输入的时间
	private TB_Unit Unit;//所属单位
	
	public String getTelephone() {
		return this.Telephone;
	}

	public void setTelephone(String telephone) {
		this.Telephone = telephone;
	}

	public String getCoupon() {
		return this.Coupon;
	}

	public void setCoupon(String coupon) {
		this.Coupon = coupon;
	}

	public Integer getTerm() {
		return this.Term;
	}

	public void setTerm(Integer term) {
		this.Term = term;
	}

	public Integer getScore() {
		return this.Score;
	}

	public void setScore(Integer score) {
		this.Score = score;
	}

	public String getOpenID() {
		return this.OpenID;
	}

	public void setOpenID(String openID) {
		this.OpenID = openID;
	}

	public String getMemberID() {
		return this.MemberID;
	}

	public void setMemberID(String memberID) {
		this.MemberID = memberID;
	}

	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public Date getCreateTime() {
		return this.CreateTime;
	}

	public void setCreateTime(Date createTime) {
		this.CreateTime = createTime;
	}

	public String getLastInput() {
		return LastInput;
	}

	public void setLastInput(String lastInput) {
		LastInput = lastInput;
	}

	public Date getLastTime() {
		return LastTime;
	}

	public void setLastTime(Date lastTime) {
		LastTime = lastTime;
	}

	public TB_Unit getUnit() {
		return Unit;
	}

	public void setUnit(TB_Unit unit) {
		Unit = unit;
	}
}