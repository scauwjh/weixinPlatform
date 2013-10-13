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
	public TB_WeixinKey findByKeyValueandUnit(String keyValue,Integer unitID);

	public void saveOrUpdate(TB_WeixinKey key);
}