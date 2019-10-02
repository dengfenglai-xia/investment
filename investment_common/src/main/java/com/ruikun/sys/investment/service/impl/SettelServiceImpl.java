package com.ruikun.sys.investment.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ruikun.sys.investment.entity.SettelMonthStatistics;
import com.ruikun.sys.investment.entity.SettelReport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.SettelMapper;
import com.ruikun.sys.investment.entity.Settel;
import com.ruikun.sys.investment.service.SettelService;

@Service
public class SettelServiceImpl implements SettelService {

    @Autowired
    private SettelMapper settelMapper;

    /**
     * @Description: 查询结算记录信息
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public List<Settel> querySettelList(Settel settel) {
        return settelMapper.querySettelList(settel);
    }

    /**
     * @Description: 通过主键查询结算记录信息详情
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public Settel querySettelDetailByPrimarykey(Long id) {
        return settelMapper.querySettelDetailByPrimarykey(id);
    }

    /**
     * @Description: 查询结算记录信息详情
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public Settel querySettelDetail(Settel settel) {
        return settelMapper.querySettelDetail(settel);
    }

    /**
     * @Description: 新增结算记录信息
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public void insertSettel(Settel settel) {
        Date date = new Date(); // 当前时间
        Integer userId = CacheUtils.getUser().getUserId();
        settel.setCreateUserId(userId); // 创建者ID
        settel.setUpdateUserId(userId); // 更新者ID
        settel.setCreateTime(date); // 创建时间
        settel.setUpdateTime(date); // 更新时间
        settelMapper.insertSettel(settel);
    }

    /**
     * @Description: 修改结算记录信息
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public void updateSettel(Settel settel) {
        Integer userId = CacheUtils.getUser().getUserId();
        settel.setUpdateUserId(userId); //更新者ID
        settel.setUpdateTime(new Date()); //更新时间
        settelMapper.updateSettel(settel);
    }

    /**
     * @Description: 删除结算记录信息
     * @author: xiachunyu
     * @date: 2019-07-16
     */
    public void deleteSettelByPrimarykey(Long id) {
        settelMapper.deleteSettelByPrimarykey(id);
    }

    /**
     * 现金流水统计
     * @param settelReport
     * @return
     */
    public List querySettelReport(SettelReport settelReport) {
        List<SettelReport> settelList = settelMapper.querySettelReport(settelReport);
        String feeTypeStr = settelReport.getFeeType();
        HashSet<String> feeTypeSet = Sets.newHashSet();
        for (SettelReport report : settelList) {
            String feeType = report.getFeeType();
            if(StringUtils.isEmpty(feeTypeStr) || feeTypeStr.contains(feeType)){
                feeTypeSet.add(report.getFeeType());
            }
        }
        List smsList = Lists.newArrayList();
        Iterator iterator = feeTypeSet.iterator();
        while (iterator.hasNext()) {
            SettelMonthStatistics sms = new SettelMonthStatistics();
            String feeType = (String) iterator.next();
            sms.setFeeType(feeType);
            for (SettelReport item : settelList) {
                String billType = item.getBillType();
                if(Constants.BILL_TYPE_RECEIVABLES.equals(billType)){
                    sms.setBillType("收入");
                }else{
                    sms.setBillType("支出");
                }
                String month = item.getMonth();
                BigDecimal amount = item.getAmount();
                if (feeType.equals(item.getFeeType())) {
                    switch (month) {
                        case "01":
                            sms.setJanAmt(amount);
                            break;
                        case "02":
                            sms.setFebAmt(amount);
                            break;
                        case "03":
                            sms.setMarAmt(amount);
                            break;
                        case "04":
                            sms.setAprAmt(amount);
                            break;
                        case "05":
                            sms.setMayAmt(amount);
                            break;
                        case "06":
                            sms.setJunAmt(amount);
                            break;
                        case "07":
                            sms.setJulAmt(amount);
                            break;
                        case "08":
                            sms.setAugAmt(amount);
                            break;
                        case "09":
                            sms.setSepAmt(amount);
                            break;
                        case "10":
                            sms.setOctAmt(amount);
                            break;
                        case "11":
                            sms.setNovAmt(amount);
                            break;
                        case "12":
                            sms.setDecAmt(amount);
                            break;
                        default:
                            break;
                    }
                }
            }
            smsList.add(sms);
        }
        return smsList;
    }

    @Override
    public List<String> queryFeeTypeList() {
        return settelMapper.queryFeeTypeList();
    }

}
