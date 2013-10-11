package com.weixin.daoimpl;

import com.weixin.dao.UnitMessageDao;
import com.weixin.domain.TB_UnitMessage;
import com.weixin.utility.HbmDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class UnitMessageDaoImpl implements UnitMessageDao {
	public static UnitMessageDaoImpl messageDao;

	public static synchronized UnitMessageDaoImpl getInstance() {
		if (messageDao == null) {
			messageDao = new UnitMessageDaoImpl();
		}
		return messageDao;
	}

	public TB_UnitMessage findByUnit(Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_UnitMessage.class)
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_UnitMessage weixin = (TB_UnitMessage) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return weixin;
	}

	public boolean saveOrUpdate(TB_UnitMessage weixin) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(weixin);
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