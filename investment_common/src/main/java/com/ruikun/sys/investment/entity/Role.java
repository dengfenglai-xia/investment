package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 角色表属性
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Data
public class Role extends BaseEntity{
	 
	/**
	 * 角色ID
	 */						
	private Integer roleId;	
		
	/**
	 * 公司编号
	 */						
	private String companyCode;	
		
	/**
	 * 角色名称
	 */						
	private String roleName;	
		
	
	
}
