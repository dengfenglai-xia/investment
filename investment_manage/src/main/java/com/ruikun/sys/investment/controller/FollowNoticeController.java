package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.FollowHistory;
import com.ruikun.sys.investment.entity.FollowNotice;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.FollowNoticeService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class FollowNoticeController {

    @Autowired
    private FollowNoticeService followNoticeService;
    /**
     * 添加跟进历史
     **/
    @RequestMapping("insertFollowNotice")
    @ResponseBody
    public Map insertFollowNotice(FollowNotice followNotice){
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            followNotice.setCreateUserId(userId);
            followNotice.setUpdateUserId(userId);
            followNoticeService.insertFollowNotice(followNotice);
            map.put(Constants.MSG, "添加成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * 查询提醒
     **/
    @RequestMapping("queryFollowNoticeList")
    @ResponseBody
    public Map queryFollowNoticeList(FollowNotice followNotice){
        User user = CacheUtils.getUser();
        Map map = Maps.newHashMap();
        followNotice.setCreateUserId(user.getUserId());
        List<FollowNotice> followNoticeList = followNoticeService.queryFollowNoticeList(followNotice);
        map.put("followNoticeList",followNoticeList);
        return map;
    }

}
