package com.weixin.daoimpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.weixin.dao.UnitDao;
import com.weixin.domain.TB_Unit;
import com.weixin.utility.HbmDao;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月19日 下午1:48:01 
 * 类说明 
 */
public class UnitDaoImpl implements UnitDao {
	
	private static UnitDaoImpl unitDao;

	public static synchronized UnitDaoImpl getInstance() {
		if (unitDao == null) {
			unitDao = new UnitDaoImpl();
		}
		return unitDao;
	}
	
	@Override
	public TB_Unit findByUnitID(Integer unitID) {
		HbmDao.begin();
		DetachedCriteria dc = DetachedCriteria.forClass(TB_Unit.class)
				.add(Property.forName("UnitID").eq(unitID));
		TB_Unit unit = (TB_Unit) HbmDao.get(dc);
		HbmDao.commit();
		HbmDao.close();
		return unit;
	}

	@Override
	public boolean saveOrUpdate(TB_Unit unit) {
		try{
			HbmDao.begin();
			HbmDao.saveOrUpdate(unit);
			HbmDao.commit();
			HbmDao.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			HbmDao.close();
			return false;
		}
	}
	
}
