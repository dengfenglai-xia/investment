package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.FollowHistory;

import java.util.List;

/**
 * 跟进历史
 **/
public interface FollowHistoryMapper {

    /**
     * 添加跟进历史
     **/
    void insertFollowHistory(FollowHistory followHistory);

    /**
     * 查询历史
     **/
    List<FollowHistory> queryFollowHistoryList(FollowHistory followHistory);
}
