package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 抄表数据属性
 * @author: xiachunyu
 * @date: 2019-09-26
 */
@Data
public class Meter extends BaseEntity{
	 
	/**
	 * ID
	 */						
	private Integer id;	
		
	/**
	 * 类型1 电表，2 水表，3 天然气
	 */						
	private String type;	
		
	/**
	 * 公司编号
	 */						
	private String companyCode;

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
	 * 房间ID
	 */						
	private Integer roomId;	
		
	/**
	 * 房间号
	 */						
	private String roomNo;
		
	/**
	 * 表号
	 */						
	private String meterNo;	
		
	/**
	 * 起始度数
	 */						
	private double initNum;
		
	/**
	 * 本次度数
	 */						
	private double nowNum;

	/**
	 * 本次用量
	 */
	private double useNum;

	/**
	 * 日期区间
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
	 * 操作人
	 */						
	private String operator;

	/**
	 * 费用
	 */
	private BigDecimal cost;

}
