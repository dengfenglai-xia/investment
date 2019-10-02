package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 合同递增率表属性
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Data
public class ContractIncreaseRate extends BaseEntity{
	 
	/**
	 * ID
	 */						
	private Integer id;	
		
	/**
	 * 合同编号
	 */						
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 递增月（即合同生效后几个月开始递增）
	 */
	private String increaseMonth;

	/**
	 * 递增日期
	 */						
	private String increaseDate;	
		
	/**
	 * 递增率（%）
	 */						
	private Double increaseRate;


	public ContractIncreaseRate(){
		super();
	}

	public ContractIncreaseRate(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
	
	
}
