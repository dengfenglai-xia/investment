package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.CommonUtil;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class ContractWorkplaceController {

    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private ContractService contractService;

}
