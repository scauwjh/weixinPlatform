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
	public TB_User findByUserAccountandPassword(String account,String password);
	public void saveOrUpdate(TB_User user);
	public List<TB_User> getAllUser();
}