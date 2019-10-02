package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.FollowHistory;
import com.ruikun.sys.investment.mapper.FollowHistoryMapper;
import com.ruikun.sys.investment.service.FollowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowHistoryServiceImpl implements FollowHistoryService {

    @Autowired
    private FollowHistoryMapper followHistoryMapper;

    /**
     * 添加跟进历史
     **/
    @Override
    public void insertFollowHistory(FollowHistory followHistory) {
        followHistoryMapper.insertFollowHistory(followHistory);
    }

    /**
     * 查询跟进历史
     **/
    @Override
    public List<FollowHistory> queryFollowHistoryList(FollowHistory followHistory) {
        return followHistoryMapper.queryFollowHistoryList(followHistory);
    }
}
