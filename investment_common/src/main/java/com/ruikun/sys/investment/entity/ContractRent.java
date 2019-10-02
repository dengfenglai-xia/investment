package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 合同_周期费用表属性
 * @author: xiachunyu
 * @date: 2019-06-27
 */
@Data
public class ContractRent extends BaseEntity{

	/**
	 * 合同编号
	 */
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 费用名称
	 */
	private String costName;

	/**
	 * 租赁开始时间
	 */
	private String startDate;

	/**
	 * 租赁截止时间
	 */
	private String endDate;

	/**
	 * 付费时间区间时间(开始时间 ~ 结束时间)
	 */
	private String rentDateRange;

	/**
	 * 租金单价
	 */
	private BigDecimal unitPrice;

	/**
	 * 租金计价单位（1 元/㎡·天，2 元/㎡·月，3 元/天，4 元/月）
	 */
	private String chargeUnit;

	/**
	 * 日租金(元/天)
	 */
	private BigDecimal dayPrice;

	/**
	 * 月租金计算方式（1 按固定租金 ，2 按实际天数）
	 */
	private String monthCountType;

	/**
	 * 月租金(元/月)
	 */
	private BigDecimal monthPrice;

	/**
	 * 提前付款日
	 */
	private Integer advancePayDays;

	/**
	 * 提前付款日类型（1 自然日，2 工作日 ，3 固定日期）
	 */
	private String advancePayType;

	/**
	 * 付款周期（每个几个月付一次款）
	 */
	private Integer payCycle;

	/**
	 * 滞纳金比例（%）
	 */
	private Double feeLateRatio;

	public ContractRent(){
		super();
	}

	public ContractRent(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}

}
