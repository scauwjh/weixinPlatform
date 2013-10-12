package com.weixin.servlet;

import com.weixin.basic.Weixin_Articles;
import com.weixin.basic.Weixin_AssemblyXML;
import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinKeyDaoImpl;
import com.weixin.daoimpl.WeixinMemberDaoImpl;
import com.weixin.daoimpl.UnitMessageDaoImpl;
import com.weixin.daoimpl.WeixinPicMessageDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinKey;
import com.weixin.domain.TB_WeixinMember;
import com.weixin.domain.TB_UnitMessage;
import com.weixin.domain.TB_WeixinPicMessage;
import com.weixin.service.AutoReplyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
	
	//各种dao的声明
	private WeixinKeyDaoImpl keyDao = WeixinKeyDaoImpl.getInstance();
	private UnitMessageDaoImpl messageDao = UnitMessageDaoImpl.getInstance();
	private WeixinMemberDaoImpl memberDao = WeixinMemberDaoImpl.getInstance();
	private WeixinPicMessageDaoImpl picMessageDao = WeixinPicMessageDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	//各种参数的声明
	private Weixin_AssemblyXML ambXML = new Weixin_AssemblyXML();
	private AutoReplyService autoReply = null;//自动回复service对象，带参构造
	private Integer unitID = null;//单位id
	private String fromID = null;//发送者，用户的openid
	private String toID = null;//接收者id，也就是当前微信公众号的openid
	private String event = null;//点击、关注等事件
	private String eventKey = null;//事件的key值，主要是click
	private String recevidedContent = null;//接收到的数据
	private String sentContent = null;//发送的的数据
	private TB_UnitMessage unitMessage = null;//单位信息类
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
		
		//获取单位ID，new自动回复对象，查找单位信息
		unitID = Integer.parseInt(request.getParameter("id"));
		autoReply = new AutoReplyService(unitID);
		unitMessage = messageDao.findByUnit(unitID);
		
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
				subscribeAction();
				flag = true;
			} 
			//click事件处理
			else {
				flag = clickAction();
			}

		}
		
		//绑定手机
		else if (this.recevidedContent.matches("#[0-9]*#")) {
			String[] spilt = this.recevidedContent.split("#");
			TB_WeixinMember member = this.memberDao.findByOpenIDandUnit(this.fromID, unitID);
			if (member == null) {
				member = new TB_WeixinMember();
			}
			member.setCreateTime(new Date());
			TB_Unit unit = unitDao.findByUnitID(unitID);
			member.setUnit(unit);
			member.setOpenID(this.fromID);
			member.setTelephone(spilt[1]);
			this.memberDao.saveOrUpdate(member);
			sentContent = "您已经成功绑定了手机号码";
		} 
		
		//功能选择
		else if (this.recevidedContent.matches(" *[0-9] *")) {
			sentContent = autoReply.autoReply(this.recevidedContent,this.fromID,this.unitID);
		}
		
		//回复有误直接返回首页
		else {
			Integer welcomeID = unitMessage.getWelcomeMessage();
			sentContent = ambXML.picMsg(getPicMsgList(welcomeID),this.fromID,this.toID);
			flag = true;
		}
		
		//返回数据
		if (flag){
			//图文信息已经封装好，直接打印
			out.println(sentContent);
		}
		else{
			//文字消息尚未封装，需要进行封装
			out.println(ambXML.fontMsg(sentContent,this.fromID,this.toID));
		}
	}
	
	/**
	 * 订阅处理
	 */
	private void subscribeAction(){
		Integer score = 0;
		Integer term = 0;
		if (unitMessage != null) {
			term = unitMessage.getTerm();
			score = unitMessage.getScore();
		}
		TB_WeixinMember member = this.memberDao.findByOpenIDandUnit(this.fromID, unitID);
		if (member == null) {
			member = new TB_WeixinMember();
			TB_Unit unit = unitDao.findByUnitID(unitID);
			member.setUnit(unit);
			member.setOpenID(this.fromID);
			boolean tmpFlag = false;
			String memberID = "";
			while (!tmpFlag) {
				for (int i = 0; i < 7; i++) {
					memberID = memberID+ Integer.toString((int) (Math.random() * 10));
				}
				if (this.memberDao.findByMemberIDandUnit(memberID,unitID) == null)
					tmpFlag = true;
			}
			member.setMemberID(memberID);
			this.memberDao.saveOrUpdate(member);
		}
		member.setScore(member.getScore()+score);
		member.setTerm(term);
		this.memberDao.saveOrUpdate(member);
		//欢迎页面
		Integer welcomeID = unitMessage.getWelcomeMessage();
		sentContent = ambXML.picMsg(getPicMsgList(welcomeID),this.fromID,this.toID);
	}
	
	/**
	 * 点击处理
	 * return boolean flag
	 * true为图文消息，false为文字消息
	 */
	private boolean clickAction(){
		boolean flag = false;
		String keyValue = this.eventKey;
		TB_WeixinKey key = this.keyDao.findByKeyValueandUnit(keyValue, unitID);
		JSONObject ret = JSONObject.fromObject(key.getMessage());
		if(ret.getString("type").equals("picMsg")){
			flag = true;
			TB_WeixinPicMessage picMsg = picMessageDao.findByID((Integer)ret.get("picMsgID"));
			JSONObject json = JSONObject.fromObject(picMsg.getPicMessage());
			sentContent = ambXML.jsonToPicMsg(json,this.fromID,this.toID);
		}
		else sentContent = ret.getString("msg");
		return flag;
	}
	
	/**
	 * 获取图文信息的list
	 * @param welcomeID
	 * @return
	 * 
	 * 由数据库中的welcomeMessage(这里是welcomeID)
	 * 读取图文消息表获取数据
	 * 再拼装list
	 */
	private List<Weixin_Articles> getPicMsgList(Integer welcomeID){
		TB_WeixinPicMessage picMessage = picMessageDao.findByID(welcomeID);
		JSONObject picMsg = JSONObject.fromObject(picMessage.getPicMessage());
		
		List<Weixin_Articles> list = new LinkedList<Weixin_Articles>();
		Integer num = (Integer)picMsg.get("picNum");
		JSONArray picDesc = JSONArray.fromObject(picMsg.getString("picDesc"));
		JSONArray picTitle = JSONArray.fromObject(picMsg.getString("picTitle"));
		JSONArray picUrl = JSONArray.fromObject(picMsg.getString("picUrl"));
		JSONArray jumpUrl = JSONArray.fromObject(picMsg.getString("jumpUrl"));
		for(int i=0;i<num;i++){
			Weixin_Articles articles = new Weixin_Articles();
			articles.setDes(picDesc.getString(i));
			articles.setTitle(picTitle.getString(i));
			articles.setPicUrl(picUrl.getString(i));
			articles.setUrl(jumpUrl.getString(i));
			list.add(articles);
		}
		return list;
	}
	
	/**
	 * 获取各种id（参数）
	 * @param content
	 * 
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