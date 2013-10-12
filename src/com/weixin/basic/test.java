package com.weixin.basic;

import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.WeixinPicMessageDaoImpl;
import com.weixin.domain.TB_WeixinPicMessage;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月11日 下午9:33:23 
 * 
 *
 */
public class test {
	
	private static WeixinPicMessageDaoImpl picMessageDao = WeixinPicMessageDaoImpl.getInstance();
	
	protected static List<Weixin_Articles> getPicMsgList(Integer welcomeID){
		TB_WeixinPicMessage picMessage = picMessageDao.findByID(welcomeID);
		JSONObject picMsg = JSONObject.fromObject(picMessage.getPicMessage());
		
		List<Weixin_Articles> list = new LinkedList<Weixin_Articles>();
		Integer num = (Integer)picMsg.get("picNum");
		JSONArray picDesc = JSONArray.fromObject(picMsg.getString("picDesc"));
		JSONArray picTitle = JSONArray.fromObject(picMsg.getString("picTitle"));
		JSONArray picUrl = JSONArray.fromObject(picMsg.getString("picUrl"));
		JSONArray jumpUrl = JSONArray.fromObject(picMsg.getString("jumpUrl"));
		for(int i=0;i<num;i++){
			Weixin_Articles articles = new Weixin_Articles();
			articles.setDes(picDesc.getString(i));//("尊敬的客户，欢迎您体验UHOTEL酒店微信营销系统\n\n回复1：酒店资讯\n\n回复2：美食推荐\n\n回复3：绑定手机\n\n回复4：查询积分")
			articles.setTitle(picTitle.getString(i));
			articles.setPicUrl(picUrl.getString(i));
			articles.setUrl(jumpUrl.getString(i));
			list.add(articles);
		}
		return list;
	}
	
	public static void main(String[] args){
		List<Weixin_Articles> list = getPicMsgList(1);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getDes());
		}
	}
}
