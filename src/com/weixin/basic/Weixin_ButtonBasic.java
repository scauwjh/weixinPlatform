package com.weixin.basic;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 自定义菜单的按钮基类
 * name 按钮名字，微信上显示的
 * 
 */
public class Weixin_ButtonBasic {
	
	private String name = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}