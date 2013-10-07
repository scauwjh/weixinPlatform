package com.weixin.basic;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 一级按钮类
 * type 按钮类型，如click、view
 * key click对应的键值，非click时不set
 * sub_button 二级按钮，没有时不set
 * url view对应的跳转地址,非view时不set
 * 
 */
public class Weixin_Button extends Weixin_ButtonBasic {
	private String type = null;
	private String key = null;
	private Weixin_Button[] sub_button = null;
	private String url = null;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Weixin_Button[] getSub_button() {
		return this.sub_button;
	}

	public void setSub_button(Weixin_Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}