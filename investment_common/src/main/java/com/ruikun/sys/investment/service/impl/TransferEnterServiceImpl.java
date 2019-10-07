package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.entity.TransferOut;
import com.ruikun.sys.investment.entity.TransferRecord;
import com.ruikun.sys.investment.mapper.TransferOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.TransferEnterMapper;
import com.ruikun.sys.investment.entity.TransferEnter;
import com.ruikun.sys.investment.service.TransferEnterService;

@Service
public class TransferEnterServiceImpl implements TransferEnterService {

    @Autowired
    private TransferEnterMapper transferEnterMapper;
    @Autowired
    private TransferOutMapper transferOutMapper;


    /**
     * @Description: 查询结转记录信息
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public List<TransferEnter> queryTransferEnterList(TransferEnter transferEnter) {
        return transferEnterMapper.queryTransferEnterList(transferEnter);
    }

    @Override
    public List<TransferRecord> queryTransferRecordList(TransferRecord transferRecord) {
        return transferEnterMapper.queryTransferRecordList(transferRecord);
    }

    @Override
    public List<String> querytransferInfoList(TransferRecord transferRecord) {
        String transferType = transferRecord.getTransferType();
        Long transferId = transferRecord.getTransferId();
        List<String> transferList = Lists.newArrayList();
        if (transferType.equals("1")) {
            TransferOut transferOut = new TransferOut();
            transferOut.setEnterId(transferId);
            List<TransferOut> transferOutList = transferOutMapper.queryTransferOutList(transferOut);
            for (TransferOut out : transferOutList) {
                transferList.add("由 " + out.getBuildingName()+" " + out.getCustomerName() + out.getRoomNos() +"房间" +" 转出 "  + out.getCostName() +" " +out.getTransferAmt()+"元");
            }
        }
        return transferList;
    }

    /**
     * @Description: 通过主键查询结转记录信息详情
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public TransferEnter queryTransferEnterDetailByPrimarykey(Long enterId) {
        return transferEnterMapper.queryTransferEnterDetailByPrimarykey(enterId);
    }

    /**
     * @Description: 查询结转记录信息详情
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public TransferEnter queryTransferEnterDetail(TransferEnter transferEnter) {
        return transferEnterMapper.queryTransferEnterDetail(transferEnter);
    }

    /**
     * @Description: 新增结转记录信息
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public void insertTransferEnter(TransferEnter transferEnter) {
        Date date = new Date(); // 当前时间
        Integer userId = CacheUtils.getUser().getUserId();
        transferEnter.setCreateUserId(userId); // 创建者ID
        transferEnter.setUpdateUserId(userId); // 更新者ID
        transferEnter.setCreateTime(date); // 创建时间
        transferEnter.setUpdateTime(date); // 更新时间
        transferEnterMapper.insertTransferEnter(transferEnter);
    }

    /**
     * @Description: 修改结转记录信息
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public void updateTransferEnter(TransferEnter transferEnter) {
        Integer userId = CacheUtils.getUser().getUserId();
        transferEnter.setUpdateUserId(userId); //更新者ID
        transferEnter.setUpdateTime(new Date()); //更新时间
        transferEnterMapper.updateTransferEnter(transferEnter);
    }

    /**
     * @Description: 删除结转记录信息
     * @author: xiachunyu
     * @date: 2019-07-18
     */
    public void deleteTransferEnterByPrimarykey(Long enterId) {
        transferEnterMapper.deleteTransferEnterByPrimarykey(enterId);
    }

}
