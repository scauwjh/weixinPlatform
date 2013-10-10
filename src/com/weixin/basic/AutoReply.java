package com.weixin.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
public class AutoReply {
	
	/**
	 * map里面的json格式 :
	 * {
	 * 	"child":"0",//0表示没有子节点，1表示有
	 * 	"message":"123456"
	 * }
	 */
	private static Map<String,JSONObject> map = new HashMap<String,JSONObject>();
	private static Map<String,String> userInput = new HashMap<String,String>();
	
	
	private static void printMenu(JSONArray array){
		for(int i=0;i<array.size();i++){
			JSONObject json = JSONObject.fromObject(array.get(i));
			String msg = json.getString("message");
			System.out.println(msg);
		}
	}
	
	private static void ergodicJsonArray(JSONArray array){
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
	
	private static void addHint(){
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
	
	private static void autoReply(){
		try{
			URL url = new URL("http://localhost:8080/weixin/autoreply?action=get");
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String str = null;
			while((str=br.readLine())!=null){
				sb.append(str);
			}
			JSONObject json = JSONObject.fromObject(sb.toString());
			JSONObject autoReply = JSONObject.fromObject(json.get("message"));
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
		autoReply();
	}
}
