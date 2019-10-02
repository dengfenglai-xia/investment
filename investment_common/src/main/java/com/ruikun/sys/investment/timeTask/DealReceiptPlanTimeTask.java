package com.ruikun.sys.investment.timeTask;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Bills;
import com.ruikun.sys.investment.entity.ContractReceiptPlan;
import com.ruikun.sys.investment.entity.Project;
import com.ruikun.sys.investment.mapper.BillsMapper;
import com.ruikun.sys.investment.mapper.ContractReceiptPlanMapper;
import com.ruikun.sys.investment.mapper.ProjectMapper;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Configuration
@EnableScheduling
public class DealReceiptPlanTimeTask {

    @Autowired
    private ContractReceiptPlanMapper contractReceiptPlanMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private BillsMapper billsMapper;

    // 每天1点执行出单任务
    @Scheduled(cron = "0 0 1 * * ?")
    private void dealReceiptPlanTask() {
        Date date = new Date();
        String today = DateTimeUtil.getFormatDate();
        BigDecimal initAmt = new BigDecimal("0");
        List billList = Lists.newArrayList();
        List<Project> projectList = projectMapper.queryProjectBaseList(null);
        Map daysMap = Maps.newHashMap();
        // 构建项目提前出单天数Map
        for (Project project : projectList) {
            daysMap.put(project.getProjectId(), project.getBillDays());
        }
        // 查询未出单的计划账单
        ContractReceiptPlan plan = new ContractReceiptPlan();
        plan.setWhetherOut(Constants.BILL_WHETHER_OUT_NO);
        plan.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        List<ContractReceiptPlan> planList = contractReceiptPlanMapper.queryReceiptPlanInfoList(plan);
        Set<String> billCodeSet = new HashSet<String>();
        for (ContractReceiptPlan crp : planList) {
            Integer projectId = crp.getProjectId();
            Integer billDays = (Integer) daysMap.get(projectId);
            if (billDays != null) {
                String receivableDate = crp.getReceivableDate();
                // 获取出单日
                String outBillDate = DateTimeUtil.getAboutDay(receivableDate, -billDays, "yyyy-MM-dd");
                // 如果出单日为今天，则进行出单操作
                if (DateTimeUtil.compare_date(today, outBillDate) == 0) {
                    String companyCode = crp.getCompanyCode();
                    String contractCode = crp.getContractCode();
                    Integer version = crp.getVersion();
                    Integer customerId = crp.getCustomerId();
                    Bills bill = new Bills();
                    int days = (int) DateTimeUtil.getIntervalDays(today, receivableDate);// 剩余/逾期天数
                    bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 账单类型：收款账单
                    bill.setProjectId(projectId);
                    bill.setCompanyCode(companyCode);
                    bill.setContractCode(contractCode);
                    bill.setVersion(version);
                    bill.setCustomerId(customerId);
                    bill.setBillCode(crp.getBillCode());
                    String costName = crp.getCostName();
                    if (costName.equals(Constants.COST_NAME_DEPOSIT)
                            || costName.equals(Constants.COST_NAME_WY_DEPOSIT)
                            || costName.equals(Constants.COST_NAME_BREACHAMT)){
                        bill.setFeeType(Constants.COST_NAME_PLEDGE);
                    }else{
                        bill.setFeeType(costName);
                    }
                    bill.setCostName(costName);
                    String startDate = crp.getStartDate();
                    bill.setStartDate(crp.getStartDate());
                    String endDate = crp.getEndDate();
                    BigDecimal costAmt = crp.getCostAmt();
                    String changeDate = crp.getChangeDate();
                    String state = crp.getStateTwo();
                    if (Constants.CONTRACT_STATE_TWO_3.equals(state)  // 已变更
                            || Constants.CONTRACT_STATE_TWO_3.equals(state)) {   // 已退租
                        float num = DateTimeUtil.getIntervalDays(startDate, changeDate) + 1;
                        float totalNum = DateTimeUtil.getIntervalDays(startDate, endDate) + 1;
                        String rate = String.valueOf(num / totalNum);
                        costAmt = costAmt.multiply(new BigDecimal(rate));
                        endDate = changeDate;
                    }
                    bill.setEndDate(endDate);
                    bill.setCostAmt(costAmt);
                    bill.setFinishAmt(initAmt);
                    bill.setResidualAmt(crp.getCostAmt());
                    bill.setFeeLateAmt(initAmt);
                    bill.setFeeLateRatio(crp.getFeeLateRatio());
                    bill.setShouldDealDate(receivableDate); // 应收时间
                    bill.setDays(days); // 剩余/逾期天数
                    bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未收款
                    bill.setBillProperty(Constants.BILL_PROPERTY_FORMAL);  // 正式账单
                    bill.setCreateTime(date);
                    bill.setUpdateTime(date);
                    bill.setCreateUserId(0);
                    bill.setUpdateUserId(0);
                    billList.add(bill);
                    billCodeSet.add(crp.getBillCode());
                }
            }
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
    }

}
