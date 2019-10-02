package com.ruikun.sys.investment.timeTask;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Bills;
import com.ruikun.sys.investment.mapper.BillsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class DealBillsInfoTimeTask {

    @Autowired
    private BillsMapper billsMapper;

    // 每天0点执行账单剩余天数和滞纳金更新任务
    @Scheduled(cron = "0 0 0 * * ?")
    private void dealBillsInfoTask() {
        BigDecimal floatFeeLateAmt = null;
        // 查询未完成付款的账单
        Bills bills = new Bills();
        String[] states = {Constants.BILL_DEAL_STATE_NO, Constants.BILL_DEAL_STATE_PART};
        bills.setStates(states);
        List<Bills> billsList = billsMapper.queryBillsList(bills);
        List<Bills> billsBatch = Lists.newArrayList();
        for (Bills bill : billsList) {
            Bills billObj = new Bills();
            Integer days = bill.getDays();
            days = days - 1;
            if (days < 0) {
                BigDecimal costAmt = bill.getCostAmt();
                BigDecimal ratio = new BigDecimal(bill.getFeeLateRatio());
                floatFeeLateAmt = costAmt.multiply(ratio);
            }else{
                floatFeeLateAmt = BigDecimal.ZERO;
            }
            billObj.setBillId(bill.getBillId());
            billObj.setDays(days);
            billObj.setFloatFeeLateAmt(floatFeeLateAmt);
            billObj.setUpdateTime(new Date());
            billsBatch.add(billObj);
        }
        billsMapper.updateBillsBatch(billsBatch);
    }
}
