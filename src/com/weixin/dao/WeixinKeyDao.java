package com.weixin.dao;

import com.weixin.domain.TB_WeixinKey;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * click事件的key值类接口
 */
public abstract interface WeixinKeyDao {
	public abstract TB_WeixinKey findByKeyValueandUnit(String keyValue,Integer unitID);

	public abstract boolean saveOrUpdate(TB_WeixinKey key);
}