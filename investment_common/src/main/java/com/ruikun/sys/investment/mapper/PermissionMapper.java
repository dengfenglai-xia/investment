package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Permission;
import com.ruikun.sys.investment.entity.PermissionTree;
import com.ruikun.sys.investment.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PermissionMapper {

	public List<Permission> findPermissionListByUserid(Integer userId);

	public List<String> findPermissionPerCodeListByUserid(@Param("userId") Integer userId, @Param("perFlag") String perFlag);

	public List<Permission> findPermissionList(Permission permission);

	public List<PermissionTree> findPermissionTreeList();

	public Permission findPermissionDetail(Integer permissionid);

	public void updatePermission(Permission permission);

	public void insertPermission(Permission permission);

	public void deletePermission(Integer permissionid);

	public void deleteChildPermission(Integer permissionid);

	public Integer findPermissionMaxSort(Permission permission);

	public List<RolePermission> findPermissionByRoleId(Integer roleId);

	public void insertRolePermission(List list);

	public void deleteRolePermission(Integer roleId);

	/**
	 * 根据url查询记录所隶属的菜单
	 */
	public String findPermissionByUrl(String url);

	public List<RolePermission> findRolePermissionByPerId(Integer permissionid);
	
}
