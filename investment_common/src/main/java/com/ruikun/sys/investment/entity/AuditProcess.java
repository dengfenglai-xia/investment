package com.ruikun.sys.investment.entity;

import lombok.Data;

/**
 * @Description: 属性
 * @author: xiachunyu
 * @date: 2019-07-09
 */
@Data
public class AuditProcess extends BaseEntity{
	 
	/**
	 * 主键ID
	 */						
	private Long id;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 审核项标志
	 */						
	private String sign;	
		
	/**
	 * 审核人ID
	 */						
	private Integer userId;

	/**
	 * 审核人
	 */
	private String auditor;

	/**
	 * 职务
	 */
	private String jobName;

	/**
	 * 部门
	 */
	private String deptName;

	/**
	 * 审核顺序
	 */
	private Integer sort;	

}
