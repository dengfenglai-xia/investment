package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class AgreementController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContractRoomService contractRoomService;
    @Autowired
    private RoomService roomService;

    /**
     * @Description: 跳转到合同列表页
     * @author: xiachunyu
     * @date: 2019-06-03
     */
    @RequestMapping("toAgreementList")
    public String toAgreementList(Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        Building building = new Building();
        building.setCompanyCode(companyCode);
        List<Building> buildingList = buildingService.queryBuildingBaseList(building);
        model.addAttribute("buildingList", buildingList);
        return "agreement/list";
    }

    /**
     * @Description: 查询协议信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryAgreementList")
    @ResponseBody
    public Map queryAgreementList(HttpServletRequest request, Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        contract.setCompanyCode(user.getCompanyCode());
        String startDateRange = request.getParameter("startDateRange"); //开始时间范围
        String endDateRange = request.getParameter("endDateRange"); //结束时间范围
        if (StringUtils.isNotEmpty(startDateRange)) {
            contract.setStartDateRange1(startDateRange.split("~")[0].trim());
            contract.setStartDateRange2(startDateRange.split("~")[1].trim());
        }
        if (StringUtils.isNotEmpty(endDateRange)) {
            contract.setEndDateRange1(endDateRange.split("~")[0].trim());
            contract.setEndDateRange2(endDateRange.split("~")[1].trim());
        }
        String state = contract.getState();
        if (state.equals("1")) {  // 草稿
            contract.setStateOne(Constants.CONTRACT_STATE_INIT);
        } else if (state.equals("2")) { // 正式
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        } else { // 归档
            contract.setStateOne(Constants.CONTRACT_STATE_FILE);
        }
        // 房源协议
        contract.setContractType(Constants.CONTRACT_TYPE_ROOM_AGREEMENT);
        PagingUtil.setPageParam(request);
        List<Contract> list = contractService.queryContractList(contract);
        map.put(Constants.RESULT_DATA, new PageInfo<Contract>(list));
        return map;
    }

    /**
     * @Description: 跳转到新增合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddAgreement")
    public String toAddAgreement(Model model) {
        //所属行业
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //租期
        List<BasicData> termList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_LEASETERM));
        //客户信息
        User user = CacheUtils.getUser();
        Customer customer = new Customer();
        customer.setCompanyCode(user.getCompanyCode());
        List<Customer> customerList = customerService.queryCustomerBaseList(customer);
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("termList", termList);
        model.addAttribute("customerList", customerList);
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("contractCode", CommonUtil.getBusinessCode(Constants.ROOM_CONTRACT_CODE));
        model.addAttribute("version", 1); // 初始版本
        model.addAttribute("sameCode", CommonUtil.getUniqueCode());
        return "agreement/add";
    }

    /**
     * @Description: 新增协议
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertAgreement")
    @ResponseBody
    public Map insertAgreement(Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        Integer userId = user.getUserId();
        String companyCode = user.getCompanyCode();
        try {
            contract.setCreateUserId(userId);
            contract.setUpdateUserId(userId);
            contract.setContractType(Constants.CONTRACT_TYPE_ROOM_AGREEMENT);
            contract.setCompanyCode(companyCode);
            contract.setFollowPerson(user.getRealName());
            contractService.insertContract(contract);
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
     * @Description: 提交
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateAgreement")
    @ResponseBody
    public Map updateAgreement(Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        Integer userId = user.getUserId();
        try {
            contract.setUpdateUserId(userId);
            contractService.updateAgreement(contract);
            map.put(Constants.MSG, "提交成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 跳转到详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryAgreementDetail")
    public String queryAgreementDetail(Contract contract, Model model) {
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        String stateTwo = contract.getStateTwo();
        if (Constants.CONTRACT_STATE_INIT.equals(stateOne)) {
            contract.setState("草稿");
        } else if (Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) {
            if (Constants.CONTRACT_STATE_TWO_1.equals(stateTwo)) {
                contract.setState("正式【待执行】");
            } else {
                contract.setState("正式【执行中】");
            }
        } else {
            contract.setState("归档");
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        model.addAttribute("contract", contract);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("roomList", roomList);
        return "agreement/detail";
    }

    /**
     * @Description: 跳转到新增合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toReturnAgreement")
    public String toReturnAgreement(Contract contract,Model model) {
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        String stateTwo = contract.getStateTwo();
        if (Constants.CONTRACT_STATE_INIT.equals(stateOne)) {
            contract.setState("草稿");
        } else if (Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) {
            if (Constants.CONTRACT_STATE_TWO_1.equals(stateTwo)) {
                contract.setState("正式【待执行】");
            } else if(Constants.CONTRACT_STATE_TWO_2.equals(stateTwo)) {
                contract.setState("正式【执行中】");
            }else{
                contract.setState("正式【已退租】");
            }
        } else {
            contract.setState("归档");
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        model.addAttribute("contract", contract);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("roomList", roomList);
        return "agreement/return";
    }
}
