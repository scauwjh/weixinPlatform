package com.weixin.dao;

import com.weixin.domain.TB_Unit;

/** 
 * @author wjh 
 * @version 创建时间：2013年9月19日 下午1:46:37 
 * 类说明 
 */
public interface UnitDao {
	TB_Unit findByUnitID(Integer UnitID);
	boolean saveOrUpdate(TB_Unit unit);
}
