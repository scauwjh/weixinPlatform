package com.weixin.basic;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 图文信息类
 * PicUrl 图片地址
 * Url 跳转地址
 * Title 标题
 * Des 详细说明
 * 
 */
public class Weixin_Articles {
	private String PicUrl = null;
	private String Url = null;
	private String Title = null;
	private String Des = null;

	public String getPicUrl() {
		return this.PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

	public String getUrl() {
		return this.Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDes() {
		return this.Des;
	}

	public void setDes(String des) {
		this.Des = des;
	}
}