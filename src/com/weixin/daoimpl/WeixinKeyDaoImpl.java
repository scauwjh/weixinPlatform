package com.weixin.daoimpl;

import com.weixin.dao.WeixinKeyDao;
import com.weixin.domain.TB_WeixinKey;
import com.weixin.utility.HbmDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class WeixinKeyDaoImpl implements WeixinKeyDao {
	private static WeixinKeyDaoImpl weixinKeyDao;

	public static synchronized WeixinKeyDaoImpl getInstance() {
		if (weixinKeyDao == null) {
			weixinKeyDao = new WeixinKeyDaoImpl();
		}
		return weixinKeyDao;
	}

	public TB_WeixinKey findByKeyValueandUnit(String keyValue,Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_WeixinKey.class)
				.add(Property.forName("KeyValue").eq(keyValue))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_WeixinKey key = (TB_WeixinKey)HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return key;
	}

	public boolean saveOrUpdate(TB_WeixinKey key) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(key);
			HbmDao.commit();
			HbmDao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			HbmDao.close();
			return false;
		}
	}
}