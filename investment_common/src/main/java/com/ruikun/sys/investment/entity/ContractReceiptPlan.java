package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 合同收款计划表属性
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Data
public class ContractReceiptPlan extends BaseEntity{
	 
	/**
	 * ID
	 */						
	private Long id;

	/**
	 * 账单编号
	 */
	private String billCode;

	/**
	 * 合同编号
	 */						
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 项目ID
	 */
	private Integer projectId;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 客户ID
	 */
	private Integer customerId;

	/**
	 * 费用名称
	 */						
	private String costName;	
		
	/**
	 * 应收日期
	 */						
	private String receivableDate;	
		
	/**
	 * 费用开始日期
	 */						
	private String startDate;	
		
	/**
	 * 费用结束日期
	 */						
	private String endDate;

	/**
	 * 变更时间
	 */
	private String changeDate;


	/**
	 * 费用金额
	 */
	private BigDecimal costAmt;

	/**
	 * 滞纳金比例
	 */
	private Double feeLateRatio;

	/**
	 * 是否已出账单（1 未出，2 已出）
	 */
	private String whetherOut;

	/**
	 * 合同状态-1
	 */
	private String stateOne;

	/**
	 * 合同状态-2
	 */
	private String stateTwo;

	/**
	 * 合同类型
	 */
	private String operType;

	public ContractReceiptPlan(){
		super();
	}

	public ContractReceiptPlan(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}

}
