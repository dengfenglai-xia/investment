package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.mapper.UserMapper;
import com.ruikun.sys.investment.service.UserService;
import com.ruikun.sys.investment.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * @Description: 查询用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<User> queryUserList(User user){
		return userMapper.queryUserList(user);
	}

	 /**
	  * @Description: 查询数量
	  * @author: xiachunyu
	  * @date: 2019-06-04
	  */
	 @Override
	 public int queryUserCount(User user) {
		 return userMapper.queryUserCount(user);
	 }

	 /**
	 * @Description: 通过主键查询用户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public User queryUserDetailByPrimarykey(Integer userId){
		return userMapper.queryUserDetailByPrimarykey(userId);
	}
	
	/**
	 * @Description: 查询用户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public User queryUserDetail(User user){
		return userMapper.queryUserDetail(user);
	}


	 /**
	  * @Description: 新增公司信息
	  * @author: xiachunyu
	  * @date: 2019-06-04
	  */
	 public void insertCompany(User user) {
		 Date date = new Date(); // 当前时间
		 user.setCreateTime(date); // 创建时间
		 user.setUpdateTime(date); // 更新时间
		 user.setUserType(Constants.USER_TYPE_COMPANY_ADMIN);
		 String salt = CommonUtil.getRandom(6);
		 String password = CommonUtil.saltMd5(user.getPassword(), salt);
		 user.setPassword(password);
		 user.setSalt(salt);
		 userMapper.insertUser(user);
	 }

	/**
	 * @Description: 新增用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertUser(User user) {
		Date date = new Date(); // 当前时间
		user.setCreateTime(date); // 创建时间
		user.setUpdateTime(date); // 更新时间
		user.setUserType(Constants.USER_TYPE_COMPANY_STAFF);
		String salt = CommonUtil.getRandom(6);
		String password = CommonUtil.saltMd5(user.getPassword(), salt);
		user.setPassword(password);
		user.setSalt(salt);
		userMapper.insertUser(user);
	}
	
	/**
	 * @Description: 修改用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateUser(User user) {
		user.setUpdateTime(new Date()); //更新时间
		userMapper.updateUser(user);
	}
	
	/**
	 * @Description: 删除用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteUserByPrimarykey(Integer userId) {
		userMapper.deleteUserByPrimarykey(userId);
	}
	
}
