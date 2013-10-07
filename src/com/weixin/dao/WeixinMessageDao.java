package com.weixin.dao;

import com.weixin.domain.TB_WeixinMessage;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 微信推送信息接口
 */
public abstract interface WeixinMessageDao {
	
	public abstract TB_WeixinMessage findByUnit(Integer unitID);

	public abstract boolean saveOrUpdate(TB_WeixinMessage message);
}