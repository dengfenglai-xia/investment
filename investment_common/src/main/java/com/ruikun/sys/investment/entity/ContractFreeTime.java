package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 合同免租期表属性
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Data
public class ContractFreeTime extends BaseEntity{
	 
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
	 * 免租开始日期
	 */						
	private String freeStartDate;	
		
	/**
	 * 免租截止日期
	 */						
	private String freeEndDate;

	/**
	 * 免租时间区间
	 */
	private String freeDateRange;

	public ContractFreeTime(){
		super();
	}

	public ContractFreeTime(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}

}
