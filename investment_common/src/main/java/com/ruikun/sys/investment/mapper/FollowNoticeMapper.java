package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.FollowNotice;

import java.util.List;

/**
 *  跟进提醒
 **/
public interface FollowNoticeMapper {

    /**
     * 添加根据提醒
     **/
    void insertFollowNotice(FollowNotice followNotice);

    /**
     * 查询提醒
     **/
    List<FollowNotice> queryFollowNoticeList(FollowNotice followNotice);

    List<String> queryFollowDateList(FollowNotice followNotice);
}
