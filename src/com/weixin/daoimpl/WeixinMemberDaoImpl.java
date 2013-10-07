package com.weixin.daoimpl;

import com.weixin.dao.WeixinMemberDao;
import com.weixin.domain.TB_WeixinMember;
import com.weixin.utility.HbmDao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class WeixinMemberDaoImpl implements WeixinMemberDao {
	public static WeixinMemberDaoImpl memberDao;

	public static synchronized WeixinMemberDaoImpl getInstance() {
		if (memberDao == null) {
			memberDao = new WeixinMemberDaoImpl();
		}
		return memberDao;
	}

	public TB_WeixinMember findByOpenIDandUnit(String openID, Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_WeixinMember.class)
				.add(Property.forName("OpenID").eq(openID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_WeixinMember member = (TB_WeixinMember) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return member;
	}

	public boolean saveOrUpdate(TB_WeixinMember member) {
		try {
			HbmDao.begin();
			HbmDao.saveOrUpdate(member);
			HbmDao.commit();
			HbmDao.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public TB_WeixinMember findByMemberIDandUnit(String memberID,
			Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_WeixinMember.class)
				.add(Property.forName("MemberID").eq(memberID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_WeixinMember member = (TB_WeixinMember) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return member;
	}

	public List<TB_WeixinMember> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_WeixinMember tb where tb.Unit.UnitID = " + unitID;
		@SuppressWarnings("unchecked")
		List<TB_WeixinMember> member = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if (member.size() > 0) {
			return member;
		}
		return null;
	}
}