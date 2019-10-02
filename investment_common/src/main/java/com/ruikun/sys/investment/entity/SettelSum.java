package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description: 结算属性
 * @author: xiachunyu
 * @date: 2019-07-16
 */
@Data
public class SettelSum extends BaseEntity {

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 合同版本
     */
    private Integer version;

    /**
     * 公司编号
     */
    private String companyCode;

    /**
     * 应该收款日期
     */
    private String shouldDealDate;

    /**
     * 账单编号
     */
    private String billCode;

    /**
     * 账单类型
     */
    private String billType;

    /**
     * 结算信息
     */
    private List<Settel> settleList;


}
