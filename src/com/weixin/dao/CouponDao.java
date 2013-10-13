package com.weixin.dao;

import com.weixin.domain.TB_Coupon;

import java.util.List;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 优惠券
 */
public abstract interface CouponDao {
	
	public void saveOrUpdate(TB_Coupon coupon);

	public List<TB_Coupon> findByUnit(Integer unitID);

	public TB_Coupon findByCouponIDandUnit(String coupon, Integer unitID);
}