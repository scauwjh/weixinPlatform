package com.weixin.basic;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午2:54:34 
 * 
 *
 */
public class Weixin_AssemblyXML {
	
	/**
	 * json转图文消息
	 * @param json
	 * @return
	 */
	public String jsonToPicMsg(JSONObject json, String fromID, String toID){
		Integer num = (Integer)json.get("picNum");
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + num + "</ArticleCount>"
				+ "<Articles>";
		JSONArray title = json.getJSONArray("picTitle");
		JSONArray desc = json.getJSONArray("picDesc");
		JSONArray picUrl = json.getJSONArray("picUrl");
		JSONArray url = json.getJSONArray("jumpUrl");
		for (int i = 0; i < num; i++) {
			print += "<item>"
					+ "<Title><![CDATA[" + title.get(i) + "]]></Title>"
					+ "<Description><![CDATA[" + desc.get(i) + "]]></Description>"
					+ "<PicUrl><![CDATA[" + picUrl.get(i) + "]]></PicUrl>"
					+ "<Url><![CDATA[" + url.get(i) + "]]></Url>"
					+ "</item>";
		}
		print += "</Articles>"
				+ "</xml>";
		return print;
	}
	
	/**
	 * 拼装图文消息
	 * @param articles
	 * @return
	 */
	public String picMsg(List<Weixin_Articles> articles, String fromID, String toID) {
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + articles.size() + "</ArticleCount>"
				+ "<Articles>";
		for (int i = 0; i < articles.size(); i++) {
			print += "<item>"
					+ "<Title><![CDATA[" + articles.get(i).getTitle()+ "]]></Title>"
					+ "<Description><![CDATA[" + articles.get(i).getDes() + "]]></Description>"
					+ "<PicUrl><![CDATA[" + articles.get(i).getPicUrl() + "]]></PicUrl>"
					+ "<Url><![CDATA[" + articles.get(i).getUrl() + "]]></Url>"
					+ "</item>";
		}
		print += "</Articles>"
				+ "</xml>";
		return print;
	}
	
	/**
	 * 拼装文字消息
	 * @param content
	 * @return
	 */
	public String fontMsg(String content, String fromID, String toID) {
		String print = "<xml>"
				+ "<ToUserName><![CDATA[" + fromID + "]]></ToUserName>"
				+ "<FromUserName><![CDATA[" + toID + "]]></FromUserName>"
				+ "<CreateTime>" + new Date().getTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[" + content + "]]></Content>"
				+ "</xml>";
		return print;
	}
}
