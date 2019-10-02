package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 房间表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class Room extends BaseEntity {

    /**
     * 房间ID
     */
    private Integer roomId;

    /**
     * 公司编号
     */
    private String companyCode;

    /**
     * 房间ID集合
     */
    private Integer[] roomIds;

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

    /**
     * 使用面积（㎡）
     */
    private Double useArea;

    /**
     * 建筑面积（㎡）
     */
    private Double buildArea;

    /**
     * 起始面积（㎡）
     */
    private Double startArea;

    /**
     * 结束面积（㎡）
     */
    private Double endArea;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 是否对外招租（1 是，2 否）
     */
    private String whetherOpen;

    /**
     * 租金底价
     */
    private BigDecimal bottomPrice;

    /**
     * 单位
     */
    private String chargeUnit;

    /**
     * 装修（1 毛坯，2 简装，3 精装）
     */
    private String decorate;

    /**
     * 朝向（基础信息维护）
     */
    private String orientation;

    /**
     * 是否临街（1 是，2 否）
     */
    private String whetherStreet;

    /**
     * 配套设施
     */
    private String facilities;

    /**
     * 房源标签
     */
    private String tags;

    /**
     * 房源照片
     */
    private String imgs;

    /**
     * 房源状态
     */
    private String state;

    /**
     * 房源状态集合
     */
    private String[] states;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 关键字
     */
    private String keyword;

    private String startDateRange1;
    private String startDateRange2;
    private String endDateRange1;
    private String endDateRange2;

    public Room() {
        super();
    }

    public Room(String roomNo) {
        this.roomNo = roomNo;
    }

    public Room(Integer buildingId) {
        this.buildingId = buildingId;
    }

}
