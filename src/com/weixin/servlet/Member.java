package com.weixin.servlet;

import com.weixin.service.MemberService;

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
 * @date 2013-9-21
 * 
 * 会员管理
 */
public class Member extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberService();
	
	private PrintWriter out = null;
	
	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		String action = request.getParameter("action");
		if(action.equals("get")){
			memberManager(request,response);
		}
		else if(action.equals("update")){
			updateMember(request,response);
		}
	}
	
	/**
	 * 传递json
	 * 通过memberid和hotelid查找
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void updateMember(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		JSONObject data = JSONObject.fromObject(request.getParameter("data"));
		boolean flag = memberService.update(data);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void memberManager(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		JSONObject json = memberService.get(unitID);
		if(json!=null){
			out.println(json);
			return;
		}
		out.println("error");
	}
}