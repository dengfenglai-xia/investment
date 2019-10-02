package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.util.Date;

/**
 * @className: BaseEntity
 * @description: 基础属性
 * @author: xiachunyu
 * @date: 2019/05/21
 */
@Data
public class BaseEntity {
    /**
     * 创建人ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者ID
     */
    private Integer updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标记（0 正常，1 删除）
     */
    private String delFlag;

}
