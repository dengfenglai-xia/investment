package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Permission;
import com.ruikun.sys.investment.entity.PermissionTree;
import com.ruikun.sys.investment.entity.RolePermission;

import java.util.List;


public interface PermissionService {
    
	public List<Permission> findPermissionListByUserid(Integer userId);
	
	public List<String> findPermissionPerCodeListByUserid(Integer userId, String perFlag);
	
	public List<Permission> findPermissionList(Permission permission);
	
	public List<PermissionTree> findPermissionTreeList();
	
	public Permission findPermissionDetail(Integer permissionid);
	
	public void updatePermission(Permission permission);
	
	public void insertPermission(Permission permission);
	
	public void deletePermission(Integer permissionid);
	
	public int findPermissionMaxSort(Permission permission);
	
	public List<RolePermission> findPermissionByRoleId(Integer roleId);
	
	public void insertRolePermission(Integer roleId, String ids) throws Exception;
	
	/**
	 * 根据url查询记录所隶属的菜单 
	 */
	public String findPermissionByUrl(String url);
	
	public List<RolePermission> findRolePermissionByPerId(Integer permissionid);
	
}