package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractSummaryInfo{

	/**
	 * 序号
	 */
	private int orderNum;

	/**
	 * 合同编号
	 */
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 新版本号
	 */
	private Integer newVersion;

	/**
	 * 所属项目(ID)
	 */
	private Integer projectId;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 所属楼宇(ID)
	 */
	private Integer buildingId;

	/**
	 * 楼宇名称
	 */
	private String buildingName;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 合同类型
	 */
	private String contractType;

	/**
	 * 跟进人
	 */
	private String followPerson;

	/**
	 * 审核状态
	 */
	private String auditState;

	/**
	 * 签订日期
	 */
	private String signDate;

	/**
	 * 客户ID
	 */
	private Integer customerId;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 合同期限
	 */
	private String term;

	/**
	 * 合同生效日期
	 */
	private String startDate;

	/**
	 * 合同截止日期
	 */
	private String endDate;

	/**
	 * 租金单价
	 */
	private BigDecimal unitPrice;

	/**
	 * 租金单价
	 */
	private String unitPriceStr;

	/**
	 * 租金计价单位（1 元/㎡·天，2 元/㎡·月，3 元/天，4 元/月）
	 */
	private String chargeUnit;

	/**
	 * 月租金
	 */
	private BigDecimal monthPrice;

	/**
	 * 年租金
	 */
	private BigDecimal yearPrice;

	/**
	 * 签约房间号
	 */
	private String roomNos;

	/**
	 * 签约房间总面积
	 */
	private Double totalArea;

	/**
	 * 免租期
	 */
	private String freeTime;

	/**
	 * 年收入
	 */
	private BigDecimal yearIncome;

	/**
	 * 年全责收入
	 */
	private BigDecimal yearActualIncome;

	/**
	 * 签约年份
	 */
	private String year;


}
