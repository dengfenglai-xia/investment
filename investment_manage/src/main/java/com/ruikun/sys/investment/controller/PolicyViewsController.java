package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.entity.Order;
import com.ruikun.sys.investment.entity.Policy;
import com.ruikun.sys.investment.mapper.PolicyViewsMapper;
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
}
