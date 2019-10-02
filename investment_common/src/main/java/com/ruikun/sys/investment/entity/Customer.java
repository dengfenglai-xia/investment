package com.ruikun.sys.investment.entity;

import lombok.Data;

/**
 * @Description: 客户表属性
 * @author: xiachunyu
 * @date: 2019-06-26
 */
@Data
public class Customer extends BaseEntity{
	 
	/**
	 * 客户ID
	 */						
	private Integer customerId;	
		
	/**
	 * 客户类型（1 公司，2 个人）
	 */						
	private String customerType;
		
	/**
	 * 客户名称
	 */						
	private String customerName;

	/**
	 * 管理公司编号
	 */
	private String companyCode;

	/**
	 * 管理公司
	 */
	private String company;

	/**
	 * 身份证号
	 */						
	private String contactsCard;
		
	/**
	 * 客户状态（1 正式客户，2 历史历史）
	 */						
	private String state;	
		
	/**
	 * 联系人
	 */						
	private String contacts;	
		
	/**
	 * 联系电话
	 */						
	private String phone;	
		
	/**
	 * 工作单位
	 */						
	private String workUnit;	
		
	/**
	 * 所属行业（基础信息维护）
	 */						
	private String industry;	
		
	/**
	 * 渠道来源（基础信息维护）
	 */						
	private String channelSource;	
		
	/**
	 * 渠道名称
	 */						
	private String sourceName;	
		
	/**
	 * 邮箱
	 */						
	private String email;

	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 通讯地址
	 */						
	private String linkAddress;	
		
	/**
	 * 纳税人识别号
	 */						
	private String ceditCode;	
		
	/**
	 * 开户行
	 */						
	private String openBank;	
		
	/**
	 * 账号
	 */						
	private String account;	
		
	/**
	 * 统一社会信用代码
	 */						
	private String unifiedCode;	
		
	/**
	 * 工商注册号
	 */						
	private String businessRegNo;	
		
	/**
	 * 组织机构代码
	 */						
	private String organCode;	
		
	/**
	 * 法人
	 */						
	private String legalPerson;	
		
	/**
	 * 注册资本
	 */						
	private String regCapital;	
		
	/**
	 * 经营状态
	 */						
	private String businessState;	
		
	/**
	 * 公司类型
	 */						
	private String companyType;	
		
	/**
	 * 登记机关
	 */						
	private String regOffice;	
		
	/**
	 * 核准日期
	 */						
	private String passDate;	
		
	/**
	 * 成立日期
	 */						
	private String buildDate;	
		
	/**
	 * 营业期限
	 */						
	private String businessTerm;	
		
	/**
	 * 人员规模
	 */						
	private String peopleScale;	
		
	/**
	 * 注册地址
	 */						
	private String regAddress;	
		
	/**
	 * 经营范围
	 */						
	private String businessScope;

	/**
	 * 合同数量
	 */
	private Integer contractNum;

	/**
	 * 房源数量
	 */
	private Integer roomNum;

	/**
	 * 租赁面积（㎡）
	 */
	private Double totalArea;

	/**
	 * 工位数量
	 */
	private Integer workplaceNum;

	/**
	 * 车位数量
	 */
	private Integer carplaceNum;

	/**
	 * 逾期缴费次数
	 */
	private Integer overdueNum;

	/**
	 * 租赁面积（㎡）区间
	 */
	private Double startArea;
	private Double endArea;

	/**
	 * 是否有过逾期
	 */
	private String overdueFlag;

	/**
	 * 关键字
	 */
	private String keyword;

	public Customer(){
		super();
	}

	public Customer(Integer customerId){
		this.customerId = customerId;
	}
}
