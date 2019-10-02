package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 结算记录属性
 * @author: xiachunyu
 * @date: 2019-07-16
 */
@Data
public class Settel extends BaseEntity{
	 
	/**
	 * 主键ID
	 */						
	private Long id;	
		
	/**
	 * 账单ID
	 */						
	private Long billId;

	/**
	 * 账单编号
	 */
	private String billCode;

	/**
	 * 合同编号
	 */						
	private String contractCode;	
		
	/**
	 * 合同版本
	 */						
	private Integer version;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 账单类型（1 收款，2 付款）
	 */						
	private String billType;

	/**
	 * 费用类别
	 */
	private String feeType;

	/**
	 * 费用名称
	 */						
	private String costName;	

	/**
	 * 费用开始日期
	 */						
	private String startDate;	
		
	/**
	 * 费用结束日期
	 */						
	private String endDate;	
		
	/**
	 * 处理时间
	 */						
	private String dealDate;	
		
	/**
	 * 应收/付金额（元）
	 */						
	private BigDecimal shouldAmt;
		
	/**
	 * 实收/付金额（元）
	 */						
	private BigDecimal actualAmt;	
		
	/**
	 * 结算方式（1 现金，2 银行转账，3 微信/支付宝，4 其他）
	 */						
	private String payType;	
		
	/**
	 * 操作人
	 */						
	private String operator;	
		
	
	
}
