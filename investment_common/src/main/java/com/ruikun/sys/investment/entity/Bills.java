package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 账单表属性
 * @author: xiachunyu
 * @date: 2019-07-03
 */
@Data
public class Bills extends BaseEntity{

	/**
	 * 账单ID
	 */
	private Long billId;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 账单编号
	 */
	private String billCode;

	/**
	 * 账单编号集合
	 */
	private String[] billCodeArr;

	/**
	 * 合同编号
	 */
	private String contractCode;

	/**
	 * 合同版本号
	 */
	private Integer version;

	/**
	 * 合同类型
	 */
	private String contractType;

	/**
	 * 项目ID
	 */
	private Integer projectId;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 楼宇ID
	 */
	private Integer buildingId;

	/**
	 * 楼宇名称
	 */
	private String buildingName;

	/**
	 * 房间号
	 */
	private String roomNos;

	/**
	 * 客户ID
	 */
	private Integer customerId;

	/**
	 * 客户名称
	 */
	private String customerName;

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
	 * 费用周期(开始时间 ~ 结束时间)
	 */
	private String dateRange;

	/**
	 * 开始日期
	 */
	private String startDate;

	/**
	 * 结束日期
	 */
	private String endDate;

	/**
	 * 单价
	 */
	private BigDecimal price;

	/**
	 * 用量
	 */
	private BigDecimal volume;

	/**
	 * 变更时间
	 */
	private String changeDate;

	/**
	 * 费用总金额（元）
	 */
	private BigDecimal costAmt;

	/**
	 * 已完结金额（元）
	 */
	private BigDecimal finishAmt;

	/**
	 * 结转金额（元）
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
	 * 完结浮动金额（元）
	 */
	private BigDecimal floatFinishAmt;

	/**
	 * 结转浮动金额（元）
	 */
	private BigDecimal floatTransferAmt;

	/**
	 * 未完结浮动金额（元）
	 */
	private BigDecimal floatResidualAmt;

	/**
	 * 滞纳金浮动金额（元）
	 */
	private BigDecimal floatFeeLateAmt;

	/**
	 * 滞纳金比例
	 */
	private Double feeLateRatio;

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

	/**
	 * 状态（1 未收/付，2 部分收/付，3 已收/付 ）
	 */
	private String state;

	/**
	 * 账单性质（1 正式，2 临时）
	 */
	private String billProperty;

	/**
	 * 状态集合
	 */
	private String[] states;

	/**
	 * 确认状态（1 未确认，2 已确认）
	 */
	private String confirmState;

	/**
	 * 确认人
	 */
	private String confirmer;

	/**
	 * 关键字
	 */
	private String keyword;


	public Bills(){
		super();
	}

	public Bills(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
}
