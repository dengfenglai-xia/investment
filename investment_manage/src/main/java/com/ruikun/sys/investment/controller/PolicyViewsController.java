package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.entity.Order;
import com.ruikun.sys.investment.entity.Policy;
import com.ruikun.sys.investment.mapper.PolicyViewsMapper;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class PolicyViewsController {

    @Autowired
    private PolicyViewsMapper policyViewsMapper;


    @RequestMapping("toCustomerView")
    public String toCustomerView() {
        return "policyViews/customer";
    }

    @RequestMapping("toRentView")
    public String toRentView() {
        return "policyViews/rent";
    }

    @RequestMapping("queryIndustryList")
    @ResponseBody
    public Map queryIndustryList(String type) {
        Map map = Maps.newHashMap();
        List<Policy> list;
        if("1".equals(type)){
            list = policyViewsMapper.queryIndustryList1();
        }else{
            list = policyViewsMapper.queryIndustryList2();
        }
        for (Policy p : list) {
            if (StringUtils.isEmpty(p.getName())) {
                p.setName("其他");
            }
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping("querySourceList")
    @ResponseBody
    public Map querySourceList(String type) {
        Map map = Maps.newHashMap();
        List<Policy> list;
        if("1".equals(type)){
            list = policyViewsMapper.querySourceList1();
        }else{
            list = policyViewsMapper.querySourceList2();
        }
        for (Policy p : list) {
            if (StringUtils.isEmpty(p.getName())) {
                p.setName("其他");
            }
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping("queryBargainList")
    @ResponseBody
    public Map queryBargainList(String type) {
        Map map = Maps.newHashMap();
        List<Policy> list = policyViewsMapper.querySourceList2();
        for (Policy p : list) {
            if (StringUtils.isEmpty(p.getName())) {
                p.setName("其他");
            }
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping("queryTop5List")
    @ResponseBody
    public Map queryTop5List(String model,String type) {
        Map map = Maps.newHashMap();
        List<Policy> list;
        if("房源".equals(model)){
            list = policyViewsMapper.queryTop5List1(type);
        }else{
            list = policyViewsMapper.queryTop5List2(type);
        }
        for (Policy p : list) {
            if (StringUtils.isEmpty(p.getName())) {
                p.setName("其他");
            }
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping("queryTendencyList")
    @ResponseBody
    public Map queryTendencyList(String type) {
        Map map = Maps.newHashMap();
        String date = DateTimeUtil.getFormatDate();
        String month = date.substring(0, 7);
        map.put("month",month);
        List<Policy> list = policyViewsMapper.queryTendencyList(map);
        for (Policy p : list) {
            if (StringUtils.isEmpty(p.getName())) {
                p.setName("其他");
            }
        }
        map.put("list", list);
        return map;
    }

    @RequestMapping("queryReasonList")
    @ResponseBody
    public Map queryReasonList(String type) {
        Map map = Maps.newHashMap();
        List<Policy> list = policyViewsMapper.queryReasonList();
        map.put("list", list);
        return map;
    }


}
