package com.weixin.service;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.basic.Weixin_Menu;
import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinKeyDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinKey;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午7:51:35 
 * 
 *
 */
public class MenuService {
	
	private WeixinKeyDaoImpl weixinKeyDao = WeixinKeyDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	private static String TOKEN = null;
	private static String URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	
	public String setMenu(JSONObject json,Integer unitID){
		
		System.out.println(json);
		//将自定义菜单的json数据保存到数据库
		TB_Unit unit = unitDao.findByUnitID(unitID);
		String ret = null;
		if(unit==null){
			ret = "没有该酒店！";
			return ret;
		}
		unit.setMenu(json.toString());
		unitDao.saveOrUpdate(unit);
		//获取按钮的josn数组
		JSONArray jsonArray = json.getJSONArray("button");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject tmpJson = JSONObject.fromObject(jsonArray.get(i));
			String type = (String)tmpJson.get("type");
			//判断是否有二级按钮，type为null即有二级按钮
			if(type==null){
				JSONArray sub = tmpJson.getJSONArray("sub_button");
				for(int j=0;j<sub.size();j++){
					JSONObject sub_json = (JSONObject)sub.get(j);
					String sub_key = (String)sub_json.get("key");
					if(sub_key==null)continue;
					JSONObject sub_message = (JSONObject) sub_json.get("message");
					System.out.println(sub_key+" "+sub_message);
					//保存key
					TB_WeixinKey weixinKey = weixinKeyDao.findByKeyValueandUnit(sub_key, unitID);
					if(weixinKey==null){
						weixinKey = new TB_WeixinKey();
					}
					weixinKey.setUnit(unit);
					weixinKey.setKeyValue(sub_key);
					weixinKey.setMessage(sub_message.toString());
					weixinKey.setUpdateTime(new Date());
					weixinKeyDao.saveOrUpdate(weixinKey);
				}
			}
			String key = (String)tmpJson.get("key");
			if(key==null)continue;
			JSONObject message = (JSONObject) tmpJson.get("message");
			System.out.println(key+" "+message);
			if(message==null)continue;
			//保存key
			TB_WeixinKey weixinKey = weixinKeyDao.findByKeyValueandUnit(key, unitID);
			if(weixinKey==null){
				weixinKey = new TB_WeixinKey();
			}
			weixinKey.setUnit(unit);
			weixinKey.setKeyValue(key);
			weixinKey.setMessage(message.toString());
			weixinKey.setUpdateTime(new Date());
			weixinKeyDao.saveOrUpdate(weixinKey);
		}
		String url = URL + TOKEN;
		
		JSONObject retJson = JSONObject.fromObject(Weixin_Menu.httpRequest(url,json));
		if (retJson.get("errcode").equals(0)) {
			ret = retJson.toString();
			return ret;
		}
		//从数据库获取appid和secret
		String appid = unit.getAppid();
		String secret = unit.getSecret();
		JSONObject tokenJson = Weixin_Menu.getToken(appid, secret);
		ret = tokenJson.toString();
		TOKEN = tokenJson.getString("access_token");
		url = URL + TOKEN;
		ret = Weixin_Menu.httpRequest(url, json).toString();
		return ret;
	}
	
	public String getMenu(Integer unitID){
		try{
			TB_Unit unit = unitDao.findByUnitID(unitID);
			String ret = unit.getMenu();
			return ret;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		System.out.println(new MenuService().getMenu(1));
	}
}
