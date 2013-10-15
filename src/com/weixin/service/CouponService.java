package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.weixin.daoimpl.UnitDaoImpl;
import com.weixin.daoimpl.CouponDaoImpl;
import com.weixin.domain.TB_Unit;
import com.weixin.domain.TB_Coupon;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月12日 下午4:26:47 
 * 
 *
 */
public class CouponService {
	private CouponDaoImpl couponDao = CouponDaoImpl.getInstance();
	private UnitDaoImpl unitDao = UnitDaoImpl.getInstance();
	
	public boolean addCoupon(JSONObject data,Integer unitID){
		try{
			TB_Coupon coupon = new TB_Coupon();
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
			TB_Coupon coupon = couponDao.findByCouponIDandUnit(couponID, unitID);
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
		List<TB_Coupon> list = couponDao.findByUnit(unitID);
		if(list==null){
			return null;
		}
		Integer num = 0;
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject json = new JSONObject();
			TB_Coupon coupon = list.get(i);
			json.put("couponID", coupon.getCouponID());
			json.put("couponName", coupon.getCouponName());
			json.put("createTime", coupon.getCreateTime().toString());
			json.put("expiredDate", coupon.getExpiredDays());
			json.put("ID", coupon.getID());
			json.put("memo", coupon.getMemo());
			json.put("startedDate", coupon.getStartedDate().toString());
			json.put("unitID", coupon.getUnit().getUnitID());
			//session没有这东西
			//json.put("unitName", coupon.getUnit().getUnitName());
			array.add(json);
			num++;
		}
		JSONObject print = new JSONObject();
		print.put("coupon", array);
		print.put("couponNum", num);
		return print;
	}
}
