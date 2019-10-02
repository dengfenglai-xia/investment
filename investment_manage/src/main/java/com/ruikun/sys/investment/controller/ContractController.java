package com.ruikun.sys.investment.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.service.impl.TransferOutServiceImpl;
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
import java.util.*;

/**
 * @Description: 合同表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ContractRoomService contractRoomService;
    @Autowired
    private ContractCarplaceService contractCarplaceService;
    @Autowired
    private ContractIncreaseRateService contractIncreaseRateService;
    @Autowired
    private ContractFreeTimeService contractFreeTimeService;
    @Autowired
    private ContractRentService contractRentService;
    @Autowired
    private ContractReceiptPlanService contractReceiptPlanService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private AuditOperService auditOperService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CarplaceService carplaceService;
    @Autowired
    private TransferOutService transferOutService;
    @Autowired
    private TransferEnterService transferEnterService;
    @Autowired
    private ProjectService projectService;

    /**
     * @Description: 跳转到合同列表页
     * @author: xiachunyu
     * @date: 2019-06-03
     */
    @RequestMapping("toContractList")
    public String toContractList(String expireState, Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        Building building = new Building();
        building.setCompanyCode(companyCode);
        List<Building> buildingList = buildingService.queryBuildingBaseList(building);
        int rentRoomNum = 0;
        double rentRoomArea = 0d;
        int expireContractNum = 0;
        int expireRoomNum = 0;
        double expireRoomArea = 0d;
        Contract contract = new Contract();
        contract.setCompanyCode(companyCode);
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        contract.setContractType(Constants.CONTRACT_TYPE_ROOM);
        int contractNum = contractService.queryContractCount(contract); // 签约合同数
        Room room = new Room();
        room.setCompanyCode(companyCode);
        List<Room> roomList = roomService.queryRoomList(room);
        for (Room r : roomList) {
            String state = r.getState();
            if (state.equals(Constants.RENTOUT_STATE_RENT_ING)
                    || state.equals(Constants.RENTOUT_STATE_SOON_EXPIRE)) {
                rentRoomNum++;
                rentRoomArea += r.getBuildArea();
                if (state.equals(Constants.RENTOUT_STATE_SOON_EXPIRE)) {
                    expireRoomNum++;
                    expireRoomArea += r.getBuildArea();
                }
            }
        }
        // 计算到期合同数量
        contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_SOON);
        expireContractNum = contractService.queryContractCount(contract);
        model.addAttribute("expireState", expireState);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("rentRoomNum", rentRoomNum);
        model.addAttribute("rentRoomArea", rentRoomArea);
        model.addAttribute("expireContractNum", expireContractNum);
        model.addAttribute("expireRoomNum", expireRoomNum);
        model.addAttribute("expireRoomArea", expireRoomArea);
        return "contract/list";
    }

    /**
     * @Description: 查询合同表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractList")
    @ResponseBody
    public Map queryContractList(HttpServletRequest request, Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        contract.setCompanyCode(user.getCompanyCode());
        String startDateRange = request.getParameter("startDateRange"); //开始时间范围
        String endDateRange = request.getParameter("endDateRange"); //结束时间范围
        String searchType = contract.getSearchType();
        if (StringUtils.isNotEmpty(startDateRange)) {
            contract.setStartDateRange1(startDateRange.split("~")[0].trim());
            contract.setStartDateRange2(startDateRange.split("~")[1].trim());
        }
        if (StringUtils.isNotEmpty(endDateRange)) {
            contract.setEndDateRange1(endDateRange.split("~")[0].trim());
            contract.setEndDateRange2(endDateRange.split("~")[1].trim());
        }
        if ("1".equals(searchType)) { // 草稿
            contract.setAuditState(Constants.AUDIT_STATE_INIT);
        } else if ("2".equals(searchType)) { // 待审核
            contract.setAuditState(Constants.AUDIT_STATE_ING);
        } else if ("3".equals(searchType)) { // 正式
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        } else if ("4".equals(searchType)) { // 待修改
            contract.setAuditState(Constants.AUDIT_STATE_NOPASS);
        } else { // 归档
            contract.setStateOne(Constants.CONTRACT_STATE_FILE);
        }
        // 房源合同
        contract.setContractType(Constants.CONTRACT_TYPE_ROOM);
        PagingUtil.setPageParam(request);
        List<Contract> list = contractService.queryContractList(contract);
        // 获取合同状态
        if ("3".equals(searchType)) { // 正式
            for (Contract c : list) {
                String stateOne = c.getStateOne();
                String stateTwo = c.getStateTwo();
                c.setState(getState1(stateOne, stateTwo));
            }
        } else if ("5".equals(searchType)) { // 归档
            for (Contract c : list) {
                c.setState("归档");
            }
        } else {
            for (Contract c : list) {
                String operType = c.getOperType();
                String auditState = c.getAuditState();
                c.setState(getState2(operType, auditState));
            }
        }
        map.put(Constants.RESULT_DATA, new PageInfo<Contract>(list));
        return map;
    }

    /**
     * @param contractCode 合同编号
     * @param contractCode 版本号
     * @Description: 查询合同表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractDetail/{contractCode}/{version}/{searchType}")
    public String queryContractDetail(@PathVariable("contractCode") String contractCode,
                                      @PathVariable("version") Integer version,
                                      @PathVariable("searchType") String searchType, Model model) {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        String stateTwo = contract.getStateTwo();
        String operType = contract.getOperType();
        String auditState = contract.getAuditState();
        String state = "";
        // 获取合同状态
        if ("3".equals(searchType)) { // 正式
            state = getState1(stateOne, stateTwo);
        } else if ("5".equals(searchType)) { // 归档
            state = "归档";
        } else {
            state = getState2(operType, auditState);
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        // 查询周期费用
        List<ContractRent> rentList = contractRentService.queryContractRentList(new ContractRent(contractCode, version));
        // 查询递增率
        List<ContractIncreaseRate> increaseRateList = contractIncreaseRateService.queryContractIncreaseRateList(new ContractIncreaseRate(contractCode, version));
        // 查询周免租期
        List<ContractFreeTime> freeTimeList = contractFreeTimeService.queryContractFreeTimeList(new ContractFreeTime(contractCode, version));
        // 查询收计划
        List<ContractReceiptPlanSum> planSumList = contractReceiptPlanService.queryContractReceiptPlanSumList(new ContractReceiptPlan(contractCode, version));
        model.addAttribute("contract", contract);
        model.addAttribute("state", state);
        model.addAttribute("searchType", searchType);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("roomList", roomList);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("rentList", rentList);
        model.addAttribute("increaseRateList", increaseRateList);
        model.addAttribute("freeTimeList", freeTimeList);
        model.addAttribute("planSumList", planSumList);
        return "contract/detail";
    }

    /**
     * @Description: 跳转到合同账单
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryBills/{contractCode}/{version}/{searchType}")
    public String queryBills(@PathVariable("contractCode") String contractCode,
                             @PathVariable("version") Integer version,
                             @PathVariable("searchType") String searchType, Model model) {
        model.addAttribute("contractCode", contractCode);
        model.addAttribute("version", version);
        model.addAttribute("searchType", searchType);
        return "contract/bills";
    }

    /**
     * @Description: 跳转到合同账单结转详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryForwardDetail/{contractCode}/{version}/{searchType}")
    public String queryForwardDetail(@PathVariable("contractCode") String contractCode,
                                     @PathVariable("version") Integer version,
                                     @PathVariable("searchType") String searchType, Model model) {
        TransferRecord transferrecord = new TransferRecord();
        transferrecord.setContractCode(contractCode);
        transferrecord.setVersion(version);
        List<TransferRecord> transferRecords = transferEnterService.queryTransferRecordList(transferrecord);
        model.addAttribute("transferRecords", transferRecords);
        model.addAttribute("contractCode", contractCode);
        model.addAttribute("version", version);
        model.addAttribute("searchType", searchType);
        return "contract/forward";
    }

    /**
     * @Description: 查询合同历史列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractHistoryList/{contractCode}/{version}/{searchType}")
    public String queryContractHistoryList(@PathVariable("contractCode") String contractCode,
                                           @PathVariable("version") Integer version,
                                           @PathVariable("searchType") String searchType, Model model) {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String sameCode = contract.getSameCode();
        contract = new Contract();
        contract.setSameCode(sameCode);
        contract.setVersion(version);
        List<Contract> contractList = contractService.queryHistoryContractList(contract);
        model.addAttribute("contractList", contractList);
        model.addAttribute("contractCode", contractCode);
        model.addAttribute("version", version);
        model.addAttribute("searchType", searchType);
        return "contract/history";
    }

    /**
     * @Description: 跳转到合同相关文件
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractFiles/{contractCode}/{version}/{searchType}")
    public String queryContractFiles(@PathVariable("contractCode") String contractCode,
                                     @PathVariable("version") Integer version,
                                     @PathVariable("searchType") String searchType, Model model) {
        // 查询合同详情
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(contract.getId(), Constants.DOC_TYPE_CONTRACT));
        model.addAttribute("docsList", docsList);
        model.addAttribute("contractCode", contractCode);
        model.addAttribute("version", version);
        model.addAttribute("id", contract.getId());
        model.addAttribute("searchType", searchType);
        return "contract/files";
    }

    /**
     * @Description: 查询审核记录
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    @RequestMapping("queryAuditRecordList/{contractCode}/{version}/{searchType}")
    public String queryAuditRecordList(@PathVariable("contractCode") String contractCode,
                                       @PathVariable("version") Integer version,
                                       @PathVariable("searchType") String searchType, Model model) {
        AuditOper auditOper = new AuditOper();
        auditOper.setCode(contractCode);
        auditOper.setVersion(version);
        List<AuditOper> recordList = auditOperService.queryAuditRecordList(auditOper);
        model.addAttribute("recordList", recordList);
        model.addAttribute("contractCode", contractCode);
        model.addAttribute("version", version);
        model.addAttribute("searchType", searchType);
        return "contract/audit";
    }

    /**
     * @Description: 跳转到新增合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddContract")
    public String toAddContract(Integer roomId, Integer customerId, String customerType, Model model) {
        //所属行业
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //租期
        List<BasicData> termList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_LEASETERM));
        //客户信息
        User user = CacheUtils.getUser();
        Customer customer = new Customer();
        customer.setCustomerType(Constants.CUSTOMER_TYPE_COMPANY);
        customer.setCompanyCode(user.getCompanyCode());
        List<Customer> customerCompanyList = customerService.queryCustomerBaseList(customer);

        customer.setCustomerType(Constants.CUSTOMER_TYPE_PERSONAL);
        List<Customer> customerPersonalList = customerService.queryCustomerBaseList(customer);
        List<Room> roomList = Lists.newArrayList();
        if (roomId != null) {
            Room room = roomService.queryRoomDetailByPrimarykey(roomId);
            roomList.add(room);
        }
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("termList", termList);
        model.addAttribute("customerCompanyList", customerCompanyList);
        model.addAttribute("customerPersonalList", customerPersonalList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("customerId", customerId);
        model.addAttribute("customerType", customerType);
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("contractCode", CommonUtil.getBusinessCode(Constants.ROOM_CONTRACT_CODE));
        model.addAttribute("version", 1); // 初始版本
        model.addAttribute("sameCode", CommonUtil.getUniqueCode());
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("DEPOSIT_CHARGE_UNIT_MAP", Constants.DEPOSIT_CHARGE_UNIT_MAP);
        return "contract/add";
    }

    /**
     * @Description: 导出
     * @author: yangyang
     * @date: 2019-03-13 15:07:28
     */
    @RequestMapping("contract/download")
    public void downContractTemplate(HttpServletResponse response) {
        Map params = Maps.newHashMap();
        User user = CacheUtils.getUser();
        Room room = new Room();
        room.setCompanyCode(user.getCompanyCode());
        List<Room> roomList = roomService.queryRoomList(room);
        Carplace carplace = new Carplace();
        carplace.setCompanyCode(user.getCompanyCode());
        List<Carplace> carplaceList = carplaceService.queryCarplaceList(carplace);
        params.put("roomList", roomList);
        params.put("carplaceList", carplaceList);
        // 模板根路径
        String path = ReadConfig.getProperty("TEMPLATE_ROOT_PATH");
        path = path + "/contract_template.xlsx";
        byte[] excelContent = ExportExcelUtil.genSingleExcelFileData(path, params);
        try {
            ExportExcelUtil.outputFileData(response, excelContent, "合同导入模板.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 生成合同账单
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("generateReceipt")
    @ResponseBody
    public Map generateReceipt(Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        Integer userId = user.getUserId();
        String companyCode = user.getCompanyCode();
        try {
            contract.setCreateUserId(userId);
            contract.setUpdateUserId(userId);
            contract.setCompanyCode(companyCode);
            contract.setFollowPerson(user.getRealName());
            contractService.generateReceipt(contract);
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
     * @Description: 新增合同信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertContract")
    @ResponseBody
    public Map insertContract(Contract contract) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            contract.setCreateUserId(userId); // 创建者ID
            contract.setUpdateUserId(userId); // 更新者ID
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
     * @Description: 跳转到修改合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditorContract/{contractCode}/{version}")
    public String toEditorContract(@PathVariable("contractCode") String contractCode,
                                   @PathVariable("version") Integer version, Model model) {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        //所属行业
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //租期
        List<BasicData> termList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_LEASETERM));
        //客户信息（限本公司客户）
        User user = CacheUtils.getUser();
        Customer customer = new Customer();
        customer.setCompanyCode(user.getCompanyCode());
        customer.setCustomerType(contract.getCustomerType());
        List<Customer> customerList = customerService.queryCustomerBaseList(customer);
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        // 查询合同车位
        ContractCarplace contractCarplace = new ContractCarplace();
        contractCarplace.setContractCode(contractCode);
        contractCarplace.setVersion(version);
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(contractCarplace);
        // 查询递增率
        ContractIncreaseRate cir = new ContractIncreaseRate();
        cir.setContractCode(contractCode);
        cir.setVersion(version);
        List<ContractIncreaseRate> increaseRateList = contractIncreaseRateService.queryContractIncreaseRateList(cir);
        int increaseRateCount = increaseRateList.size();
        // 查询免租期
        ContractFreeTime cft = new ContractFreeTime();
        cft.setContractCode(contractCode);
        cft.setVersion(version);
        List<ContractFreeTime> freeTimeList = contractFreeTimeService.queryContractFreeTimeList(cft);
        int freeTimeCount = freeTimeList.size();
        // 查询周期费用
        ContractRent contractRent = new ContractRent();
        contractRent.setContractCode(contractCode);
        contractRent.setVersion(version);
        List<ContractRent> rentList = contractRentService.queryContractRentList(contractRent);
        int cycleCostCount = rentList.size();
        String startDate = rentList.get(0).getStartDate();
        String endDate = rentList.get(0).getEndDate();
        model.addAttribute("contract", contract);
        model.addAttribute("newVersion", contract.getVersion() + 1); //版本递增
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("termList", termList);
        model.addAttribute("customerList", customerList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("increaseRateList", increaseRateList);
        model.addAttribute("increaseRateCount", increaseRateCount);
        model.addAttribute("freeTimeList", freeTimeList);
        model.addAttribute("freeTimeCount", freeTimeCount);
        model.addAttribute("rentList", rentList);
        model.addAttribute("cycleCostCount", cycleCostCount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("DEPOSIT_CHARGE_UNIT_MAP", Constants.DEPOSIT_CHARGE_UNIT_MAP);
        return "contract/editor";
    }

    /**
     * @Description: 跳转到变更合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toChangeContract/{contractCode}/{version}")
    public String toChangeContract(@PathVariable("contractCode") String contractCode,
                                   @PathVariable("version") Integer version, Model model) throws Exception {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        if (!Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) {
            throw new Exception("非法合同变更操作");
        }
        //所属行业
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //租期
        List<BasicData> termList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_LEASETERM));
        //客户信息（限本公司客户）
        User user = CacheUtils.getUser();
        Customer customer = new Customer();
        customer.setCompanyCode(user.getCompanyCode());
        customer.setCustomerType(contract.getCustomerType());
        List<Customer> customerList = customerService.queryCustomerBaseList(customer);
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        // 查询递增率
        ContractIncreaseRate cir = new ContractIncreaseRate();
        cir.setContractCode(contractCode);
        cir.setVersion(version);
        List<ContractIncreaseRate> increaseRateList = contractIncreaseRateService.queryContractIncreaseRateList(cir);
        int increaseRateCount = increaseRateList.size();
        // 查询免租期
        ContractFreeTime cft = new ContractFreeTime();
        cft.setContractCode(contractCode);
        cft.setVersion(version);
        List<ContractFreeTime> freeTimeList = contractFreeTimeService.queryContractFreeTimeList(cft);
        int freeTimeCount = freeTimeList.size();
        // 查询周期费用
        ContractRent contractRent = new ContractRent();
        contractRent.setContractCode(contractCode);
        contractRent.setVersion(version);
        List<ContractRent> rentList = contractRentService.queryContractRentList(contractRent);
        int cycleCostCount = rentList.size();
        String startDate = rentList.get(0).getStartDate();
        String endDate = rentList.get(0).getEndDate();

        model.addAttribute("contract", contract);
        model.addAttribute("newContractCode", CommonUtil.getBusinessCode(Constants.ROOM_CONTRACT_CODE));
        model.addAttribute("newVersion", contract.getVersion() + 1); //版本递增
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("termList", termList);
        model.addAttribute("customerList", customerList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("increaseRateList", increaseRateList);
        model.addAttribute("increaseRateCount", increaseRateCount);
        model.addAttribute("freeTimeList", freeTimeList);
        model.addAttribute("freeTimeCount", freeTimeCount);
        model.addAttribute("rentList", rentList);
        model.addAttribute("cycleCostCount", cycleCostCount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("DEPOSIT_CHARGE_UNIT_MAP", Constants.DEPOSIT_CHARGE_UNIT_MAP);
        return "contract/change";
    }

    /**
     * @Description: 跳转到退租合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toReturnContract/{contractCode}/{version}")
    public String toReturnContract(@PathVariable("contractCode") String contractCode,
                                   @PathVariable("version") Integer version, Model model) throws Exception {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        // 只有正式合同才能发起退租
        if (!Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) {
            throw new Exception("非法退租操作");
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        // 查询周期费用
        ContractRent contractRent = new ContractRent();
        contractRent.setContractCode(contractCode);
        contractRent.setVersion(version);
        List<ContractRent> rentList = contractRentService.queryContractRentList(contractRent);
        model.addAttribute("contract", contract);
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("roomList", roomList);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("rentList", rentList);
        return "contract/return";
    }

    /**
     * @Description: 跳转到续租合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toRenewContract/{contractCode}/{version}")
    public String toRenewContract(@PathVariable("contractCode") String contractCode,
                                  @PathVariable("version") Integer version, Model model) throws Exception {
        Contract contract = contractService.queryContractDetail(new Contract(contractCode, version));
        String stateOne = contract.getStateOne();
        if (!Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) {
            throw new Exception("非法续租操作");
        }
        //所属行业
        List<BasicData> industryList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_INDUSTRY));
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        //租期
        List<BasicData> termList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_LEASETERM));
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        // 查询周期费用
        ContractRent contractRent = new ContractRent();
        contractRent.setContractCode(contractCode);
        contractRent.setVersion(version);
        List<ContractRent> rentList = contractRentService.queryContractRentList(contractRent);
        int cycleCostCount = rentList.size();
        //租期
        double leaseTerm = Double.parseDouble(contract.getLeaseTerm());
        String endDate = rentList.get(0).getEndDate();
        // 续租的开始时间等于原合同的结束时间加一天
        String startDate = DateTimeUtil.getAboutDay(endDate, 1, "yyyy-MM-dd");
        // 结束时间等于开始时间加上租期
        endDate = DateTimeUtil.getAboutMonth(endDate, (int) (leaseTerm * 12), "yyyy-MM-dd");
        model.addAttribute("contract", contract);
        model.addAttribute("newContractCode", CommonUtil.getBusinessCode(Constants.ROOM_CONTRACT_CODE));
        model.addAttribute("newVersion", contract.getVersion() + 1); //版本递增
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("industryList", industryList);
        model.addAttribute("sourceList", sourceList);
        model.addAttribute("termList", termList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("rentList", rentList);
        model.addAttribute("cycleCostCount", cycleCostCount);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("DEPOSIT_CHARGE_UNIT_MAP", Constants.DEPOSIT_CHARGE_UNIT_MAP);
        return "contract/renew";
    }

    /**
     * @Description: 修改合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateContract")
    @ResponseBody
    public Map updateContract(Contract contract) {
        Map map = Maps.newHashMap();
        Integer userId = CacheUtils.getUser().getUserId();
        try {
            contract.setUpdateUserId(userId);
            contract.setUpdateTime(new Date());
            contractService.updateContract(contract);
            map.put(Constants.MSG, "操作成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    @RequestMapping("updateContractBaseInfo")
    @ResponseBody
    public Map updateContractBaseInfo(Contract contract) {
        Map map = Maps.newHashMap();
        Integer userId = CacheUtils.getUser().getUserId();
        try {
            contract.setUpdateUserId(userId);
            contract.setUpdateTime(new Date());
            contractService.updateContractBaseInfo(contract);
            map.put(Constants.MSG, "操作成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }


    /**
     * @Description: 删除合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteContract")
    @ResponseBody
    public Map deleteContract(String contractCode, Integer version) {
        Map map = Maps.newHashMap();
        try {
            Contract contract = new Contract();
            contract.setContractCode(contractCode);
            contract.setVersion(version);
            contractService.deleteContract(contract);
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
     * @Description: 跳转到新增合同页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddWorkplaceContract")
    public String toAddWorkplaceContract(Model model) {
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
        model.addAttribute("contractCode", CommonUtil.getBusinessCode(Constants.WORK_PLACE_CONTRACT_CODE));
        model.addAttribute("version", 1); // 初始版本
        model.addAttribute("sameCode", CommonUtil.getUniqueCode());
        model.addAttribute("CHARGE_UNIT_MAP", Constants.WORKPLACE_CHARGE_UNIT_MAP);
        model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
        model.addAttribute("DEPOSIT_CHARGE_UNIT_MAP", Constants.DEPOSIT_CHARGE_UNIT_MAP);
        return "contract/addWorkplace";
    }

    /**
     * @Description: 跳转到合同列表页
     * @author: xiachunyu
     * @date: 2019-06-03
     */
    @RequestMapping("toContractWorkplaceList")
    public String toContractWorkplaceList(Model model) {
        List<Building> buildingList = buildingService.queryBuildingBaseList(null);
        model.addAttribute("buildingList", buildingList);
        return "contract/workplaceList";
    }

    /**
     * @Description: 查询工位合同表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryWorkplaceContractList")
    @ResponseBody
    public Map queryWorkplaceContractList(HttpServletRequest request, Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        contract.setCompanyCode(user.getCompanyCode());
        String startDateRange = request.getParameter("startDateRange"); //开始时间范围
        String endDateRange = request.getParameter("endDateRange"); //结束时间范围
        String searchType = contract.getSearchType();
        if (StringUtils.isNotEmpty(startDateRange)) {
            contract.setStartDateRange1(startDateRange.split("~")[0].trim());
            contract.setStartDateRange2(startDateRange.split("~")[1].trim());
        }
        if (StringUtils.isNotEmpty(endDateRange)) {
            contract.setEndDateRange1(endDateRange.split("~")[0].trim());
            contract.setEndDateRange2(endDateRange.split("~")[1].trim());
        }
        if ("1".equals(searchType)) { // 草稿
            contract.setAuditState(Constants.AUDIT_STATE_INIT);
        } else if ("2".equals(searchType)) { // 待审核
            contract.setAuditState(Constants.AUDIT_STATE_ING);
        } else if ("3".equals(searchType)) { // 正式
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        } else if ("4".equals(searchType)) { // 待修改
            contract.setAuditState(Constants.AUDIT_STATE_NOPASS);
        } else { // 归档
            contract.setStateOne(Constants.CONTRACT_STATE_FILE);
        }
        // 工位合同
        contract.setContractType(Constants.CONTRACT_TYPE_WORKPLACE);
        PagingUtil.setPageParam(request);
        List<Contract> list = contractService.queryContractList(contract);
        // 获取合同状态
        if ("3".equals(searchType)) { // 正式
            for (Contract c : list) {
                String stateOne = c.getStateOne();
                String stateTwo = c.getStateTwo();
                c.setState(getState1(stateOne, stateTwo));
            }
        } else if ("5".equals(searchType)) { // 归档
            for (Contract c : list) {
                c.setState("归档");
            }
        } else {
            for (Contract c : list) {
                String operType = c.getOperType();
                String auditState = c.getAuditState();
                c.setState(getState2(operType, auditState));
            }
        }
        map.put(Constants.RESULT_DATA, new PageInfo<Contract>(list));
        return map;
    }

    /**
     * @Description: 导入数据
     * @author: xiachunyu
     * @date: 2019-06-19
     */
    @RequestMapping("contract/importInfo")
    @ResponseBody
    public Map importInfo(@RequestParam("file") MultipartFile file) {
        Map map = Maps.newHashMap();
        try {
            map = contractService.importContractInfo(file);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "导入失败，请检查导入文件");
        }
        return map;
    }

    /**
     * 获取正式合同状态
     *
     * @param stateOne
     * @param stateTwo
     * @return
     */
    private String getState1(String stateOne, String stateTwo) {
        String state = "";
        if (Constants.CONTRACT_STATE_FORMAL.equals(stateOne)) { // 正式
            if (Constants.CONTRACT_STATE_TWO_1.equals(stateTwo)) {
                state = "正式【待执行】";
            } else if (Constants.CONTRACT_STATE_TWO_2.equals(stateTwo)) {
                state = "正式【执行中】";
            } else if (Constants.CONTRACT_STATE_TWO_3.equals(stateTwo)) {
                state = "正式【已变更】";
            } else if (Constants.CONTRACT_STATE_TWO_4.equals(stateTwo)) {
                state = "正式【已退租】";
            } else if (Constants.CONTRACT_STATE_TWO_5.equals(stateTwo)) {
                state = "正式【已续租】";
            } else if (Constants.CONTRACT_STATE_TWO_6.equals(stateTwo)) {
                state = "正式【已到期】";
            }
        }
        return state;
    }

    /**
     * 获取非正式合同状态
     *
     * @param operType
     * @param auditState
     * @return
     */
    private String getState2(String operType, String auditState) {
        String state = "";
        if (Constants.AUDIT_STATE_INIT.equals(auditState)) {
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) {
                state = "新建草稿";
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) {
                state = "变更草稿";
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {
                state = "退租草稿";
            } else if (Constants.CONTRACT_OPER_TYPE_RENEW.equals(operType)) {
                state = "续租草稿";
            }
        } else if (Constants.AUDIT_STATE_ING.equals(auditState)) {
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) {
                state = "新建待审核";
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) {
                state = "变更待审核";
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {
                state = "退租待审核";
            } else if (Constants.CONTRACT_OPER_TYPE_RENEW.equals(operType)) {
                state = "续租待审核";
            }
        } else if (Constants.AUDIT_STATE_NOPASS.equals(auditState)) {
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) {
                state = "新建待修改";
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) {
                state = "变更待修改";
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {
                state = "退租待修改";
            } else if (Constants.CONTRACT_OPER_TYPE_RENEW.equals(operType)) {
                state = "续租待修改";
            }
        }
        return state;
    }

    /**
     * @Description: 客单数据
     * @author: xiachunyu
     * @date: 2019-06-19
     */
    @RequestMapping("toCustomerBills")
    public String toCustomerBills(Model model) {
        String year = DateTimeUtil.getFormatNowTime("yyyy");
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List projectList = projectService.queryProjectBaseList(project);
        model.addAttribute("projectList", projectList);
        model.addAttribute("startDate", year + "-01");
        model.addAttribute("endDate", year + "-12");
        return "report/customerBillsReport";
    }

    /**
     * @Description: 客单数据
     * @author: xiachunyu
     * @date: 2019-06-19
     */
    @RequestMapping("queryContractCustomerBills")
    @ResponseBody
    public Map queryContractCustomerBills(ContractSummaryInfo contractSummaryInfo) {
        Map map = Maps.newHashMap();
        List<ContractSummaryInfo> list = null;
        try {
            User user = CacheUtils.getUser();
            contractSummaryInfo.setCompanyCode(user.getCompanyCode());
            list = contractService.queryContractCustomerBills(contractSummaryInfo);
            map.put(Constants.RESULT_DATA, new PageInfo<ContractSummaryInfo>(list));
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 合同配置项
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractConfigList")
    public String queryContractConfigList(Model model) {
        model.addAttribute("CONTRACT_FIELD_MAP", Constants.CONTRACT_FIELD_MAP);
        return "contract/config";
    }

    @RequestMapping("generateContractDetails")
    @ResponseBody
    public Map generateContractDetails(Contract contract) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        contract.setCompanyCode(user.getCompanyCode());
        contract.setCompanyName(user.getCompany());
        try {
            map = contractService.generateContractDetails(contract);
            map.put(Constants.MSG, "生成成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    @RequestMapping("uploadContractTemplate")
    @ResponseBody
    public Map uploadContractTemplate(MultipartFile file) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        try {
            UploadUtils.uploadFileRootPath(file,companyCode);
            map.put(Constants.MSG, "上传成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }


}
