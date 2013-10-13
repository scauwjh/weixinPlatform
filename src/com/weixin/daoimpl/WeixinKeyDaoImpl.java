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

	@Override
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

	@Override
	public void saveOrUpdate(TB_WeixinKey key) {
		HbmDao.begin();
		HbmDao.saveOrUpdate(key);
		HbmDao.commit();
		HbmDao.close();
	}
}