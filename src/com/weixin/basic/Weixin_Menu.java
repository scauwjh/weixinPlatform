package com.weixin.basic;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;


/**
 * 创建微信自定义菜单
 * 
 * @author wjh
 * @date 2013-09-02
 */
public class Weixin_Menu {
	
	public static JSONObject httpRequest(String requestUrl, JSONObject requestJson) {
		JSONObject returnValue = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new Weixin_TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//创建连接
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			//设置输出输入流，禁止缓存
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式
			httpUrlConn.setRequestMethod("POST");
			//打印输出流
			if(requestJson!=null){
				OutputStream outputStream = httpUrlConn.getOutputStream();
				outputStream.write(requestJson.toString().getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			httpUrlConn.disconnect();
			returnValue = JSONObject.fromObject(buffer.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	/*
	 * 获取TOKEN
	 * 
	 * @APPID wx6c5157bbf599d162
	 * @SECRET f7a0889bf9162c7f642e77e5bca1bc2a
	 */
	public static JSONObject getToken(String appid,String secret){
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		JSONObject json = httpRequest(url,null);
		return json;
	}
	
	public static JSONObject packageJson(Weixin_Button[] button){
		JSONObject json = new JSONObject();
		List<JSONObject> list = new LinkedList<JSONObject>();
		for(int i=0,leni=button.length;i<leni;i++){
			//获取对象
			Weixin_Button[] buttonEx = button[i].getSub_button();
			String type = button[i].getType();
			String name = button[i].getName();
			String key = button[i].getKey();
			String url = button[i].getUrl();
			//判断是否有二级菜单
			if(buttonEx == null){
				//没有二级菜单
				JSONObject tmpJson = new JSONObject();
				tmpJson.element("type", type);
				tmpJson.element("name", name);
				if(key!=null)
					tmpJson.element("key", key);
				else
					tmpJson.element("url", url);
				list.add(tmpJson);
			}
			else{
				//有二级菜单
				List<JSONObject> listEx = new LinkedList<JSONObject>();
				for(int j=0,lenj=buttonEx.length;j<lenj;j++){
					JSONObject tmpJson = new JSONObject();
					tmpJson.element("type", buttonEx[j].getType());
					tmpJson.element("name", buttonEx[j].getName());
					String tmpKey = buttonEx[j].getKey();
					String tmpUrl = buttonEx[j].getUrl();
					if(tmpKey!=null)
						tmpJson.element("key", tmpKey);
					else
						tmpJson.element("url", tmpUrl);
					listEx.add(tmpJson);
				}
				JSONObject jsonEx = new JSONObject();
				jsonEx.element("name", name);
				jsonEx.element("sub_button", listEx);
				list.add(jsonEx);
			}
		}
		json.element("button", list);
		return json;
	}
	
	//测试用
	public static void main(String[] args ){
		//获取表单信息，这里为测试数据
		Integer buttonNum = 3;
		Integer buttonExNum[] = {2,0,0};
		String buttonType[] = {"sub_button","view","click","click","click"};
		String buttonUrl[] = {"http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com","http://www.baidu.com"};
		//String buttonMessage[] = {"1","2","3","4","5"};
		String buttonName[] = {"会员卡","会员1","会员2","优惠信息","其他菜单"};
		//创建菜单对象
		//获取token，此处appid和secret应从数据库读出
		String appid = "wx6c5157bbf599d162";
		String secret = "f7a0889bf9162c7f642e77e5bca1bc2a";
		JSONObject tokenJson = getToken(appid,secret);
		System.out.println(tokenJson);
		String TOKEN = tokenJson.getString("access_token");
		//拼装请求地址
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+TOKEN;
		//创建菜单对象
		JSONObject requestJson = new JSONObject();
		Weixin_Button[] button = null;//一级菜单
		Weixin_Button[] buttonEx = null;//二级菜单
		Integer id = 0;//一级菜单标记变量
		Integer idEx = 0;//二级菜单标记变量
		Integer num = 0;//全局统计变量
		button = new Weixin_Button[buttonNum];
		for(int i=0;i<buttonNum;i++,id++){
			button[i] = new Weixin_Button();
			button[i].setName(buttonName[num]);
			button[i].setType(buttonType[num]);
			if(buttonType[num].equals("sub_button")){
				//含二级菜单
				num++;
				buttonEx = new Weixin_Button[buttonExNum[i]];
				for(int j=0;j<buttonExNum[i];j++,num++,idEx++){
					buttonEx[j] = new Weixin_Button();
					buttonEx[j].setName(buttonName[num]);
					buttonEx[j].setType(buttonType[num]);
					if(buttonType[num].equals("click")){
						buttonEx[j].setKey("keyEx"+idEx);
					}
					else{
						buttonEx[j].setUrl(buttonUrl[num]);
					}
				}
				button[i].setSub_button(buttonEx);
			}
			else if(buttonType[num].equals("click")){
				num++;
				button[i].setKey("key"+id);
			}
			else{
				button[i].setUrl(buttonUrl[num++]);
			}
		}
		//将菜单对象封装为JSON
		requestJson = packageJson(button);
		System.out.println(requestJson);
		//发出请求
		System.out.println(httpRequest(url,requestJson));
	}
}