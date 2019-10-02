package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.AuditOperMapper;
import com.ruikun.sys.investment.mapper.AuditProcessMapper;
import com.ruikun.sys.investment.mapper.ContractMapper;
import com.ruikun.sys.investment.mapper.MatterMapper;
import com.ruikun.sys.investment.service.AuditOperService;
import com.ruikun.sys.investment.service.BillsService;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AuditOperServiceImpl implements AuditOperService {

    @Autowired
    private BillsService billsService;
    @Autowired
    private AuditOperMapper auditOperMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private AuditProcessMapper auditProcessMapper;
    @Autowired
    private MatterMapper matterMapper;

    /**
     * 生成审核流
     * @param contractCode
     * @param version
     */
    public void generateAuditOper(String contractCode, Integer version) throws Exception {
        // 查询合同详情
        Contract contract = contractMapper.queryContractDetail(new Contract(contractCode, version));
        String operType = contract.getOperType();
        AuditProcess auditProcess = new AuditProcess();
        auditProcess.setSign(operType);
        auditProcess.setCompanyCode(contract.getCompanyCode());
        List<AuditProcess> list = auditProcessMapper.queryAuditProcessList(auditProcess);
        if (list.size() > 0) { // 如果配置了审核流程生成审核流
            for (int i = 0; i < list.size(); i++) {
                AuditProcess obj = list.get(i);
                AuditOper oper = new AuditOper();
                oper.setSign(operType);
                oper.setCode(contractCode);
                oper.setVersion(version);
                oper.setUserId(obj.getUserId());
                oper.setSort(i + 1);
                if (i == 0) {
                    oper.setLightenUp(Constants.LIGHTEN_UP_YES);
                } else {
                    oper.setLightenUp(Constants.LIGHTEN_UP_NO);
                }
                auditOperMapper.insertAuditOper(oper);

//                Matter matter = new Matter();
//                if(Constants.CONTRACT_OPER_TYPE_NEW.equals(operType)){
//                    matter.setType("新建合同");
//                }else if(Constants.CONTRACT_OPER_TYPE_CHANGE.equals(operType)){
//                    matter.setType("变更合同");
//                }else if(Constants.CONTRACT_OPER_TYPE_RETURN.equals(operType)){
//                    matter.setType("退租合同");
//                }else if(Constants.CONTRACT_OPER_TYPE_RENEW.equals(operType)){
//                    matter.setType("续租合同");
//                }
//                matter.setContractCode(contractCode);
//                matter.setVersion(version);
//                matter.setCustomerId(contract.getCustomerId());
//                matter.setCustomerName(contract.getCustomerName());
//                matter.setSender(contract.getFollowPerson());
//                matter.setSendTime(DateTimeUtil.getFormatDate());
//                matter.setDealerId(obj.getUserId());
//                matter.setLinkId(oper.getId());
//                matter.setState("2"); // 代办
//                matter.setCreateUserId(contract.getCreateUserId());
//                matter.setCreateTime(new Date());
//                matter.setUpdateUserId(contract.getUpdateUserId());
//                matter.setUpdateTime(new Date());
//                matterMapper.insertMatter(matter);
            }
        } else { // 如果没有配置则按照不需要审核操作
            Bills bills = new Bills();
            bills.setContractCode(contractCode);
            bills.setVersion(version);
            billsService.generateBills(bills);
        }
    }

    /**
     * @Description: 查询信息
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    public List<AuditOper> queryAuditOperList(AuditOper auditOper) {
        return auditOperMapper.queryAuditOperList(auditOper);
    }

    @Override
    public List<AuditOper> queryAuditRecordList(AuditOper auditOper) {
        return auditOperMapper.queryAuditRecordList(auditOper);
    }

    /**
     * @Description: 通过主键查询信息详情
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    public AuditOper queryAuditOperDetailByPrimarykey(Long id) {
        return auditOperMapper.queryAuditOperDetailByPrimarykey(id);
    }

    /**
     * @Description: 查询信息详情
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    public AuditOper queryAuditOperDetail(AuditOper auditOper) {
        return auditOperMapper.queryAuditOperDetail(auditOper);
    }

    /**
     * @Description: 新增信息
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    public void insertAuditOper(AuditOper auditOper) {
        auditOperMapper.insertAuditOper(auditOper);
    }

    /**
     * @Description: 修改信息
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    @Transactional
    public void updateAuditOper(AuditOper auditOper) throws Exception {
        auditOper.setAuditDate(DateTimeUtil.getFormatDateTime());
        auditOperMapper.updateAuditOper(auditOper);
        Long id = auditOper.getId();
        int sort = auditOper.getSort();
        String code = auditOper.getCode();
        Integer version = auditOper.getVersion();
        String auditState = auditOper.getAuditState();
        int maxSort = auditOperMapper.queryMaxSort(auditOper);//获取当合同最大排序
        if (Constants.AUDIT_STATE_PASS.equals(auditState)) {
            if (maxSort == sort) {
                Bills bills = new Bills();
                bills.setContractCode(code);
                bills.setVersion(version);
                billsService.generateBills(bills);
            } else {
                auditOper = new AuditOper();
                auditOper.setId(id + 1);
                auditOper.setCode(code);
                auditOper.setVersion(version);
                auditOper.setLightenUp(Constants.LIGHTEN_UP_YES);
                auditOperMapper.updateAuditOper(auditOper);
            }
        } else {
            Contract contract = new Contract(code, version);
            contract.setAuditState(Constants.AUDIT_STATE_NOPASS);
            contractMapper.updateContract(contract); //更新合同状态
        }

        // 更新代办事项
        Matter matter = new Matter();
        matter.setId(auditOper.getMatterId());
        matter.setState("1"); // 已办
        matter.setUpdateTime(new Date());
        matterMapper.updateMatter(matter);
    }

    /**
     * @Description: 删除信息
     * @author: xiachunyu
     * @date: 2019-07-09
     */
    public void deleteAuditOperByPrimarykey(Long id) {
        auditOperMapper.deleteAuditOperByPrimarykey(id);
    }

}
