package com.weixin.servlet;

import com.weixin.basic.Weixin_Articles;
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
	private WeixinKeyDaoImpl keyDao = WeixinKeyDaoImpl.getInstance();
	private UnitMessageDaoImpl messageDao = UnitMessageDaoImpl.getInstance();
	private WeixinMemberDaoImpl memberDao = WeixinMemberDaoImpl.getInstance();
	private WeixinPicMessageDaoImpl picMessageDao = WeixinPicMessageDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();

	private String fromID = null;
	private String toID = null;
	private String event = null;
	private String eventKey = null;
	private String recevidedContent = null;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println(request.getParameter("echostr"));
	}
	
	protected List<Weixin_Articles> getPicMsgList(Integer welcomeID){
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
			articles.setDes(picDesc.getString(i));//("尊敬的客户，欢迎您体验UHOTEL酒店微信营销系统\n\n回复1：酒店资讯\n\n回复2：美食推荐\n\n回复3：绑定手机\n\n回复4：查询积分")
			articles.setTitle(picTitle.getString(i));
			articles.setPicUrl(picUrl.getString(i));
			articles.setUrl(jumpUrl.getString(i));
			list.add(articles);
		}
		return list;
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		getParameter(sb);
		//单位ID
		Integer unitID = Integer.parseInt(request.getParameter("id"));
		boolean flag = false;

		String sentContent = null;
		if (this.event != null) {
			//订阅事件处理
			if (this.event.equals("subscribe")) {
				Integer score = 0;
				Integer term = 0;
				TB_UnitMessage weixin = this.messageDao.findByUnit(unitID);
				if (weixin != null) {
					term = weixin.getTerm();
					score = weixin.getScore();
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
				Integer welcomeID = weixin.getWelcomeMessage();
				sentContent = picMsg(getPicMsgList(welcomeID));
				flag = true;
			} 
			//click事件处理
			else {
				String keyValue = this.eventKey;
				TB_WeixinKey key = this.keyDao.findByKeyValueandUnit(keyValue, unitID);
				JSONObject ret = JSONObject.fromObject(key.getMessage());
				if(ret.getString("type").equals("picMsg")){
					flag = true;
					TB_WeixinPicMessage picMsg = picMessageDao.findByID((Integer)ret.get("picMsgID"));
					JSONObject json = JSONObject.fromObject(picMsg.getPicMessage());
					sentContent = jsonToPicMsg(json);
				}
				else sentContent = ret.getString("msg");
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
			Integer tmp = Integer.parseInt(this.recevidedContent);
			switch (tmp) {
			case 1:
				sentContent = "广州纪梵酒店于198x年开业， 200X年装修，拥有900多间房，"
						+ "位于广州市繁盛商业区之心脏地带，交通极为便利，是中国首批三家白金酒店之一。"
						+ "广州纪梵酒店装饰富丽堂皇，拥有客、套房和数百套寓及写字楼，设备豪华，环境典雅舒适。"
						+ "别具特色的中西餐厅及酒吧，荟萃中、法、日等多国风味美食，配合细致殷勤。"
						+ "电话020-XXXXXX 传真020-XXXXX";
				break;
			case 2:
				flag = true;
				TB_UnitMessage weixinMsg = this.messageDao.findByUnit(unitID);
				TB_WeixinPicMessage picMsg = this.picMessageDao
						.findByID(weixinMsg.getWelcomeMessage());
				JSONObject json = new JSONObject();
				json = JSONObject.fromObject(picMsg.getPicMessage());
				Integer picNum = (Integer) json.get("picNum");
				JSONArray picUrl = json.getJSONArray("picUrl");
				JSONArray picTitle = json.getJSONArray("picTitle");
				JSONArray picDesc = json.getJSONArray("picDesc");
				JSONArray jumpUrl = json.getJSONArray("jumpUrl");
				List<Weixin_Articles> articles = new LinkedList<Weixin_Articles>();
				for (int i = 0; i < picNum.intValue(); i++) {
					Weixin_Articles art = new Weixin_Articles();
					art.setDes(picDesc.getString(i));
					art.setPicUrl(picUrl.getString(i));
					art.setTitle(picTitle.getString(i));
					art.setUrl(jumpUrl.getString(i));
					articles.add(art);
				}
				sentContent = picMsg(articles);
				break;
			case 3:
				sentContent = "您好，请按格式回复手机号码绑定手机（如：“#123456#”）";
				break;
			case 4:
				TB_WeixinMember member = this.memberDao.findByOpenIDandUnit(this.fromID, unitID);
				sentContent = "ID：" + member.getMemberID() + "的积分为："
						+ member.getScore() + "分";
				break;
			default:
				sentContent = "对不起，您的回复有误，请重新输入。";

				break;
			}
		} else {
			TB_UnitMessage weixin = messageDao.findByUnit(unitID);
			Integer welcomeID = weixin.getWelcomeMessage();
			sentContent = picMsg(getPicMsgList(welcomeID));
//			sentContent = "尊敬的客户，欢迎您体验UHOTEL酒店微信营销系统\n"
//					+ "回复1：酒店资讯\n"
//					+ "回复2：美食推荐\n"
//					+ "回复3：获取入住优惠\n"
//					+ "回复4：查询积分";
		}
		if (flag)
			out.println(sentContent);
		else
			out.println(fontMsg(sentContent));
	}

	protected void getParameter(StringBuilder content) {
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
	
	protected String jsonToPicMsg(JSONObject json){
		Integer num = (Integer)json.get("picNum");
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + this.fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + this.toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + num + "</ArticleCount>"
				+ "<Articles>";
		JSONArray title = json.getJSONArray("picTitle");
		JSONArray desc = json.getJSONArray("picDesc");
		JSONArray picUrl = json.getJSONArray("picUrl");
		JSONArray url = json.getJSONArray("jumpUrl");
		for (int i = 0; i < num; i++) {
			print += "<item>"
					+ "<Title><![CDATA[" + title.get(i) + "]]></Title>"
					+ "<Description><![CDATA[" + desc.get(i) + "]]></Description>"
					+ "<PicUrl><![CDATA[" + picUrl.get(i) + "]]></PicUrl>"
					+ "<Url><![CDATA[" + url.get(i) + "]]></Url>"
					+ "</item>";
		}
		print += "</Articles>"
				+ "</xml>";
		return print;
	}
	
	protected String picMsg(List<Weixin_Articles> articles) {
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + this.fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + this.toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + articles.size() + "</ArticleCount>"
				+ "<Articles>";
		for (int i = 0; i < articles.size(); i++) {
			print += "<item>"
					+ "<Title><![CDATA[" + articles.get(i).getTitle()+ "]]></Title>"
					+ "<Description><![CDATA[" + articles.get(i).getDes() + "]]></Description>"
					+ "<PicUrl><![CDATA[" + articles.get(i).getPicUrl() + "]]></PicUrl>"
					+ "<Url><![CDATA[" + articles.get(i).getUrl() + "]]></Url>"
					+ "</item>";
		}
		print += "</Articles>"
				+ "</xml>";

		return print;
	}

	protected String fontMsg(String content) {
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + this.fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + this.toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[" + content + "]]></Content>"
				+ "</xml>";
		return print;
	}
}