package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 费用类别属性
 * @author: xiachunyu
 * @date: 2019-08-11
 */
@Data
public class FeeType extends BaseEntity{
	 
	/**
	 * ID
	 */						
	private Integer id;	
		
	/**
	 * 公司编号
	 */						
	private String companyCode;	
		
	/**
	 * 费用类别
	 */						
	private String type;	
		
	
	
}
