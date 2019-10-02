package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 合同表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Contract extends BaseEntity{
	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 合同编号
	 */
	private String contractCode;

	/**
	 * 同类合同编号
	 */
	private String sameCode;

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
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 合同类型
	 */
	private String contractType;

	/**
	 * 跟进人
	 */
	private String followPerson;

	/**
	 * 变更生效日/期退租日期
	 */
	private String changeDate;

	/**
	 * 合同变更/退租原因
	 */
	private String changeReason;

	/**
	 * 合同状态 1
	 */
	private String stateOne;

	/**
	 * 合同状态 2
	 */
	private String stateTwo;

	/**
	 * 合同状态
	 */
	private String state;

	/**
	 * 操作类型
	 */
	private String operType;

	/**
	 * 审核状态
	 */
	private String auditState;

	/**
	 * 签订日期
	 */
	private String signDate;

	/**
	 * 客户类型（1 公司，2 个人）
	 */
	private String leaseType;

	/**
	 * 客户ID
	 */
	private Integer customerId;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 所属行业
	 */
	private String industry;

	/**
	 * 纳税人识别号
	 */
	private String ceditCode;

	/**
	 * 社会统一信用代码
	 */
	private String unifiedCode;

	/**
	 * 法人
	 */
	private String legalPerson;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 联系人身份证
	 */
	private String contactsCard;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 工作单位
	 */
	private String workUnit;

	/**
	 * 通讯地址
	 */
	private String linkAddress;

	/**
	 * 渠道来源
	 */
	private String channelSource;

	/**
	 * 渠道名称
	 */
	private String sourceName;

	/**
	 * 合同生效日期
	 */
	private String startDate;

	/**
	 * 合同生效日期范围
	 */
	private String startDateRange1;
	private String startDateRange2;

	/**
	 * 合同截止日期
	 */
	private String endDate;

	/**
	 * 合同截止日期范围
	 */
	private String endDateRange1;
	private String endDateRange2;

	/**
	 * 租期
	 */
	private String leaseTerm;

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

	/**
	 * 保证金
	 */
	private BigDecimal deposit;

	/**
	 * 保证金类型
	 */
	private String depositType;

	/**
	 * 保证金滞纳金
	 */
	private Double depositLateRatio;

	/**
	 * 租金
	 */
	private BigDecimal rentAmt;

	/**
	 * 物业押金
	 */
	private BigDecimal wyDeposit;

	/**
	 * 履约保障金
	 */
	private BigDecimal breachAmt;

	/**
	 * 合同剩余天数
	 */
	private Integer remainderDays;

	/**
	 * 项目提前提醒天数
	 */
	private Integer remindDays;

	/**
	 * 合同是否到期（1 未到期，2 即将到期，3 已到期）
	 */
	private String expireState;

	/**
	 *  操作人
	 */
	private String operator;

	/**
	 * 签约房间集合
	 */
	private List<ContractRoom> roomList;

	/**
	 * 签约车位集合
	 */
	private List<ContractCarplace> carplaceList;

	/**
	 * 签约工位集合
	 */
	private List<ContractWorkplace> workplaceList;

	/**
	 * 签约房间号
	 */
	private String roomNos;

	/**
	 * 签约房间总面积
	 */
	private Double totalArea;

	/**
	 * 注册房号
	 */
	private String regRoomNo;

	/**
	 * 合同租项费用集合
	 */
	private List<ContractRent> rentList;

	/**
	 * 合同递增率集合
	 */
	private List<ContractIncreaseRate> increaseRateList;

	/**
	 * 合同免租期集合
	 */
	private List<ContractFreeTime> freeTimeList;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 查询类型
	 */
	private String searchType;

	/**
	 * 操作类型
	 */
	private String operFlag;


	public Contract(){
		super();
	}

	public Contract(Integer projectId){
		this.projectId = projectId;
	}

	public Contract(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
	public Contract(String sameCode){
		this.sameCode = sameCode;
	}

}
