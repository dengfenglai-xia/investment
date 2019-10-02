package com.ruikun.sys.investment.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 合同收款计划合计
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Data
public class ContractReceiptPlanSum{

	/**
	 * 账单编号
	 */
	private String billCode;

	/**
	 * 应收日期
	 */
	private String receivableDate;

	/**
	 * 费用金额合计
	 */
	private BigDecimal costAmtSum;

	/**
	 * 收款计划明细
	 */
	List<ContractReceiptPlan> planList;

}
