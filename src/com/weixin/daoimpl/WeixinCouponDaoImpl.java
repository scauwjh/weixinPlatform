package com.weixin.daoimpl;

import com.weixin.dao.WeixinCouponDao;
import com.weixin.domain.TB_WeixinCoupon;
import com.weixin.utility.HbmDao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class WeixinCouponDaoImpl implements WeixinCouponDao {
	public static WeixinCouponDaoImpl couponDao;

	public static WeixinCouponDaoImpl getInstance() {
		if (couponDao == null) {
			couponDao = new WeixinCouponDaoImpl();
		}
		return couponDao;
	}

	public boolean saveOrUpdate(TB_WeixinCoupon coupon) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(coupon);
			HbmDao.commit();
			HbmDao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HbmDao.close();
			return false;
		}
	}

	public List<TB_WeixinCoupon> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_WeixinCoupon tb where tb.Unit.UnitID = " + unitID;
		@SuppressWarnings("unchecked")
		List<TB_WeixinCoupon> coupon = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if (coupon.size() > 0)
			return coupon;
		return null;
	}

	public TB_WeixinCoupon findByCouponIDandUnit(String couponID,
			Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_WeixinCoupon.class)
				.add(Property.forName("CouponID").eq(couponID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_WeixinCoupon coupon = (TB_WeixinCoupon) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return coupon;
	}
}