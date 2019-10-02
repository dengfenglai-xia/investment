package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 属性
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Data
public class AuditItem {
	 
	/**
	 * 主键ID
	 */						
	private Integer id;	
		
	/**
	 * 审核项
	 */						
	private String name;	
		
	/**
	 * 审核项标志
	 */						
	private String sign;	
		
	/**
	 * 开关（1 关闭 ，2 开启）
	 */						
	private String switchFlag;
		
	
	
}
