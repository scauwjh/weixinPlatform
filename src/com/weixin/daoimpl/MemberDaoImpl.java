package com.weixin.daoimpl;

import com.weixin.dao.MemberDao;
import com.weixin.domain.TB_Member;
import com.weixin.utility.HbmDao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

public class MemberDaoImpl implements MemberDao {
	public static MemberDaoImpl memberDao;

	public static synchronized MemberDaoImpl getInstance() {
		if (memberDao == null) {
			memberDao = new MemberDaoImpl();
		}
		return memberDao;
	}

	@Override
	public TB_Member findByOpenIDandUnit(String openID, Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_Member.class)
				.add(Property.forName("OpenID").eq(openID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_Member member = (TB_Member) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return member;
	}

	@Override
	public void saveOrUpdate(TB_Member member) {
		HbmDao.begin();
		HbmDao.saveOrUpdate(member);
		HbmDao.commit();
		HbmDao.close();
	}

	@Override
	public TB_Member findByMemberIDandUnit(String memberID,
			Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_Member.class)
				.add(Property.forName("MemberID").eq(memberID))
				.add(Property.forName("Unit.UnitID").eq(unitID));
		TB_Member member = (TB_Member) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return member;
	}

	@Override
	public List<TB_Member> findByUnit(Integer unitID) {
		HbmDao.begin();
		String hql = "from TB_Member tb where tb.Unit.UnitID = " + unitID;
		@SuppressWarnings("unchecked")
		List<TB_Member> member = HbmDao.getSession().createQuery(hql).list();
		HbmDao.commit();
		HbmDao.close();
		if (member.size() > 0) {
			return member;
		}
		return null;
	}
}