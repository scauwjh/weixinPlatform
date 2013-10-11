package com.weixin.dao;

import com.weixin.domain.TB_UnitMessage;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 微信推送信息接口
 */
public abstract interface UnitMessageDao {
	
	public abstract TB_UnitMessage findByUnit(Integer unitID);

	public abstract boolean saveOrUpdate(TB_UnitMessage message);
}