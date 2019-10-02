package com.ruikun.sys.investment.entity;

import lombok.Data;

/**
 * 跟进提醒
 **/
@Data
public class FollowNotice extends BaseEntity{
    private Integer noticeId;
    private String followPlanTime;
    private String noticeContent;
    private Integer temporaryId;
    private String isShow;
    private String today;
}
