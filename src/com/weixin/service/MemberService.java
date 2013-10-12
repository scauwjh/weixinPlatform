package com.weixin.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinMemberDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinMember;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午8:58:03 
 * 
 *
 */
public class MemberService {
	
	private WeixinMemberDaoImpl memberDao = WeixinMemberDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	public boolean update(JSONObject data){
		try{
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
	//		member.setScore((Integer)data.get("score"));
	//		member.setTerm((Integer)data.get("term"));
			member.setTelephone(data.getString("telephone"));
			memberDao.saveOrUpdate(member);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public JSONObject get(Integer unitID){
		List<TB_WeixinMember> member = memberDao.findByUnit(unitID);
		if(member==null){
			return null;
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
		return print;
	}
}
