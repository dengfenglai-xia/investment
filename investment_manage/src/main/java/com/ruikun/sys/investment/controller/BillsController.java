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
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 账单表相关操作
 * @author: xiachunyu
 * @date: 2019-07-03
 */
@Controller
public class BillsController {

    @Autowired
    private BillsService billsService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractRoomService contractRoomService;
    @Autowired
    private ContractCarplaceService contractCarplaceService;
    @Autowired
    private SettelService settelService;
    @Autowired
    private FeeTypeService feeTypeService;
    @Autowired
    private ProjectService projectService;


    /**
     * @Description: 审核通过后，生成账单数据
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("generateBills")
    @ResponseBody
    public Map generateBills(Bills bills) {
        Map map = Maps.newHashMap();
        try {
            billsService.generateBills(bills);
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
     * @Description:获取结算账单汇总数据
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @RequestMapping("queryBillsSumList")
    @ResponseBody
    public Map queryBillsSumList(Bills bills) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        bills.setCompanyCode(companyCode);
        List<BillsSum> list = billsService.queryBillsSumList(bills);
        map.put(Constants.RESULT_DATA, new PageInfo<BillsSum>(list));
        return map;
    }

    /**
     * @Description: 应收账单列表
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("receiptList")
    public String receiptList(Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        Bills bills = new Bills();
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        bills.setCompanyCode(companyCode);
        List<Bills> billsList = billsService.queryBillsList(bills);
        BigDecimal shouldAmt = new BigDecimal("0");
        BigDecimal finishAmt = new BigDecimal("0");
        BigDecimal residualAmt = new BigDecimal("0");
        BigDecimal overdueAmt = new BigDecimal("0");
        for (Bills b : billsList) {
            Integer days = b.getDays();
            // 费用金额+滞纳金-减免金额
            shouldAmt = shouldAmt.add(b.getCostAmt().add(b.getFeeLateAmt()).subtract(b.getFreeAmt()));
            finishAmt = finishAmt.add(b.getFinishAmt());
            residualAmt = residualAmt.add(b.getResidualAmt());
            if (days < 0) {
                overdueAmt = overdueAmt.add(b.getResidualAmt());
            }
        }
        model.addAttribute("shouldAmt", shouldAmt);
        model.addAttribute("finishAmt", finishAmt);
        model.addAttribute("residualAmt", residualAmt);
        model.addAttribute("overdueAmt", overdueAmt);
        return "finance/receiptList";
    }

    /**
     * @Description: 查询收款账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("toAddBill")
    public String toAddBill(Model model) {
        // 查询类型列表
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        String billCode = CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_SK_CODE);
        Contract contract = new Contract();
        contract.setCompanyCode(user.getCompanyCode());
        List<Contract> contractList = contractService.queryContractList(contract);
        // 费用类型
        List<FeeType> feeTypeList = feeTypeService.queryFeeTypeList(null);
        model.addAttribute("companyCode", companyCode);
        model.addAttribute("billCode", billCode);
        model.addAttribute("contractList", contractList);
        model.addAttribute("feeTypeList", feeTypeList);
        return "finance/receipt/addBill";
    }

    /**
     * @Description: 应付账单列表
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("paymentList")
    public String paymentList(Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        Bills bills = new Bills();
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        bills.setCompanyCode(companyCode);
        List<Bills> billsList = billsService.queryBillsList(bills);
        BigDecimal shouldAmt = new BigDecimal("0");
        BigDecimal finishAmt = new BigDecimal("0");
        BigDecimal residualAmt = new BigDecimal("0");
        for (Bills b : billsList) {
            // 费用金额+滞纳金-减免金额
            shouldAmt = shouldAmt.add(b.getCostAmt().add(b.getFeeLateAmt()).subtract(b.getFreeAmt()));
            finishAmt = finishAmt.add(b.getFinishAmt());
            residualAmt = residualAmt.add(b.getResidualAmt());
        }
        model.addAttribute("shouldAmt", shouldAmt);
        model.addAttribute("finishAmt", finishAmt);
        model.addAttribute("residualAmt", residualAmt);
        return "finance/paymentList";
    }

    /**
     * @Description:查询财务账单列表
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @RequestMapping("queryFinanceBillsList")
    @ResponseBody
    public Map queryFinanceBillsList(HttpServletRequest request, Bills bills) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        bills.setCompanyCode(user.getCompanyCode()); //公司编号
        String today = DateTimeUtil.getFormatDate();
        if ("0".equals(bills.getState())) {  // 等于0时，标识未确认账单
            bills.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);
        } else {
            bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        }
        PagingUtil.setPageParam(request);
        List<BillsSum> list = billsService.queryFinanceBillsList(bills);
        for (BillsSum billsSum : list) {
            if (!"3".equals(bills.getState())) {
                String shouldDealDate = billsSum.getShouldDealDate();
                float days = DateTimeUtil.getIntervalDays(today, shouldDealDate);
                billsSum.setDays((int) days);
            }
            if (billsSum.getFinishAmt().compareTo(BigDecimal.ZERO) == 0) {
                billsSum.setState(Constants.BILL_DEAL_STATE_NO);
            } else if (billsSum.getResidualAmt().compareTo(BigDecimal.ZERO) == 0) {
                billsSum.setState(Constants.BILL_DEAL_STATE_YES);
            } else {
                billsSum.setState(Constants.BILL_DEAL_STATE_PART);
            }
        }
        map.put(Constants.RESULT_DATA, new PageInfo<BillsSum>(list));
        return map;
    }

    /**
     * @Description: 查询收款账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryReceiptConfirmBillsDetail")
    public String queryReceiptConfirmBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String billCode = bills.getBillCode();
        bills.setBillCode(billCode);
        bills.setCompanyCode(companyCode);
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        // 费用类型
        List<FeeType> feeTypeList = feeTypeService.queryFeeTypeList(null);
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        model.addAttribute("feeTypeList", feeTypeList);
        return "finance/receipt/confirmBills";
    }

    /**
     * @Description: 查询付款账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryPaymentConfirmBillsDetail")
    public String queryPaymentConfirmBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String billCode = bills.getBillCode();
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        bills.setBillCode(billCode);
        bills.setCompanyCode(companyCode);
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        return "finance/payment/confirmBills";
    }

    /**
     * 确认订单
     *
     * @param billsSum
     * @return
     */
    @RequestMapping("confirmBills")
    @ResponseBody
    public Map confirmBills(BillsSum billsSum) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            billsSum.setConfirmerId(user.getUserId());
            billsSum.setConfirmer(user.getRealName());
            billsService.confirmBills(billsSum);
            map.put(Constants.MSG, "确认成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 查询结算账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryReceiptSettleBillsDetail")
    public String queryReceiptSettleBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String companyCode = user.getCompanyCode();
        String billCode = bills.getBillCode();
        bills.setBillCode(billCode);
        bills.setCompanyCode(companyCode);
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        String settleState = ""; // 结算状态
        if (billsSum.getFinishAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "未收";
        } else if (billsSum.getResidualAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "全部收款";
        } else {
            settleState = "部分收款";
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询账单结算历史
        Settel settel = new Settel();
        settel.setBillCode(billCode);
        settel.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        List<Settel> settleHistoryList = settelService.querySettelList(settel);
        BigDecimal actualAmt = new BigDecimal("0");
        for (Settel s : settleHistoryList) {
            actualAmt = actualAmt.add(s.getActualAmt());
        }
        // 查询结算账单明细(未完成结算的账单明细)
        bills.setCompanyCode(user.getCompanyCode());
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        String[] state = {Constants.BILL_DEAL_STATE_NO, Constants.BILL_DEAL_STATE_PART};
        bills.setStates(state);
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        List<Bills> settleList = billsService.queryBillsList(bills);
        BigDecimal shouldAmt = new BigDecimal("0");
        for (Bills bill : settleList) {
            shouldAmt = shouldAmt.add(bill.getResidualAmt());
        }
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        model.addAttribute("settleHistoryList", settleHistoryList);
        model.addAttribute("settleList", settleList);
        model.addAttribute("shouldAmt", shouldAmt);
        model.addAttribute("actualAmt", actualAmt);
        model.addAttribute("settleState", settleState);
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("companyCode", companyCode);
        return "finance/receipt/settleBills";
    }

