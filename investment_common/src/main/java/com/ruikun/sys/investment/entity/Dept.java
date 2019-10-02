package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 部门表属性
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Data
public class Dept extends BaseEntity{

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 部门名称
	 */
	private String deptName;



}
