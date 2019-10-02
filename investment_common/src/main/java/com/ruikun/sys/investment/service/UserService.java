package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.User;

import java.util.List;

/**
 * @Description: 用户表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface UserService {
	 	
	/**
	 * @Description: 查询用户表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<User> queryUserList(User user);

	/**
	 * @Description: 查询数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryUserCount(User user);

	/**
	 * @Description: 通过主键查询用户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public User queryUserDetailByPrimarykey(Integer userId);
		
	/**
	 * @Description: 查询用户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public User queryUserDetail(User user);

	/**
	 * @Description: 新增公司
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertCompany(User user) ;

	/**
	 * @Description: 新增用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertUser(User user) ;
	
	/**
	 * @Description: 修改用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateUser(User user) ;
	
	/**
	 * @Description: 删除用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteUserByPrimarykey(Integer userId) ;
	
}
