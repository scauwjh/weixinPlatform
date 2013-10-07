package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.domain.TB_Unit;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月25日 下午18:04:27 
 *  
 * 优惠券
 */
public class Unit extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
	
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		//单位管理
		if(action.equals("get")){
			unitMessage(request,response);
		}
		//更新（添加）单位信息
		else if(action.equals("update")){
			updateUnit(request,response);
		}
		//删除单位
		else if(action.equals("delete")){
			
		}
	}
	
	private void updateUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			JSONObject data = JSONObject.fromObject(request.getParameter("data"));
			Integer unitID = (Integer)data.get("unitID");
			TB_Unit unit = unitDao.findByUnitID(unitID);
			if(unit==null){
				unit = new TB_Unit();
				unit.setUnitID(unitID);
			}
			unit.setUnitName(data.getString("unitName"));
			unit.setAppid(data.getString("appid"));
			unit.setSecret(data.getString("secret"));
			unit.setUnitMemo(data.getString("unitMemo"));
			unitDao.saveOrUpdate(unit);
			out.println("ok");
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
		
	}
	
	private void unitMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			Integer unitID = (Integer) request.getSession().getAttribute("unitID");
			TB_Unit unit = unitDao.findByUnitID(unitID);
			if(unit==null){
				out.println("error");
				Exception e = new Exception("can not find the unit");
				e.printStackTrace();
				return;
			}
			JSONObject print = new JSONObject();
			print.element("unitID", unit.getUnitID());
			print.element("unitName", unit.getUnitName());
			print.element("appid", unit.getAppid());
			print.element("secret", unit.getSecret());
			print.element("unitMemo", unit.getUnitMemo());
			print.element("createTime", unit.getCreateTime().toString());
			out.println(print);
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
	}

}
