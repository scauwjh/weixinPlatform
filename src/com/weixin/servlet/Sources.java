package com.weixin.servlet;

import com.weixin.service.SourcesService;

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
public class Sources extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private SourcesService sourcesService = new SourcesService();
	private PrintWriter out = null;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		String action = request.getParameter("action");
		if (action.equals("update")) {
			//添加更新
			saveSource(request, response);
		} 
		else if(action.equals("delete")){
			//删除
		}
		else if(action.equals("get")){
			//管理
			getSources(request,response);
		}
	}
	
	private void getSources(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		JSONObject json = sourcesService.get(unitID);
		if(json!=null){
			out.println(json);
			return;
		}
		out.println("error");
	}
	
	private void saveSource(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//单位id，从session获取
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		boolean flag = sourcesService.save(json, unitID);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
}