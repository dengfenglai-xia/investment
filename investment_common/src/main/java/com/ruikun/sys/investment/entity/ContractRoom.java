package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 合同_房间表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class ContractRoom extends BaseEntity{
	 
	/**
	 * 房间ID
	 */						
	private Integer id;

	/**
	 *  同类合同编号
	 */
	private String sameCode;

	/**
	 * 合同编号
	 */						
	private String contractCode;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 合同状态1
	 */
	private String stateOne;

	/**
	 * 合同状态2
	 */
	private String stateTwo;

	/**
	 * 合同审核状态
	 */
	private String auditState;

	/**
	 * 跟进人
	 */
	private String followPerson;

	/**
	 * 签约房间ID
	 */						
	private Integer roomId;	
		
	/**
	 * 签约房间号
	 */						
	private String roomNo;

	/**
	 * 签约房间状态
	 */
	private String roomState;

	/**
	 * 签约房间面积
	 */
	private Double buildArea;

	/**
	 * 所在楼层ID
	 */						
	private Integer floorId;	
		
	/**
	 * 所在楼层
	 */						
	private String floorNo;	
		
	/**
	 * 所在楼宇ID
	 */						
	private Integer buildingId;	
		
	/**
	 * 所在楼宇
	 */						
	private String buildingName;	
		
	/**
	 * 项目ID
	 */						
	private Integer projectId;	
		
	/**
	 * 项目名称
	 */						
	private String projectName;

	/**
	 * 开始日期
	 */
	private String startDate;

	/**
	 * 截止日期
	 */
	private String endDate;

	/**
	 * 合同剩余天数
	 */
	private Integer remainderDays;

	public ContractRoom(){
		super();
	}

	public ContractRoom(Integer buildingId){
		this.buildingId = buildingId;
	}

	public ContractRoom(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
}
