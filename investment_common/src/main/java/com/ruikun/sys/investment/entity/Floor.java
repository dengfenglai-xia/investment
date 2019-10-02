package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 楼层表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Floor extends BaseEntity{
	 
	/**
	 * 楼层ID
	 */						
	private Integer floorId;	
		
	/**
	 * 楼层号
	 */						
	private String floorNo;

	/**
	 * 楼层名称
	 */
	private String floorName;

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
	 * 楼层平面图
	 */						
	private String imgs;

	/**
	 * 楼层ID集合
	 */
	private String[] ids;

	/**
	 * 起始楼层
	 */
	private String floorNo1;

	/**
	 * 结束楼层
	 */
	private String floorNo2;

	public Floor(){
		super();
	}

	public Floor(Integer buildingId){
		this.buildingId = buildingId;
	}
	
}
