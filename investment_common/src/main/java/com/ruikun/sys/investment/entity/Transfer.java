package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 结转
 * @author: xiachunyu
 * @date: 2019-07-18
 */
@Data
public class Transfer {

    /**
     * 转入账单ID
     */
    private Long billId;

    /**
     * 转入账单编号
     */
    private String billCode;

    /**
     * 转入合同编号
     */
    private String contractCode;

    /**
     * 转入合同版本号
     */
    private Integer version;

    /**
     * 转入公司编号
     */
    private String companyCode;

    /**
     * 结转金额
     */
    private BigDecimal transferAmt;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作人ID
     */
    private Integer operatorId;

    /**
     * 转出账集合
     */
    List<TransferOut> transferOutList;

}
