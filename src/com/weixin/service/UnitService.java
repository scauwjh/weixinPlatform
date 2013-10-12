package com.weixin.service;

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
			JSONObject print = new JSONObject();
			print.element("unitID", unit.getUnitID());
			print.element("unitName", unit.getUnitName());
			print.element("appid", unit.getAppid());
			print.element("secret", unit.getSecret());
			print.element("unitMemo", unit.getUnitMemo());
			print.element("createTime", unit.getCreateTime().toString());
			return print;
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
			unit.setUnitName(data.getString("unitName"));
			unit.setAppid(data.getString("appid"));
			unit.setSecret(data.getString("secret"));
			unit.setUnitMemo(data.getString("unitMemo"));
			unitDao.saveOrUpdate(unit);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
