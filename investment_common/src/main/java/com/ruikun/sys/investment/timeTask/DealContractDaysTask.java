package com.ruikun.sys.investment.timeTask;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
public class DealContractDaysTask {

    @Autowired
    private ContractMapper contractMapper;

    // 每天2点处理合同剩余天数以及到期状态
    @Scheduled(cron = "0 0 2 * * ?")
    private void dealContractDays() {
        contractMapper.updateContractDays();
        // 查询正在执行的合同
        Contract contract = new Contract();
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式合同
        List<Contract> contractList = contractMapper.queryContractList(contract);
        for (Contract c : contractList) {
            Integer remainderDays = c.getRemainderDays(); // 剩余天数
            Integer remindDays = c.getRemindDays(); // 提前提醒天数
            if(remainderDays <= remindDays){
                contract = new Contract();
                contract.setId(c.getId());
                if(remainderDays != 0){
                    contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_SOON);
                }else{
                    contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_YES);
                }
                contractMapper.updateExpireState(contract);
            }
        }
    }
}
