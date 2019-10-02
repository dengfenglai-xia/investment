package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.FollowNotice;

import java.util.List;

public interface FollowNoticeService {

    void insertFollowNotice(FollowNotice followNotice);

    List<FollowNotice> queryFollowNoticeList(FollowNotice followNotice);

    String queryFollowDateList(FollowNotice followNotice);
}
