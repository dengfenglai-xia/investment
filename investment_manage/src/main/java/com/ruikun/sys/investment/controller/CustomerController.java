package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.BasicDataService;
import com.ruikun.sys.investment.service.ContractService;
import com.ruikun.sys.investment.service.CustomerService;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户表相关操作
 * @author: xiachunyu
 * @date: 2019-06-26
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private ContractService contractService;

    @RequestMapping("toTempCustomerList")
    public String toTempCustomerList(Model model) {
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        model.addAttribute("industryList", industryList);
        return "customer/tempList";
    }

    /**
     * @Description: 跳转到客户信息列表页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toFormalCustomerList/{state}")
    public String toFormalCustomerList(@PathVariable("state") String state, Model model) {
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        model.addAttribute("industryList", industryList);
        model.addAttribute("state", state);
        return "customer/formalList";
    }

    /**
     * @Description: 查询客户表信息列表
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("queryCustomerList")
    @ResponseBody
    public Map queryCustomerList(HttpServletRequest request, Customer customer) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        customer.setCompanyCode(user.getCompanyCode());
        PagingUtil.setPageParam(request);
        List<Customer> list = customerService.queryCustomerList(customer);
        map.put(Constants.RESULT_DATA, new PageInfo<Customer>(list));
        return map;
    }

    /**
     * @Description: 查询客户表信息列表
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("queryCustomerBaseList")
    @ResponseBody
    public Map queryCustomerBaseList(Customer customer) {
        Map map = Maps.newHashMap();
        //客户信息
        User user = CacheUtils.getUser();
        customer.setCompanyCode(user.getCompanyCode());
        List<Customer> list = customerService.queryCustomerBaseList(customer);
        map.put("list", list);
        return map;
    }

    /**
     * @Description: 通过主键查询客户表信息详情
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("queryCustomerDetail/{customerId}")
    public String queryCustomerDetailByPrimarykey(@PathVariable("customerId") Integer customerId, Model model) {
        Customer customer = customerService.queryCustomerDetailByPrimarykey(customerId);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(customerId, Constants.DOC_TYPE_CUSTOMER));
        model.addAttribute("customer", customer);
        model.addAttribute("docsList", docsList);
        return "customer/formalDetail";
    }

    /**
     * @Description: 查询客户表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("queryCustomerDetail")
    @ResponseBody
    public Customer queryCustomerDetail(Integer customerId) {
        Customer customer = customerService.queryCustomerDetailByPrimarykey(customerId);
        return customer;
    }

    /**
     * @Description: 跳转到新增客户页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddCustomer")
    public String toAddCustomer(Model model) {
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        return "customer/addFormal";
    }

    /**
     * @Description: 新增客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("insertCustomer")
    @ResponseBody
    public Map insertCustomer(Customer customer) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            customer.setCreateUserId(user.getUserId()); // 创建者ID
            customer.setUpdateUserId(user.getUserId()); // 更新者ID
            customer.setCompanyCode(user.getCompanyCode());
            customer.setState(Constants.CUSTOMER_STATE_NOW);
            customerService.insertCustomer(customer);
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
     * @Description: 跳转到新增客户页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditorCustomer/{customerId}")
    public String toEditorCustomer(@PathVariable("customerId") Integer customerId, Model model) {
        Customer customer = customerService.queryCustomerDetailByPrimarykey(customerId);
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("customer", customer);
        return "customer/editorFormal";
    }

    /**
     * @Description: 修改客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("updateCustomer")
    @ResponseBody
    public Map updateCustomer(Customer customer) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            customer.setUpdateUserId(userId); //更新者ID
            customerService.updateCustomer(customer);
            map.put(Constants.MSG, "更新成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    @RequestMapping("deleteCustomer")
    @ResponseBody
    public Map deleteCustomer(Customer customer) {
        Map map = Maps.newHashMap();
        try {
            Contract contract = new Contract();
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
            contract.setCustomerId(customer.getCustomerId());
            List<Contract> list = contractService.queryContractList(contract);
            if(list.size() > 0){
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前客户下存在进行中合同，暂不能删除");
                return map;
            }
            Integer userId = CacheUtils.getUser().getUserId();
            customer.setUpdateUserId(userId);
            customer.setDelFlag(Constants.DEL_FLAG_DEL);
            customerService.updateCustomer(customer);
            map.put(Constants.MSG, "删除成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 查询合同表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractList")
    @ResponseBody
    public Map queryContractList(Contract contract) {
        Map map = Maps.newHashMap();
        String state = contract.getState();
        List<Contract> list = contractService.queryContractList(contract);
        if (state.equals("1")) {
            for (int i = list.size() - 1; i >= 0; i--) {
                Contract c = list.get(i);
                if (c.getStateOne().equals(Constants.CONTRACT_STATE_FILE)) {
                    list.remove(i);
                }
            }
        }
        map.put(Constants.RESULT_DATA, new PageInfo<Contract>(list));
        return map;
    }

}
