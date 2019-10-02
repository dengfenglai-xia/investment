package com.ruikun.sys.investment.timeTask;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.mapper.ContractMapper;
import com.ruikun.sys.investment.mapper.ContractRoomMapper;
import com.ruikun.sys.investment.service.RoomService;
import com.ruikun.sys.investment.service.WorkplaceService;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class DealContractStateTask {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractRoomMapper contractRoomMapper;
    @Autowired
    private RoomService roomService;
    @Autowired
    private WorkplaceService workplaceService;

    // 每天23点执行检测合同状态
    @Scheduled(cron = "0 0 23 * * ?")
    private void dealContractTask() {
        Date date = new Date();
        String today = DateTimeUtil.getFormatDate();
        // 查询正在执行的合同
        Contract contract = new Contract();
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL); // 正式合同
        List<Contract> contractList = contractMapper.queryContractList(contract);
        for (Contract c : contractList) {
            Integer contractId = c.getId();
            String stateTwo = c.getStateTwo();
            String changeDate = c.getChangeDate();
            String startDate = c.getStartDate();
            String endDate = c.getEndDate();
            String contractCode = contract.getContractCode();
            Integer version = contract.getVersion();
            if (Constants.CONTRACT_STATE_TWO_1.equals(stateTwo)) { // 待执行
                if (DateTimeUtil.compare_date(DateTimeUtil.getAboutDay(today, 1, "yyyy-MM-dd"), startDate) == 0) {
                    Contract contractObj = new Contract();
                    contractObj.setId(contractId);
                    contractObj.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 修改为执行中
                    contractObj.setUpdateTime(date);
                    contractMapper.updateContractState(contractObj);
                }
            } else if (Constants.CONTRACT_STATE_TWO_2.equals(stateTwo)) { // 执行中（未做什么处理）
                if (DateTimeUtil.compare_date(today, endDate) == 0) {
                    Contract contractObj = new Contract();
                    contractObj.setId(contractId);
                    contractObj.setStateTwo(Constants.CONTRACT_STATE_TWO_6); // 修改为已到期
                    contractObj.setUpdateTime(date);
                    contractMapper.updateContractState(contractObj);
                    // 更新签约房间状态（到期未处理）
                    roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_REACH_EXCEED);
                    // 更新工位状态（到期未处理）
                    workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_REACH_EXCEED);
                }
            } else if (Constants.CONTRACT_STATE_TWO_3.equals(stateTwo)) { // 已变更
                if (DateTimeUtil.compare_date(today, changeDate) == 0) {
                    Contract contractObj = new Contract();
                    contractObj.setId(contractId);
                    contractObj.setStateOne(Constants.CONTRACT_STATE_FILE); //已归档
                    contractObj.setStateTwo(Constants.CONTRACT_STATE_TWO_1); // 变更归档
                    contractObj.setUpdateTime(date);
                    contractMapper.updateContractState(contractObj);
                }
            } else if (Constants.CONTRACT_STATE_TWO_4.equals(stateTwo)) { // 已退租
                if (DateTimeUtil.compare_date(today, changeDate) == 0) {
                    Contract contractObj = new Contract();
                    contractObj.setId(contractId);
                    contractObj.setStateOne(Constants.CONTRACT_STATE_FILE); //已归档
                    contractObj.setStateTwo(Constants.CONTRACT_STATE_TWO_2); // 退租归档
                    contractObj.setUpdateTime(date);
                    contractMapper.updateContractState(contractObj);
                    // 更新签约房间状态（空置）
                    roomService.updateRoomState(contractCode, version, Constants.RENTOUT_STATE_VACANT_ING);
                    // 更新工位状态（空置）
                    workplaceService.updateWorkplaceState(contractCode, version, Constants.RENTOUT_STATE_VACANT_ING);
                }
            } else if (Constants.CONTRACT_STATE_TWO_5.equals(stateTwo)) { // 已续租
                if (DateTimeUtil.compare_date(today, changeDate) == 0) {
                    Contract contractObj = new Contract();
                    contractObj.setId(contractId);
                    contractObj.setStateOne(Constants.CONTRACT_STATE_FILE); //已归档
                    contractObj.setStateTwo(Constants.CONTRACT_STATE_TWO_3); // 续租归档
                    contractObj.setUpdateTime(date);
                    contractMapper.updateContractState(contractObj);
                }
            }
        }
    }
}
