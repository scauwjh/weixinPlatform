package com.weixin.servlet;

import com.weixin.service.MessageService;

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
public class Message extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private MessageService msgService = new MessageService();
	private PrintWriter out = null;
	private Integer unitID = null;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	/**
	 * @param get 获取信息
	 * @param updateUM 更新单位信息
	 * @param updateAR 更新自动回复
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		out = response.getWriter();
		unitID = (Integer)request.getSession().getAttribute("unitID");
		if (action.equals("updateAR")) {
			//更新自动回复
			updateAutoReply(request, response);
		} else if(action.equals("get")){
			//获取单位信息
			getMessage(request,response);
		} else if(action.equals("updateUM")){
			//更新单位信息
			updateUnitMessage(request,response);
		}
	}
	
	private void getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject print = msgService.getMessage(unitID);
		if(print!=null){
			out.println(print);
			return;
		}
		out.println("error");
	}
	
	private void updateUnitMessage(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		boolean ret = msgService.saveOrUpdate(json);
		if(ret){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void updateAutoReply(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String autoReply = request.getParameter("data");
		boolean ret = msgService.updateAutoReply(unitID, autoReply);
		if(ret){
			out.println("ok");
			return;
		}
		out.println("error");
	}
}