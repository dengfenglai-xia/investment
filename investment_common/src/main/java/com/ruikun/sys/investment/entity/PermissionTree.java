package com.ruikun.sys.investment.entity;

import lombok.Data;

@Data
public class PermissionTree {
	
	/**
	 * 菜单id
	 */
	private Integer id;
	/**
	 * 菜单父类ID
	 */
	private String pId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private String sort;

}
