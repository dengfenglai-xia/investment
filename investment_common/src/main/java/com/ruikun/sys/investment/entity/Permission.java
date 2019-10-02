package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Permission implements Serializable {
	
	/**
	 * 权限id
	 */
	private Integer permissionid;
	/**
	 * 权限名称
	 */
	private String permissionname;
	/**
	 * 资源类型（menu：菜单，permission： 按钮）
	 */
	private String type;
	/**
	 * 图标
	 */
	private String icon;	
	/**
	 * 访问地址
	 */
	private String url;
	/**
	 * 资源权限标识
	 */
	private String percode;
	/**
	 * 菜单级别
	 */
	private Integer levelNo;
	/**
	 * 父结点name
	 */
	private String parentname;	
	/**
	 * 父结点id
	 */
	private Integer parentid;
	/**
	 * 父结点id列表串
	 */
	private String parentids;
	/**
	 * 排序
	 */
	private Integer sort;

}
