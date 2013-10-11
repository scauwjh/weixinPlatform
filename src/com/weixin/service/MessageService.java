package com.weixin.service;

import java.util.Date;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.UnitMessageDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_UnitMessage;

import net.sf.json.JSONObject;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月11日 下午1:52:32 
 * 
 *
 */
public class MessageService {
	
	private UnitMessageDaoImpl unitMessageDao = UnitMessageDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	public JSONObject getMessage(Integer unitID){
		try{
			TB_UnitMessage message = unitMessageDao.findByUnit(unitID);
			JSONObject json = new JSONObject();
			json.element("ID", message.getID());
			json.element("introduction", message.getIntroduction());
			json.element("message", message.getMessage());
			json.element("welcomeMessage", message.getWelcomeMessage());
			json.element("score", message.getScore());
			json.element("term", message.getTerm());
			json.element("unitID", message.getUnit().getUnitID());
			json.element("updateTime", message.getUpdateTime());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateAutoReply(Integer unitID, String msg){
		try{
			TB_UnitMessage message = unitMessageDao.findByUnit(unitID);
			if(message==null){
				message = new TB_UnitMessage();
			}
			TB_Unit unit = unitDao.findByUnitID(unitID);
			message.setUnit(unit);
			message.setMessage(msg);
			unitMessageDao.saveOrUpdate(message);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveOrUpdate(JSONObject json){
		Integer unitID = Integer.parseInt(json.getString("unitID"));
		String introduction = json.getString("introduction");
		Integer score = Integer.parseInt(json.getString("score"));
		Integer term = Integer.parseInt(json.getString("term"));
		String message = json.getString("message");
		Integer welcomeNum = Integer.parseInt(json.getString("welcomeNum"));
		Date updateTime = new Date();
		try{
			TB_UnitMessage unitMessage = unitMessageDao.findByUnit(unitID);
			if(unitMessage==null){
				unitMessage = new TB_UnitMessage();
			}
			TB_Unit unit = unitDao.findByUnitID(unitID);
			unitMessage.setUnit(unit);
			unitMessage.setIntroduction(introduction);
			unitMessage.setMessage(message);
			unitMessage.setScore(score);
			unitMessage.setTerm(term);
			unitMessage.setUpdateTime(updateTime);
			unitMessage.setWelcomeMessage(welcomeNum);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