    /**
     * @Description: 查询结算账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryPaymentSettleBillsDetail")
    public String queryPaymentSettleBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String companyCode = user.getCompanyCode();
        String billCode = bills.getBillCode();
        bills.setBillCode(billCode);
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        bills.setCompanyCode(companyCode);
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        String settleState = ""; // 结算状态
        if (billsSum.getFinishAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "未付";
        } else if (billsSum.getResidualAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "全部付款";
        } else {
            settleState = "部分付款";
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询账单结算历史
        Settel settel = new Settel();
        settel.setBillCode(billCode);
        settel.setBillType(Constants.BILL_TYPE_PAYMENT);
        List<Settel> settleHistoryList = settelService.querySettelList(settel);
        BigDecimal actualAmt = new BigDecimal("0");
        for (Settel s : settleHistoryList) {
            actualAmt = actualAmt.add(s.getActualAmt());
        }
        // 查询结算账单明细(未完成结算的账单明细)
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        String[] state = {Constants.BILL_DEAL_STATE_NO, Constants.BILL_DEAL_STATE_PART};
        bills.setStates(state);
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        List<Bills> settleList = billsService.queryBillsList(bills);
        BigDecimal shouldAmt = new BigDecimal("0");
        for (Bills bill : settleList) {
            shouldAmt = shouldAmt.add(bill.getResidualAmt());
        }
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        model.addAttribute("settleHistoryList", settleHistoryList);
        model.addAttribute("settleList", settleList);
        model.addAttribute("shouldAmt", shouldAmt);
        model.addAttribute("actualAmt", actualAmt);
        model.addAttribute("settleState", settleState);
        model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("companyCode", companyCode);
        return "finance/payment/settleBills";
    }

    /**
     * 账单结算
     *
     * @param settelSum
     * @return
     */
    @RequestMapping("settleBills")
    @ResponseBody
    public Map settleBills(SettelSum settelSum) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            Session session = CacheUtils.getSession();
            session.setAttribute("operator", user.getRealName());
            settelSum.setCreateUserId(user.getUserId());
            settelSum.setUpdateUserId(user.getUserId());
            billsService.settleBills(settelSum);
            map.put(Constants.MSG, "结算成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryReceiptFinishBillsDetail")
    public String queryReceiptFinishBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String billCode = bills.getBillCode();
        bills.setBillCode(billCode);
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        bills.setCompanyCode(user.getCompanyCode());
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        String settleState = ""; // 结算状态
        if (billsSum.getFinishAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "未收";
        } else if (billsSum.getResidualAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "全部收款";
        } else {
            settleState = "部分收款";
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询账单结算历史
        Settel settel = new Settel();
        settel.setBillCode(billCode);
        settel.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        List<Settel> settleHistoryList = settelService.querySettelList(settel);
        BigDecimal actualAmt = new BigDecimal("0");
        for (Settel s : settleHistoryList) {
            actualAmt = actualAmt.add(s.getActualAmt());
        }
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        model.addAttribute("settleHistoryList", settleHistoryList);
        model.addAttribute("actualAmt", actualAmt);
        model.addAttribute("settleState", settleState);
        return "finance/receipt/finishBills";
    }

