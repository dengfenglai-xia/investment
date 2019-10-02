package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.BasicDataService;
import com.ruikun.sys.investment.service.CustomerTemporaryService;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.service.RoomService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
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
 * 临时客户相关操作
 */
@Controller
@RequestMapping("customerTemporary")
public class CustomerTemporaryController {

    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private CustomerTemporaryService customerTemporaryService;
    @Autowired
    private RoomService roomService;

    /**
     * 列表页
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String list(Model model) {
        User user = CacheUtils.getUser();
        CustomerTemporary customerTemporary = new CustomerTemporary();
        customerTemporary.setCompanyCode(user.getCompanyCode());
        List<CustomerTemporary> customerTemporaryList = customerTemporaryService.queryCustomerBaseList(customerTemporary);
        int count = 0;
        int firstTime = 0;
        int potential = 0;
        int intention = 0;
        int deal = 0;
        int loss = 0;
        for (CustomerTemporary ct : customerTemporaryList) {
            count ++;
            String state = ct.getState();
            if(Constants.CUSTOMER_STATE_FISRT.equals(state)){
                firstTime++;
            }
            if(Constants.CUSTOMER_STATE_POTENTIAL.equals(state)){
                potential++;
            }
            if(Constants.CUSTOMER_STATE_INTENTION.equals(state)){
                intention++;
            }
            if(Constants.CUSTOMER_STATE_DEAL.equals(state)){
                deal++;
            }
            if(Constants.CUSTOMER_STATE_LOSS.equals(state)){
                loss++;
            }
        }
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("count", count);
        model.addAttribute("firstTime", firstTime);
        model.addAttribute("potential", potential);
        model.addAttribute("intention", intention);
        model.addAttribute("deal", deal);
        model.addAttribute("loss", loss);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("customerStateMap", Constants.MAP_CUSTOMER_STATE);
        return "customerTemporary/list";
    }

    /**
     * @Description: 查询临时客户表信息列表
     */
    @RequestMapping("queryCustomerBaseList")
    @ResponseBody
    public PageInfo<CustomerTemporary> queryCustomerBaseList(HttpServletRequest request, CustomerTemporary customerTemporary) {
        User user = CacheUtils.getUser();
        customerTemporary.setCompanyCode(user.getCompanyCode());
        PagingUtil.setPageParam(request);
        List<CustomerTemporary> list = customerTemporaryService.queryCustomerBaseList(customerTemporary);
        PageInfo<CustomerTemporary> customerPageInfo = new PageInfo<CustomerTemporary>(list);
        return customerPageInfo;
    }

    /**
     * @Description: 跳转到新增页
     */
    @RequestMapping("toAddCustomer")
    public String toAddCustomer(Integer roomId,Model model) {
        Room room = new Room();
        if(roomId != null){
            room = roomService.queryRoomDetailByPrimarykey(roomId);
        }
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("roomId", roomId);
        model.addAttribute("room", room);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("customerStateMap", Constants.MAP_CUSTOMER_STATE);
        return "customerTemporary/addFormal";
    }

    /**
     * 跳转到编辑页面
     **/
    @RequestMapping("toEditCustomer/{temporaryId}")
    public String toEditCustomer(@PathVariable("temporaryId") Integer temporaryId, Model model) {
        CustomerTemporary customerTemporary = customerTemporaryService.queryCustomerDetail(temporaryId);
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("customerTemporary", customerTemporary);
        model.addAttribute("customerStateMap", Constants.MAP_CUSTOMER_STATE);
        return "customerTemporary/editFormal";
    }

    /**
     * @Description: 添加临时客户
     */
    @RequestMapping("addCustomer")
    @ResponseBody
    public Map addCustomer(CustomerTemporary customerTemporary) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            customerTemporary.setCreateUserId(user.getUserId()); // 创建者ID
            customerTemporary.setUpdateUserId(user.getUserId()); // 更新者ID
            customerTemporary.setCompanyCode(user.getCompanyCode());
            customerTemporary.setFollowUserName(user.getRealName());
            customerTemporaryService.insertCustomerTemporary(customerTemporary);
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
     * @Description: 修改临时客户
     */
    @RequestMapping("updateCustomer")
    @ResponseBody
    public Map updateCustomer(CustomerTemporary customerTemporary) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            customerTemporary.setUpdateUserId(user.getUserId()); // 更新者ID
            customerTemporaryService.updateCustomerTemporary(customerTemporary);
            map.put(Constants.MSG, "修改成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除临时客户
     */
    @RequestMapping("deleteCustomer/{temporaryId}")
    @ResponseBody
    public Map deleteCustomer(@PathVariable("temporaryId") Integer temporaryId) {
        Map map = Maps.newHashMap();
        try {
            customerTemporaryService.deleteCustomerTemporary(temporaryId);
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
     * @Description: 跳转到临时客户详情页面
     */
    @RequestMapping("toCustomerDetail/{temporaryId}")
    public String toEditorCustomer(@PathVariable("temporaryId") Integer temporaryId, Model model) {
        CustomerTemporary customerTemporary = customerTemporaryService.queryCustomerDetail(temporaryId);
        //行业类型
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //跟进方式
        List<BasicData> typeList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_FOLLOW_TYPE));
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(temporaryId, Constants.DOC_TYPE_TEMPORARY));
        model.addAttribute("docsList", docsList);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("customerTemporary", customerTemporary);
        model.addAttribute("typeList", typeList);
        model.addAttribute("customerStateMap", Constants.MAP_CUSTOMER_STATE);
        return "customerTemporary/detail";
    }
}
