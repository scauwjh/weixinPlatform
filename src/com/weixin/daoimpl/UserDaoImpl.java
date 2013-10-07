package com.weixin.daoimpl;

import java.util.List;

import com.weixin.dao.UserDao;
import com.weixin.domain.TB_User;
import com.weixin.utility.HbmDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class UserDaoImpl implements UserDao {
	private static UserDaoImpl userDao;

	public static synchronized UserDaoImpl getInstance() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}

	public TB_User findByUserAccountandPassword(String account, String password) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_User.class)
				.add(Property.forName("UserAccount").eq(account))
				.add(Property.forName("Password").eq(password));
		TB_User user = (TB_User) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return user;
	}

	public boolean saveOrUpdate(TB_User user) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(user);
			HbmDao.commit();
			HbmDao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TB_User> getAllUser() {
		HbmDao.begin();
		@SuppressWarnings("unchecked")
		List<TB_User> list = HbmDao.getSession().createQuery("from TB_User tb order by tb.UserID").list();
		if(list.size()>0)
			return list;
		return null;
	}
}