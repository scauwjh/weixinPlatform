package com.weixin.daoimpl;

import java.util.List;

import com.weixin.dao.WeixinPicMessageDao;
import com.weixin.domain.TB_WeixinPicMessage;
import com.weixin.utility.HbmDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class WeixinPicMessageDaoImpl implements WeixinPicMessageDao {
	public static WeixinPicMessageDaoImpl messageDao;

	public static synchronized WeixinPicMessageDaoImpl getInstance() {
		if (messageDao == null) {
			messageDao = new WeixinPicMessageDaoImpl();
		}
		return messageDao;
	}

	public List<TB_WeixinPicMessage> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_WeixinPicMessage tb where tb.Unit.UnitID = "+unitID;
		@SuppressWarnings("unchecked")
		List<TB_WeixinPicMessage> list = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if(list.size()>0){
			return list;
		}
		return null;
	}

	public boolean saveOrUpdate(TB_WeixinPicMessage weixin) {
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

	public TB_WeixinPicMessage findByID(Integer picMsgID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(
				TB_WeixinPicMessage.class).add(Property.forName("ID").eq(picMsgID));
		TB_WeixinPicMessage weixin = (TB_WeixinPicMessage) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return weixin;
	}
}