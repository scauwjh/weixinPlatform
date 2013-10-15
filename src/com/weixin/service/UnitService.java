package com.weixin.service;

import java.util.Date;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.domain.TB_Unit;

import net.sf.json.JSONObject;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午11:41:20 
 * 
 *
 */
public class UnitService {
	
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	/**
	 * get
	 * @param unitID
	 * @return
	 */
	public JSONObject get(Integer unitID){
		try{
			TB_Unit unit = unitDao.findByUnitID(unitID);
//			if(unit==null){
//				Exception e = new Exception("can not find the unit");
//				e.printStackTrace();
//				return null;
//			}
			JSONObject json = new JSONObject();
			json.put("unitID", unit.getUnitID());
			json.put("unitName", unit.getUnitName());
			json.put("appid", unit.getAppid());
			json.put("secret", unit.getSecret());
			json.put("introduction", unit.getIntroduction());
			json.put("autoReply", unit.getAutoReply());
			json.put("score", unit.getScore());
			json.put("term", unit.getTerm());
			json.put("welcomePage", unit.getWelcomePage());
			json.put("menu", unit.getMenu());
			json.put("updateTime", unit.getUpdateTime().toString());
			json.put("createTime", unit.getCreateTime().toString());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * saveOrUpdate
	 * @param data
	 * @return
	 */
	public boolean saveOrUpdate(JSONObject data){
		try{
			Integer unitID = (Integer)data.get("unitID");
			TB_Unit unit = unitDao.findByUnitID(unitID);
			if(unit==null){
				unit = new TB_Unit();
				unit.setUnitID(unitID);
			}
			unit.setAppid(data.getString("appid"));
			unit.setAutoReply(data.getString("autoReply"));
			unit.setIntroduction(data.getString("introduction"));
			unit.setMenu(data.getString("menu"));
			unit.setScore((Integer)data.get("score"));
			unit.setSecret(data.getString("secret"));
			unit.setTerm((Integer)data.get("term"));
			unit.setUnitName(data.getString("unitName"));
			unit.setUpdateTime(new Date());
			unit.setWelcomePage((Integer)data.get("welcomePage"));
			unitDao.saveOrUpdate(unit);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 更新自动回复
	 * @param unitID
	 * @param autoReply
	 * @return boolean
	 */
	public boolean updateAutoReply(Integer unitID, String autoReply){
		try{
			TB_Unit unit = unitDao.findByUnitID(unitID);
			unit.setAutoReply(autoReply);
			unitDao.saveOrUpdate(unit);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取积分
	 * @param unitID
	 * @return json含两个节点：score和term
	 */
	public JSONObject getIntegral(Integer unitID){
		JSONObject json = get(unitID);
		if(json!=null){
			JSONObject ret = new JSONObject();
			ret.put("score", json.get("score"));
			ret.put("term", json.get("term"));
			return ret;
		}
		return null;
	}
	
	/**
	 * 更新积分
	 * @param json json含两个节点：score和term
	 * @param unitID
	 * @return
	 */
	public boolean updateIntegral(JSONObject json,Integer unitID){
		try{
			TB_Unit unit = unitDao.findByUnitID(unitID);
			unit.setScore((Integer)json.get("score"));
			unit.setTerm((Integer)json.get("term"));
			unitDao.saveOrUpdate(unit);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
