package com.weixin.dao;

import com.weixin.domain.TB_Member;

import java.util.List;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 微信会员接口
 */
public abstract interface MemberDao {
	
	public TB_Member findByOpenIDandUnit(String paramString,Integer unitID);

	public void saveOrUpdate(TB_Member member);

	public TB_Member findByMemberIDandUnit(String memberID, Integer unitID);

	public List<TB_Member> findByUnit(Integer unitID);
}