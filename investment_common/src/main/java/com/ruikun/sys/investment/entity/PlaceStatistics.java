package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlaceStatistics {

    /**
     * 总面积
     */
    private double totalArea;

    /**
     * 总数
     */
    private Integer totalNum;

    /**
     * 可招商面积
     */
    private double enableArea;

    /**
     * 可招商数
     */
    private int enableNum;

    /**
     * 在租总面积
     */
    private double rentArea;

    /**
     * 在租总数
     */
    private int rentNum;

    /**
     * 空置总面积
     */
    private double vacantArea;

    /**
     * 空置总数
     */
    private int vacantNum;

    /**
     * 在租均价（元㎡·天）
     */
    private BigDecimal avgPrice;

    /**
     * 出租率
     */
    private double rentRate;

    /**
     * 签约合同数
     */
    private int contractNum;

    /**
     * 签约客户数
     */
    private int customerNum;

}
