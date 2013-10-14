package com.weixin.servlet;

import com.weixin.domain.TB_Unit;
import com.weixin.service.AutoReplyService;
import com.weixin.service.WeixinInterfaceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 微信服务器接口
 * 
 * 关注事件的推送
 * 自动回复，可定制JSON（尚未实现）
 * 绑定手机号
 */
public class WeixinInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//各种声明
	private WeixinInterfaceService weixinInterface = new WeixinInterfaceService();
	private AutoReplyService autoReply = null;//自动回复service对象，带参构造
	private Integer unitID = null;//单位id
	private String fromID = null;//发送者，用户的openid
	private String toID = null;//接收者id，也就是当前微信公众号的openid
	private String event = null;//点击、关注等事件
	private String eventKey = null;//事件的key值，主要是click
	private String recevidedContent = null;//接收到的数据
	private String sentContent = null;//发送的的数据
	private TB_Unit unit = null;//单位
	/**
	 * doGet
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println(request.getParameter("echostr"));
	}
	
	/**
	 * doPost
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//获取单位ID，new自动回复对象，查找单位
		unitID = Integer.parseInt(request.getParameter("id"));
		autoReply = new AutoReplyService(unitID);
		unit = weixinInterface.getUnit(unitID);
		
		//读取用户发送来的消息
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		//由获取的消息进行数据初始化
		getParameter(sb);
		boolean flag = false;
		sentContent = null;//返回的消息
		
		//判断是否为点击、关注等事件
		if (this.event != null) {
			//订阅事件处理
			if (this.event.equals("subscribe")) {
				sentContent = weixinInterface.subscribeAction(unitID,this.fromID,this.toID);
				flag = true;
			} 
			//click事件处理
			else {
				JSONObject tmpJson = weixinInterface.clickAction(unitID,this.eventKey,fromID,toID);
				if(tmpJson.getString("flag").equals("1")){
					flag = true;
				}
				sentContent = tmpJson.getString("ret");
			}

		}
		
		//绑定手机
		else if (this.recevidedContent.matches("#[0-9]*#")) {
			String[] spilt = this.recevidedContent.split("#");
			boolean ret = weixinInterface.boundTelephone(unitID, fromID, spilt[1]);
			if(ret){
				sentContent = "您已经成功绑定手机号码";
			}else{
				sentContent = "绑定失败";
			}
		} 
		
		//功能选择
		else if (this.recevidedContent.matches(" *[0-9] *")) {
			sentContent = autoReply.autoReply(recevidedContent,fromID,unitID);
		}
		
		//回复有误直接返回首页
		else {
			Integer welcomeID = unit.getWelcomePage();
			sentContent = weixinInterface.getMainPage(welcomeID, fromID, toID);
			flag = true;
		}
		
		//返回数据
		if (flag){
			//图文信息已经封装好，直接打印
			out.println(sentContent);
		}
		else{
			//文字消息尚未封装，需要进行封装
			String print = weixinInterface.getFontMsg(sentContent,this.fromID,this.toID);
			out.println(print);
		}
	}
	
	/**
	 * 获取各种id（参数）
	 * @param content
	 * 由用户发送过来的消息获取用户的openid和公众号的id
	 * 以及各种事件、内容
	 */
	private void getParameter(StringBuilder content) {
		String xmlS = content.toString();
		int user_s = xmlS.indexOf("<FromUserName><![CDATA[");
		int user_e = xmlS.indexOf("]]></FromUserName>");
		this.fromID = xmlS.substring(user_s + 23, user_e);

		int admin_s = xmlS.indexOf("<ToUserName><![CDATA[");
		int admin_e = xmlS.indexOf("]]></ToUserName>");
		this.toID = xmlS.substring(admin_s + 21, admin_e);
		try {
			int event_s = xmlS.indexOf("<Event><![CDATA[");
			int event_e = xmlS.indexOf("]]></Event>");
			this.event = xmlS.substring(event_s + 16, event_e);
		} catch (Exception e) {
			this.event = null;
		}
		try {
			int event_s = xmlS.indexOf("<EventKey><![CDATA[");
			int event_e = xmlS.indexOf("]]></EventKey>");
			this.eventKey = xmlS.substring(event_s + 19, event_e);
		} catch (Exception e) {
			this.eventKey = null;
		}
		try {
			int content_s = xmlS.indexOf("<Content><![CDATA[");
			int content_e = xmlS.indexOf("]]></Content>");
			this.recevidedContent = xmlS.substring(content_s + 18, content_e);
		} catch (Exception e) {
			this.recevidedContent = null;
		}
	}
}