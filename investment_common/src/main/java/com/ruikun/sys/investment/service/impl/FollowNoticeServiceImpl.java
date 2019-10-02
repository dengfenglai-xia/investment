package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.FollowNotice;
import com.ruikun.sys.investment.mapper.FollowNoticeMapper;
import com.ruikun.sys.investment.service.FollowNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跟进提醒
 **/
@Service
public class FollowNoticeServiceImpl implements FollowNoticeService {

    @Autowired
    private FollowNoticeMapper followNoticeMapper;

    /**
     * 添加跟进记录
     **/
    @Override
    public void insertFollowNotice(FollowNotice followNotice) {
        followNoticeMapper.insertFollowNotice(followNotice);
    }

    /**
     * 查询提醒
     **/
    @Override
    public List<FollowNotice> queryFollowNoticeList(FollowNotice followNotice) {
        List<FollowNotice> followNoticeList = followNoticeMapper.queryFollowNoticeList(followNotice);
        return followNoticeList;
    }

    /**
     * 查询日期
     **/
    @Override
    public String queryFollowDateList(FollowNotice followNotice) {
        String dates = "";
        List<String> dateList = followNoticeMapper.queryFollowDateList(followNotice);
        for (String date : dateList) {
            dates += date + ",";
        }
        return dates;
    }

}
