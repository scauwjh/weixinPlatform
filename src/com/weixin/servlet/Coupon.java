package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.weixin.service.CouponService;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月21日 下午1:59:27 
 *  
 * 优惠券
 */
public class Coupon extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CouponService couponService = new CouponService();
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		//获取优惠券信息
		if(action.equals("get")){
			couponManager(request,response);
		}
		//更新优惠券
		else if(action.equals("update")){
			updateCoupon(request,response);
		}
		//删除优惠券
		else if(action.equals("delete")){
			
		}
		//添加优惠券
		else if(action.equals("add")){
			addCoupon(request,response);
		}
	}
	
	private void addCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject data = JSONObject.fromObject(request.getParameter("data"));
		Integer unitID = (Integer)request.getSession().getAttribute("unitID");
		boolean flag = couponService.addCoupon(data,unitID);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void updateCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		JSONObject data = JSONObject.fromObject(request.getParameter("data"));
		boolean flag = couponService.update(data,unitID);
		if(flag){
			out.println("ok");
			return;
		}
		out.println("error");
	}
	
	private void couponManager(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		JSONObject print = couponService.get(unitID);
		if(print==null){
			out.println("error");
			return;
		}
		out.println(print);
	}

}
