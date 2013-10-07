package com.weixin.daoimpl;

import com.weixin.dao.WeixinMessageDao;
import com.weixin.domain.TB_WeixinMessage;
import com.weixin.utility.HbmDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class WeixinMessageDaoImpl implements WeixinMessageDao {
	public static WeixinMessageDaoImpl messageDao;

	public static synchronized WeixinMessageDaoImpl getInstance() {
		if (messageDao == null) {
			messageDao = new WeixinMessageDaoImpl();
		}
		return messageDao;
	}

	public TB_WeixinMessage findByUnit(Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_WeixinMessage.class)
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_WeixinMessage weixin = (TB_WeixinMessage) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return weixin;
	}

	public boolean saveOrUpdate(TB_WeixinMessage weixin) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(weixin);
			HbmDao.commit();
			HbmDao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}