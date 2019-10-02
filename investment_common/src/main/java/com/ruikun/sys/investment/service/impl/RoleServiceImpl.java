package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.RoleMapper;	
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.service.RoleService;
 @Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * @Description: 查询角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Role> queryRoleList(Role role){
		return roleMapper.queryRoleList(role);
	}
	
	/**
	 * @Description: 通过主键查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Role queryRoleDetailByPrimarykey(Integer roleId){
		return roleMapper.queryRoleDetailByPrimarykey(roleId);
	}
	
	/**
	 * @Description: 查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Role queryRoleDetail(Role role){
		return roleMapper.queryRoleDetail(role);
	}
	
	/**
	 * @Description: 新增角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertRole(Role role) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		role.setCreateUserId(userId); // 创建者ID
		role.setUpdateUserId(userId); // 更新者ID
		role.setCreateTime(date); // 创建时间
		role.setUpdateTime(date); // 更新时间
		roleMapper.insertRole(role);
	}
	
	/**
	 * @Description: 修改角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateRole(Role role) {
		Integer userId = CacheUtils.getUser().getUserId();
		role.setUpdateUserId(userId); //更新者ID
		role.setUpdateTime(new Date()); //更新时间
		roleMapper.updateRole(role);
	}
	
	/**
	 * @Description: 删除角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteRoleByPrimarykey(Integer roleId) {
		roleMapper.deleteRoleByPrimarykey(roleId);
	}
	
}
