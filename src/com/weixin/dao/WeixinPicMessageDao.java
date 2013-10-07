package com.weixin.dao;

import java.util.List;

import com.weixin.domain.TB_WeixinPicMessage;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 推送图文信息接口
 */
public abstract interface WeixinPicMessageDao {
	
	public abstract List<TB_WeixinPicMessage> findByUnit(Integer unitID);

	public abstract TB_WeixinPicMessage findByID(Integer picMsgID);

	public abstract boolean saveOrUpdate(TB_WeixinPicMessage picMsg);
}