package com.weixin.dao;

import java.util.List;

import com.weixin.domain.TB_Sources;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 推送图文信息接口
 */
public abstract interface SourcesDao {
	
	public List<TB_Sources> findByUnit(Integer unitID);

	public TB_Sources findByID(Integer picMsgID);

	public void saveOrUpdate(TB_Sources picMsg);
}