package com.weixin.dao;

import com.weixin.domain.TB_WeixinMember;

import java.util.List;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 微信会员接口
 */
public abstract interface WeixinMemberDao {
	
	public abstract TB_WeixinMember findByOpenIDandUnit(String paramString,Integer unitID);

	public abstract boolean saveOrUpdate(TB_WeixinMember member);

	public abstract TB_WeixinMember findByMemberIDandUnit(String memberID, Integer unitID);

	public abstract List<TB_WeixinMember> findByUnit(Integer unitID);
}