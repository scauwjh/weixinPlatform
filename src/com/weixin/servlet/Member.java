package com.weixin.servlet;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinMemberDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinMember;

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
 * @date 2013-9-21
 * 
 * 会员管理
 */
public class Member extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WeixinMemberDaoImpl memberDao = WeixinMemberDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try{
			JSONObject data = JSONObject.fromObject(request.getParameter("data"));
			String memberID = (String) data.get("memberID");
			Integer unitID = (Integer) data.get("unitID");
			TB_WeixinMember member = memberDao.findByMemberIDandUnit(memberID, unitID);
			if(member==null){
				member = new TB_WeixinMember();
				member.setMemberID(memberID);
				TB_Unit unit = unitDao.findByUnitID(unitID);
				member.setUnit(unit);
			}
			member.setCoupon(data.getString("coupon"));
//			member.setScore((Integer)data.get("score"));
//			member.setTerm((Integer)data.get("term"));
			member.setTelephone(data.getString("telephone"));
			memberDao.saveOrUpdate(member);
			out.println("ok");
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
	}
	
	private void memberManager(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Integer unitID = (Integer) request.getSession().getAttribute("unitID");
		List<TB_WeixinMember> member = memberDao.findByUnit(unitID);
		if(member==null){
			out.println("error");
			return;
		}
		JSONArray array = new JSONArray();
		for(int i=0;i<member.size();i++){
			JSONObject json = new JSONObject();
			TB_WeixinMember tmpTB = member.get(i);
			json.element("coupon",tmpTB.getCoupon());
			json.element("createTime", tmpTB.getCreateTime().toString());
			json.element("ID", tmpTB.getID());
			json.element("memberID", tmpTB.getMemberID());
			json.element("openID", tmpTB.getOpenID());
			json.element("score", tmpTB.getScore());
			json.element("telephone", tmpTB.getTelephone());
			json.element("term", tmpTB.getTerm());
			json.element("unitID", tmpTB.getUnit().getUnitID());
			//json.element("unitName", tmpTB.getUnit().getUnitName());
			array.add(json);
		}
		JSONObject print = new JSONObject();
		print.element("member", array);
		out.println(print);
	}
}