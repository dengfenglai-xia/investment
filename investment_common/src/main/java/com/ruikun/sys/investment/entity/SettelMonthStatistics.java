package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SettelMonthStatistics {

    private String feeType;

    private String billType;

    private BigDecimal janAmt = new BigDecimal("0");

    private BigDecimal febAmt = new BigDecimal("0");

    private BigDecimal marAmt = new BigDecimal("0");

    private BigDecimal aprAmt = new BigDecimal("0");

    private BigDecimal mayAmt = new BigDecimal("0");

    private BigDecimal junAmt = new BigDecimal("0");

    private BigDecimal julAmt = new BigDecimal("0");

    private BigDecimal augAmt = new BigDecimal("0");

    private BigDecimal sepAmt = new BigDecimal("0");

    private BigDecimal octAmt = new BigDecimal("0");

    private BigDecimal novAmt = new BigDecimal("0");

    private BigDecimal decAmt = new BigDecimal("0");

}
