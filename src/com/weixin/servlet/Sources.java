package com.weixin.servlet;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinPicMessageDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinPicMessage;
import com.weixin.utility.Hint;

import java.io.IOException;
import java.io.PrintWriter;
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
 * 微信推送信息servlet
 * 
 * 1.添加图文信息
 * 2.更新自动回复（暂未实现）
 */
public class Sources extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private WeixinPicMessageDaoImpl picMessageDao = WeixinPicMessageDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		if (action.equals("update")) {
			//添加更新
			addPicMsg(request, response);
		} 
		else if(action.equals("delete")){
			//删除
		}
		else if(action.equals("get")){
			//管理
			msgManager(request,response);
		}
	}
	
	private void msgManager(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			Integer unitID = (Integer)request.getSession().getAttribute("unitID");
			List<TB_WeixinPicMessage> list = picMessageDao.findByUnit(unitID);
			Integer num = 0;
			JSONArray array = new JSONArray();
			//遍历所有数据
			for(int i=0;i<list.size();i++){
				JSONObject json = new JSONObject();
				TB_WeixinPicMessage message = list.get(i);
				json.element("ID", message.getID());
				json.element("message", message.getPicMessage());
				json.element("unitID", message.getUnit().getUnitID());
				json.element("updateTime", message.getUpdateTime().toString());
				array.element(json);
				num++;
			}
			JSONObject print = new JSONObject();
			print.element("messageNum", num);
			print.element("message", array);
			out.println(print);
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
	}
	
	private void addPicMsg(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		try{
			//单位id，从session获取
			Integer unitID = (Integer)request.getSession().getAttribute("unitID");
			TB_WeixinPicMessage picMsg = new TB_WeixinPicMessage();
			TB_Unit unit = unitDao.findByUnitID(unitID);
			picMsg.setUnit(unit);
			String msg = request.getParameter("picMsg");
			picMsg.setPicMessage(msg);
			this.picMessageDao.saveOrUpdate(picMsg);
			Hint.hint("保存成功", "addpicmessage.jsp", request, response);
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().println("add picmsg error");
		}
	}
}