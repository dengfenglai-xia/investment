package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 转入
 * @author: xiachunyu
 * @date: 2019-07-18
 */
@Data
public class TransferEnter extends BaseEntity{
	 
	/**
	 * 转入ID（主键）
	 */						
	private Long enterId;	

	/**
	 * 转入账单ID
	 */						
	private Long billId;

	/**
	 * 转入账单编号
	 */
	private String billCode;

	/**
	 * 转入客户ID
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
	 * 转入合同编号
	 */						
	private String contractCode;	
		
	/**
	 * 转入合同版本号
	 */						
	private Integer version;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 转入签约房间
	 */						
	private String roomNos;	
		
	/**
	 * 转入费用名称
	 */						
	private String costName;	
		
	/**
	 * 转入开始时间
	 */						
	private String startDate;	
		
	/**
	 * 转入结束时间
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
