package com.weixin.dao;

import java.util.List;

import com.weixin.domain.TB_User;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 系统用户
 */
public abstract interface UserDao {
	public abstract TB_User findByUserAccountandPassword(String account,String password);
	public abstract boolean saveOrUpdate(TB_User user);
	public abstract List<TB_User> getAllUser();
}