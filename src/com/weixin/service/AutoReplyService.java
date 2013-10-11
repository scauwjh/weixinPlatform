package com.weixin.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月10日 下午3:48:06 
 * 
 *
 */
public class AutoReplyService {
	
	/**
	 * map里面的json格式 :
	 * {
	 * 	"child":"0",//0表示没有子节点，1表示有
	 * 	"message":"123456"
	 * }
	 */
	private Map<String,JSONObject> map = null;
	private Map<String,String> userInput = null;
	
	/**
	 * 构造方法
	 */
	public AutoReplyService(){
		this.map = new HashMap<String,JSONObject>();
		this.userInput = new HashMap<String,String>();
	}
	
	/**
	 * @param array 自动回复的JSONArray
	 * 打印首页菜单
	 */
	public void printMenu(JSONArray array){
		for(int i=0;i<array.size();i++){
			JSONObject json = JSONObject.fromObject(array.get(i));
			String msg = json.getString("message");
			System.out.println(msg);
		}
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
	 * 
	 */
	public void autoReply(Integer unitID){
		try{
			JSONObject unitMessage = new MessageService().getMessage(unitID);
			JSONObject autoReply = JSONObject.fromObject(unitMessage.get("message"));
			JSONArray replyArray = JSONArray.fromObject(autoReply.get("message"));
			ergodicJsonArray(replyArray);
			addHint();
			
			//以下操作为java控制台输出
			printMenu(replyArray);
			Scanner input = new Scanner(System.in);
			String print = null;
			userInput.put("wjh", "");
			while(true){
				print = null;
				JSONObject tmpJson = null;
				String inStr = input.next();
				if(inStr.equals("e")){
					break;
				}
				if(inStr.equals("0")){
					userInput.put("wjh", "");
					printMenu(replyArray);
				}else{
					tmpJson = map.get(userInput.get("wjh")+inStr);
					if(tmpJson==null){
						print = "您的输入有误";
						System.out.println(print);
						printMenu(replyArray);
					}
					else{
						print = tmpJson.getString("message");
						if(tmpJson.getString("child").equals("1")){
							String tmpStr = userInput.get("wjh");
							tmpStr += inStr;
							userInput.put("wjh", tmpStr);
						}
						System.out.println(print);
					}
				}
			}
			input.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new AutoReplyService().autoReply(1);
	}
}
