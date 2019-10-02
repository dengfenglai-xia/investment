package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 转出
 * @author: xiachunyu
 * @date: 2019-07-18
 */
@Data
public class TransferOut extends BaseEntity{
	 
	/**
	 * 主键ID
	 */						
	private Long outId;

	/**
	 * 转入ID
	 */
	private Long enterId;

	/**
	 * 转出账单ID
	 */						
	private Long billId;

	/**
	 * 转出账单编号
	 */
	private String billCode;

	/**
	 * 转出客户ID
	 */						
	private Integer customerId;

	/**
	 * 转出客户
	 */
	private String customerName;

	/**
	 * 转出楼宇
	 */
	private String buildingName;

	/**
	 * 转出合同编号
	 */						
	private String contractCode;	
		
	/**
	 * 转出合同版本号
	 */						
	private Integer version;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 转出签约房间
	 */						
	private String roomNos;

	/**
	 * 费用类别
	 */
	private String feeType;

	/**
	 * 转出费用名称
	 */						
	private String costName;	
		
	/**
	 * 转出开始时间
	 */						
	private String startDate;	
		
	/**
	 * 转出结束时间
	 */						
	private String endDate;	
		
	/**
	 * 结转金额
	 */						
	private BigDecimal transferAmt;
		
	/**
	 * 操作人
	 */						
	private String operator;	
		
	
	
}
