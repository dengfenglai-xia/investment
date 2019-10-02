package com.ruikun.sys.investment.entity;

import lombok.Data;

/**
 * 跟进记录
 **/
@Data
public class FollowHistory extends BaseEntity{
    private Integer followId;
    private Integer temporaryId;
    private String followTime;
    private String followWay;
    private String followContent;
}
