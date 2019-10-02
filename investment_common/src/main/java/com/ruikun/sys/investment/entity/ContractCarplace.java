package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 合同_车位表属性
 * @author: xiachunyu
 * @date: 2019-06-28
 */
@Data
public class ContractCarplace extends BaseEntity{
	 
	/**
	 * ID
	 */						
	private Long id;	
		
	/**
	 * 合同编号
	 */						
	private String contractCode;	
		
	/**
	 * 版本号
	 */						
	private Integer version;	
		
	/**
	 * 签约车位ID
	 */						
	private Integer placeId;	
		
	/**
	 * 签约车位号
	 */						
	private String placeNo;	
		
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
	 * 结束日期
	 */
	private String endDate;

	public ContractCarplace(){
		super();
	}

	public ContractCarplace(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
	
}
