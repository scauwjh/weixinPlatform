package com.weixin.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinPicMessageDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinPicMessage;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午9:09:53 
 * 
 *
 */
public class SourcesService {
	
	private WeixinPicMessageDaoImpl sourcesDao = WeixinPicMessageDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	public JSONObject get(Integer unitID){
		try{
			List<TB_WeixinPicMessage> list = sourcesDao.findByUnit(unitID);
			Integer num = 0;
			JSONArray array = new JSONArray();
			//遍历所有数据
			for(int i=0;i<list.size();i++){
				JSONObject json = new JSONObject();
				TB_WeixinPicMessage message = list.get(i);
				json.element("ID", message.getID());
				json.element("message", message.getPicMessage());
				json.element("unitID", message.getUnit().getUnitID());
				json.element("updateTime", message.getUpdateTime().toString());
				array.element(json);
				num++;
			}
			JSONObject print = new JSONObject();
			print.element("messageNum", num);
			print.element("message", array);
			return print;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(JSONObject json, Integer unitID){
		try{
			TB_WeixinPicMessage source = new TB_WeixinPicMessage();
			TB_Unit unit = unitDao.findByUnitID(unitID);
			source.setUnit(unit);
			String msg = json.getString("picMsg");
			source.setPicMessage(msg);
			sourcesDao.saveOrUpdate(source);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(JSONObject json, Integer sourceID){
		try{
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
