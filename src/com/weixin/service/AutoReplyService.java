package com.weixin.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import com.weixin.daoimpl.WeixinMemberDaoImpl;
import com.weixin.domain.TB_WeixinMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月10日 下午3:48:06 
 * 
 * 自动回复Service
 */
public class AutoReplyService {
	
	/**
	 * map里面的json格式 :
	 * {
	 * 	"child":"0",//0表示没有子节点，1表示有
	 * 	"message":"123456"
	 * }
	 */
	private WeixinMemberDaoImpl memberDao = WeixinMemberDaoImpl.getInstance();
	
	private Map<String,JSONObject> map = null;
	private JSONObject unitMessage = null;
	private JSONObject autoReply = null;
	private JSONArray replyArray = null;
	
	
	/**
	 * 有参构造方法
	 * @param unitID
	 */
	public AutoReplyService(Integer unitID){
		this.map = new HashMap<String,JSONObject>();
		unitMessage = new MessageService().getMessage(unitID);
		autoReply = JSONObject.fromObject(unitMessage.get("message"));
		replyArray = JSONArray.fromObject(autoReply.get("message"));
		ergodicJsonArray(replyArray);
		addHint();
	}
	
	/**
	 * @param array 自动回复的JSONArray
	 * 拼装首页菜单
	 */
	private String printMenu(JSONArray array){
		String str = "";
		for(int i=0;i<array.size();i++){
			JSONObject json = JSONObject.fromObject(array.get(i));
			String msg = json.getString("message");
			str += msg+"\n\n";
		}
		return str;
	}
	
	/**
	 * @param array 自动回复的JSONArray
	 * 遍历array，用map存储内容
	 */
	private void ergodicJsonArray(JSONArray array){
		for(int i=0;i<array.size();i++){
			JSONObject json = JSONObject.fromObject(array.get(i));
			String num = json.getString("num");
			if(json.get("child")!=null){
				if(!num.equals("root")){
					String msg = "";
					if(map.get(num)!=null){
						msg = map.get(num).getString("message");
					}
					msg += json.getString("message") + "\n";
					JSONObject tmpJson = new JSONObject();
					tmpJson.put("child", "1");
					tmpJson.put("num", num);
					tmpJson.put("message", msg);
					//加进map
					map.put(num, tmpJson);
				}
				JSONArray tmpArray = JSONArray.fromObject(json.get("child"));
				ergodicJsonArray(tmpArray);
			}
			else if(!num.equals("root")){
				JSONObject tmpJson = new JSONObject();
				tmpJson.put("child", "0");
				tmpJson.put("num", num);
				tmpJson.put("message", json.getString("message"));
				//加进map
				map.put(num, tmpJson);
			}
		}
		return;
	}
	
	/**
	 * 添加返回提示
	 */
	private void addHint(){
		Collection<JSONObject> c = map.values();
		Iterator<JSONObject> it = c.iterator();
		for(;it.hasNext();){
			JSONObject json = it.next();
			if(json.getString("child").equals("1")){
				String msg = json.getString("message");
				msg += "回复0    返回首页";
				json.put("message", msg);
				map.put(json.getString("num"), json);
			}
		}
	}
	
	/**
	 * 自动回复
	 * @param unitID
	 * @param key
	 * @return String
	 */
	public String autoReply(String key, String fromID, Integer unitID){
		try{
			String ret = null;
			JSONObject tmpJson = null;
			if(key.equals("0")){
				ret = printMenu(replyArray);
			}else{
				TB_WeixinMember member = memberDao.findByOpenIDandUnit(fromID, unitID);
				String lastInput = member.getLastInput();
				if(key.equals("0")||lastInput==null){
					lastInput = "";
				}
				lastInput += key;
				member.setLastInput(lastInput);
				member.setLastTime(new Date());
				memberDao.saveOrUpdate(member);
				
				tmpJson = map.get(lastInput);
				if(tmpJson==null){
					ret = "您的输入有误\n";
					ret += printMenu(replyArray);
				}
				else{
					ret = tmpJson.getString("message");
				}
			}
			return ret;
		}catch(Exception e){
			e.printStackTrace();
			String ret = "error";
			return ret;
		}
	}
	
	public static void main(String args[]){
		AutoReplyService ARService = new AutoReplyService(1);
		Scanner input = new Scanner(System.in);
		String in = null;
		String print = null;
		while(true){
			in = input.next();
			if(in.equals("e")){
				System.out.println("Bey!");
				break;
			}
			print = ARService.autoReply(in,"oKoALj_QxOeQK-AKwI354nX9kIWM",1);
			System.out.println(print);
		}
		input.close();
	}
}
