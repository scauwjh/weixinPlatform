package com.weixin.dao;

import com.weixin.domain.TB_WeixinCoupon;

import java.util.List;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 优惠券
 */
public abstract interface WeixinCouponDao {
	
	public abstract boolean saveOrUpdate(TB_WeixinCoupon coupon);

	public abstract List<TB_WeixinCoupon> findByUnit(Integer unitID);

	public abstract TB_WeixinCoupon findByCouponIDandUnit(String coupon, Integer unitID);
}