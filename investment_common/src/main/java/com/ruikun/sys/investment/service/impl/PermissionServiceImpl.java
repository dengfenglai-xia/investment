package com.ruikun.sys.investment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.ruikun.sys.investment.entity.Permission;
import com.ruikun.sys.investment.entity.PermissionTree;
import com.ruikun.sys.investment.entity.RolePermission;
import com.ruikun.sys.investment.mapper.PermissionMapper;
import com.ruikun.sys.investment.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @ClassName: PermissionServiceImpl
 * @Description: 权限管理
 * @author: chunyu.xia
 * @date: 2017-11-16 下午2:13:36
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	PermissionMapper permissionMapper;

	@Override
	public List<Permission> findPermissionListByUserid(Integer userId) {
		return permissionMapper.findPermissionListByUserid(userId);
	}

	@Override
	public List<Permission> findPermissionList(Permission permission) {
		return permissionMapper.findPermissionList(permission);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionMapper.updatePermission(permission);
	}

	/**
	 * @Title: insertPermission
	 * @Description: 新增下级菜单
	 * @param: @param permission   
	 * @return: void   
	 * @throws
	 */
	@Override
	public void insertPermission(Permission permission) {
		Integer parentid = permission.getParentid();
		Permission per = permissionMapper.findPermissionDetail(parentid); // 查询父级菜单明细
		Integer levelNo_child = per.getLevelNo() + 1 ; // 子级菜单级别 = levelNo（父级） + 1
		permission.setLevelNo(levelNo_child);
		Integer sort = permissionMapper.findPermissionMaxSort(permission); // 查询同级子菜单最大排序
		permission.setSort(sort+1);
		permissionMapper.insertPermission(permission);
		
	}

	@Override
	public void deletePermission(Integer permissionid) {
		permissionMapper.deletePermission(permissionid);
		permissionMapper.deleteChildPermission(permissionid);
	}
	
	@Override
	public int findPermissionMaxSort(Permission permission) {
		return permissionMapper.findPermissionMaxSort(permission);
	}

	@Override
	public Permission findPermissionDetail(Integer permissionid) {
		return permissionMapper.findPermissionDetail(permissionid);
	}

	@Override
	public List<PermissionTree> findPermissionTreeList() {
		return permissionMapper.findPermissionTreeList();
	}
	
	@Override
	public List<RolePermission> findPermissionByRoleId(Integer roleId) {
		return permissionMapper.findPermissionByRoleId(roleId);
	}
	@Transactional
	@Override
	public void insertRolePermission(Integer roleId,String ids) throws Exception{
		permissionMapper.deleteRolePermission(roleId);
		JSONArray ja = JSON.parseArray(ids);  
		Object[] objs = new Object[ja.size()];
		for (int i = 0; i < ja.size(); i++) {  
			objs[i] = ja.get(i);  
		}
		List rpList = Lists.newArrayList();
		for(int i=0;i<objs.length;i++){
			RolePermission rp = new RolePermission();
			rp.setRoleId(roleId);
			rp.setPermissionid((Integer) objs[i]);
			rpList.add(rp);
		}
		permissionMapper.insertRolePermission(rpList);
	}

	/**
	 * 根据用户id查询用户权限标识的集合
	 */
	public List<String> findPermissionPerCodeListByUserid(Integer userId,String perFlag) {
		return permissionMapper.findPermissionPerCodeListByUserid(userId,perFlag);
	}

	/**
	 * 根据url查询记录所隶属的菜单 
	 */
	public String findPermissionByUrl(String url) {
		return permissionMapper.findPermissionByUrl(url);
	}

	@Override
	public List<RolePermission> findRolePermissionByPerId(Integer permissionid) {
		return permissionMapper.findRolePermissionByPerId(permissionid);
	}

}
