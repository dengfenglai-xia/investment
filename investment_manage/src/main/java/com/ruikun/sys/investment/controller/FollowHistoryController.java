package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.BasicData;
import com.ruikun.sys.investment.entity.Dept;
import com.ruikun.sys.investment.entity.FollowHistory;
import com.ruikun.sys.investment.entity.FollowNotice;
import com.ruikun.sys.investment.service.BasicDataService;
import com.ruikun.sys.investment.service.FollowHistoryService;
import com.ruikun.sys.investment.service.FollowNoticeService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class FollowHistoryController {

    @Autowired
    private FollowHistoryService followHistoryService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private FollowNoticeService followNoticeService;

    /**
     * 添加跟进历史
     **/
    @RequestMapping("insertFollowHistory")
    @ResponseBody
    public Map insertFollowHistory(FollowHistory followHistory){
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            followHistory.setCreateUserId(userId);
            followHistory.setUpdateUserId(userId);
            followHistoryService.insertFollowHistory(followHistory);
            FollowNotice followNotice = new FollowNotice();
            followNotice.setFollowPlanTime(followHistory.getFollowTime());
            followNotice.setNoticeContent(followHistory.getFollowContent());
            followNotice.setCreateTime(new Date());
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
     * 查询跟进历史
     **/
    @RequestMapping("queryFollowHistoryList")
    @ResponseBody
    public Map queryFollowHistoryList(FollowHistory followHistory){
        Map map = Maps.newHashMap();
        List<FollowHistory> followHistoryList = followHistoryService.queryFollowHistoryList(followHistory);
        map.put("followHistoryList",followHistoryList);
        return map;
    }

    /**
     *  跟进记录页面
     **/
    @RequestMapping("toFollowHistoryPage")
    public String toFollowHistoryPage(FollowHistory followHistory, Model model){
        //跟进方式
        List<BasicData> typeList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_FOLLOW_TYPE));
        model.addAttribute("typeList",typeList);
        return "followHistory/list";
    }

}
