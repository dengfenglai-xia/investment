package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BillsSum{

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 楼宇名称
	 */
	private String buildingName;

	/**
	 * 合同类型
	 */
	private String contractType;

	/**
	 * 合同编号
	 */
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 签约房间号
	 */
	private String roomNos;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 账单编号
	 */
	private String billCode;

	/**
	 * 应收/付金额（元）
	 */						
	private BigDecimal costAmt;

	/**
	 * 已完结金额（元）
	 */						
	private BigDecimal finishAmt;

	/**
	 * 结转总金额（元）
	 */
	private BigDecimal transferAmt;

	/**
	 * 未完结金额（元）
	 */						
	private BigDecimal residualAmt;
		
	/**
	 * 滞纳金（元）
	 */						
	private BigDecimal feeLateAmt;

	/**
	 * 减免金额（元）
	 */
	private BigDecimal freeAmt;

	/**
	 * 应收/付款时间
	 */						
	private String shouldDealDate;	
		
	/**
	 * 剩余/逾期天数
	 */						
	private Integer days;

	private String billType;

	/**
	 * 状态（1 未收/付，2 部分收/付，3 已收/付 ）
	 */						
	private String state;

	/**
	 * 确认状态（1 未确认，2 已确认）
	 */
	private String confirmState;

	/**
	 * 确认人
	 */
	private String confirmer;

	/**
	 * 确认人ID
	 */
	private Integer confirmerId;

	/**
	 * 账单集合
	 */
	List<Bills> billsList;

	/**
	 * 临时费用账单集合
	 */
	List<Bills> tempBillsList;

}
