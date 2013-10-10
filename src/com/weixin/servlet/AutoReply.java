package com.weixin.servlet;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinMessageDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinMessage;
import com.weixin.utility.Hint;

import java.io.IOException;
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
 * 微信推送信息servlet
 * 
 * 1.添加图文信息
 * 2.更新自动回复（暂未实现）
 */
public class AutoReply extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private WeixinMessageDaoImpl messageDao = WeixinMessageDaoImpl.getInstance();
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
			update(request, response);
		} 
		else if(action.equals("get")){
			//管理
			manager(request,response);
		}
	}
	
	private void manager(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			Integer unitID = 1;//(Integer)request.getSession().getAttribute("unitID");
			TB_WeixinMessage message = messageDao.findByUnit(unitID);
			JSONObject json = new JSONObject();
			json.element("ID", message.getID());
			json.element("introduction", message.getIntroduction());
			json.element("message", message.getMessage());
			json.element("welcomeMessage", message.getWelcomeMessage());
			json.element("score", message.getScore());
			json.element("term", message.getTerm());
			json.element("unitID", message.getUnit().getUnitID());
			json.element("updateTime", message.getUpdateTime());
			out.println(json);
			return;
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
			return;
		}
	}
	
	private void update(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		try{
			//单位id，从session获取
			Integer unitID = (Integer)request.getSession().getAttribute("unitID");
			TB_WeixinMessage message = messageDao.findByUnit(unitID);
			if(message==null){
				message = new TB_WeixinMessage();
			}
			TB_Unit unit = unitDao.findByUnitID(unitID);
			message.setUnit(unit);
			String msg = request.getParameter("data");
			message.setMessage(msg);
			this.messageDao.saveOrUpdate(message);
			Hint.hint("更新成功", "autoreply.jsp", request, response);
			return;
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().println("error");
			return;
		}
	}
}