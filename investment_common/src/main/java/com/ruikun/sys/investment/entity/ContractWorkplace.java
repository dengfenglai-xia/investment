package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 合同-工位属性
 * @author: xiachunyu
 * @date: 2019-08-04
 */
@Data
public class ContractWorkplace extends BaseEntity{
	 
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
	 * 合同生效日期
	 */						
	private String startDate;	
		
	/**
	 * 合同截止时间
	 */						
	private String endDate;	
		
	/**
	 * 工位ID
	 */						
	private Integer placeId;	
		
	/**
	 * 工位号
	 */						
	private String placeNo;	
		
	/**
	 * 房间ID
	 */						
	private Integer roomId;	
		
	/**
	 * 房间号
	 */						
	private String roomNo;	
		
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

	public ContractWorkplace(){
		super();
	}

	public ContractWorkplace(String contractCode,Integer version){
		this.contractCode = contractCode;
		this.version = version;
	}
	
}
