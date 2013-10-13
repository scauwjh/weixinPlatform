package com.weixin.daoimpl;

import java.util.List;

import com.weixin.dao.SourcesDao;
import com.weixin.domain.TB_Sources;
import com.weixin.utility.HbmDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class SourcesDaoImpl implements SourcesDao {
	public static SourcesDaoImpl messageDao;

	public static synchronized SourcesDaoImpl getInstance() {
		if (messageDao == null) {
			messageDao = new SourcesDaoImpl();
		}
		return messageDao;
	}

	@Override
	public List<TB_Sources> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_Sources tb where tb.Unit.UnitID = "+unitID;
		@SuppressWarnings("unchecked")
		List<TB_Sources> list = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if(list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public void saveOrUpdate(TB_Sources weixin) {
		HbmDao.begin();
		HbmDao.saveOrUpdate(weixin);
		HbmDao.commit();
		HbmDao.close();
	}

	@Override
	public TB_Sources findByID(Integer picMsgID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(
				TB_Sources.class).add(Property.forName("ID").eq(picMsgID));
		TB_Sources weixin = (TB_Sources) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return weixin;
	}
}