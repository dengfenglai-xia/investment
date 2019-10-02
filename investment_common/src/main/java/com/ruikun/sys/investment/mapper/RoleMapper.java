package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.Role;

/**
 * @Description: 角色表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
public interface RoleMapper {
	 	
	/**
	 * @Description: 查询角色表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Role> queryRoleList(Role role);
	
	/**
	 * @Description: 通过主键查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Role queryRoleDetailByPrimarykey(Integer roleId);
		
	/**
	 * @Description: 查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Role queryRoleDetail(Role role);
	
	/**
	 * @Description: 新增角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertRole(Role role) ;
	
	/**
	 * @Description: 修改角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateRole(Role role) ;
	
	/**
	 * @Description: 删除角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteRoleByPrimarykey(Integer roleId) ;
	
}
