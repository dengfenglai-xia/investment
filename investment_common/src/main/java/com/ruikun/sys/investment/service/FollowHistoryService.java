package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.FollowHistory;

import java.util.List;

public interface FollowHistoryService {
    void insertFollowHistory(FollowHistory followHistory);

    List<FollowHistory> queryFollowHistoryList(FollowHistory followHistory);
}
