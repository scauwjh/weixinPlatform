package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.WeixinCouponDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_WeixinCoupon;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午4:26:47 
 * 
 *
 */
public class CouponService {
	private WeixinCouponDaoImpl couponDao = WeixinCouponDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	public boolean addCoupon(JSONObject data,Integer unitID){
		try{
			TB_WeixinCoupon coupon = new TB_WeixinCoupon();
			coupon.setCouponID(data.getString("couponID"));
			coupon.setCouponName(data.getString("couponName"));
			coupon.setCreateTime(new Date());
			coupon.setExpiredDays((Integer)data.get("expiredDays"));
			coupon.setMemo(data.getString("memo"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startedDate = df.parse(data.getString("startedDate"));
			coupon.setStartedDate(startedDate);
			TB_Unit unit = unitDao.findByUnitID(unitID);
			coupon.setUnit(unit);
			couponDao.saveOrUpdate(coupon);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(JSONObject data, Integer unitID){
		try{
			String couponID = data.getString("couponID");
			TB_WeixinCoupon coupon = couponDao.findByCouponIDandUnit(couponID, unitID);
			coupon.setCouponName(data.getString("couponName"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startedDate = df.parse(data.getString("startedDate"));
			coupon.setExpiredDays((Integer)data.get("expiredDays"));
			coupon.setMemo(data.getString("memo"));
			coupon.setStartedDate(startedDate);
			couponDao.saveOrUpdate(coupon);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public JSONObject get(Integer unitID){
		List<TB_WeixinCoupon> list = couponDao.findByUnit(unitID);
		if(list==null){
			return null;
		}
		Integer num = 0;
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json = new JSONObject();
			TB_WeixinCoupon coupon = list.get(i);
			json.element("couponID", coupon.getCouponID());
			json.element("couponName", coupon.getCouponName());
			json.element("createTime", coupon.getCreateTime().toString());
			json.element("expiredDate", coupon.getExpiredDays());
			json.element("ID", coupon.getID());
			json.element("memo", coupon.getMemo());
			json.element("startedDate", coupon.getStartedDate().toString());
			json.element("unitID", coupon.getUnit().getUnitID());
			//session没有这东西
			//json.element("unitName", coupon.getUnit().getUnitName());
			array.add(json);
			num++;
		}
		JSONObject print = new JSONObject();
		print.element("coupon", array);
		print.element("couponNum", num);
		return print;
	}
}
