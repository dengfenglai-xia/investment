package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.service.AuditOperService;
import com.ruikun.sys.investment.service.ContractReceiptPlanService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ContractReceiptPlanServiceImpl implements ContractReceiptPlanService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractRoomMapper contractRoomMapper;
    @Autowired
    private ContractCarplaceMapper contractCarplaceMapper;
    @Autowired
    private ContractWorkplaceMapper contractWorkplaceMapper;
    @Autowired
    private ContractReceiptPlanMapper contractReceiptPlanMapper;
    @Autowired
    private ContractRentMapper contractRentMapper;
    @Autowired
    AuditOperService auditOperService;

    /**
     * @Description: 查询合同收款计划
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    @Override
    public List<ContractReceiptPlanSum> queryContractReceiptPlanSumList(ContractReceiptPlan contractReceiptPlan) {
        List<ContractReceiptPlanSum> planSumList = contractReceiptPlanMapper.queryContractReceiptPlanSumList(contractReceiptPlan);
        List<ContractReceiptPlan> planList = contractReceiptPlanMapper.queryContractReceiptPlanList(contractReceiptPlan);
        for (int i = 0; i < planSumList.size(); i++) {
            ContractReceiptPlanSum planSum = planSumList.get(i);
            String receivableDate = planSum.getReceivableDate();
            List<ContractReceiptPlan> plans = Lists.newArrayList();
            for (int j = 0; j < planList.size(); j++) {
                ContractReceiptPlan plan = planList.get(j);
                if (receivableDate.equals(plan.getReceivableDate())) {
                    plans.add(plan);
                } else {
                    continue;
                }
            }
            planSum.setPlanList(plans);
            // 如果账单编号为空，说明是首次生成账单计划，系统需要分配一个账单编号，否则用原有账单编号
            if (StringUtils.isEmpty(planSum.getBillCode())) {
                planSum.setBillCode(CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_SK_CODE));
            }
        }
        return planSumList;
    }

    /**
     * @Description: 查询合同收款计划表信息
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    public List<ContractReceiptPlan> queryContractReceiptPlanList(ContractReceiptPlan contractReceiptPlan) {
        return contractReceiptPlanMapper.queryContractReceiptPlanList(contractReceiptPlan);
    }

    /**
     * @Description: 通过主键查询合同收款计划表信息详情
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    public ContractReceiptPlan queryContractReceiptPlanDetailByPrimarykey(Long id) {
        return contractReceiptPlanMapper.queryContractReceiptPlanDetailByPrimarykey(id);
    }

    /**
     * @Description: 查询合同收款计划表信息详情
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    public ContractReceiptPlan queryContractReceiptPlanDetail(ContractReceiptPlan contractReceiptPlan) {
        return contractReceiptPlanMapper.queryContractReceiptPlanDetail(contractReceiptPlan);
    }

    /**
     * @Description: 新增合同收款计划表信息
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    public void insertContractReceiptPlan(ContractReceiptPlan contractReceiptPlan) {
        Date date = new Date(); // 当前时间
        Integer userId = CacheUtils.getUser().getUserId();
        contractReceiptPlan.setCreateUserId(userId); // 创建者ID
        contractReceiptPlan.setUpdateUserId(userId); // 更新者ID
        contractReceiptPlan.setCreateTime(date); // 创建时间
        contractReceiptPlan.setUpdateTime(date); // 更新时间
        contractReceiptPlanMapper.insertContractReceiptPlan(contractReceiptPlan);
    }

    /**
     * @Description: 修改合同收款计划表信息
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    public void updateContractReceiptPlan(ContractReceiptPlan contractReceiptPlan) {
        Integer userId = CacheUtils.getUser().getUserId();
        contractReceiptPlan.setUpdateUserId(userId); //更新者ID
        contractReceiptPlan.setUpdateTime(new Date()); //更新时间
        contractReceiptPlanMapper.updateContractReceiptPlan(contractReceiptPlan);
    }

    /**
     * @Description: 批量修改合同收款计划表信息
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    @Override
    @Transactional
    public void updateContractReceiptPlanBatch(List<ContractReceiptPlan> planList) throws Exception {
        HttpServletRequest request = CommonUtil.getRequest();
        String operType = request.getParameter("operType");
        String operFlag = request.getParameter("operFlag"); // 1 保存，2 提交
        String sameCode = request.getParameter("sameCode"); // 同类合同编号
        String contractCode = request.getParameter("contractCode"); // 合同编号
        Integer version = Integer.parseInt(request.getParameter("version")); // 版本号
        Contract contract = new Contract(contractCode, version);
        if (Constants.OPER_FLAG_SAVE.equals(operFlag)) {
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) {            // 新建草稿
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_NEW);
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) {  // 变更草稿
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_CHANGE);
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {  // 退租草稿
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_RETURN);
            } else {                                                            // 续租草稿
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_RENEW);
            }
        } else {
            if (Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)) {           // 新建待审批
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_NEW);
                contract.setAuditState(Constants.AUDIT_STATE_ING);
            } else if (Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)) {  // 变更待审批
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_CHANGE);
                contract.setAuditState(Constants.AUDIT_STATE_ING);
            } else if (Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)) {  // 退租待审批
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_RETURN);
                contract.setAuditState(Constants.AUDIT_STATE_ING);
            } else {                                                           // 续租待审批
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_RENEW);
                contract.setAuditState(Constants.AUDIT_STATE_ING);
            }
        }
        /*
         * 当草稿(审核状态为初始)/退回(审核状态为驳回)修改时候，版本号均做了递增操作,修改确认后：
         * 需要将原合同删除,并将新合同的版本号退回到原合同版本
         * */
        Contract originalContract = new Contract();
        originalContract.setSameCode(sameCode);
        originalContract.setVersion(contract.getVersion() - 1);
        originalContract = contractMapper.queryContractDetail(originalContract);
        if (originalContract != null) {
            String originalAuditState = originalContract.getAuditState();
            if (Constants.AUDIT_STATE_INIT.equals(originalAuditState)
                    || Constants.AUDIT_STATE_NOPASS.equals(originalAuditState)) {
                String originalContractCode = originalContract.getContractCode();
                Integer originalVersion = originalContract.getVersion();
                // 删除原合同
                contractMapper.deleteContract(new Contract(originalContractCode, originalVersion));
                // 删除当前合同下房间信息
                contractRoomMapper.deleteContractRoom(new ContractRoom(originalContractCode, originalVersion));
                // 删除当前合同下车位信息
                contractCarplaceMapper.deleteContractCarplace(new ContractCarplace(originalContractCode, originalVersion));
                // 删除当前合同下工位信息
                contractWorkplaceMapper.deleteContractWorkplace(new ContractWorkplace(originalContractCode, originalVersion));
                // 删除原合同收款计划
                contractReceiptPlanMapper.deleteContractReceiptPlan(new ContractReceiptPlan(originalContractCode, originalVersion));
                //删除原合同周期性费用
                contractRentMapper.deleteContractRent(new ContractRent(originalContractCode, originalVersion));
            }
        }
        contractMapper.updateContract(contract); //更新合同状态
        contractReceiptPlanMapper.updateContractReceiptPlanBatch(planList); //更新收款计划
        if (Constants.OPER_FLAG_COMMIT.equals(operFlag)) {
            // 配置审核流程
            // 1.如果配置了审核流程 按照流程审核
            // 2.如果没配置审核流程，则直接进入正式合同
            auditOperService.generateAuditOper(contractCode, version);
        }
    }

    /**
     * @Description: 根据合同编号删除合同收款计划
     * @author: xiachunyu
     * @date: 2019-06-10
     */
    @Transactional
    public void deleteContractReceiptPlan(String contractCode, Integer version) {
        //清空当前合同信息
        contractMapper.deleteContract(new Contract(contractCode, version));
        //清空当前合同下房间信息
        contractRoomMapper.deleteContractRoom(new ContractRoom(contractCode, version));
        // 删除当前合同下车位信息
        contractCarplaceMapper.deleteContractCarplace(new ContractCarplace(contractCode, version));
        // 删除当前合同下工位信息
        contractWorkplaceMapper.deleteContractWorkplace(new ContractWorkplace(contractCode, version));
        //清空当前合同周期费用
        contractRentMapper.deleteContractRent(new ContractRent(contractCode, version));
        //清空当前合同收款计划
        contractReceiptPlanMapper.deleteContractReceiptPlan(new ContractReceiptPlan(contractCode, version));
    }

}
