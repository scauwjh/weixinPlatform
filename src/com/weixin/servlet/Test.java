package com.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.json.JSONObject;
//
//import com.weixin.dao.UnitDao;
//import com.weixin.daoimpl.UnitDaoImpl;
//import com.weixin.daoimpl.UserDaoImpl;
//import com.weixin.daoimpl.WeixinKeyDaoImpl;
//import com.weixin.domain.TB_Unit;
//import com.weixin.domain.TB_User;
//import com.weixin.domain.TB_WeixinKey;
//import com.weixin.utility.Hint;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 测试用的servlet
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private WeixinKeyDaoImpl weixinKeyDao = WeixinKeyDaoImpl.getInstance();
//	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
//	private UserDaoImpl userDao = UserDaoImpl.getInstance();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		
//		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
//		response.getWriter().println(json.getString("coupon"));
		
//		try{
//			TB_Unit unit = new TB_Unit();
//			unit.setAppid("wx6c5157bbf599d162");
//			unit.setSecret("f7a0889bf9162c7f642e77e5bca1bc2a");
//			unit.setUnitName("wjh公司");
//			unitDao.saveOrUpdate(unit);
//			unit = unitDao.findByUnitID(1);
//			TB_User user = new TB_User();
//			user.setPassword("123");
//			user.setRole(2);
//			user.setUnit(unit);
//			user.setUserAccount("wjh");
//			userDao.saveOrUpdate(user);
//			Hint.hint("添加成功！", "menu.jsp", request, response);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		TB_WeixinKey key = weixinKeyDao.findByKeyValueandUnit("fuck2", 1);
//		if(key==null){
//			System.out.println("!!");
//			return;
//		}
//		System.out.println(key.getKeyValue());
//		TB_WeixinKey key = new TB_WeixinKey();
//		key.setKeyValue("fuck");
//		key.setMessage("fuck2");
//		TB_Unit unit = unitDao.findByUnitID(1);
//		key.setUnit(unit);
//		weixinKeyDao.saveOrUpdate(key);
//		TB_User user = new TB_User();
//		user.setPassword("123");
//		user.setUserAccount("wjh");
//		user.setRole(1);
//		TB_Unit unit = unitDao.findByUnitID(1);
//		user.setUnit(unit);
//		userDao.saveOrUpdate(user);
	}
	/*
	protected void createmenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//unitID 先定为1
		Integer unitID = 1;
		JSONObject requestJson = JSONObject.fromObject(request.getParameter("data"));
		System.out.println(requestJson);
		JSONArray jsonArray = requestJson.getJSONArray("button");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject tmpJson = JSONObject.fromObject(jsonArray.get(i));
			String type = (String)tmpJson.get("type");
			if(type==null){
				JSONArray sub = tmpJson.getJSONArray("sub_button");
				for(int j=0;j<sub.size();j++){
					JSONObject sub_json = (JSONObject)sub.get(j);
					String sub_key = (String)sub_json.get("key");
					if(sub_key==null)continue;
					JSONObject sub_message = (JSONObject) sub_json.get("message");
					System.out.println(sub_key+" "+sub_message);
					//保存key
					TB_WeixinKey weixinKey = weixinKeyDao.findByKeyValueandUnit(sub_key, unitID);
					if(weixinKey==null){
						weixinKey = new TB_WeixinKey();
					}
					TB_Unit unit = unitDao.findByUnitID(unitID);
					weixinKey.setUnit(unit);
					weixinKey.setKeyValue(sub_key);
					weixinKey.setMessage(sub_message.toString());
					weixinKey.setUpdateTime(new Date());
					weixinKeyDao.saveOrUpdate(weixinKey);
				}
			}
			String key = (String)tmpJson.get("key");
			if(key==null)continue;
			JSONObject message = (JSONObject) tmpJson.get("message");
			System.out.println(key+" "+message);
			//保存key
			TB_WeixinKey weixinKey = weixinKeyDao.findByKeyValueandUnit(key, unitID);
			if(weixinKey==null){
				weixinKey = new TB_WeixinKey();
			}
			TB_Unit unit = unitDao.findByUnitID(unitID);
			weixinKey.setUnit(unit);
			weixinKey.setKeyValue(key);
			weixinKey.setMessage(message.toString());
			weixinKey.setUpdateTime(new Date());
			weixinKeyDao.saveOrUpdate(weixinKey);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TOKEN;
		
		JSONObject ret = JSONObject.fromObject(Weixin_Menu.httpRequest(url,requestJson));
		
		if (ret.get("errcode").equals(0)) {
			out.println(ret);
			System.out.println(ret);
			return;
		}
		out.println("TOKEN已过时，重新获取TOKEN...");

		String appid = "wx6c5157bbf599d162";
		String secret = "f7a0889bf9162c7f642e77e5bca1bc2a";
		JSONObject tokenJson = Weixin_Menu.getToken(appid, secret);
		response.getWriter().println(tokenJson);
		TOKEN = tokenJson.getString("access_token");

		url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TOKEN;
		out.println(Weixin_Menu.httpRequest(url, requestJson));
	}
	
	protected String jsonToPicMsg(JSONObject json){
		System.out.println(json);
		Integer num = (Integer)json.get("picNum");
		String print = "<xml><ToUserName><![CDATA[" + this.fromID
				+ "]]></ToUserName>" + "<FromUserName><![CDATA[" + this.toID
				+ "]]></FromUserName>" + "<CreateTime>" + new Date().getTime()
				+ "</CreateTime>" + "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + num + "</ArticleCount>"
				+ "<Articles>";
		JSONArray title = json.getJSONArray("picTitle");
		JSONArray desc = json.getJSONArray("picDesc");
		JSONArray picUrl = json.getJSONArray("picUrl");
		JSONArray url = json.getJSONArray("jumpUrl");
		for (int i = 0; i < num; i++) {
			print = print + "<item><Title><![CDATA["
					+ title.get(i)
					+ "]]></Title>" + "<Description><![CDATA["
					+ desc.get(i)
					+ "]]></Description>" + "<PicUrl><![CDATA["
					+ picUrl.get(i)
					+ "]]></PicUrl>" + "<Url><![CDATA["
					+ url.get(i)
					+ "]]></Url>" + "</item>";
		}
		print = print + "</Articles></xml>";
		return print;
	}*/
}
