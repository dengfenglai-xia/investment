package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 职务表属性
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Data
public class Job extends BaseEntity{
	 
	/**
	 * 职务ID
	 */						
	private Integer jobId;	
		
	/**
	 * 公司编号
	 */						
	private String companyCode;	
		
	/**
	 * 职务名称
	 */						
	private String jobName;	
		
	
	
}
