package com.weixin.servlet;

import com.weixin.basic.Weixin_Menu;
import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinKeyDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinKey;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
 * 创建菜单servlet
 * 
 * TOKEN过时会重新获取
 * 从前端获取JSON数据
 */
public class CreateMenu extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String TOKEN = null;

	private WeixinKeyDaoImpl weixinKeyDao = WeixinKeyDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if(action.equals("post")){
			createMenu(request,response);
		}
		else{
			getMenuDatas(request,response);
		}
	}

	private void createMenu(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//单位id，从session获取
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		JSONObject requestJson = JSONObject.fromObject(request.getParameter("data"));
		System.out.println(requestJson);
		//将自定义菜单的json数据保存到数据库
		TB_Unit unit = unitDao.findByUnitID(unitID);
		if(unit==null){
			out.println("没有该酒店！");
			return;
		}
		unit.setMenu(requestJson.toString());
		unitDao.saveOrUpdate(unit);
		//获取按钮的josn数组
		JSONArray jsonArray = requestJson.getJSONArray("button");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject tmpJson = JSONObject.fromObject(jsonArray.get(i));
			String type = (String)tmpJson.get("type");
			//判断是否有二级按钮，type为null即有二级按钮
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
		//从数据库获取appid和secret
		String appid = unit.getAppid();
		String secret = unit.getSecret();
		JSONObject tokenJson = Weixin_Menu.getToken(appid, secret);
		response.getWriter().println(tokenJson);
		TOKEN = tokenJson.getString("access_token");

		url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + TOKEN;
		out.println(Weixin_Menu.httpRequest(url, requestJson));
	}
	
	private void getMenuDatas(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//单位id，从session获取
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		TB_Unit unit = unitDao.findByUnitID(unitID);
		try{
			response.getWriter().println(unit.getMenu());
			return;
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().println("error");
			return;
		}
	}
}