package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 楼宇表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Building extends BaseEntity{

	/**
	 * 楼宇ID
	 */
	private Integer buildingId;

	/**
	 * 管理公司编号
	 */
	private String companyCode;

	/**
	 * 楼宇名称
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
	 * 所有权人
	 */
	private String owner;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 占地面积（㎡）
	 */
	private Double coverArea;

	/**
	 * 建筑面积（㎡）
	 */
	private Double buildArea;

	/**
	 * 用途
	 */
	private String purpose;

	/**
	 * 建成时间
	 */
	private String buildTime;

	/**
	 * 使用年限
	 */
	private Integer yearNum;

	/**
	 * 地上层数
	 */
	private Integer upNum;

	/**
	 * 地下层数
	 */
	private Integer downNum;

	/**
	 * 建筑高度（m）
	 */
	private Double height;

	/**
	 * 标准层高（m）
	 */
	private Double floorHeight;

	/**
	 * 得房率(%)
	 */
	private Double roomRate;

	/**
	 * 车位数
	 */
	private Integer parkingNum;

	/**
	 * 电梯数
	 */
	private Integer elevatorNum;

	/**
	 * 开发商
	 */
	private String developers;

	/**
	 * 楼宇图片
	 */
	private String imgs;

	/**
	 * 管理房源数
	 */
	private Integer roomNum;

	/**
	 * 管理工位数（个）
	 */
	private Integer workplacemNum;

	/**
	 * 管理车位数（个）
	 */
	private Integer carplaceNum;

	/**
	 * 管理房源/车位/工位数
	 */
	private Integer totalNum;

	/**
	 * 可招商房源/车位/工位数
	 */
	private Integer enableNum;

	/**
	 * 空置房源/车位/工位数
	 */
	private Integer vacantNum;

	/**
	 * 在租房源/车位/工位数量
	 */
	private Integer rentNum;

	/**
	 * 在租房源/车位/工位均价
	 */
	private BigDecimal avgPrice;

	/**
	 * 房源/车位/工位签约合同数
	 */
	private Integer contractNum;

	/**
	 * 房源/车位/工位签约企业数
	 */
	private Integer customerNum;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 楼层集合
	 */
	private List<Floor> floorList;

	public Building(){
		super();
	}

	public Building(String buildingName){
		this.buildingName = buildingName;
	}

	public Building(Integer projectId){
		this.projectId = projectId;
	}

}
