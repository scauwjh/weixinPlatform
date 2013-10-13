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
	
	public JSONObject get(Integer unitID){
		try{
			TB_Unit unit = unitDao.findByUnitID(unitID);
//			if(unit==null){
//				Exception e = new Exception("can not find the unit");
//				e.printStackTrace();
//				return null;
//			}
			JSONObject json = new JSONObject();
			json.element("unitID", unit.getUnitID());
			json.element("unitName", unit.getUnitName());
			json.element("appid", unit.getAppid());
			json.element("secret", unit.getSecret());
			json.element("introduction", unit.getIntroduction());
			json.element("autoReply", unit.getAutoReply());
			json.element("score", unit.getScore());
			json.element("term", unit.getTerm());
			json.element("welcomePage", unit.getWelcomePage());
			json.element("menu", unit.getMenu());
			json.element("updateTime", unit.getUpdateTime().toString());
			json.element("createTime", unit.getCreateTime().toString());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
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
}
