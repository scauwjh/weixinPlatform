package com.weixin.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.basic.Weixin_Articles;
import com.weixin.basic.Weixin_AssemblyXML;
import com.weixin.daoimpl.MemberDaoImpl;
import com.weixin.daoimpl.SourcesDaoImpl;
import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinKeyDaoImpl;
import com.weixin.domain.TB_Member;
import com.weixin.domain.TB_Sources;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinKey;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月13日 下午11:05:28 
 * 
 *
 */
public class WeixinInterfaceService {
	
	private WeixinKeyDaoImpl keyDao = null;
	private SourcesDaoImpl sourcesDao = null;
	private MemberDaoImpl memberDao = null;
	private UnitDaoImpl unitDao = null;
	private Weixin_AssemblyXML ambXML = null;
	
	public WeixinInterfaceService(){
		unitDao = UnitDaoImpl.getInstance();
		memberDao = MemberDaoImpl.getInstance();
		sourcesDao = SourcesDaoImpl.getInstance();
		keyDao = WeixinKeyDaoImpl.getInstance();
		ambXML = new Weixin_AssemblyXML();
	}
	
	/**
	 * 点击处理
	 * @param unitID
	 * @param eventKey
	 * @param fromID
	 * @param toID
	 * @return 返回一个json
	 * json节点：flag,ret
	 * flag 1为图文消息，0为文字消息
	 * ret 返回的消息内容
	 * 
	 */
	public JSONObject clickAction(Integer unitID, String eventKey, String fromID, String toID){
		String keyValue = eventKey;
		TB_WeixinKey key = keyDao.findByKeyValueandUnit(keyValue, unitID);
		JSONObject json = JSONObject.fromObject(key.getMessage());
		JSONObject retJson = new JSONObject();
		if(json.getString("type").equals("picMsg")){
			TB_Sources picMsg = sourcesDao.findByID((Integer)json.get("picMsgID"));
			JSONObject tmpJson = JSONObject.fromObject(picMsg.getPicMessage());
			String ret = ambXML.jsonToPicMsg(tmpJson,fromID,toID);
			retJson.element("flag", "1");
			retJson.element("ret", ret);
		} else{
			String ret = retJson.getString("msg");
			retJson.element("flag", "0");
			retJson.element("ret", ret);
		}
		return retJson;
	}
	
	/**
	 * 订阅处理
	 * @param unitID
	 * @param fromID
	 * @param toID
	 * @return 返回客户的消息（图文消息）
	 */
	public String subscribeAction(Integer unitID, String fromID, String toID){
		try{
			Integer score = 0;
			Integer term = 0;
			TB_Unit unit = unitDao.findByUnitID(unitID);
			if (unit != null) {
				term = unit.getTerm();
				score = unit.getScore();
			}
			TB_Member member = this.memberDao.findByOpenIDandUnit(fromID, unitID);
			if (member == null) {
				member = new TB_Member();
				member.setUnit(unit);
				member.setOpenID(fromID);
				member.setLastInput("");
				member.setLastTime(new Date());
				boolean tmpFlag = false;
				String memberID = "";
				while (!tmpFlag) {
					for (int i = 0; i < 7; i++) {
						memberID = memberID+ Integer.toString((int) (Math.random() * 10));
					}
					if (this.memberDao.findByMemberIDandUnit(memberID,unitID) == null)
						tmpFlag = true;
				}
				member.setMemberID(memberID);
				this.memberDao.saveOrUpdate(member);
			}
			member.setScore(member.getScore()+score);
			member.setTerm(term);
			this.memberDao.saveOrUpdate(member);
			//欢迎页面
			Integer welcomeID = unit.getWelcomePage();
			List<Weixin_Articles> list = getPicMsgList(welcomeID);
			
			String ret = ambXML.picMsg(list,fromID,toID);
			return ret;
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	public String getFontMsg(String content, String fromID, String toID){
		String ret = ambXML.fontMsg(content,fromID,toID);
		return ret;
	}
	
	/**
	 * 返回微信端首页
	 * @param welcomeID
	 * @param fromID
	 * @param toID
	 * @return
	 */
	public String getMainPage(Integer welcomeID, String fromID,String toID){
		String ret = ambXML.picMsg(getPicMsgList(welcomeID),fromID,toID);
		return ret;
	}
	
	/**
	 * 返回单位对象
	 * @param unitID
	 * @return
	 */
	public TB_Unit getUnit(Integer unitID){
		return unitDao.findByUnitID(unitID);
	}
	
	
	/**
	 * 获取图文信息的list
	 * @param welcomeID
	 * @return
	 * 
	 * 由数据库中的welcomeMessage(这里是welcomeID)
	 * 读取图文消息表获取数据
	 * 再拼装list
	 */
	public List<Weixin_Articles> getPicMsgList(Integer welcomeID){
		TB_Sources picMessage = sourcesDao.findByID(welcomeID);
		JSONObject picMsg = JSONObject.fromObject(picMessage.getPicMessage());
		
		List<Weixin_Articles> list = new LinkedList<Weixin_Articles>();
		Integer num = (Integer)picMsg.get("picNum");
		JSONArray picDesc = JSONArray.fromObject(picMsg.getString("picDesc"));
		JSONArray picTitle = JSONArray.fromObject(picMsg.getString("picTitle"));
		JSONArray picUrl = JSONArray.fromObject(picMsg.getString("picUrl"));
		JSONArray jumpUrl = JSONArray.fromObject(picMsg.getString("jumpUrl"));
		for(int i=0;i<num;i++){
			Weixin_Articles articles = new Weixin_Articles();
			articles.setDes(picDesc.getString(i));
			articles.setTitle(picTitle.getString(i));
			articles.setPicUrl(picUrl.getString(i));
			articles.setUrl(jumpUrl.getString(i));
			list.add(articles);
		}
		return list;
	}
	
	/**
	 * 绑定手机号码
	 * @param unitID
	 * @param fromID
	 * @param telphone
	 * @return boolean
	 */
	public boolean boundTelephone(Integer unitID, String fromID, String telphone){
		try{
			TB_Member member = this.memberDao.findByOpenIDandUnit(fromID, unitID);
			if (member == null) {
				member = new TB_Member();
			}
			member.setCreateTime(new Date());
			TB_Unit unit = unitDao.findByUnitID(unitID);
			member.setUnit(unit);
			member.setOpenID(fromID);
			member.setTelephone(telphone);
			memberDao.saveOrUpdate(member);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args){
		System.out.println(new WeixinInterfaceService().subscribeAction(1, "1234567", "123456"));
	}
}
