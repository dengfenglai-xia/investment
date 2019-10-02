package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SettelMonthStatistics {

    private String feeType;

    private String billType;

    private BigDecimal janAmt;

    private BigDecimal febAmt;

    private BigDecimal marAmt;

    private BigDecimal aprAmt;

    private BigDecimal mayAmt;

    private BigDecimal junAmt;

    private BigDecimal julAmt;

    private BigDecimal augAmt;

    private BigDecimal sepAmt;

    private BigDecimal octAmt;

    private BigDecimal novAmt;

    private BigDecimal decAmt;

}
