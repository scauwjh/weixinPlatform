package com.weixin.daoimpl;

import com.weixin.dao.CouponDao;
import com.weixin.domain.TB_Coupon;
import com.weixin.utility.HbmDao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class CouponDaoImpl implements CouponDao {
	public static CouponDaoImpl couponDao;
	
	public static CouponDaoImpl getInstance() {
		if (couponDao == null) {
			couponDao = new CouponDaoImpl();
		}
		return couponDao;
	}
	
	@Override
	public void saveOrUpdate(TB_Coupon coupon) {
		HbmDao.begin();
		HbmDao.saveOrUpdate(coupon);
		HbmDao.commit();
		HbmDao.close();
	}

	@Override
	public List<TB_Coupon> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_Coupon tb where tb.Unit.UnitID = " + unitID;
		@SuppressWarnings("unchecked")
		List<TB_Coupon> coupon = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if (coupon.size() > 0)
			return coupon;
		return null;
	}

	@Override
	public TB_Coupon findByCouponIDandUnit(String couponID,
			Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_Coupon.class)
				.add(Property.forName("CouponID").eq(couponID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_Coupon coupon = (TB_Coupon) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return coupon;
	}
}