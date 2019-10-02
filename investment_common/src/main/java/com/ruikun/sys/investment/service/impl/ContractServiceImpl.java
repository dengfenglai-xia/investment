package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.poiWord.WordReporter;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContractReceiptPlanMapper contractReceiptPlanMapper;
    @Autowired
    private ContractRoomMapper contractRoomMapper;
    @Autowired
    private ContractCarplaceMapper contractCarplaceMapper;
    @Autowired
    private ContractRentMapper contractRentMapper;
    @Autowired
    AuditOperService auditOperService;
    @Autowired
    private ContractIncreaseRateMapper increaseRateMapper;
    @Autowired
    private ContractFreeTimeMapper freeTimeMapper;
    @Autowired
    private BillsService billsService;
    @Autowired
    private ContractWorkplaceMapper contractWorkplaceMapper;
    @Autowired
    private BillsMapper billsMapper;
    @Autowired
    private SettelMapper settelMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RoomService roomService;

    /**
     * @Description: 查询合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Contract> queryContractList(Contract contract) {
        return contractMapper.queryContractList(contract);
    }

    /**
     * @Description: 查询合同数量
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public int queryContractCount(Contract contract) {
        return contractMapper.queryContractCount(contract);
    }

    /**
     * @Description: 查询合同房源数量
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public int queryContractRoomCount(Contract contract) {
        return contractMapper.queryContractRoomCount(contract);
    }

    @Override
    public List<ContractRoom> queryContractRoomInfoList(Contract contract) {
        return contractMapper.queryContractRoomInfoList(contract);
    }

    /**
     * @Description: 查询历史合同列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public List<Contract> queryHistoryContractList(Contract contract) {
        return contractMapper.queryHistoryContractList(contract);
    }

    /**
     * @Description: 查询合同表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Contract queryContractDetail(Contract contract) {
        return contractMapper.queryContractDetail(contract);
    }

    /**
     * @Description: 查询合同表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public int queryCheckContractCode(String contractCode) {
        return contractMapper.queryCheckContractCode(contractCode);
    }

    /**
     * @Description: 新增合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Transactional
    public void insertContract(Contract contract) {
        String operFlag = contract.getOperFlag();
        Date date = new Date(); // 当前时间
        String today = DateTimeUtil.getFormatDate();
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        String startDate = contract.getStartDate();
        String endDate = contract.getEndDate();
        String roomNos = "";
        Integer projectId = 0;
        Integer buildingId = 0;
        double totalArea = 0d;
        contract.setCreateTime(date); // 创建时间
        contract.setUpdateTime(date); // 更新时间
        List<ContractRoom> roomList = contract.getRoomList();
        int remindDays = 0; // 提前到期提醒天数
        int intervalDays = (int) DateTimeUtil.getIntervalDays(today, endDate);
        if (roomList != null) {
            for (int i = 0; i < roomList.size(); i++) {
                ContractRoom cr = roomList.get(i);
                if (i == 0) {
                    projectId = cr.getProjectId();
                    buildingId = cr.getBuildingId();
                    contract.setProjectId(projectId); // 设置合同所属项目
                    contract.setBuildingId(buildingId); // 设置楼宇ID
                    Project project = projectService.queryProjectDetailByPrimarykey(projectId);
                    remindDays = project.getRemindDays();
                }
                if (i == roomList.size() - 1) {
                    roomNos += cr.getRoomNo();
                } else {
                    roomNos += cr.getRoomNo() + "、";
                }
                totalArea += cr.getBuildArea();
                cr.setContractCode(contractCode);
                cr.setVersion(version);
                cr.setStartDate(startDate);
                cr.setEndDate(endDate);
                cr.setCreateTime(date);
                cr.setUpdateTime(date);
                cr.setCreateUserId(contract.getCreateUserId());
                cr.setUpdateUserId(contract.getUpdateUserId());
                contractRoomMapper.insertContractRoom(cr);

                Room room = new Room();
                room.setRoomId(cr.getRoomId());
                room.setUpdateTime(new Date());
                if (intervalDays < remindDays) {
                    room.setState(Constants.RENTOUT_STATE_SOON_EXPIRE);
                } else {
                    room.setState(Constants.RENTOUT_STATE_RENT_ING);
                }
                roomService.updateRoom(room);
            }
        }
        if (Constants.OPER_FLAG_COMMIT.equals(operFlag)) {
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
            if (DateTimeUtil.compare_date(today, startDate) == 1) {
                contract.setStateTwo(Constants.CONTRACT_STATE_TWO_1);
            } else {
                contract.setStateTwo(Constants.CONTRACT_STATE_TWO_2);
            }
            // 获取收款订单
            List<Bills> billList = getBills(contract);
            billsMapper.insertBillsBatch(billList);
        }
        if (intervalDays < remindDays) {
            contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_SOON);
        } else {
            contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_NO);
        }
        contract.setAuditState(Constants.AUDIT_STATE_PASS);
        contract.setRoomNos(roomNos);
        contract.setRemainderDays(intervalDays); //合同剩余天数
        contract.setTotalArea(totalArea);
        contract.setStartDate(startDate); //合同开始日期
        contract.setEndDate(endDate); //合同结束日期
        contract.setCreateTime(date);
        contract.setUpdateTime(date);
        contractMapper.insertContract(contract);
    }

    /**
     * 更新合同基础数据
     *
     * @param contract
     */
    @Transactional
    public void updateAgreement(Contract contract) {
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        String today = DateTimeUtil.getFormatDate();
        Contract contractDetail = contractMapper.queryContractDetail(new Contract(contractCode, version));
        String startDate = contractDetail.getStartDate();
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        if (DateTimeUtil.compare_date(today, startDate) == 1) {
            contract.setStateTwo(Constants.CONTRACT_STATE_TWO_1);
        } else {
            contract.setStateTwo(Constants.CONTRACT_STATE_TWO_2);
        }
        // 获取收款订单
        List<Bills> billList = getBills(contractDetail);
        billsMapper.insertBillsBatch(billList);
        contractMapper.updateContract(contract);
    }

    /**
     * @Description: 修改合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Transactional
    public void updateContract(Contract contract) throws Exception {
        HttpServletRequest request = CommonUtil.getRequest();
        String operFlag = request.getParameter("operFlag"); // 1 保存，2 提交
        if (Constants.OPER_FLAG_SAVE.equals(operFlag)) {
            contract.setAuditState(Constants.AUDIT_STATE_INIT);
            contractMapper.updateContract(contract);
        } else {
            // 更新合同信息
            contract.setAuditState(Constants.AUDIT_STATE_ING);
            contractMapper.updateContract(contract);

            // 配置审核流程
            // 1.如果配置了审核流程 按照流程审核
            // 2.如果没配置审核流程，则直接进入正式合同
            String contractCode = contract.getContractCode();
            Integer version = contract.getVersion();
            auditOperService.generateAuditOper(contractCode, version);
        }
    }

    /**
     * 更新合同基础数据
     *
     * @param contract
     */
    public void updateContractBaseInfo(Contract contract) {
        contractMapper.updateContract(contract);
    }

    /**
     * @Description: 删除合同表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteContract(Contract contract) {
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        // 删除原合同
        contractMapper.deleteContract(new Contract(contractCode, version));
        // 删除当前合同下房间信息
        contractRoomMapper.deleteContractRoom(new ContractRoom(contractCode, version));
        // 删除当前合同下车位信息
        contractCarplaceMapper.deleteContractCarplace(new ContractCarplace(contractCode, version));
        // 删除当前合同下工位信息
        contractWorkplaceMapper.deleteContractWorkplace(new ContractWorkplace(contractCode, version));
        // 删除原合同收款计划
        contractReceiptPlanMapper.deleteContractReceiptPlan(new ContractReceiptPlan(contractCode, version));
        // 删除原合同周期性费用
        contractRentMapper.deleteContractRent(new ContractRent(contractCode, version));
    }

    /**
     * @Description: 生成合同预览账单
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    @Transactional
    public void generateReceipt(Contract contract) {
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion(); // 初始版本号
        Integer userId = contract.getCreateUserId();
        String companyCode = contract.getCompanyCode();
        Integer customerId = contract.getCustomerId();
        String changeDate = contract.getChangeDate();
        Date currentDate = new Date();
        List<ContractReceiptPlan> planList = Lists.newArrayList();
        List<ContractRent> rentList = contract.getRentList();
        ContractRent rent = rentList.get(0); // 租金
        String startDate = rent.getStartDate(); // 合同生效日期
        String endDate = rent.getEndDate(); // 合同截止日期
        String chargeUnit = rent.getChargeUnit(); // 租金计价单位
        BigDecimal dayPrice = rent.getDayPrice(); // 日租金
        String monthCountType = rent.getMonthCountType(); // 月租金计算方式
        BigDecimal monthPrice = rent.getMonthPrice(); // 月租金
        Integer payCycle = rent.getPayCycle(); // 付款周期
        Integer advancePayDays = rent.getAdvancePayDays(); // 提前付款日
        String advancePayType = rent.getAdvancePayType(); // 提前付款日类型
        String costName = rent.getCostName(); // 费用名称
        Double feeLateRatio = rent.getFeeLateRatio(); // 滞纳金比例
        String today = DateTimeUtil.getFormatDate();

        /*处理合同-房间信息*/
        String roomNos = "";
        Double totalArea = 0d;
        contractRoomMapper.deleteContractRoom(new ContractRoom(contractCode, version)); //清空当前合同下的房源信息
        List<ContractRoom> roomList = contract.getRoomList();
        if (roomList != null) {
            for (int i = 0; i < roomList.size(); i++) {
                ContractRoom cr = roomList.get(i);
                if (i == roomList.size() - 1) {
                    roomNos += cr.getRoomNo();
                } else {
                    roomNos += cr.getRoomNo() + "、";
                }
                totalArea += cr.getBuildArea();
                cr.setVersion(version);
                if (StringUtils.isNotEmpty(changeDate)) { // 合同变更生效时间
                    cr.setStartDate(changeDate);
                } else {
                    cr.setStartDate(startDate);
                }
                cr.setEndDate(endDate);
                cr.setCreateTime(currentDate);
                cr.setUpdateTime(currentDate);
                cr.setCreateUserId(contract.getCreateUserId());
                cr.setUpdateUserId(contract.getUpdateUserId());
                contractRoomMapper.insertContractRoom(cr);
            }
        }

        /*处理合同-车位信息*/
        List<ContractCarplace> carplaceList = contract.getCarplaceList();
        contractCarplaceMapper.deleteContractCarplace(new ContractCarplace(contractCode, version));
        if (carplaceList != null) {
            for (int i = 0; i < carplaceList.size(); i++) {
                ContractCarplace cr = carplaceList.get(i);
                cr.setVersion(version);
                if (StringUtils.isNotEmpty(changeDate)) {
                    cr.setStartDate(changeDate);
                } else {
                    cr.setStartDate(startDate);
                }
                cr.setEndDate(endDate);
                cr.setCreateTime(currentDate);
                cr.setUpdateTime(currentDate);
                cr.setCreateUserId(contract.getCreateUserId());
                cr.setUpdateUserId(contract.getUpdateUserId());
                contractCarplaceMapper.insertContractCarplace(cr);
            }
        }

        /*处理合同-工位信息*/
        List<ContractWorkplace> workplaceList = contract.getWorkplaceList();
        contractWorkplaceMapper.deleteContractWorkplace(new ContractWorkplace(contractCode, version));
        if (workplaceList != null) {
            for (int i = 0; i < workplaceList.size(); i++) {
                ContractWorkplace cw = workplaceList.get(i);
                if (i == workplaceList.size() - 1) {
                    roomNos += cw.getPlaceNo();
                } else {
                    roomNos += cw.getPlaceNo() + "、";
                }
                cw.setVersion(version);
                if (StringUtils.isNotEmpty(changeDate)) {
                    cw.setStartDate(changeDate);
                } else {
                    cw.setStartDate(startDate);
                }
                cw.setEndDate(endDate);
                cw.setCreateTime(currentDate);
                cw.setUpdateTime(currentDate);
                cw.setCreateUserId(contract.getCreateUserId());
                cw.setUpdateUserId(contract.getUpdateUserId());
                contractWorkplaceMapper.insertContractWorkplace(cw);
            }
        }

        /* 处理合同信息 */
        // 如果保证金类型为“月”，则为两个月租金的金额
        if (Constants.DEPOSIT_CHARGE_UNIT_2.equals(contract.getDepositType())) {
            BigDecimal deposit = contract.getDeposit();
            contract.setDeposit(deposit.multiply(monthPrice));
        }
        Integer projectId = 0;
        Integer buildingId = 0;
        if (roomList != null && roomList.size() > 0) {
            ContractRoom room = roomList.get(0);
            projectId = room.getProjectId();
            buildingId = room.getBuildingId();
        }
        if (workplaceList != null && workplaceList.size() > 0) {
            ContractWorkplace workplace = workplaceList.get(0);
            projectId = workplace.getProjectId();
            buildingId = workplace.getBuildingId();
        }
        contract.setProjectId(projectId); // 设置合同所属项目
        contract.setBuildingId(buildingId); // 设置楼宇ID
        contract.setRoomNos(roomNos);
        contract.setRemainderDays((int) DateTimeUtil.getIntervalDays(today, endDate)); //合同剩余天数
        contract.setTotalArea(totalArea);
        contract.setStartDate(startDate); //合同开始日期
        contract.setEndDate(endDate); //合同结束日期
        contract.setCreateTime(currentDate);
        contract.setUpdateTime(currentDate);
        contractMapper.deleteContract(contract); //清空当前合同信息
        contractMapper.insertContract(contract); //插入合同信息

        // 递增率
        List<ContractIncreaseRate> increaseRateList = contract.getIncreaseRateList();
        if (increaseRateList != null) {
            increaseRateMapper.deleteContractIncreaseRate(new ContractIncreaseRate(contractCode, version));
            for (ContractIncreaseRate cir : increaseRateList) {
                cir.setContractCode(contractCode);
                cir.setVersion(version);
                cir.setCreateUserId(userId);
                cir.setUpdateUserId(userId);
                cir.setCreateTime(currentDate);
                cir.setUpdateTime(currentDate);
                increaseRateMapper.insertContractIncreaseRate(cir);
            }
        }

        // 免租期
        List<ContractFreeTime> freeTimeList = contract.getFreeTimeList();
        if (freeTimeList != null) {
            freeTimeMapper.deleteContractFreeTime(new ContractFreeTime(contractCode, version));
            for (ContractFreeTime cft : freeTimeList) {
                String freeDateRange = cft.getFreeDateRange();
                cft.setFreeStartDate(freeDateRange.split("~")[0].trim());
                cft.setFreeEndDate(freeDateRange.split("~")[1].trim());
                cft.setContractCode(contractCode);
                cft.setVersion(version);
                cft.setCreateUserId(userId);
                cft.setUpdateUserId(userId);
                cft.setCreateTime(currentDate);
                cft.setUpdateTime(currentDate);
                freeTimeMapper.insertContractFreeTime(cft);
            }
        }
        /* ------ 计算租金账单开始 ------ */
        ContractReceiptPlan plan = new ContractReceiptPlan();
        String billStartDate = startDate;
        if (StringUtils.isNotEmpty(changeDate)) { // 合同变更生效时间
            billStartDate = changeDate;
        }
        String billEndDate = null;
        String tempDate = null;
        BigDecimal costAmt = null;
        int monthNum = DateTimeUtil.getMonths(billStartDate, DateTimeUtil.getAboutDay(endDate, 1, "yyyy-MM-dd"));
        List<BigDecimal> costAmts = Lists.newArrayList();
        for (int n = 0; n < monthNum; n++) {
            if (increaseRateList != null) {
                for (ContractIncreaseRate cir : increaseRateList) {
                    String increaseDate = cir.getIncreaseDate();
                    Double increaseRate = cir.getIncreaseRate();
                    if (billStartDate.equals(increaseDate)) {
                        // 递后的日租金
                        dayPrice = dayPrice.add(dayPrice.multiply(BigDecimal.valueOf(increaseRate / 100)));
                        // 递增后的月租金
                        monthPrice = monthPrice.add(monthPrice.multiply(BigDecimal.valueOf(increaseRate / 100)));
                        break;
                    }
                }
            }
            tempDate = DateTimeUtil.getAboutMonth(billStartDate, 1, "yyyy-MM-dd");
            // 获取每个月租金
            costAmt = getCostAmt(chargeUnit, dayPrice, monthCountType, monthPrice, 1, billStartDate, tempDate);
            // 获取每个月免租后的租金
            billEndDate = DateTimeUtil.getAboutDay(tempDate, -1, "yyyy-MM-dd");
            if (freeTimeList != null) {
                costAmt = getFreeAfterAmt(billStartDate, billEndDate, freeTimeList, costAmt, dayPrice);
            }
            costAmts.add(costAmt);
            billStartDate = tempDate;
        }

        //如果存在不足一个月（零头）的租期，按照实际天数计算租金
        if (DateTimeUtil.compare_date(billEndDate, endDate) == 1) {
            BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(billEndDate, endDate)));
            costAmt = dayPrice.multiply(days);
            if (freeTimeList != null) {
                costAmt = getFreeAfterAmt(billStartDate, endDate, freeTimeList, costAmt, dayPrice);
            }
            costAmts.add(costAmt);
        }

        costAmt = new BigDecimal("0");
        billStartDate = startDate;
        if (StringUtils.isNotEmpty(changeDate)) { // 合同变更生效时间
            billStartDate = changeDate;
        }
        for (int i = 0; i < costAmts.size(); i++) {
            costAmt = costAmt.add(costAmts.get(i));
            if ((i + 1) % payCycle == 0) {
                plan = new ContractReceiptPlan();
                plan.setBillCode("");
                plan.setContractCode(contractCode);
                plan.setVersion(version);
                plan.setProjectId(projectId);
                plan.setCompanyCode(companyCode);
                plan.setCustomerId(customerId);
                plan.setCostName(costName);
                plan.setFeeLateRatio(feeLateRatio);
                // 计算应收款日期(首次默认合同生效日期)
                if ((i + 1) == payCycle) {
                    plan.setReceivableDate(billStartDate);
                } else {
                    plan.setReceivableDate(getReceivableDate(billStartDate, advancePayDays, advancePayType));
                }
                plan.setStartDate(billStartDate);
                tempDate = DateTimeUtil.getAboutMonth(billStartDate, payCycle, "yyyy-MM-dd");
                billEndDate = DateTimeUtil.getAboutDay(tempDate, -1, "yyyy-MM-dd");
                if (i + 1 == costAmts.size() && DateTimeUtil.compare_date(endDate, billEndDate) == 1) {
                    plan.setEndDate(endDate);
                } else {
                    plan.setEndDate(billEndDate);
                }
                plan.setCostAmt(costAmt);
                plan.setCreateUserId(userId);
                plan.setUpdateUserId(userId);
                plan.setCreateTime(currentDate);
                plan.setUpdateTime(currentDate);
                planList.add(plan);
                billStartDate = tempDate;
                costAmt = new BigDecimal("0");
            }
        }
        // 如果付金额不等于零，说明有不满足付款周期的付款区间，则需计算剩余的付款数据
        if (costAmt.compareTo(BigDecimal.ZERO) != 0) {
            plan = new ContractReceiptPlan();
            plan.setBillCode("");
            plan.setContractCode(contractCode);
            plan.setVersion(version);
            plan.setProjectId(projectId);
            plan.setCompanyCode(companyCode);
            plan.setCustomerId(customerId);
            plan.setCostName(costName);
            plan.setFeeLateRatio(feeLateRatio);
            plan.setReceivableDate(getReceivableDate(billStartDate, advancePayDays, advancePayType));
            plan.setStartDate(billStartDate);
            plan.setEndDate(endDate);
            plan.setCostAmt(costAmt);
            plan.setCreateUserId(userId);
            plan.setUpdateUserId(userId);
            plan.setCreateTime(currentDate);
            plan.setUpdateTime(currentDate);
            planList.add(plan);
        }
        /* ------ 计算租金账单结束 ------ */

        /* ------ 租金保证金start ------ */
        BigDecimal deposit = contract.getDeposit();
        if (deposit != null && deposit.compareTo(BigDecimal.ZERO) != 0) {
            plan = new ContractReceiptPlan();
            plan.setBillCode("");
            plan.setContractCode(contractCode);
            plan.setVersion(version);
            plan.setProjectId(projectId);
            plan.setCompanyCode(companyCode);
            plan.setCustomerId(customerId);
            plan.setCostName(Constants.COST_NAME_DEPOSIT);
            if (StringUtils.isNotEmpty(changeDate)) {
                plan.setReceivableDate(changeDate);
                plan.setStartDate(changeDate);
            } else {
                plan.setReceivableDate(startDate);
                plan.setStartDate(startDate);
            }
            plan.setEndDate(endDate);
            plan.setCostAmt(deposit);
            plan.setFeeLateRatio(0d);
            plan.setCreateUserId(userId);
            plan.setUpdateUserId(userId);
            plan.setCreateTime(currentDate);
            plan.setUpdateTime(currentDate);
            planList.add(plan);
        }
        /* ------ 租金保证金end ------ */

        /* ------ 物业费押金start ------ */
        BigDecimal wyDeposit = contract.getWyDeposit();
        if (wyDeposit != null && wyDeposit.compareTo(BigDecimal.ZERO) != 0) {
            plan = new ContractReceiptPlan();
            plan.setBillCode("");
            plan.setContractCode(contractCode);
            plan.setVersion(version);
            plan.setProjectId(projectId);
            plan.setCompanyCode(companyCode);
            plan.setCustomerId(customerId);
            plan.setCostName(Constants.COST_NAME_WY_DEPOSIT);
            if (StringUtils.isNotEmpty(changeDate)) {
                plan.setReceivableDate(changeDate);
                plan.setStartDate(changeDate);
            } else {
                plan.setReceivableDate(startDate);
                plan.setStartDate(startDate);
            }
            plan.setEndDate(endDate);
            plan.setCostAmt(wyDeposit);
            plan.setFeeLateRatio(0d);
            plan.setCreateUserId(userId);
            plan.setUpdateUserId(userId);
            plan.setCreateTime(currentDate);
            plan.setUpdateTime(currentDate);
            planList.add(plan);
        }
        /* ------ 物业费押金end ------ */

        /* ------ 履约保障金start ------ */
        BigDecimal breachAmt = contract.getBreachAmt();
        if (breachAmt != null && breachAmt.compareTo(BigDecimal.ZERO) != 0) {
            plan = new ContractReceiptPlan();
            plan.setBillCode("");
            plan.setContractCode(contractCode);
            plan.setVersion(version);
            plan.setProjectId(projectId);
            plan.setCompanyCode(companyCode);
            plan.setCustomerId(customerId);
            plan.setCostName(Constants.COST_NAME_BREACHAMT);
            if (StringUtils.isNotEmpty(changeDate)) {
                plan.setReceivableDate(changeDate);
                plan.setStartDate(changeDate);
            } else {
                plan.setReceivableDate(startDate);
                plan.setStartDate(startDate);
            }
            plan.setEndDate(endDate);
            plan.setCostAmt(breachAmt);
            plan.setFeeLateRatio(0d);
            plan.setCreateUserId(userId);
            plan.setUpdateUserId(userId);
            plan.setCreateTime(currentDate);
            plan.setUpdateTime(currentDate);
            planList.add(plan);
        }
        /* ------ 履约保障金end ------ */

        /*------ 计算各项周期性费用账单开始 ------ */
        for (int i = 1; i < rentList.size(); i++) {
            rent = rentList.get(i);
            String rentDateRange = rentList.get(i).getRentDateRange();
            startDate = rentDateRange.split("~")[0].trim(); // 开始日期
            endDate = rentDateRange.split("~")[1].trim(); // 结束日期
            chargeUnit = rent.getChargeUnit(); // 租金计价单位
            dayPrice = rent.getDayPrice(); // 日租金
            monthCountType = rent.getMonthCountType(); // 月租金计算方式
            monthPrice = rent.getMonthPrice(); // 月租金
            payCycle = rent.getPayCycle(); // 付款周期
            advancePayDays = rent.getAdvancePayDays(); // 提前付款日
            advancePayType = rent.getAdvancePayType(); // 提前付款日类型
            costName = rent.getCostName(); // 费用名称
            feeLateRatio = rent.getFeeLateRatio(); // 滞纳金比例

            monthNum = DateTimeUtil.getMonths(startDate, DateTimeUtil.getAboutDay(endDate, 1, "yyyy-MM-dd"));
            costAmts = Lists.newArrayList();
            billStartDate = startDate;
            for (int n = 0; n < monthNum; n++) {
                tempDate = DateTimeUtil.getAboutMonth(billStartDate, 1, "yyyy-MM-dd");
                // 获取每个月金额
                costAmt = getCostAmt(chargeUnit, dayPrice, monthCountType, monthPrice, 1, billStartDate, tempDate);
                costAmts.add(costAmt);
                billStartDate = tempDate;
                billEndDate = DateTimeUtil.getAboutDay(tempDate, -1, "yyyy-MM-dd");
            }

            //如果存在不足一个月（零头）的周期，按照实际天数计算租金
            if (DateTimeUtil.compare_date(billEndDate, endDate) == 1) {
                BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(billEndDate, endDate)));
                costAmt = dayPrice.multiply(days);
                costAmts.add(costAmt);
            }

            costAmt = new BigDecimal("0"); // 重置金额
            billStartDate = startDate; // 重置起始时间
            for (int j = 0; j < costAmts.size(); j++) {
                costAmt = costAmt.add(costAmts.get(j));
                if ((j + 1) % payCycle == 0) {
                    plan = new ContractReceiptPlan();
                    plan.setBillCode("");
                    plan.setContractCode(contractCode);
                    plan.setVersion(version);
                    plan.setProjectId(projectId);
                    plan.setCompanyCode(companyCode);
                    plan.setCustomerId(customerId);
                    plan.setCostName(costName);
                    plan.setFeeLateRatio(feeLateRatio);
                    // 计算应收款日期(首次默认开始日期)
                    if ((j + 1) == payCycle) {
                        plan.setReceivableDate(billStartDate);
                    } else {
                        plan.setReceivableDate(getReceivableDate(billStartDate, advancePayDays, advancePayType));
                    }
                    plan.setStartDate(billStartDate);
                    tempDate = DateTimeUtil.getAboutMonth(billStartDate, payCycle, "yyyy-MM-dd");
                    billEndDate = DateTimeUtil.getAboutDay(tempDate, -1, "yyyy-MM-dd");
                    plan.setEndDate(billEndDate);
                    plan.setCostAmt(costAmt);
                    plan.setCreateUserId(userId);
                    plan.setUpdateUserId(userId);
                    plan.setCreateTime(currentDate);
                    plan.setUpdateTime(currentDate);
                    planList.add(plan);
                    billStartDate = tempDate;
                    costAmt = new BigDecimal("0");
                }
            }
            if (costAmt.compareTo(BigDecimal.ZERO) != 0) {
                plan = new ContractReceiptPlan();
                plan.setBillCode("");
                plan.setContractCode(contractCode);
                plan.setVersion(version);
                plan.setProjectId(projectId);
                plan.setCompanyCode(companyCode);
                plan.setCustomerId(customerId);
                plan.setCostName(costName);
                plan.setFeeLateRatio(feeLateRatio);
                plan.setReceivableDate(getReceivableDate(billStartDate, advancePayDays, advancePayType));
                plan.setStartDate(billStartDate);
                plan.setEndDate(endDate);
                plan.setCostAmt(costAmt);
                plan.setCreateUserId(userId);
                plan.setUpdateUserId(userId);
                plan.setCreateTime(currentDate);
                plan.setUpdateTime(currentDate);
                planList.add(plan);
            }
        }
        contractReceiptPlanMapper.deleteContractReceiptPlan(new ContractReceiptPlan(contractCode, version)); //清空当前合同下的收款计划
        contractReceiptPlanMapper.insertContractReceiptPlanBatch(planList); //插入收款计划
        /*------ 计算各项周期性费用账单结束 ------ */

        // 补充周期性费用配置信息（包括租金）
        for (ContractRent cr : rentList) {
            cr.setStartDate(startDate);
            cr.setEndDate(endDate);
            cr.setContractCode(contractCode);
            cr.setVersion(version);
            cr.setCreateUserId(userId);
            cr.setUpdateUserId(userId);
            cr.setCreateTime(currentDate);
            cr.setUpdateTime(currentDate);
        }
        contractRentMapper.deleteContractRent(new ContractRent(contractCode, version)); //清空当前合同下周期性费用配置信息
        contractRentMapper.insertContractRentBatch(rentList); //插入周期性费用配置信息
    }

    /**
     * 客单汇总
     */
    @Override
    public List<ContractSummaryInfo> queryContractCustomerBills(ContractSummaryInfo contractSummaryInfo) {
        String endDate = contractSummaryInfo.getEndDate();
        List<ContractSummaryInfo> infoList = contractMapper.queryContractSummaryInfo(contractSummaryInfo);
        List<Settel> settelList = settelMapper.queryContractSummarySettel(contractSummaryInfo);
        for (int i = 0; i < infoList.size(); i++) {
            ContractSummaryInfo info = infoList.get(i);
            BigDecimal yearIncome = new BigDecimal("0");
            BigDecimal yearActualIncome = new BigDecimal("0");
            String contractCode = info.getContractCode();
            Integer version = info.getVersion();
            String chargeUnit = (String) Constants.CHARGE_UNIT_MAP.get(info.getChargeUnit());
            info.setOrderNum(i + 1);
            info.setTerm(info.getStartDate() + " ~ " + info.getEndDate());
            info.setUnitPriceStr(info.getUnitPrice() + " " + chargeUnit);
            if (info.getFreeTime() == null) info.setFreeTime("无");
            for (Settel settel : settelList) {
                String settelStartDate = settel.getStartDate();
                String settelEndDate = settel.getEndDate();
                String endMonth = settelEndDate.substring(0, 7);
                BigDecimal actualAmt = settel.getActualAmt();
                if (contractCode.equals(settel.getContractCode())
                        && version.equals(settel.getVersion())) {
                    yearIncome = yearIncome.add(actualAmt);
                    if (DateTimeUtil.compare_date(endDate, endMonth, "yyyy-MM") == 1) {
                        float num = DateTimeUtil.getIntervalDays(settelStartDate, DateTimeUtil.getAboutMonth(endDate, 1, "yyyy-MM") + "-01");
                        float totalNum = DateTimeUtil.getIntervalDays(settelStartDate, settelEndDate) + 1;
                        String rate = String.valueOf(num / totalNum);
                        yearActualIncome = yearActualIncome.add(actualAmt.multiply(new BigDecimal(rate)));
                    } else {
                        yearActualIncome = yearActualIncome.add(actualAmt);
                    }
                }
            }
            info.setYearIncome(yearIncome);
            info.setYearActualIncome(yearActualIncome);
            info.setYearPrice(info.getMonthPrice().multiply(new BigDecimal("12")).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return infoList;
    }

    /**
     * 生成合同明细
     *
     * @param contract
     */
    @Override
    public Map generateContractDetails(Contract contract) throws Exception {
        Map map = Maps.newHashMap();
        DecimalFormat df = new DecimalFormat("#.00");
        String contractCode = contract.getContractCode();
        Integer version = contract.getVersion();
        String companyCode = contract.getCompanyCode();
        String companyName = contract.getCompanyName();
        contract = contractMapper.queryContractDetail(contract);
        // 查询递增率
        String increaseRate = "";
        ContractIncreaseRate cir = new ContractIncreaseRate();
        cir.setContractCode(contractCode);
        cir.setVersion(version);
        List<ContractIncreaseRate> increaseRateList = increaseRateMapper.queryContractIncreaseRateList(cir);
        for (ContractIncreaseRate rate : increaseRateList) {
            increaseRate += rate.getIncreaseDate() + "起，递增" + rate.getIncreaseRate() + "%，";
        }
        // 查询免租期
        String freeTime = "";
        ContractFreeTime cft = new ContractFreeTime();
        cft.setContractCode(contractCode);
        cft.setVersion(version);
        List<ContractFreeTime> freeTimeList = freeTimeMapper.queryContractFreeTimeList(cft);
        for (ContractFreeTime ft : freeTimeList) {
            freeTime += ft.getFreeStartDate() + " 至 " + ft.getFreeEndDate() + "，";
        }
        // 查询周期费用
        ContractRent contractRent = new ContractRent();
        contractRent.setContractCode(contractCode);
        contractRent.setVersion(version);
        List<ContractRent> rentList = contractRentMapper.queryContractRentList(contractRent);

        String leaseUnitPrice = "";
        String leaseDayPrice = "";
        String leaseMonthPrice = "";
        String leaseAdvancePayDays = "";
        String leasePayCycle = "";
        String leaseFeeLateRatio = "";

        String wyRentDateRange = "";
        String wyUnitPrice = "";
        String wyDayPrice = "";
        String wyMonthPrice = "";
        String wyAdvancePayDays = "";
        String wyPayCycle = "";
        String wyFeeLateRatio = "";

        String clearRentDateRange = "";
        String clearUnitPrice = "";
        String clearDayPrice = "";
        String clearMonthPrice = "";
        String clearAdvancePayDays = "";
        String clearPayCycle = "";
        String clearFeeLateRatio = "";

        String carRentDateRange = "";
        String carUnitPrice = "";
        String carDayPrice = "";
        String carMonthPrice = "";
        String carAdvancePayDays = "";
        String carPayCycle = "";
        String carFeeLateRatio = "";

        String energyRentDateRange = "";
        String energyUnitPrice = "";
        String energyDayPrice = "";
        String energyMonthPrice = "";
        String energyAdvancePayDays = "";
        String energyPayCycle = "";
        String energyFeeLateRatio = "";
        for (ContractRent rent : rentList) {
            String costName = rent.getCostName();
            if (Constants.COST_NAME_RENT.equals(costName)) {
                leaseUnitPrice = rent.getUnitPrice() + " " + Constants.CHARGE_UNIT_MAP.get(rent.getChargeUnit());
                leaseDayPrice = rent.getDayPrice().toString();
                leaseMonthPrice = rent.getMonthPrice().toString();
                leaseAdvancePayDays = rent.getAdvancePayDays() + "个" + Constants.ADVANCE_PAY_TYPE_MAP.get(rent.getAdvancePayType());
                leasePayCycle = rent.getPayCycle() + "个月一付";
                leaseFeeLateRatio = rent.getFeeLateRatio() + "%";
            }
            if (Constants.COST_NAME_PROPERTY.equals(costName)) {
                wyRentDateRange = rent.getStartDate() + "至" + rent.getEndDate();
                wyUnitPrice = rent.getUnitPrice() + " " + Constants.CHARGE_UNIT_MAP.get(rent.getChargeUnit());
                wyDayPrice = rent.getDayPrice().toString();
                wyMonthPrice = rent.getMonthPrice().toString();
                wyAdvancePayDays = rent.getAdvancePayDays() + " " + Constants.ADVANCE_PAY_TYPE_MAP.get(rent.getAdvancePayType());
                wyPayCycle = rent.getPayCycle() + "个月一付";
                wyFeeLateRatio = rent.getFeeLateRatio() + "%";
            }
            if (Constants.COST_NAME_CLEAR.equals(costName)) {
                clearRentDateRange = rent.getStartDate() + "至" + rent.getEndDate();
                clearUnitPrice = rent.getUnitPrice() + " " + Constants.CHARGE_UNIT_MAP.get(rent.getChargeUnit());
                clearDayPrice = rent.getDayPrice().toString();
                clearMonthPrice = rent.getMonthPrice().toString();
                clearAdvancePayDays = rent.getAdvancePayDays() + " " + Constants.ADVANCE_PAY_TYPE_MAP.get(rent.getAdvancePayType());
                clearPayCycle = rent.getPayCycle() + "个月一付";
                clearFeeLateRatio = rent.getFeeLateRatio() + "%";
            }
            if (Constants.COST_NAME_CARPLACE.equals(costName)) {
                carRentDateRange = rent.getStartDate() + "至" + rent.getEndDate();
                carUnitPrice = rent.getUnitPrice() + " " + Constants.CHARGE_UNIT_MAP.get(rent.getChargeUnit());
                carDayPrice = rent.getDayPrice().toString();
                carMonthPrice = rent.getMonthPrice().toString();
                carAdvancePayDays = rent.getAdvancePayDays() + " " + Constants.ADVANCE_PAY_TYPE_MAP.get(rent.getAdvancePayType());
                carPayCycle = rent.getPayCycle() + "个月一付";
                carFeeLateRatio = rent.getFeeLateRatio() + "%";
            }
            if (Constants.COST_NAME_ENERGY.equals(costName)) {
                energyRentDateRange = rent.getStartDate() + "至" + rent.getEndDate();
                energyUnitPrice = rent.getUnitPrice() + " " + Constants.CHARGE_UNIT_MAP.get(rent.getChargeUnit());
                energyDayPrice = rent.getDayPrice().toString();
                energyMonthPrice = rent.getMonthPrice().toString();
                energyAdvancePayDays = rent.getAdvancePayDays() + " " + Constants.ADVANCE_PAY_TYPE_MAP.get(rent.getAdvancePayType());
                energyPayCycle = rent.getPayCycle() + "个月一付";
                energyFeeLateRatio = rent.getFeeLateRatio() + "%";
            }
        }
        // 模板路径
        String rootPath = ReadConfig.getProperty("FILE_ROOT_PATH");
        String templatePath = rootPath + "/company/" + companyCode + ".docx";
        // 生成合同路径
        String generatePath = rootPath + "/company/" + companyCode + "/";
        File file = new File(generatePath);
        if (!file.exists()) file.mkdirs();
        WordReporter exporter = new WordReporter(templatePath);
        exporter.init();
        Map params = Maps.newHashMap();
        //双方信息
        params.put("companyName", companyName);
        params.put("buildingName", contract.getBuildingName());
        params.put("followPerson", contract.getFollowPerson());
        params.put("customerName", contract.getCustomerName());
        params.put("contacts", contract.getContacts());
        params.put("phone", contract.getPhone());
        String customerType = contract.getCustomerType();
        if("2".equals(customerType)){
            params.put("customerType", "个人");
        }else{
            params.put("customerType", "公司");
        }
        // 合同基础信息
        params.put("contractCode", contractCode);
        params.put("contractType", contract.getContractType());
        params.put("signDate", contract.getSignDate());
        params.put("startDate", contract.getStartDate());
        params.put("endDate", contract.getEndDate());
        params.put("roomNos", contract.getRoomNos());
        params.put("totalArea", df.format(contract.getTotalArea()));
        params.put("deposit", contract.getDeposit()+"");
        params.put("wyDeposit", contract.getWyDeposit()+"");
        params.put("breachAmt", contract.getBreachAmt()+"");
        params.put("increaseRate", increaseRate);
        params.put("freeTime", freeTime);
        // 周期性费用
        params.put("leaseUnitPrice", leaseUnitPrice);
        params.put("leaseDayPrice", leaseDayPrice);
        params.put("leaseMonthPrice", leaseMonthPrice);
        params.put("leaseAdvancePayDays", leaseAdvancePayDays);
        params.put("leasePayCycle", leasePayCycle);
        params.put("leaseFeeLateRatio", leaseFeeLateRatio);
        params.put("wyRentDateRange", wyRentDateRange);
        params.put("wyUnitPrice", wyUnitPrice);
        params.put("wyDayPrice", wyDayPrice);
        params.put("wyMonthPrice", wyMonthPrice);
        params.put("wyAdvancePayDays", wyAdvancePayDays);
        params.put("wyPayCycle", wyPayCycle);
        params.put("wyFeeLateRatio", wyFeeLateRatio);
        params.put("clearRentDateRange", clearRentDateRange);
        params.put("clearUnitPrice", clearUnitPrice);
        params.put("clearDayPrice", clearDayPrice);
        params.put("clearMonthPrice", clearMonthPrice);
        params.put("clearAdvancePayDays", clearAdvancePayDays);
        params.put("clearPayCycle", clearPayCycle);
        params.put("clearFeeLateRatio", clearFeeLateRatio);
        params.put("carRentDateRange", carRentDateRange);
        params.put("carUnitPrice", carUnitPrice);
        params.put("carDayPrice", carDayPrice);
        params.put("carMonthPrice", carMonthPrice);
        params.put("carAdvancePayDays", carAdvancePayDays);
        params.put("carPayCycle", carPayCycle);
        params.put("carFeeLateRatio", carFeeLateRatio);
        params.put("energyRentDateRange", energyRentDateRange);
        params.put("energyUnitPrice", energyUnitPrice);
        params.put("energyDayPrice", energyDayPrice);
        params.put("energyMonthPrice", energyMonthPrice);
        params.put("energyAdvancePayDays", energyAdvancePayDays);
        params.put("energyPayCycle", energyPayCycle);
        params.put("energyFeeLateRatio", energyFeeLateRatio);
        exporter.export(params);
        exporter.generate(generatePath + contractCode + ".docx");
        map.put("path", "/company/" + companyCode + "/" + contractCode + ".docx");
        map.put("name", contractCode + ".docx");
        return map;
    }

    /**
     * @Description: 导入数据
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Transactional
    public Map importContractInfo(MultipartFile file) throws Exception {
        Date currentDate = new Date();
        BigDecimal initAmt = new BigDecimal("0");
        User user = CacheUtils.getUser();
        Integer userId = user.getUserId();
        String companyCode = user.getCompanyCode();
        String today = DateTimeUtil.getFormatDate();
        Map map = Maps.newHashMap();
        Workbook wb = ImportExcelUtil.getWorkbok(file);
        if (wb != null) {
            Customer customer = new Customer();
            customer.setCompanyCode(companyCode);
            List<Customer> customerList = customerMapper.queryCustomerList(customer);
            List<Contract> contractList = Lists.newArrayList();
            List<ContractRoom> roomList = Lists.newArrayList();
            List<ContractCarplace> carplaceList = Lists.newArrayList();
            List<ContractIncreaseRate> increaseRateList = Lists.newArrayList();
            List<ContractFreeTime> freeTimeList = Lists.newArrayList();
            List<ContractRent> contractRentList = Lists.newArrayList();

            // 第一个sheet（合同基础信息）
            Sheet sheet_1 = wb.getSheetAt(0);
            //获取最后一行数据所在行数（从0开始）
            int rownum = sheet_1.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                Contract contract = new Contract();
                Row row = sheet_1.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                int count = contractMapper.queryCheckContractCode(contractCode);
                if (count > 0) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "合同基础信息：第" + (i + 1) + "行，合同编号在系统中已经存在");
                    return map;
                }
                String customerType = (String) ImportExcelUtil.getCellFormatValue(row.getCell(1));
                String customerName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(2));
                Date date = row.getCell(3).getDateCellValue();
                String signDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                date = row.getCell(4).getDateCellValue();
                String startDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                date = row.getCell(5).getDateCellValue();
                String endDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                String unitPrice = (String) ImportExcelUtil.getCellFormatValue(row.getCell(6));
                String chargeUnit = (String) ImportExcelUtil.getCellFormatValue(row.getCell(7));
                String monthCountType = (String) ImportExcelUtil.getCellFormatValue(row.getCell(8));
                String advancePayDays = (String) ImportExcelUtil.getCellFormatValue(row.getCell(9));
                String advancePayType = (String) ImportExcelUtil.getCellFormatValue(row.getCell(10));
                String payCycle = (String) ImportExcelUtil.getCellFormatValue(row.getCell(11));
                String feeLateRatio = (String) ImportExcelUtil.getCellFormatValue(row.getCell(12));
                String deposit = (String) ImportExcelUtil.getCellFormatValue(row.getCell(13));
                String wyDeposit = (String) ImportExcelUtil.getCellFormatValue(row.getCell(14));
                String breachAmt = (String) ImportExcelUtil.getCellFormatValue(row.getCell(15));
                String remarks = (String) ImportExcelUtil.getCellFormatValue(row.getCell(16));
                String followPerson = (String) ImportExcelUtil.getCellFormatValue(row.getCell(17));

                contract.setContractCode(contractCode);
                contract.setVersion(1); // 初始版本
                if ("公司".equals(customerType)) {
                    contract.setCustomerType("1");
                } else {
                    contract.setCustomerType("2");
                }

                Integer customerId = 0;
                if (StringUtils.isNotEmpty(customerName)) {
                    contract.setCustomerName(customerName);
                    for (int n = 0; n < customerList.size(); n++) {
                        customer = customerList.get(n);
                        if (customerName.trim().equals(customer.getCustomerName())) {
                            customerId = customer.getCustomerId();
                            break;
                        }
                    }
                    if (customerId == 0) {
                        customer = new Customer();
                        customer.setCustomerType(contract.getCustomerType());
                        customer.setCustomerName(customerName);
                        customer.setCompanyCode(companyCode);
                        customer.setState(Constants.CUSTOMER_STATE_NOW);
                        customer.setCreateTime(currentDate);
                        customer.setUpdateTime(currentDate);
                        customer.setCreateUserId(user.getUserId());
                        customer.setUpdateUserId(user.getUserId());
                        customerMapper.insertCustomer(customer);
                        customerId = customer.getCustomerId();
                    }
                    contract.setCustomerId(customerId);
                } else {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "合同基础信息：第" + (i + 1) + "行，客户名称不能为空");
                    return map;
                }

                contract.setSignDate(signDate);
                contract.setStartDate(startDate);
                contract.setEndDate(endDate);
                if (StringUtils.isNotEmpty(unitPrice)) {
                    contract.setUnitPrice(new BigDecimal(unitPrice));
                } else {
                    contract.setUnitPrice(initAmt);
                }

                if ("元/㎡·天".equals(chargeUnit)) {
                    contract.setChargeUnit("1");
                } else if ("元/㎡·月".equals(chargeUnit)) {
                    contract.setChargeUnit("2");
                } else if ("元/天".equals(chargeUnit)) {
                    contract.setChargeUnit("3");
                } else if ("元/月".equals(chargeUnit)) {
                    contract.setChargeUnit("4");
                } else {
                    contract.setChargeUnit("5");
                }

                if ("按固定租金".equals(monthCountType)) {
                    contract.setMonthCountType("1");
                } else {
                    contract.setMonthCountType("2");
                }

                if (StringUtils.isNotEmpty(advancePayDays)) {
                    contract.setAdvancePayDays((int) Double.parseDouble(advancePayDays));
                } else {
                    contract.setAdvancePayDays(0);
                }

                if ("自然日".equals(advancePayType)) {
                    contract.setAdvancePayType("1");
                } else if ("工作日".equals(advancePayType)) {
                    contract.setAdvancePayType("2");
                } else if ("固定日期".equals(advancePayType)) {
                    contract.setAdvancePayType("3");
                } else {
                    contract.setAdvancePayType("4");
                }

                if (StringUtils.isNotEmpty(payCycle)) {
                    contract.setPayCycle((int) Double.parseDouble(payCycle));
                } else {
                    contract.setPayCycle(0);
                }

                if (StringUtils.isNotEmpty(feeLateRatio)) {
                    contract.setFeeLateRatio(Double.parseDouble(feeLateRatio));
                } else {
                    contract.setFeeLateRatio(0d);
                }

                if (StringUtils.isNotEmpty(deposit)) {
                    contract.setDeposit(new BigDecimal(deposit));
                }
                if (StringUtils.isNotEmpty(wyDeposit)) {
                    contract.setWyDeposit(new BigDecimal(wyDeposit));
                }
                if (StringUtils.isNotEmpty(breachAmt)) {
                    contract.setBreachAmt(new BigDecimal(breachAmt));
                }
                contract.setRemarks(remarks);
                contract.setFollowPerson(followPerson);
                contractList.add(contract);
            }

            // 第二个sheet(房源信息)
            Sheet sheet_2 = wb.getSheetAt(1);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_2.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                ContractRoom contractRoom = new ContractRoom();
                Row row = sheet_2.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                String projectId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(1));
                String projectName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(2));
                String buildingId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(3));
                String buildingName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(4));
                String floorId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(5));
                String floorNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(6));
                String roomId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(7));
                String roomNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(8));
                String buildArea = (String) ImportExcelUtil.getCellFormatValue(row.getCell(9));
                contractRoom.setContractCode(contractCode);
                contractRoom.setVersion(1);
                contractRoom.setProjectId((int) Double.parseDouble(projectId));
                contractRoom.setProjectName(projectName);
                contractRoom.setBuildingId((int) Double.parseDouble(buildingId));
                contractRoom.setBuildingName(buildingName);
                contractRoom.setFloorId((int) Double.parseDouble(floorId));
                contractRoom.setFloorNo(floorNo);
                contractRoom.setRoomId((int) Double.parseDouble(roomId));
                contractRoom.setRoomNo(roomNo);
                contractRoom.setBuildArea(Double.parseDouble(buildArea));
                roomList.add(contractRoom);
            }

            // 第三个sheet(车位信息)
            Sheet sheet_3 = wb.getSheetAt(2);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_3.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                ContractCarplace carplace = new ContractCarplace();
                Row row = sheet_3.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                String projectId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(1));
                String projectName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(2));
                String buildingId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(3));
                String buildingName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(4));
                String floorId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(5));
                String floorNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(6));
                String placeId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(7));
                String placeNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(8));
                carplace.setContractCode(contractCode);
                carplace.setVersion(1);
                carplace.setProjectId((int) Double.parseDouble(projectId));
                carplace.setProjectName(projectName);
                carplace.setBuildingId((int) Double.parseDouble(buildingId));
                carplace.setBuildingName(buildingName);
                if (floorId != null && StringUtils.isNotEmpty(floorId)) {
                    carplace.setFloorId((int) Double.parseDouble(floorId));
                } else {
                    carplace.setFloorId(0);
                }
                carplace.setFloorNo(floorNo);
                carplace.setPlaceId((int) Double.parseDouble(placeId));
                carplace.setPlaceNo(placeNo);
                carplaceList.add(carplace);
            }

            // 第四个sheet(递增率)
            Sheet sheet_4 = wb.getSheetAt(3);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_4.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                ContractIncreaseRate contractIncreaseRate = new ContractIncreaseRate();
                Row row = sheet_4.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                Date date = row.getCell(1).getDateCellValue();
                String increaseDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                String increaseRate = (String) ImportExcelUtil.getCellFormatValue(row.getCell(2));
                contractIncreaseRate.setContractCode(contractCode);
                contractIncreaseRate.setVersion(1);
                contractIncreaseRate.setIncreaseMonth("0");
                contractIncreaseRate.setIncreaseDate(increaseDate);
                if (StringUtils.isNotEmpty(increaseDate)) {
                    contractIncreaseRate.setIncreaseRate(Double.parseDouble(increaseRate));
                } else {
                    contractIncreaseRate.setIncreaseRate(0d);
                }
                increaseRateList.add(contractIncreaseRate);
            }

            // 第五个sheet(免租期)
            Sheet sheet_5 = wb.getSheetAt(4);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_5.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                ContractFreeTime freeTime = new ContractFreeTime();
                Row row = sheet_5.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                Date date = row.getCell(1).getDateCellValue();
                String freeStartDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                date = row.getCell(2).getDateCellValue();
                String freeEndDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                freeTime.setContractCode(contractCode);
                freeTime.setVersion(1);
                freeTime.setFreeStartDate(freeStartDate);
                freeTime.setFreeEndDate(freeEndDate);
                freeTimeList.add(freeTime);
            }

            // 第六个sheet（周期性费用）
            Sheet sheet_6 = wb.getSheetAt(5);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_6.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                ContractRent contractRent = new ContractRent();
                Row row = sheet_6.getRow(i);
                String contractCode = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                if (StringUtils.isEmpty(contractCode) || contractCode.contains("示例")) continue;
                String costName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(1));
                Date date = row.getCell(2).getDateCellValue();
                String startDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                date = row.getCell(3).getDateCellValue();
                String endDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                String unitPrice = (String) ImportExcelUtil.getCellFormatValue(row.getCell(4));
                String chargeUnit = (String) ImportExcelUtil.getCellFormatValue(row.getCell(5));
                String monthCountType = (String) ImportExcelUtil.getCellFormatValue(row.getCell(6));
                String advancePayDays = (String) ImportExcelUtil.getCellFormatValue(row.getCell(7));
                String advancePayType = (String) ImportExcelUtil.getCellFormatValue(row.getCell(8));
                String payCycle = (String) ImportExcelUtil.getCellFormatValue(row.getCell(9));
                String feeLateRatio = (String) ImportExcelUtil.getCellFormatValue(row.getCell(10));

                contractRent.setContractCode(contractCode);
                contractRent.setVersion(1);
                contractRent.setCostName(costName);
                contractRent.setStartDate(startDate);
                contractRent.setEndDate(endDate);
                if (StringUtils.isNotEmpty(unitPrice)) {
                    contractRent.setUnitPrice(new BigDecimal(unitPrice));
                } else {
                    contractRent.setUnitPrice(initAmt);
                }

                if ("元/㎡·天".equals(chargeUnit)) {
                    contractRent.setChargeUnit("1");
                } else if ("元/㎡·月".equals(chargeUnit)) {
                    contractRent.setChargeUnit("2");
                } else if ("元/天".equals(chargeUnit)) {
                    contractRent.setChargeUnit("3");
                } else if ("元/月".equals(chargeUnit)) {
                    contractRent.setChargeUnit("4");
                } else {
                    contractRent.setChargeUnit("5");
                }

                if ("按固定租金".equals(monthCountType)) {
                    contractRent.setMonthCountType("1");
                } else {
                    contractRent.setMonthCountType("2");
                }

                if (StringUtils.isNotEmpty(advancePayDays)) {
                    contractRent.setAdvancePayDays((int) Double.parseDouble(advancePayDays));
                } else {
                    contractRent.setAdvancePayDays(0);
                }

                if ("自然日".equals(advancePayType)) {
                    contractRent.setAdvancePayType("1");
                } else if ("工作日".equals(advancePayType)) {
                    contractRent.setAdvancePayType("2");
                } else if ("固定日期".equals(advancePayType)) {
                    contractRent.setAdvancePayType("3");
                } else {
                    contractRent.setAdvancePayType("4");
                }

                if (StringUtils.isNotEmpty(payCycle)) {
                    contractRent.setPayCycle((int) Double.parseDouble(payCycle));
                } else {
                    contractRent.setPayCycle(0);
                }

                if (StringUtils.isNotEmpty(feeLateRatio)) {
                    contractRent.setFeeLateRatio(Double.parseDouble(feeLateRatio));
                } else {
                    contractRent.setFeeLateRatio(0d);
                }
                contractRentList.add(contractRent);
            }

            for (Contract contract : contractList) {
                String contractCode = contract.getContractCode();
                String startDate = contract.getStartDate();
                String endDate = contract.getEndDate();
                BigDecimal unitPrice = contract.getUnitPrice();
                String chargeUnit = contract.getChargeUnit();
                String monthCountType = contract.getMonthCountType();
                Integer advancePayDays = contract.getAdvancePayDays();
                String advancePayType = contract.getAdvancePayType();
                Integer payCycle = contract.getPayCycle();
                Double feeLateRatio = contract.getFeeLateRatio();
                Integer projectId = 0;
                Integer buildingId = 0;
                String roomNos = "";
                double totalArea = 0d;
                List<ContractRoom> rooms = Lists.newArrayList();
                List<ContractCarplace> carplaces = Lists.newArrayList();
                List<ContractIncreaseRate> increaseRates = Lists.newArrayList();
                List<ContractFreeTime> freeTimes = Lists.newArrayList();
                List<ContractRent> contractRents = Lists.newArrayList();
                for (ContractRoom room : roomList) {
                    if (contractCode.equals(room.getContractCode())) {
                        roomNos += room.getRoomNo() + "  ";
                        totalArea += room.getBuildArea();
                        projectId = room.getProjectId();
                        buildingId = room.getBuildingId();
                        room.setStartDate(startDate);
                        room.setEndDate(endDate);
                        room.setCreateTime(currentDate);
                        room.setUpdateTime(currentDate);
                        room.setCreateUserId(userId);
                        room.setUpdateUserId(userId);
                        rooms.add(room);
                    }
                }

                for (ContractCarplace carplace : carplaceList) {
                    if (contractCode.equals(carplace.getContractCode())) {
                        carplace.setCreateTime(currentDate);
                        carplace.setUpdateTime(currentDate);
                        carplace.setCreateUserId(userId);
                        carplace.setUpdateUserId(userId);
                        carplaces.add(carplace);
                    }
                }

                for (ContractIncreaseRate increaseRate : increaseRateList) {
                    if (contractCode.equals(increaseRate.getContractCode())) {
                        increaseRate.setCreateTime(currentDate);
                        increaseRate.setUpdateTime(currentDate);
                        increaseRate.setCreateUserId(userId);
                        increaseRate.setUpdateUserId(userId);
                        increaseRates.add(increaseRate);
                    }
                }

                for (ContractFreeTime freeTime : freeTimeList) {
                    if (contractCode.equals(freeTime.getContractCode())) {
                        freeTime.setFreeDateRange(freeTime.getFreeStartDate() + "~" + freeTime.getFreeEndDate());
                        freeTime.setCreateTime(currentDate);
                        freeTime.setUpdateTime(currentDate);
                        freeTime.setCreateUserId(userId);
                        freeTime.setUpdateUserId(userId);
                        freeTimes.add(freeTime);
                    }
                }

                ContractRent contractRent = new ContractRent(contractCode, 1);
                contractRent.setCostName(Constants.COST_NAME_RENT);
                contractRent.setStartDate(startDate);
                contractRent.setEndDate(endDate);
                contractRent.setRentDateRange(startDate + "~" + endDate);
                contractRent.setUnitPrice(unitPrice);
                contractRent.setChargeUnit(chargeUnit);
                Map priceMap = getDayAndMonthPrice(unitPrice, chargeUnit, monthCountType, totalArea);
                contractRent.setDayPrice((BigDecimal) priceMap.get("dayPrice"));
                contractRent.setMonthCountType(monthCountType);
                contractRent.setMonthPrice((BigDecimal) priceMap.get("monthPrice"));
                contractRent.setAdvancePayDays(advancePayDays);
                contractRent.setAdvancePayType(advancePayType);
                contractRent.setPayCycle(payCycle);
                contractRent.setFeeLateRatio(feeLateRatio);
                contractRent.setCreateTime(currentDate);
                contractRent.setUpdateTime(currentDate);
                contractRent.setCreateUserId(userId);
                contractRent.setUpdateUserId(userId);
                contractRents.add(contractRent);

                for (ContractRent rent : contractRentList) {
                    if (contractCode.equals(rent.getContractCode())) {
                        priceMap = getDayAndMonthPrice(rent.getUnitPrice(), rent.getChargeUnit(), rent.getMonthCountType(), totalArea);
                        rent.setRentDateRange(rent.getStartDate() + "~" + rent.getEndDate());
                        rent.setDayPrice((BigDecimal) priceMap.get("dayPrice"));
                        rent.setMonthPrice((BigDecimal) priceMap.get("monthPrice"));
                        rent.setCreateTime(currentDate);
                        rent.setUpdateTime(currentDate);
                        rent.setCreateUserId(userId);
                        rent.setUpdateUserId(userId);
                        contractRents.add(rent);
                    }
                }

                contract.setProjectId(projectId);
                contract.setBuildingId(buildingId);
                int remainderDays = (int) DateTimeUtil.getIntervalDays(today, endDate);
                contract.setRemainderDays(remainderDays);
                contract.setCompanyCode(companyCode);
                DecimalFormat df = new DecimalFormat("#.0");
                double leaseTerm = Double.parseDouble(DateTimeUtil.getIntervalDays(startDate, endDate) / 365 + "");
                contract.setLeaseTerm(df.format(leaseTerm));
                contract.setContractType(Constants.CONTRACT_TYPE_ROOM);
                contract.setSameCode(CommonUtil.getUniqueCode());
                contract.setRoomNos(roomNos);
                contract.setTotalArea(totalArea);
                contract.setAuditState(Constants.AUDIT_STATE_ING);
                contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
                contract.setStateTwo(Constants.CONTRACT_STATE_TWO_2);
                contract.setOperType(Constants.CONTRACT_OPER_TYPE_NEW);
                contract.setRoomList(rooms);
                contract.setCarplaceList(carplaces);
                contract.setIncreaseRateList(increaseRates);
                contract.setFreeTimeList(freeTimes);
                contract.setRentList(contractRents);
                contract.setCreateTime(currentDate);
                contract.setUpdateTime(currentDate);
                contract.setCreateUserId(userId);
                contract.setUpdateUserId(userId);
            }
            // 生成收款计划，并且出收款账单
            for (int i = 0; i < contractList.size(); i++) {
                Contract contract = contractList.get(i);
                List<ContractRoom> list = contract.getRoomList();
                if (list.size() == 0) continue;
                // 生成收款计划
                generateReceipt(contract);
                // 设置收款计划账单编号，并更新
                ContractReceiptPlan contractReceiptPlan = new ContractReceiptPlan(contract.getContractCode(), contract.getVersion());
                List<ContractReceiptPlan> planList = contractReceiptPlanMapper.queryContractReceiptPlanList(contractReceiptPlan);
                Set<String> receivableDateSet = Sets.newHashSet();
                for (ContractReceiptPlan plan : planList) {
                    receivableDateSet.add(plan.getReceivableDate());
                }
                Iterator<String> iterator = receivableDateSet.iterator();
                while (iterator.hasNext()) {
                    String receivableDate = iterator.next();
                    String billCode = CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_SK_CODE);
                    for (ContractReceiptPlan plan : planList) {
                        if (receivableDate.equals(plan.getReceivableDate())) {
                            plan.setBillCode(billCode);
                        }
                    }
                }
                contractReceiptPlanMapper.updateContractReceiptPlanBatch(planList);
                Bills bills = new Bills(contract.getContractCode(), contract.getVersion());
                // 生成收款实际账单
                billsService.generateBills(bills);
            }
        }
        map.put(Constants.SUCCESS, true);
        map.put(Constants.MSG, "导入成功");
        return map;
    }

    /**
     * 计算日租金和月租金
     *
     * @param unitPrice
     * @param chargeUnit
     * @param conutType
     * @param totalArea
     * @return
     */
    private Map getDayAndMonthPrice(BigDecimal unitPrice, String chargeUnit, String conutType, double totalArea) {
        Map map = Maps.newHashMap();
        BigDecimal dayPrice = new BigDecimal("0");
        BigDecimal monthPrice = new BigDecimal("0");
        switch (chargeUnit) {
            case "1":
                dayPrice = unitPrice.multiply(new BigDecimal(totalArea));
                if ("1".equals(conutType)) {
                    monthPrice = dayPrice.multiply(new BigDecimal("365")).divide(new BigDecimal("12"), 6, BigDecimal.ROUND_HALF_UP);
                }
                break;
            case "2":
                monthPrice = unitPrice.multiply(new BigDecimal(totalArea));
                dayPrice = monthPrice.multiply(new BigDecimal("12")).divide(new BigDecimal("365"), 6, BigDecimal.ROUND_HALF_UP);
                break;
            case "3":
                dayPrice = unitPrice;
                if ("1".equals(conutType)) {
                    monthPrice = dayPrice.multiply(new BigDecimal("365")).divide(new BigDecimal("12"), 6, BigDecimal.ROUND_HALF_UP);
                }
                break;
            case "4":
                dayPrice = unitPrice.multiply(new BigDecimal("12")).divide(new BigDecimal("365"), 6, BigDecimal.ROUND_HALF_UP);
                monthPrice = unitPrice;
                break;
            case "5":
                dayPrice = unitPrice.divide(new BigDecimal("365"), 6, BigDecimal.ROUND_HALF_UP);
                monthPrice = unitPrice.divide(new BigDecimal("12"), 6, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                break;
        }
        map.put("dayPrice", dayPrice);
        map.put("monthPrice", monthPrice);
        return map;
    }


    /**
     * @param chargeUnit     租金计价单位
     * @param dayPrice       日租金
     * @param monthCountType 月租金计算方式
     * @param monthPrice     提月租金
     * @param payCycle       付款周期
     * @param startDate      开始时间
     * @param endDate        结束时间
     * @Description: 计算应收款日期
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    private BigDecimal getCostAmt(String chargeUnit, BigDecimal dayPrice, String monthCountType, BigDecimal monthPrice,
                                  Integer payCycle, String startDate, String endDate) {
        BigDecimal costAmt = null;
        if (Constants.CHARGE_UNIT_1.equals(chargeUnit) // 按天计算
                || Constants.CHARGE_UNIT_3.equals(chargeUnit)) {
            if (Constants.MONTH_RENT_COUNT_TYPE_1.equals(monthCountType)) { // 固定租金
                costAmt = new BigDecimal(String.valueOf(payCycle)).multiply(monthPrice);
            } else { // 实际天数
                BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(startDate, endDate)));
                costAmt = dayPrice.multiply(days);
            }
        } else { // 按月计算（默认固定租金）
            costAmt = new BigDecimal(String.valueOf(payCycle)).multiply(monthPrice);
        }
        return costAmt;
    }

    /**
     * @param payDate        付款日
     * @param advancePayDays 提前付款日
     * @param advancePayType 提前付款日类型（1 自然日，2 工作日 ，3 固定日期）
     * @Description: 计算应收金额
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    private String getReceivableDate(String payDate, Integer advancePayDays, String advancePayType) {
        if (Constants.ADVANCE_PAY_TYPE_NATURAL.equals(advancePayType)) { // 自然日
            payDate = DateTimeUtil.getAboutDay(payDate, -advancePayDays, "yyyy-MM-dd");
        } else if (Constants.ADVANCE_PAY_TYPE_WORK.equals(advancePayType)) { // 工作日
            payDate = DateTimeUtil.getAboutDay(payDate, -advancePayDays, "yyyy-MM-dd");
            if (DateTimeUtil.getWeekDay(payDate) == 6) {
                payDate = DateTimeUtil.getAboutDay(payDate, -1, "yyyy-MM-dd");
            }
            if (DateTimeUtil.getWeekDay(payDate) == 0) {
                payDate = DateTimeUtil.getAboutDay(payDate, -2, "yyyy-MM-dd");
            }
        } else if (Constants.ADVANCE_PAY_TYPE_FIXED.equals(advancePayType)) { // 固定日期
            String day = String.valueOf(advancePayDays);
            if (day.length() == 1) day = "0" + day;
            payDate = DateTimeUtil.getAboutMonth(payDate, -1, "yyyy-MM-dd");
            payDate = payDate.substring(0, 8) + day;
        } else {
            payDate = DateTimeUtil.getAboutMonth(payDate, -1, "yyyy-MM-dd");
        }
        return payDate;
    }

    /**
     * 计算每个月免租后金额
     *
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @param freeTimeList 免租期集合
     * @param costAmt      租金总额
     * @param dayPrice     日租金
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    private BigDecimal getFreeAfterAmt(String startDate, String endDate, List<ContractFreeTime> freeTimeList, BigDecimal costAmt, BigDecimal dayPrice) {
        BigDecimal freeAmt = new BigDecimal("0");
        for (int i = 0; i < freeTimeList.size(); i++) {
            ContractFreeTime freeTime = freeTimeList.get(i);
            String freeDateRange = freeTime.getFreeDateRange();
            String freeStartDate = freeDateRange.split("~")[0].trim(); // 免租开始日期
            String freeEndDate = freeDateRange.split("~")[1].trim(); // 免租截止日期
            if (DateTimeUtil.compare_date(freeEndDate, startDate) == 1
                    || DateTimeUtil.compare_date(endDate, freeStartDate) == 1) { // 相离
                continue;
            } else {
                if ((DateTimeUtil.compare_date(freeStartDate, startDate) == 1
                        || DateTimeUtil.compare_date(freeStartDate, startDate) == 0)
                        && (DateTimeUtil.compare_date(endDate, freeEndDate) == 1
                        || DateTimeUtil.compare_date(endDate, freeEndDate) == 0)) { // 免租期包含租期
                    freeAmt = costAmt; // 全免
                    break;
                } else if (DateTimeUtil.compare_date(freeStartDate, startDate) == 1
                        && DateTimeUtil.compare_date(startDate, freeEndDate) == 1) { // 左侧相交
                    BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(startDate, freeEndDate) + 1)); // 免租当天计算在内，所以需要加一天
                    freeAmt = freeAmt.add(dayPrice.multiply(days)); // 免费金额累加
                } else if (DateTimeUtil.compare_date(freeStartDate, endDate) == 1
                        && DateTimeUtil.compare_date(endDate, freeEndDate) == 1) { // 右侧相交
                    BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(freeStartDate, endDate) + 1)); // 免租当天计算在内，所以需要加一天
                    freeAmt = freeAmt.add(dayPrice.multiply(days));  // 免费金额累加
                } else { // 租期包含免租期
                    BigDecimal days = new BigDecimal(Float.toString(DateTimeUtil.getIntervalDays(freeStartDate, freeEndDate) + 1)); // 免租当天计算在内，所以需要加一天
                    freeAmt = freeAmt.add(dayPrice.multiply(days));  // 免费金额累加
                }
            }
        }
        return costAmt.subtract(freeAmt);
    }

    /**
     * 房源协议生成收款单
     *
     * @param contract
     * @return
     */
    private List<Bills> getBills(Contract contract) {
        List<Bills> bills = Lists.newArrayList();
        Date date = new Date(); // 当前时间
        BigDecimal initAmt = new BigDecimal("0");
        String today = DateTimeUtil.getFormatDate();
        String shouldDealDate = contract.getStartDate();
        String billCode = CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_SK_CODE);
        int days = (int) DateTimeUtil.getIntervalDays(today, shouldDealDate);// 剩余/逾期天数
        // 租金
        BigDecimal rentAmt = contract.getRentAmt();
        if (rentAmt != null && rentAmt.compareTo(BigDecimal.ZERO) != 0) {
            Bills bill = new Bills();
            bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 账单类型：收款账单
            bill.setProjectId(contract.getProjectId());
            bill.setCompanyCode(contract.getCompanyCode());
            bill.setContractCode(contract.getContractCode());
            bill.setVersion(contract.getVersion());
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(billCode);
            bill.setFeeType(Constants.COST_NAME_RENT);
            bill.setCostName(Constants.COST_NAME_RENT);
            bill.setCostAmt(rentAmt);
            bill.setStartDate(contract.getStartDate());
            bill.setEndDate(contract.getEndDate());
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(rentAmt);
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(0d);
            bill.setShouldDealDate(shouldDealDate); // 应收时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未收款
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            bills.add(bill);
        }

        // 押金
        BigDecimal deposit = contract.getDeposit();
        if (deposit != null && deposit.compareTo(BigDecimal.ZERO) != 0) {
            Bills bill = new Bills();
            bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 账单类型：收款账单
            bill.setProjectId(contract.getProjectId());
            bill.setCompanyCode(contract.getCompanyCode());
            bill.setContractCode(contract.getContractCode());
            bill.setVersion(contract.getVersion());
            bill.setCustomerId(contract.getCustomerId());
            bill.setBillCode(billCode);
            bill.setFeeType(Constants.COST_NAME_PLEDGE);
            bill.setCostName(Constants.COST_NAME_AGREEMENT_PLEDGE);
            bill.setCostAmt(deposit);
            bill.setStartDate(contract.getStartDate());
            bill.setEndDate(contract.getEndDate());
            bill.setFinishAmt(initAmt);
            bill.setResidualAmt(deposit);
            bill.setFeeLateAmt(initAmt);
            bill.setFeeLateRatio(0d);
            bill.setShouldDealDate(shouldDealDate); // 应收时间
            bill.setDays(days); // 剩余/逾期天数
            bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未收款
            bill.setCreateTime(date);
            bill.setUpdateTime(date);
            bill.setCreateUserId(0);
            bill.setUpdateUserId(0);
            bills.add(bill);
        }
        return bills;
    }


}
