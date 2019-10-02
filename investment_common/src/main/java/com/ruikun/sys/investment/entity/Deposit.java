package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Deposit {
    private String startDate;
    private String endDate;
    private Integer projectId;
    private Integer buildingId;
    private String companyCode;
    private int orderNum;
    private String customerName;
    private String costName;
    private BigDecimal costAmt;
    private String state;
    private String payTime;
    private String returnTime;
}
