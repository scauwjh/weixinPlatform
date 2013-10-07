package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinCouponDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinCoupon;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月21日 下午1:59:27 
 *  
 * 优惠券
 */
public class Coupon extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private WeixinCouponDaoImpl couponDao = WeixinCouponDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	//private long day = 86400000;//86400000为一天的毫秒数
	
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
		try{
			JSONObject data = JSONObject.fromObject(request.getParameter("data"));
			TB_WeixinCoupon coupon = new TB_WeixinCoupon();
			coupon.setCouponID(data.getString("couponID"));
			coupon.setCouponName(data.getString("couponName"));
			coupon.setCreateTime(new Date());
			coupon.setExpiredDays((Integer)data.get("expiredDays"));
			coupon.setMemo(data.getString("memo"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startedDate = df.parse(data.getString("startedDate"));
			coupon.setStartedDate(startedDate);
			Integer unitID = (Integer)request.getSession().getAttribute("unitID");
			TB_Unit unit = unitDao.findByUnitID(unitID);
			coupon.setUnit(unit);
			couponDao.saveOrUpdate(coupon);
			out.println("ok");
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
		
	}
	
	private void updateCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			Integer unitID = (Integer) request.getSession().getAttribute("unitID");
			String couponID = request.getParameter("couponID");
			TB_WeixinCoupon coupon = couponDao.findByCouponIDandUnit(couponID, unitID);
			JSONObject data = JSONObject.fromObject(request.getParameter("data"));
			coupon.setCouponName(data.getString("couponName"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startedDate = df.parse(data.getString("startedDate"));
			coupon.setExpiredDays((Integer)data.get("expiredDays"));
			coupon.setMemo(data.getString("memo"));
			coupon.setStartedDate(startedDate);
			couponDao.saveOrUpdate(coupon);
			out.println("ok");
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
	}
	
	private void couponManager(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		List<TB_WeixinCoupon> list = couponDao.findByUnit(unitID);
		if(list==null){
			out.println("error");
			return;
		}
		Integer num = 0;
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json = new JSONObject();
			TB_WeixinCoupon coupon = list.get(i);
			json.element("couponID", coupon.getCouponID());
			json.element("couponName", coupon.getCouponName());
			json.element("createTime", coupon.getCreateTime().toString());
			json.element("expiredDate", coupon.getExpiredDays());
			json.element("ID", coupon.getID());
			json.element("memo", coupon.getMemo());
			json.element("startedDate", coupon.getStartedDate().toString());
			json.element("unitID", coupon.getUnit().getUnitID());
			//session没有这东西
			//json.element("unitName", coupon.getUnit().getUnitName());
			array.add(json);
			num++;
		}
		JSONObject print = new JSONObject();
		print.element("coupon", array);
		print.element("couponNum", num);
		out.println(print);
	}

}