    /**
     * @Description: 查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryPaymentFinishBillsDetail")
    public String queryPaymentFinishBillsDetail(Bills bills, Model model) {
        User user = CacheUtils.getUser();
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String billCode = bills.getBillCode();
        bills.setBillCode(billCode);
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        bills.setCompanyCode(user.getCompanyCode());
        BillsSum billsSum = billsService.queryBillsSumDetail(bills);
        String settleState = ""; // 结算状态
        if (billsSum.getFinishAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "未付";
        } else if (billsSum.getResidualAmt().compareTo(BigDecimal.ZERO) == 0) {
            settleState = "全部付款";
        } else {
            settleState = "部分付款";
        }
        // 查询合同房间
        List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode, version));
        int totalArea = 0;
        for (ContractRoom r : roomList) {
            totalArea += r.getBuildArea();
        }
        // 查询账单明细
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        List<Bills> billsList = billsService.queryBillsList(bills);
        // 查询账单结算历史
        Settel settel = new Settel();
        settel.setBillCode(billCode);
        settel.setBillType(Constants.BILL_TYPE_PAYMENT);
        List<Settel> settleHistoryList = settelService.querySettelList(settel);
        BigDecimal actualAmt = new BigDecimal("0");
        for (Settel s : settleHistoryList) {
            actualAmt = actualAmt.add(s.getActualAmt());
        }
        // 查询合同车位
        List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode, version));
        model.addAttribute("roomList", roomList);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("carplaceList", carplaceList);
        model.addAttribute("obj", billsSum);
        model.addAttribute("billsList", billsList);
        model.addAttribute("settleHistoryList", settleHistoryList);
        model.addAttribute("actualAmt", actualAmt);
        model.addAttribute("settleState", settleState);
        return "finance/payment/finishBills";
    }

    /**
     * @Description:查询可结转账单
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @RequestMapping("queryTransferBillsList")
    @ResponseBody
    public Map queryTransferBillsList(HttpServletRequest request, Bills bills) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        bills.setCompanyCode(user.getCompanyCode()); //公司编号
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK); // 已确认
        bills.setBillType(Constants.BILL_TYPE_PAYMENT); // 付款单
        String[] states = {Constants.BILL_DEAL_STATE_NO, Constants.BILL_DEAL_STATE_PART};
        bills.setStates(states);
        PagingUtil.setPageParam(request);
        List<Bills> list = billsService.queryTransferBillsList(bills);
        map.put(Constants.RESULT_DATA, new PageInfo<Bills>(list));
        return map;
    }

    /**
     * 账单结转
     * @param transfer
     * @return
     */
    @RequestMapping("transferBills")
    @ResponseBody
    public Map transferBills(Transfer transfer) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            transfer.setOperatorId(user.getUserId());
            transfer.setOperator(user.getRealName());
            billsService.transferBills(transfer);
            map.put(Constants.MSG, "结转成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 查询账单表信息列表
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryBillsList")
    @ResponseBody
    public Map queryBillsList(HttpServletRequest request, Bills bills) {
        Map map = Maps.newHashMap();
        PagingUtil.setPageParam(request);
        List<Bills> list = billsService.queryBillsList(bills);
        map.put(Constants.RESULT_DATA, new PageInfo<Bills>(list));
        return map;
    }

    /**
     * @Description: 通过主键查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryBillsDetailByPrimarykey/{billId}")
    public String queryBillsDetailByPrimarykey(@PathVariable("billId") Integer billId, Model model) {
        Bills bills = billsService.queryBillsDetailByPrimarykey(billId);
        model.addAttribute("bills", bills);
        return "billsDetail";
    }

    /**
     * @Description: 查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryBillsDetail")
    public String queryBillsDetail(Bills bills, Model model) {
        bills = billsService.queryBillsDetail(bills);
        model.addAttribute("bills", bills);
        return "billsDetail";
    }

    /**
     * @Description: 查询账单表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("queryBillsDetailByPrimarykey")
    @ResponseBody
    public Bills queryBillsDetailByPrimarykey(Integer billId) {
        Bills bills = billsService.queryBillsDetailByPrimarykey(billId);
        return bills;
    }

    /**
     * @Description: 新增账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("insertBills")
    @ResponseBody
    public Map insertBills(Bills bills) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            bills.setCreateUserId(userId); // 创建者ID
            bills.setUpdateUserId(userId); // 更新者ID
            billsService.insertBills(bills);
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
     * @Description: 修改账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("updateBills")
    @ResponseBody
    public Map updateBills(Bills bills) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            bills.setUpdateUserId(userId); //更新者ID
            billsService.updateBills(bills);
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
     * @Description: 批量确认账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("updateConfirmBillsBatch")
    @ResponseBody
    public Map updateConfirmBillsBatch(@RequestParam("billCodeArr[]") String[] billCodeArr) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            Bills bills = new Bills();
            bills.setBillCodeArr(billCodeArr);
            bills.setUpdateUserId(user.getUserId());
            bills.setConfirmer(user.getRealName());
            billsService.updateConfirmBillsBatch(bills);
            map.put(Constants.MSG, "确认成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 批量结算账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("updateSettleBillsBatch")
    @ResponseBody
    public Map updateSettleBillsBatch(@RequestParam("billCodeArr[]") String[] billCodeArr) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            Bills bills = new Bills();
            bills.setBillCodeArr(billCodeArr);
            bills.setUpdateUserId(user.getUserId());
            bills.setConfirmer(user.getRealName());
            billsService.updateSettleBillsBatch(bills);
            map.put(Constants.MSG, "结算成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 批量生成账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("generateBillsNoticeBatch")
    @ResponseBody
    public Map generateBillsNoticeBatch(@RequestParam("billCodeArr[]") String[] billCodeArr) {
        Map map = Maps.newHashMap();
        try {
            // 生成账单,并且打压缩
            String zipPath = billsService.generateBillsNoticeBatch(billCodeArr);
            map.put(Constants.MSG, "生成成功");
            map.put(Constants.SUCCESS, true);
            map.put("noticePath", zipPath);
            map.put("noticeName", "notice.zip");
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @RequestMapping("deleteBills")
    @ResponseBody
    public Map deleteBills(Bills bills) {
        Map map = Maps.newHashMap();
        try {
            billsService.deleteBills(bills);
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
     * @Description:获取押金报表
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @RequestMapping("toDepositBillsInfo")
    public String toDepositBillsInfo(Model model){
        String year = DateTimeUtil.getFormatNowTime("yyyy");
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List projectList = projectService.queryProjectBaseList(project);
        model.addAttribute("projectList", projectList);
        model.addAttribute("startDate",year+"-01");
        model.addAttribute("endDate",year+"-12");
        return "report/depositBillsReport";
    }

    /**
     * @Description:获取押金报表
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @RequestMapping("queryDepositBillsInfo")
    @ResponseBody
    public Map queryDepositBillsInfo(Deposit deposit) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        deposit.setCompanyCode(companyCode);
        List<Deposit> list = billsService.queryDepositBillsInfo(deposit);
        map.put(Constants.RESULT_DATA, new PageInfo<Deposit>(list));
        return map;
    }

}
