package com.ruikun.sys.investment.entity;

import lombok.Data;

@Data
public class RolePermission extends BaseEntity{
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 角色Id
	 */
	private Integer roleId;
	/**
	 * 权限id
	 */
	private Integer permissionid;
	

}
