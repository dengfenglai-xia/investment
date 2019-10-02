package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 结算报表
 * @author: xiachunyu
 * @date: 2019-07-16
 */
@Data
public class SettelReport{

    private String billType;

    private String feeType;

    private BigDecimal amount;

    private String month;

    private String companyCode;

    private Integer projectId;

    private Integer buildingId;

    private String customerName;

    private String year;

}
