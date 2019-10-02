package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 项目表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Project extends BaseEntity{
	 
	/**
	 * 项目ID
	 */						
	private Integer projectId;	
		
	/**
	 * 项目名
	 */						
	private String projectName;	
		
	/**
	 * 项目业态（基础信息维护）
	 */						
	private String businessMode;

	/**
	 * 管理公司编号
	 */
	private String companyCode;

	/**
	 * 管理公司
	 */						
	private String company;	
		
	/**
	 * 所在省
	 */						
	private String province;	
		
	/**
	 * 所在市
	 */						
	private String city;	
		
	/**
	 * 所在区/县
	 */						
	private String region;	
		
	/**
	 * 详细地址
	 */						
	private String address;	
		
	/**
	 * 经度
	 */						
	private String longitude;	
		
	/**
	 * 纬度
	 */						
	private String latitude;	
		
	/**
	 * 占地面积（㎡）
	 */						
	private Double coverArea;
		
	/**
	 * 建筑面积（㎡）
	 */						
	private Double buildArea;
		
	/**
	 * 项目图片
	 */						
	private String imgs;

	/**
	 * 电费单价
	 */
	private BigDecimal powerPrice;

	/**
	 * 水费单价
	 */
	private BigDecimal waterPrice;

	/**
	 * 燃气费单价
	 */
	private BigDecimal gasPrice;

	/**
	 * 合同到期提前提醒天数
	 */
	private Integer remindDays;

	/**
	 * 账单提前天数
	 */
	private Integer billDays;

	/**
	 * 楼宇数量（栋）
	 */
	private int buildingNum;

	/**
	 * 管理房源（间）
	 */
	private int roomNum;

	/**
	 * 管理总面积（㎡）
	 */
	private double totalArea;

	/**
	 * 管理工位数（个）
	 */
	private int workplaceNum;

	/**
	 * 管理车位数（个）
	 */
	private int carplaceNum;

	/**
	 * 关键字
	 */
	private String keyword;

	public Project(){
		super();
	}

	public Project(Integer projectId){
		this.projectId = projectId;
	}

}
