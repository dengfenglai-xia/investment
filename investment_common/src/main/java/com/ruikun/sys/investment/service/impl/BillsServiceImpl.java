package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.poiWord.WordReporter;
import com.ruikun.sys.investment.service.BillsService;
import com.ruikun.sys.investment.service.ProjectService;
import com.ruikun.sys.investment.service.RoomService;
import com.ruikun.sys.investment.service.WorkplaceService;
import com.ruikun.sys.investment.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class BillsServiceImpl implements BillsService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private BillsMapper billsMapper;
    @Autowired
    private ContractReceiptPlanMapper contractReceiptPlanMapper;
    @Autowired
    private SettelMapper settelMapper;
    @Autowired
    private TransferEnterMapper transferEnterMapper;
    @Autowired
    private TransferOutMapper transferOutMapper;
    @Autowired
    private RoomService roomService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * @Description: 审核通过，生成账单数据
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @Override
    @Transactional
    public void generateBills(Bills bills) {
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        Date date = new Date();
        // 查询当前合同详情
        Contract contract = contractMapper.queryContractDetail(new Contract(contractCode, version));
        String operType = contract.getOperType();
        String auditState = contract.getAuditState();
        String changeDate = contract.getChangeDate();
        Integer customerId = contract.getCustomerId();
        String endDate = contract.getEndDate();
        Integer remindDays = contract.getRemindDays();
        Contract originalContract = new Contract();
        if (Constants.AUDIT_STATE_ING.equals(auditState)) { // 待审批的合同
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) { // 新建合同
                // 生成新合同的首期账单
                dealInitBills(contract);
                Contract contract_new = new Contract(contractCode, version);
                contract_new.setUpdateTime(date);
                if(DateTimeUtil.compare_date(endDate,today) == 1){
                    contract_new.setStateOne(Constants.CONTRACT_STATE_FILE); // 已归档
                    contract_new.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 退租归档
                    contractMapper.updateContract(contract_new);
                }else{
                    int intervalDays = (int) DateTimeUtil.getIntervalDays(today, endDate);
                    if (intervalDays < remindDays) {
                        // 合同到期状态（即将到期）
                        contract_new.setExpireState(Constants.CONTRACT_EXPIRE_STATE_SOON);
                        contractMapper.updateContract(contract_new);
                        // 更新房源状态（即将到期）
                        roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_SOON_EXPIRE);
                        // 更新工位状态（即将到期）
                        workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_SOON_EXPIRE);
                    } else {
                        // 更新房源状态（在租）
                        roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_RENT_ING);
                        // 更新工位状态（在租）
                        workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_RENT_ING);
                    }
                    // 更新客户为正式客户
                    Customer customer = new Customer();
                    customer.setCustomerId(customerId);
                    customer.setState(Constants.CUSTOMER_STATE_NOW);
                    customer.setUpdateTime(date);
                    customerMapper.updateCustomer(customer); // 更新客户为正式客户
                }
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) { // 变更合同
                // 生成新合同的首期账单
                dealInitBills(contract);
                // 处理原合同
                originalContract.setSameCode(contract.getSameCode());
                originalContract.setVersion(contract.getVersion() - 1);
                originalContract = contractMapper.queryContractDetail(originalContract);
                String originalContractCode = originalContract.getContractCode();
                Integer originalVersion = originalContract.getVersion();
                contract = new Contract(originalContractCode, originalVersion);
                contract.setRemainderDays((int) DateTimeUtil.getIntervalDays(today, changeDate));
                if (DateTimeUtil.compare_date(today, changeDate) != 1) { // 当前日期等于或者大于变更日期
                    contract.setStateOne(Constants.CONTRACT_STATE_FILE); // 归档
                    contract.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 变更归档
                } else {
                    // 更新原合同的变更时间（比新合同生效日期晚一天）
                    contract.setChangeDate(DateTimeUtil.getAboutDay(changeDate, -1, "yyyy-MM-dd"));
                    contract.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式
                    contract.setStateTwo(Constants.CONTRACT_STATE_TWO_3); // 已变更
                }
                contract.setUpdateTime(date);
                contractMapper.updateContract(contract);
                // 账单变更
                dealChangeBills(originalContractCode, originalVersion, changeDate);
                // 生成押金付款账单
                dealDepositReturnBills(contract);
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) { // 退租合同
                Contract contract_new = new Contract(contractCode, version);
                // 更新当前合同状态
                contract_new.setAuditState(Constants.AUDIT_STATE_PASS); // 审核通过
                if (DateTimeUtil.compare_date(today, changeDate) == 1) {
                    contract_new.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式
                    contract_new.setStateTwo(Constants.CONTRACT_STATE_TWO_4); // 已退租
                } else {
                    contract_new.setStateOne(Constants.CONTRACT_STATE_FILE); // 已归档
                    contract_new.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 退租归档
                    // 更新房源状态（空置）
                    roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_VACANT_ING);
                    // 更新工位状态（空置）
                    workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_VACANT_ING);
                }
                contract_new.setUpdateTime(date);
                contractMapper.updateContract(contract_new);
                // 生成押金付款账单
                dealDepositReturnBills(contract);
                // 账单变更
                dealChangeBills(contractCode, version, changeDate);
                // 更新客户为历史客户
                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customer.setState(Constants.CUSTOMER_STATE_HISTORY);
                customer.setUpdateTime(date);
                customerMapper.updateCustomer(customer); // 更新客户为历史客户
            } else if (Constants.CONTRACT_OPER_TYPE_RENEW.equals(operType)) { // 续租合同
                // 生成新合同的首期账单
                dealInitBills(contract);
                // 更新房源状态（在租）
                roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_RENT_ING);
                // 更新工位状态（在租）
                workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_RENT_ING);
                // 处理原合同
                originalContract = new Contract();
                originalContract.setSameCode(contract.getSameCode());
                originalContract.setVersion(contract.getVersion() - 1);
                originalContract = contractMapper.queryContractDetail(originalContract);
                String originalEndDate = originalContract.getEndDate();
                String originalContractCode = originalContract.getContractCode();
                Integer originalVersion = originalContract.getVersion();
                contract = new Contract(originalContractCode, originalVersion);
                if (DateTimeUtil.compare_date(today, originalEndDate) != 1) { // 当前日期等于或者大于变更日期
                    contract.setStateOne(Constants.CONTRACT_STATE_FILE); // 归档
                    contract.setStateTwo(Constants.CONTRACT_STATE_TWO_3); // 续租归档
                } else {
                    contract.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式
                    contract.setStateTwo(Constants.CONTRACT_STATE_TWO_5); // 已续租
                }
                contract.setUpdateTime(date);
                contractMapper.updateContract(contract);
            } else {
                throw new RuntimeException("非法审核操作");
            }
        } else {
            throw new RuntimeException("非法审核操作");
        }
    }

    /**
     * @Description:获取退租账单汇总数据
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @Override
    @Transactional
    public List<BillsSum> queryBillsSumList(Bills bills) {
        HttpServletRequest request = CommonUtil.getRequest();
        String operType = request.getParameter("operType"); // 操作类型
        String contractCode = bills.getContractCode();
        Integer version = bills.getVersion();
        String billType = bills.getBillType();
        String companyCode = bills.getCompanyCode();
        List<Bills> billsList = null;
        // 查询合同详情
        Contract contract = new Contract(contractCode, version);
        contract = contractMapper.queryContractDetail(contract);
        String[] states = {Constants.BILL_DEAL_STATE_NO, Constants.BILL_DEAL_STATE_PART};
        bills.setStates(states);
        bills.setBillProperty(Constants.BILL_PROPERTY_FORMAL);  // 查询正式账单
        if (Constants.BILL_TYPE_RECEIVABLES.equals(billType)) { // 查询应收账单明细数据
            billsList = billsMapper.queryBillsList(bills);
        } else {                                                // 查询应付账单明细数据
            billsList = billsMapper.queryBillsList(bills);
            List<Bills> depositBills = getDepositBills(contract);
            for (Bills b : depositBills) {
                b.setBillProperty(Constants.BILL_PROPERTY_TEMP);  // 临时账单
                b.setCompanyCode(companyCode);
                billsList.add(b);
            }
            // 如果是退租操作将临时账单保存到数据库（PS：目的是在查看详情和审核的会后能看到临时待付款账单）
            if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {
                // 将临时账单存入数据库（PS：如果审核通过将临时变为正式,否则删除临时账单）
                Bills b = new Bills(contractCode, version);
                b.setBillProperty(Constants.BILL_PROPERTY_TEMP);
                billsMapper.deleteBills(b); //清空当前合同的临时账单
                billsMapper.insertBillsBatch(depositBills); // 将临时账单插入数据库
            }
        }
        // 计算汇总数据
        List<BillsSum> billsSumList = Lists.newArrayList();
        Set<String> billCodeSet = Sets.newHashSet();
        for (Bills bill : billsList) {
            billCodeSet.add(bill.getBillCode());
        }
        BigDecimal costAmt = new BigDecimal("0");
        BigDecimal finishAmt = new BigDecimal("0");
        BigDecimal residualAmt = new BigDecimal("0");
        BigDecimal feeLateAmt = new BigDecimal("0");
        BigDecimal freeAmt = new BigDecimal("0");
        String shouldDealDate = null;
        String today = DateTimeUtil.getFormatDate();
        Iterator<String> value = billCodeSet.iterator();
        boolean bool = true; // 标识各项费用是否已收全，默认未收全
        while (value.hasNext()) {
            costAmt = new BigDecimal("0");
            finishAmt = new BigDecimal("0");
            residualAmt = new BigDecimal("0");
            feeLateAmt = new BigDecimal("0");
            freeAmt = new BigDecimal("0");
            String billCode = value.next();
            BillsSum billsSum = new BillsSum();
            for (int i = billsList.size() - 1; i >= 0; i--) {
                Bills b = billsList.get(i);
                if (billCode.equals(b.getBillCode())) {
                    costAmt = costAmt.add(b.getCostAmt());
                    finishAmt = finishAmt.add(b.getFinishAmt());
                    residualAmt = residualAmt.add(b.getResidualAmt());
                    feeLateAmt = feeLateAmt.add(b.getFeeLateAmt());
                    freeAmt = freeAmt.add(b.getFreeAmt());
                    shouldDealDate = b.getShouldDealDate();
                    // 本次账单如果存在未收或部分收的费用，证明账单未完成
                    if (bool == true && !b.getState().equals(Constants.BILL_DEAL_STATE_YES)) {
                        bool = false;
                    }
                    billsList.remove(i);
                }
            }
            float days = 0f;
            if (bool == false) { // 账单未完成，计算剩余/逾期天数
                days = DateTimeUtil.getIntervalDays(shouldDealDate, today);
            }
            billsSum.setBillCode(billCode);
            billsSum.setCostAmt(costAmt);
            billsSum.setFinishAmt(finishAmt);
            billsSum.setResidualAmt(residualAmt);
            billsSum.setFeeLateAmt(feeLateAmt);
            billsSum.setFreeAmt(freeAmt);
            billsSum.setShouldDealDate(shouldDealDate);
            billsSum.setDays((int) days);
            /*
             * 收款：
             * 1.如果已收金额 等于 0，证明未收
             * 2.如果已收金额 等于 （费用金额+滞纳金-减免），证明已全部收
             *
             * 付款：
             * 1.如果已付金额 等于 0，证明未付
             * 2.如果已付金额 等于 （费用金额+滞纳金（默认0）-减免），证明已全部付款
             *
             * */
            if (finishAmt.compareTo(BigDecimal.ZERO) == 0) {
                billsSum.setState(Constants.BILL_DEAL_STATE_NO);
            } else if (finishAmt.equals(costAmt.add(feeLateAmt).subtract(freeAmt))) {
                billsSum.setState(Constants.BILL_DEAL_STATE_YES);
            } else {
                billsSum.setState(Constants.BILL_DEAL_STATE_PART);
            }
            billsSumList.add(billsSum);
        }
        return billsSumList;
    }

    /**
     * 处理首期账单并且更新合同状态
     *
     * @param contract
     */
    private void dealInitBills(Contract contract) {
        Date date = new Date(); // 当前时间
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        BigDecimal initAmt = new BigDecimal("0");
        List billList = Lists.newArrayList();
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        String changeDate = contract.getChangeDate(); // 合同变更生效日期
        String startDate = contract.getStartDate(); // 合同起始日期
        String expireState = contract.getExpireState();
        // 如果变更日期不等于空，起始日期以变更日期为起点
        if (StringUtils.isNotEmpty(changeDate)) {
            startDate = changeDate;
        }
        List<ContractReceiptPlan> planList = Lists.newArrayList();
        ContractReceiptPlan contractReceiptPlan = new ContractReceiptPlan(contractCode, version);
        contractReceiptPlan.setWhetherOut(Constants.BILL_WHETHER_OUT_NO);
        // 如果合同生效日期小于今天，证明是历史合同补录，否则为新建合同
        if (DateTimeUtil.compare_date(startDate, today) == 1) {
            Integer projectId = contract.getProjectId();
            Project project = projectService.queryProjectDetailByPrimarykey(projectId);
            Integer billDays = project.getBillDays();
            contractReceiptPlan.setStartDate(DateTimeUtil.getAboutDay(today, billDays, "yyyy-MM-dd"));
            planList = contractReceiptPlanMapper.queryContractReceiptPlanList(contractReceiptPlan);
        } else {
            planList = contractReceiptPlanMapper.queryExpectantReceiptPlanList(contractReceiptPlan);
        }
        Set<String> billCodeSet = new HashSet<String>();
        for (ContractReceiptPlan plan : planList) {
            Bills bill = new Bills();
            String receivableDate = plan.getReceivableDate(); // 应收款时间
            int days = (int) DateTimeUtil.getIntervalDays(today, receivableDate);// 剩余/逾期天数
            bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 账单类型：收款账单
            bill.setProjectId(contract.getProjectId());
            bill.setCompanyCode(contract.getCompanyCode());
            bill.setContractCode(contractCode);
            bill.setVersion(version);
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(plan.getBillCode());
            String costName = plan.getCostName();
            if (costName.equals(Constants.COST_NAME_DEPOSIT)
                    || costName.equals(Constants.COST_NAME_WY_DEPOSIT)
                    || costName.equals(Constants.COST_NAME_BREACHAMT)) {
                bill.setFeeType(Constants.COST_NAME_PLEDGE);
            } else {
                bill.setFeeType(costName);
            }
            bill.setCostName(costName);
            bill.setStartDate(plan.getStartDate());
            bill.setEndDate(plan.getEndDate());
            bill.setCostAmt(plan.getCostAmt());
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(plan.getCostAmt());
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(plan.getFeeLateRatio());
            bill.setShouldDealDate(receivableDate); // 应收时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未收款
            bill.setBillProperty(Constants.BILL_PROPERTY_FORMAL);  // 正式账单
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            billList.add(bill);
            billCodeSet.add(plan.getBillCode());
        }
        // 更新当前合同计划账单出单状态
        Iterator iterator = billCodeSet.iterator();
        while (iterator.hasNext()) {
            String billCode = (String) iterator.next();
            ContractReceiptPlan crp = new ContractReceiptPlan();
            crp.setBillCode(billCode); // 账单编号
            crp.setUpdateTime(date);
            crp.setWhetherOut(Constants.BILL_WHETHER_OUT_YES);
            contractReceiptPlanMapper.updateContractReceiptPlan(crp);
        }
        // 插入当前合同收款账单信息
        if (billList.size() > 0) {
            billsMapper.insertBillsBatch(billList);
        }
        // 更新当前合同状态：如果合同开始日期小于审批时间，合同为正式-待执行合同，否则为正式-执行中合同
        contract = new Contract(contractCode, version);
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式
        if (DateTimeUtil.compare_date(today, startDate) == 1) {
            contract.setStateTwo(Constants.CONTRACT_STATE_TWO_1); // 待执行
        } else {
            contract.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 执行中
        }
        contract.setAuditState(Constants.AUDIT_STATE_PASS); // 审核状态：通过
        contract.setExpireState(expireState);
        contract.setUpdateTime(date);
        contractMapper.updateContract(contract);
    }

    /**
     * 账单变更：
     * 查询原合同变更/退租时账单生成情况，
     * 如果周期内已经生成账单并且未收款，则变更账收款金额和账单周期；否则生成收款生成退款账单
     * 如果周期外，则删除账单
     */
    private void dealChangeBills(String contractCode, Integer version, String changeDate) {
        BigDecimal initAmt = new BigDecimal("0");
        Date date = new Date();
        Bills detail = new Bills(contractCode, version);
        detail.setChangeDate(changeDate);
        List<Bills> bills = billsMapper.queryBillsList(detail);
        for (Bills bill : bills) {
            Long billId = bill.getBillId();
            String startDate = bill.getStartDate();
            String endDate = bill.getEndDate();
            String costName = bill.getCostName();
            if(Constants.CYCLE_COST_NAME_MAP.containsKey(costName)){
                if (DateTimeUtil.compare_date(startDate, changeDate) == 1) {
                    String state = bill.getState();
                    if (Constants.BILL_DEAL_STATE_NO.equals(state)) {
                        BigDecimal costAmt = bill.getCostAmt();
                        BigDecimal feeLateAmt = bill.getFeeLateAmt();
                        float num = DateTimeUtil.getIntervalDays(startDate, changeDate);
                        float totalNum = DateTimeUtil.getIntervalDays(startDate, endDate) + 1;
                        String rate = String.valueOf(num / totalNum);
                        costAmt = costAmt.multiply(new BigDecimal(rate));
                        bill = new Bills(contractCode, version);
                        bill.setBillId(billId);
                        bill.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);
                        bill.setEndDate(DateTimeUtil.getAboutDay(changeDate, -1, "yyyy-MM-dd"));
                        bill.setCostAmt(costAmt);
                        bill.setResidualAmt(costAmt.add(feeLateAmt));
                        billsMapper.updateBillInfo(bill);
                    } else {
                        BigDecimal costAmt = bill.getCostAmt();
                        float num = DateTimeUtil.getIntervalDays(startDate, changeDate);
                        float totalNum = DateTimeUtil.getIntervalDays(startDate, endDate) + 1;
                        String rate = String.valueOf(num / totalNum);
                        costAmt = costAmt.subtract(costAmt.multiply(new BigDecimal(rate)));
                        bill.setBillCode(CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_FK_CODE));
                        bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 付款订单
                        bill.setStartDate(changeDate);
                        bill.setEndDate(endDate);
                        bill.setCostAmt(costAmt);
                        bill.setFinishAmt(initAmt);
                        bill.setTransferAmt(initAmt);
                        bill.setFreeAmt(initAmt);
                        bill.setFeeLateAmt(initAmt);
                        bill.setFeeLateRatio(0d);
                        bill.setResidualAmt(costAmt);
                        bill.setShouldDealDate(changeDate);
                        bill.setDays(0);
                        bill.setState(Constants.BILL_DEAL_STATE_NO);
                        bill.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);
                        bill.setConfirmer("");
                        bill.setCreateTime(date);
                        bill.setUpdateTime(date);
                        billsMapper.insertBills(bill);
                    }
                }else{
                    billsMapper.deleteBillsById(billId);
                }
            }
        }
    }

    /**
     * 处理退租账单：
     * @param contract
     */
    private void dealDepositReturnBills(Contract contract) {
        String startDate = contract.getStartDate();
        String endDate = contract.getEndDate();
        String changeDate = contract.getChangeDate();
        Integer projectId = contract.getProjectId();
        String companyCode = contract.getCompanyCode();
        Integer customerId = contract.getCustomerId();
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();

        Date date = new Date(); // 当前时间
        BigDecimal initAmt = new BigDecimal("0");
        List billList = Lists.newArrayList();
        // 生成付款账单
        String billCode = CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_FK_CODE);// 生成付款账单编号
        Bills object = new Bills();
        object.setCompanyCode(companyCode);
        object.setContractCode(contractCode);
        object.setVersion(version);

        /*租赁押金start*/
        object.setCostName(Constants.COST_NAME_DEPOSIT);
        Bills detail = billsMapper.queryBillsCostDetail(object);
        if (detail != null) {
            BigDecimal deposit = detail.getCostAmt().subtract(detail.getFreeAmt());
            if (deposit.compareTo(BigDecimal.ZERO) != 0) {
                Bills bill = new Bills();
                bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
                bill.setProjectId(projectId);
                bill.setCompanyCode(companyCode);
                bill.setContractCode(contractCode);
                bill.setVersion(version);
                bill.setCustomerId(customerId);
                bill.setBillCode(billCode);
                bill.setFeeType(Constants.COST_NAME_PLEDGE);
                bill.setCostName(Constants.COST_NAME_DEPOSIT);
                bill.setStartDate(startDate);
                bill.setEndDate(endDate);
                bill.setCostAmt(deposit);
                bill.setFinishAmt(initAmt);
                bill.setResidualAmt(deposit);
                bill.setFreeAmt(initAmt);
                bill.setFeeLateAmt(initAmt);
                bill.setFeeLateRatio(0d);
                bill.setShouldDealDate(changeDate); // 应付时间
                bill.setDays(0); // 剩余/逾期天数
                bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
                bill.setCreateTime(date);
                bill.setUpdateTime(date);
                bill.setCreateUserId(0);
                bill.setUpdateUserId(0);
                billList.add(bill);
            }
        }
        /*租赁押金end*/

        /*物业费押金start*/
        object.setCostName(Constants.COST_NAME_WY_DEPOSIT);
        detail = billsMapper.queryBillsCostDetail(object);
        if (detail != null) {
            BigDecimal wyDeposit = detail.getCostAmt().subtract(detail.getFreeAmt());
            if (wyDeposit.compareTo(BigDecimal.ZERO) != 0) {
                Bills bill = new Bills();
                bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
                bill.setProjectId(contract.getProjectId());
                bill.setCompanyCode(companyCode);
                bill.setContractCode(contractCode);
                bill.setVersion(version);
                bill.setCustomerId(customerId);
                bill.setBillCode(billCode);
                bill.setFeeType(Constants.COST_NAME_PLEDGE);
                bill.setCostName(Constants.COST_NAME_WY_DEPOSIT);
                bill.setStartDate(startDate);
                bill.setEndDate(endDate);
                bill.setCostAmt(wyDeposit);
                bill.setFinishAmt(initAmt);
                bill.setResidualAmt(wyDeposit);
                bill.setFreeAmt(initAmt);
                bill.setFeeLateAmt(initAmt);
                bill.setFeeLateRatio(0d);
                bill.setShouldDealDate(changeDate); // 应付时间
                bill.setDays(0); // 剩余/逾期天数
                bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
                bill.setCreateTime(date);
                bill.setUpdateTime(date);
                bill.setCreateUserId(0);
                bill.setUpdateUserId(0);
                billList.add(bill);
            }
        }
        /*物业费押金end*/

        /*履约保证金start*/
        object.setCostName(Constants.COST_NAME_BREACHAMT);
        detail = billsMapper.queryBillsCostDetail(object);
        if (detail != null) {
            BigDecimal breachAmt = detail.getCostAmt().subtract(detail.getFreeAmt());
            if (breachAmt.compareTo(BigDecimal.ZERO) != 0) {
                Bills bill = new Bills();
                bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
                bill.setProjectId(contract.getProjectId());
                bill.setCompanyCode(companyCode);
                bill.setContractCode(contractCode);
                bill.setVersion(version);
                bill.setCustomerId(customerId);
                bill.setBillCode(billCode);
                bill.setFeeType(Constants.COST_NAME_PLEDGE);
                bill.setCostName(Constants.COST_NAME_BREACHAMT);
                bill.setStartDate(startDate);
                bill.setEndDate(endDate);
                bill.setCostAmt(breachAmt);
                bill.setFinishAmt(initAmt);
                bill.setResidualAmt(breachAmt);
                bill.setFreeAmt(initAmt);
                bill.setFeeLateAmt(initAmt);
                bill.setFeeLateRatio(0d);
                bill.setShouldDealDate(changeDate); // 应付时间
                bill.setDays(0); // 剩余/逾期天数
                bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
                bill.setCreateTime(date);
                bill.setUpdateTime(date);
                bill.setCreateUserId(0);
                bill.setUpdateUserId(0);
                billList.add(bill);
            }
        }
        /*履约保障金end*/

        // 批量插入合同款账单信息
        if (billList.size() > 0) {
            billsMapper.insertBillsBatch(billList);
        }
    }

    /**
     * 获取押金应退账单
     *
     * @param contract
     */
    private List<Bills> getDepositBills(Contract contract) {
        Date date = new Date(); // 当前时间
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        BigDecimal initAmt = new BigDecimal("0");
        List billList = Lists.newArrayList();
        String changeDate = contract.getChangeDate();
        // 剩余/逾期天数
        int days = (int) DateTimeUtil.getIntervalDays(today, changeDate);
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        String billCode = CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_FK_CODE);
        /*租金保证金start*/
        BigDecimal deposit = contract.getDeposit();
        if (deposit != null && deposit.compareTo(BigDecimal.ZERO) != 0) {
            Bills bill = new Bills();
            bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
            bill.setProjectId(contract.getProjectId());
            bill.setContractCode(contractCode);
            bill.setVersion(version);
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(billCode);
            bill.setFeeType(Constants.COST_NAME_PLEDGE);
            bill.setCostName(Constants.COST_NAME_DEPOSIT);
            bill.setStartDate(contract.getStartDate());
            bill.setEndDate(contract.getEndDate());
            bill.setCostAmt(deposit);
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(deposit);
            bill.setFreeAmt(initAmt);
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(0d);
            bill.setShouldDealDate(changeDate); // 应付时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            billList.add(bill);
        }
        /*租金保证金end*/

        /*物业费押金start*/
        BigDecimal wyDeposit = contract.getWyDeposit();
        if (wyDeposit != null && wyDeposit.compareTo(BigDecimal.ZERO) != 0) {
            Bills bill = new Bills();
            bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
            bill.setProjectId(contract.getProjectId());
            bill.setContractCode(contractCode);
            bill.setVersion(version);
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(billCode);
            bill.setFeeType(Constants.COST_NAME_PLEDGE);
            bill.setCostName(Constants.COST_NAME_WY_DEPOSIT);
            bill.setStartDate(contract.getStartDate());
            bill.setEndDate(contract.getEndDate());
            bill.setCostAmt(wyDeposit);
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(wyDeposit);
            bill.setFreeAmt(initAmt);
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(0d);
            bill.setShouldDealDate(changeDate); // 应付时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            billList.add(bill);
        }
        /*物业费押金end*/

        /*物业费押金start*/
        BigDecimal breachAmt = contract.getBreachAmt();
        if (breachAmt != null && breachAmt.compareTo(BigDecimal.ZERO) != 0) {
            Bills bill = new Bills();
            bill.setBillType(Constants.BILL_TYPE_PAYMENT); // 账单类型：付款账单
            bill.setProjectId(contract.getProjectId());
            bill.setContractCode(contractCode);
            bill.setVersion(version);
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(billCode);
            bill.setFeeType(Constants.COST_NAME_PLEDGE);
            bill.setCostName(Constants.COST_NAME_BREACHAMT);
            bill.setStartDate(contract.getStartDate());
            bill.setEndDate(contract.getEndDate());
            bill.setCostAmt(wyDeposit);
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(breachAmt);
            bill.setFreeAmt(initAmt);
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(0d);
            bill.setShouldDealDate(changeDate); // 应付时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            billList.add(bill);
        }
        /*物业费押金end*/
        return billList;
    }

    /**
     * 账单汇总详情
     *
     * @param bills
     * @return
     */
    @Override
    public BillsSum queryBillsSumDetail(Bills bills) {
        return billsMapper.queryBillsSumDetail(bills);
    }

    /**
     * @Description:查询财务账单列表
     * @author: xiachunyu
     * @date: 2019-07-05
     */
    @Override
    public List<BillsSum> queryFinanceBillsList(Bills bills) {
        return billsMapper.queryFinanceBillsList(bills);
    }

    /**
     * @Description: 查询账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public List<Bills> queryBillsList(Bills bills) {
        return billsMapper.queryBillsList(bills);
    }

    /**
     * @Description: 查询可结转账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public List<Bills> queryTransferBillsList(Bills bills) {
        return billsMapper.queryTransferBillsList(bills);
    }

    /**
     * @Description: 确认账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @Override
    @Transactional
    public void confirmBills(BillsSum billsSum) {
        String contractCode = billsSum.getContractCode();
        Integer version = billsSum.getVersion();
        String shouldDealDate = billsSum.getShouldDealDate();
        String billCode = billsSum.getBillCode();
        BigDecimal initAmt = new BigDecimal("0");
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        Date date = new Date();
        // 查询合同详情
        Contract contract = contractMapper.queryContractDetail(new Contract(contractCode, version));
        List<Bills> list = billsSum.getBillsList();
        for (Bills bill : list) {
            bill.setFloatResidualAmt(bill.getFreeAmt());
            bill.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
            bill.setConfirmer(billsSum.getConfirmer());
            bill.setUpdateTime(date);
            bill.setUpdateUserId(billsSum.getConfirmerId());
            billsMapper.updateBills(bill);
        }
        List<Bills> tempList = billsSum.getTempBillsList();
        if (tempList != null) {
            for (Bills bill : tempList) {
                bill.setProjectId(contract.getProjectId());
                bill.setCompanyCode(contract.getCompanyCode());
                bill.setContractCode(contractCode);
                bill.setVersion(version);
                bill.setCustomerId(contract.getCustomerId());
                bill.setShouldDealDate(shouldDealDate);
                bill.setBillCode(billCode);
                bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 收款
                String dateRange = bill.getDateRange();
                if (StringUtils.isNotEmpty(dateRange)) {
                    bill.setStartDate(dateRange.split("~")[0].trim());
                    bill.setEndDate(dateRange.split("~")[1].trim());
                }
                bill.setFinishAmt(initAmt);
                bill.setResidualAmt(bill.getCostAmt());
                bill.setFreeAmt(initAmt);
                bill.setFeeLateAmt(initAmt);
                bill.setDays((int) DateTimeUtil.getIntervalDays(today, shouldDealDate)); // 剩余/逾期天数
                bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
                bill.setConfirmState(Constants.BILL_CONFIRM_STATE_OK); // 默认已确认
                bill.setConfirmer(billsSum.getConfirmer());
                bill.setCreateTime(date);
                bill.setUpdateTime(date);
                bill.setCreateUserId(billsSum.getConfirmerId());
                bill.setUpdateUserId(billsSum.getConfirmerId());
                billsMapper.insertBills(bill);
            }
        }
    }

    /**
     * 结算账单
     *
     * @param settelSum
     */
    @Override
    public void settleBills(SettelSum settelSum) {
        String contractCode = settelSum.getContractCode();
        Integer version = settelSum.getVersion();
        String companyCode = settelSum.getCompanyCode();
        String billType = settelSum.getBillType();
        String operator = (String) CacheUtils.getSession().getAttribute("operator");
        Integer userId = settelSum.getCreateUserId();
        Date date = new Date();
        List<Settel> settleList = settelSum.getSettleList();
        for (Settel settel : settleList) {
            settel.setContractCode(contractCode);
            settel.setVersion(version);
            settel.setCompanyCode(companyCode);
            settel.setBillType(billType);
            settel.setOperator(operator);
            settel.setCreateTime(date);
            settel.setCreateUserId(userId);
            settel.setUpdateTime(date);
            settel.setUpdateUserId(userId);
        }
        // 批量插入收款记录
        if (settleList != null) {
            settelMapper.insertSettelBatch(settleList);
        }
        /* 更新收款信息 */
        for (Settel settel : settleList) {
            Long billId = settel.getBillId();  // 账单ID
            BigDecimal shouldAmt = settel.getShouldAmt(); // 应收/付金额
            BigDecimal actualAmt = settel.getActualAmt(); // 实收/付金额
            String state; // 收款状态
            if (shouldAmt.compareTo(actualAmt) == 0) {
                state = Constants.BILL_DEAL_STATE_YES; // 全部收/付款
            } else {
                state = Constants.BILL_DEAL_STATE_PART;// 部分收/付款
            }
            Bills bills = new Bills(contractCode, version);
            bills.setBillId(billId);
            bills.setFloatFinishAmt(actualAmt);
            bills.setFloatResidualAmt(actualAmt);
            bills.setState(state);
            bills.setUpdateTime(date);
            bills.setUpdateUserId(userId);
            billsMapper.updateBills(bills);
        }
    }

    /**
     * 账单结转
     *
     * @param transfer
     */
    @Override
    @Transactional
    public void transferBills(Transfer transfer) {
        String contractCode = transfer.getContractCode();
        Integer version = transfer.getVersion();
        Long billId = transfer.getBillId(); // 转入账单ID
        BigDecimal transferAmt = transfer.getTransferAmt(); // 转入金额
        Integer userId = transfer.getOperatorId();
        String operator = transfer.getOperator();
        Date date = new Date();
        String today = DateTimeUtil.getFormatDate();
        List<Settel> settleList = Lists.newArrayList();
        Settel settel = new Settel();
        // 查询账单详情
        Bills bills = new Bills(contractCode, version);
        bills.setBillId(billId);
        bills.setCompanyCode(transfer.getCompanyCode());
        bills = billsMapper.queryBillsDetail(bills);
        BigDecimal residualAmt = bills.getResidualAmt(); //剩余未收款金额
        // 查询合同详情
        Contract contract = contractMapper.queryContractDetail(new Contract(contractCode, version));
        // 设置转入参数
        TransferEnter transferEnter = new TransferEnter();
        transferEnter.setBillId(transfer.getBillId());
        transferEnter.setBillCode(transfer.getBillCode());
        transferEnter.setCustomerId(contract.getCustomerId());
        transferEnter.setContractCode(contractCode);
        transferEnter.setVersion(version);
        transferEnter.setCompanyCode(contract.getCompanyCode());
        transferEnter.setRoomNos(contract.getRoomNos());
        transferEnter.setCostName(bills.getCostName());
        transferEnter.setCompanyCode(contract.getCompanyCode());
        transferEnter.setStartDate(bills.getStartDate());
        transferEnter.setEndDate(bills.getEndDate());
        transferEnter.setTransferAmt(transferAmt);
        transferEnter.setOperator(operator);
        transferEnter.setCreateTime(date);
        transferEnter.setCreateUserId(userId);
        transferEnter.setUpdateTime(date);
        transferEnter.setUpdateUserId(userId);
        // 插入转入记录
        transferEnterMapper.insertTransferEnter(transferEnter);
        Long enterId = transferEnter.getEnterId();
        // 收款记录
        settel.setBillId(billId);
        settel.setBillCode(transfer.getBillCode());
        settel.setContractCode(contractCode);
        settel.setVersion(version);
        settel.setCompanyCode(transfer.getCompanyCode());
        settel.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        settel.setFeeType(bills.getFeeType());
        settel.setCostName(bills.getCostName());
        settel.setStartDate(bills.getStartDate());
        settel.setEndDate(bills.getEndDate());
        settel.setShouldAmt(bills.getResidualAmt());
        settel.setActualAmt(transferAmt);
        settel.setDealDate(today);
        settel.setPayType(Constants.SETTLE_TYPE_OTHER);
        settel.setOperator(operator);
        settel.setCreateTime(date);
        settel.setCreateUserId(userId);
        settel.setUpdateTime(date);
        settel.setUpdateUserId(userId);
        settel.setRemarks("转入"+bills.getTransferAmt()+"元");
        settleList.add(settel);

        /* 更新收款账单信息 */
        bills = new Bills(contractCode, version);
        // 如果转入金额等于应收款金额，状态为全部收款；否则为部分收款
        if (residualAmt.compareTo(transferAmt) == 0) {
            bills.setState(Constants.BILL_DEAL_STATE_YES); // 全部收款
        } else {
            bills.setState(Constants.BILL_DEAL_STATE_PART); // 部分收款
        }
        bills.setBillId(billId);
        bills.setFloatFinishAmt(transferAmt);
        bills.setFloatResidualAmt(transferAmt);
        bills.setFloatTransferAmt(transferAmt);
        bills.setUpdateTime(date);
        bills.setUpdateUserId(userId);
        billsMapper.updateBills(bills);

        // 设置转出参数
        List<TransferOut> transferOutList = transfer.getTransferOutList();
        if (transferOutList != null) {
            for (TransferOut transferOut : transferOutList) {
                settel = new Settel();
                transferOut.setEnterId(enterId);
                transferOut.setOperator(operator);
                transferOut.setCreateTime(date);
                transferOut.setCreateUserId(userId);
                transferOut.setUpdateTime(date);
                transferOut.setUpdateUserId(userId);
                // 插入转出记录
                transferOutMapper.insertTransferOut(transferOut);
                // 付款记录
                settel.setBillId(transferOut.getBillId());
                settel.setBillCode(transferOut.getBillCode());
                settel.setContractCode(transferOut.getContractCode());
                settel.setVersion(transferOut.getVersion());
                settel.setCompanyCode(transferOut.getCompanyCode());
                settel.setBillType(Constants.BILL_TYPE_PAYMENT);
                settel.setFeeType(transferOut.getFeeType());
                settel.setCostName(transferOut.getCostName());
                settel.setStartDate(transferOut.getStartDate());
                settel.setEndDate(transferOut.getEndDate());
                settel.setShouldAmt(transferOut.getTransferAmt());
                settel.setActualAmt(transferOut.getTransferAmt());
                settel.setDealDate(today);
                settel.setPayType(Constants.SETTLE_TYPE_OTHER);
                settel.setOperator(operator);
                settel.setCreateTime(date);
                settel.setCreateUserId(userId);
                settel.setUpdateTime(date);
                settel.setUpdateUserId(userId);
                settel.setRemarks("转出"+transferOut.getTransferAmt()+"元");
                settleList.add(settel);

                /* 更新付款账单信息 */
                BigDecimal outAmt = transferOut.getTransferAmt();
                bills = new Bills();
                bills.setBillId(transferOut.getBillId());
                bills.setState(Constants.BILL_DEAL_STATE_YES);
                bills.setFloatFinishAmt(outAmt);
                bills.setFloatResidualAmt(outAmt);
                bills.setFloatTransferAmt(outAmt);
                bills.setUpdateTime(date);
                bills.setUpdateUserId(userId);
                billsMapper.updateBills(bills);
            }
            if (settleList != null) {
                settelMapper.insertSettelBatch(settleList);
            }
        }
    }

    /**
     * @Description: 通过主键查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public Bills queryBillsDetailByPrimarykey(Integer billId) {
        return billsMapper.queryBillsDetailByPrimarykey(billId);
    }

    /**
     * @Description: 查询账单表信息详情
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public Bills queryBillsDetail(Bills bills) {
        return billsMapper.queryBillsDetail(bills);
    }

    /**
     * @Description: 新增账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public void insertBills(Bills bills) {
        Date date = new Date(); // 当前时间
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        BigDecimal initAmt = new BigDecimal("0");
        bills.setCreateTime(date); // 创建时间
        bills.setUpdateTime(date); // 更新时间
        String dateRange = bills.getDateRange();
        String shouldDealDate = bills.getShouldDealDate();
        if (StringUtils.isNotEmpty(dateRange)) {
            bills.setStartDate(dateRange.split("~")[0].trim());
            bills.setEndDate(dateRange.split("~")[1].trim());
        }
        bills.setFinishAmt(initAmt);
        bills.setResidualAmt(bills.getCostAmt());
        bills.setFreeAmt(initAmt);
        bills.setFeeLateAmt(initAmt);
        bills.setDays((int) DateTimeUtil.getIntervalDays(today, shouldDealDate)); // 剩余/逾期天数
        billsMapper.insertBills(bills);
    }

    /**
     * @Description: 修改账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public void updateBills(Bills bills) {
        bills.setUpdateTime(new Date()); //更新时间
        billsMapper.updateBills(bills);
    }

    /**
     * @Description: 修改账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public void updateConfirmBillsBatch(Bills bills) {
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        bills.setUpdateTime(new Date()); //更新时间
        billsMapper.updateConfirmBillsBatch(bills);
    }

    /**
     * @Description: 批量结算账单
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    @Transactional
    public void updateSettleBillsBatch(Bills bills) {
        String currentDate = DateTimeUtil.getFormatDate();
        List<Bills> billsList = billsMapper.querySettleBillsListByCode(bills);
        List<Settel> settleList = Lists.newArrayList();
        for (Bills b : billsList) {
            Settel settel = new Settel();
            settel.setBillId(b.getBillId());
            settel.setBillCode(b.getBillCode());
            settel.setContractCode(b.getContractCode());
            settel.setVersion(b.getVersion());
            settel.setCompanyCode(b.getCompanyCode());
            settel.setBillType(b.getBillType());
            settel.setFeeType(b.getFeeType());
            settel.setCostName(b.getCostName());
            settel.setStartDate(b.getStartDate());
            settel.setEndDate(b.getEndDate());
            settel.setDealDate(currentDate);
            settel.setShouldAmt(b.getResidualAmt());
            settel.setActualAmt(b.getResidualAmt());
            settel.setPayType(Constants.SETTLE_TYPE_CASH);
            settel.setOperator(bills.getConfirmer());
            settel.setCreateTime(new Date());
            settel.setCreateUserId(bills.getUpdateUserId());
            settel.setUpdateTime(new Date());
            settel.setUpdateUserId(bills.getUpdateUserId());
            settel.setRemarks("");
            settleList.add(settel);
        }
        bills.setState(Constants.BILL_DEAL_STATE_YES);
        bills.setUpdateTime(new Date()); //更新时间
        billsMapper.updateSettleBillsBatch(bills);
        // 批量插入收款记录
        if (settleList != null) {
            settelMapper.insertSettelBatch(settleList);
        }
    }

    /**
     * 批量生成通知单
     *
     * @param billCodeArr
     */
    @Override
    public String generateBillsNoticeBatch(String[] billCodeArr) throws Exception {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        String templatePath = ReadConfig.getProperty("TEMPLATE_ROOT_PATH") + "/notice.docx";
        String exportPath = ReadConfig.getProperty("FILE_ROOT_PATH");
        // 账单生成路径
        String uniqueCode = CommonUtil.getUniqueCode();
        String generatePath = exportPath + "/bill_out/" + companyCode + "/" + uniqueCode + "/notice";
        File file = new File(generatePath);
        if (!file.exists()) file.mkdirs();
        String baseDir = "缴费通知单/";
        for (String billCode : billCodeArr) {
            List<Bills> bills = billsMapper.queryBillsNoticeList(billCode);
            if (bills.size() > 0) {
                String customerName = bills.get(0).getCustomerName();
                String contractCode = bills.get(0).getContractCode();
                String dealDate = bills.get(0).getShouldDealDate();
                String tempPath = generatePath + "/" + customerName + "（" + billCode + "）.docx";
                WordReporter exporter = new WordReporter(templatePath);
                exporter.init();
                Map params = Maps.newHashMap();
                params.put("billCode", billCode);
                params.put("contractCode", contractCode);
                params.put("customerName", customerName);
                params.put("dealDate", dealDate);
                params.put("linkMan", user.getRealName());
                params.put("phone", user.getPhone());
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (Bills bill : bills) {
                    BigDecimal costAmt = bill.getCostAmt();
                    BigDecimal finishAmt = bill.getFinishAmt();
                    BigDecimal residualAmt = bill.getResidualAmt();
                    BigDecimal feeLateAmt = bill.getFeeLateAmt();
                    BigDecimal freeAmt = bill.getFreeAmt();
                    Map map = Maps.newHashMap();
                    map.put("costName", bill.getCostName());
                    map.put("cycleDate", bill.getStartDate() + " ~ " + bill.getEndDate());
                    map.put("totalAmt", costAmt.add(feeLateAmt).subtract(freeAmt).toString());
                    map.put("finishAmt", finishAmt.toString());
                    map.put("residualAmt", residualAmt.toString());
                    if (bill.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                        map.put("remarks", "");
                    } else {
                        map.put("remarks", bill.getPrice() + "/" + bill.getVolume());
                    }
                    list.add(map);
                }
                exporter.export(params);
                exporter.export(list, 0);
                exporter.generate(tempPath);
            }
        }
        // 打压缩通知单
        FileUtil.fileZip(generatePath, baseDir);
        // 压缩包下载路径
        String zipPath = "/bill_out/" + companyCode + "/" + uniqueCode + "/notice.zip";
        return zipPath;
    }

    /**
     * @Description: 删除账单表信息
     * @author: xiachunyu
     * @date: 2019-07-03
     */
    public void deleteBills(Bills bills) {
        billsMapper.deleteBills(bills);
    }

    @Override
    public int queryLoseCustomer(Bills bills) {
        return billsMapper.queryLoseCustomer(bills);
    }

    @Override
    public BigDecimal queryResidualAmt(Bills bills) {
        return billsMapper.queryResidualAmt(bills);
    }

    @Override
    public List<Deposit> queryDepositBillsInfo(Deposit deposit) {
        List<Deposit> depositList = billsMapper.queryDepositBillsInfo(deposit);
        for (int i = 0; i < depositList.size(); i++) {
            deposit = depositList.get(i);
            deposit.setOrderNum(i + 1);
            if (deposit.getReturnTime() != null) {
                deposit.setState("已退");
            } else {
                deposit.setState("已缴");
            }
        }
        return depositList;

    }

}
