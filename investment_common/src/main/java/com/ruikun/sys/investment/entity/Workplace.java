package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 工位表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Workplace extends BaseEntity{
	 
	/**
	 * 工位ID
	 */						
	private Integer placeId;

	/**
	 * 工位ID集合
	 */
	private Integer[] placeIds;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 工位号
	 */						
	private String placeNo;	
		
	/**
	 * 所在房间ID
	 */						
	private Integer roomId;	
		
	/**
	 * 所在房间号
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
		
	/**
	 * 是否对外招租（1 是，2 否）
	 */						
	private String whetherOpen;

	/**
	 * 租金底价（元/月）
	 */
	private BigDecimal bottomPrice;

	/**
	 * 工位状态
	 */
	private String state;

	/**
	 * 工位状态集合
	 */
	private String[] states;

	/**
	 * 关键字
	 */
	private String keyword;

	public Workplace(){
		super();
	}

	public Workplace(String placeNo){
		this.placeNo = placeNo;
	}

	public Workplace(Integer roomId){
		this.roomId = roomId;
	}

}
