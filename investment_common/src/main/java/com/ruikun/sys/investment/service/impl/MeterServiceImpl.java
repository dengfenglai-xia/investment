package com.ruikun.sys.investment.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.utils.CommonUtil;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import com.ruikun.sys.investment.utils.ImportExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.service.MeterService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MeterServiceImpl implements MeterService {

    @Autowired
    private MeterMapper meterMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private BillsMapper billsMapper;

    /**
     * @Description: 查询抄表数据信息
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public List<Meter> queryMeterList(Meter meter) {
        return meterMapper.queryMeterList(meter);
    }

    /**
     * @Description: 通过主键查询抄表数据信息详情
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public Meter queryMeterDetailByPrimarykey(Integer id) {
        return meterMapper.queryMeterDetailByPrimarykey(id);
    }

    /**
     * @Description: 查询抄表数据信息详情
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public Meter queryMeterDetail(Meter meter) {
        return meterMapper.queryMeterDetail(meter);
    }

    /**
     * @Description: 新增抄表数据信息
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public void insertMeter(Meter meter) {
        Date date = new Date(); // 当前时间
        Integer userId = CacheUtils.getUser().getUserId();
        meter.setCreateUserId(userId); // 创建者ID
        meter.setUpdateUserId(userId); // 更新者ID
        meter.setCreateTime(date); // 创建时间
        meter.setUpdateTime(date); // 更新时间
        meterMapper.insertMeter(meter);
    }

    /**
     * @Description: 修改抄表数据信息
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public void updateMeter(Meter meter) {
        Integer userId = CacheUtils.getUser().getUserId();
        meter.setUpdateUserId(userId); //更新者ID
        meter.setUpdateTime(new Date()); //更新时间
        meterMapper.updateMeter(meter);
    }

    /**
     * @Description: 删除抄表数据信息
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    public void deleteMeterByPrimarykey(Integer id) {
        meterMapper.deleteMeterByPrimarykey(id);
    }

    /**
     * @Description: 导入
     * @author: xiachunyu
     * @date: 2019-09-26
     */
    @Override
    @Transactional
    public Map importInfo(Meter meter, MultipartFile file) throws IOException {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String today = DateTimeUtil.getFormatDate(); // 今天日期
        String type = meter.getType();
        String companyCode = meter.getCompanyCode();
        BigDecimal initAmt = new BigDecimal("0");
        Workbook wb = ImportExcelUtil.getWorkbok(file);
        if (wb != null) {
            // 第一个sheet（抄表记录）
            Sheet sheet_1 = wb.getSheetAt(0);
            //获取最后一行数据所在行数（从0开始）
            int rownum = sheet_1.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                Row row = sheet_1.getRow(i);
                String projectId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(0));
                projectId = CommonUtil.trimPoint(projectId.trim());
                String buildingId = (String) ImportExcelUtil.getCellFormatValue(row.getCell(1));
                buildingId = CommonUtil.trimPoint(buildingId.trim());
                String buildingName = (String) ImportExcelUtil.getCellFormatValue(row.getCell(2));
                String roomNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(3));
                if (StringUtils.isEmpty(roomNo)) continue;
                roomNo = CommonUtil.trimPoint(roomNo.trim());
                String meterNo = (String) ImportExcelUtil.getCellFormatValue(row.getCell(4));
                meterNo = CommonUtil.trimPoint(meterNo.trim());
                String initNum = (String) ImportExcelUtil.getCellFormatValue(row.getCell(5));
                String nowNum = (String) ImportExcelUtil.getCellFormatValue(row.getCell(7));
                double useNum = Double.parseDouble(nowNum) - Double.parseDouble(initNum);
                Date date = row.getCell(6).getDateCellValue();
                String startDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                date = row.getCell(8).getDateCellValue();
                String endDate = DateTimeUtil.getFormatTime(date, "yyyy-MM-dd");
                String operator = (String) ImportExcelUtil.getCellFormatValue(row.getCell(9));
                Project project = projectMapper.queryProjectDetailByPrimarykey(Integer.parseInt(projectId));
                BigDecimal price = new BigDecimal("0");
                if ("1".equals(type)) {
                    price = project.getPowerPrice();
                    if (price.compareTo(BigDecimal.ZERO) == 0) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "未设置电费单价，请核实");
                        return map;
                    }
                }
                if ("2".equals(type)) {
                    price = project.getWaterPrice();
                    if (price.compareTo(BigDecimal.ZERO) == 0) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "未设置水费单价，请核实");
                        return map;
                    }
                }
                if ("3".equals(type)) {
                    price = project.getGasPrice();
                    if (price.compareTo(BigDecimal.ZERO) == 0) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "未设置燃气费单价，请核实");
                        return map;
                    }
                }
                BigDecimal costAmt = new BigDecimal(useNum).multiply(price);
                meter = new Meter();
                meter.setType(type);
                meter.setProjectId(Integer.parseInt(projectId));
                meter.setProjectName(project.getProjectName());
                meter.setBuildingId(Integer.parseInt(buildingId));
                meter.setBuildingName(buildingName);
                meter.setRoomNo(roomNo);
                Room room = new Room();
                room.setBuildingId(Integer.parseInt(buildingId));
                room.setRoomNo(roomNo);
                List<Room> roomList = roomMapper.queryRoomBaseList(room);
                if (roomList.size() > 0) {
                    room = roomList.get(0);
                    meter.setRoomId(room.getRoomId());
                } else {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "第" + (i + 1 + "列:房间号在系统中不存在，请核实"));
                    return map;
                }
                String roomState = room.getState();
                if ("1".equals(roomState) || "2".equals(roomState)) {
                    Contract contract = new Contract();
                    contract.setBuildingId(Integer.parseInt(buildingId));
                    contract.setRegRoomNo(roomNo);
                    contract = contractMapper.queryContractDetailInfo(contract);
                    if (contract != null) {
                        Bills bill = new Bills();
                        bill.setCompanyCode(companyCode);
                        bill.setContractCode(contract.getContractCode());
                        bill.setVersion(contract.getVersion());
                        bill.setProjectId(Integer.parseInt(projectId));
                        bill.setCustomerId(contract.getCustomerId());
                        bill.setBillCode(CommonUtil.getBusinessCode(Constants.RECEIPT_BILL_SK_CODE));
                        bill.setBillType(Constants.BILL_TYPE_RECEIVABLES); // 付款订单
                        bill.setFeeType("水费");
                        bill.setCostName("水费");
                        bill.setPrice(price);
                        bill.setVolume(new BigDecimal(useNum));
                        bill.setStartDate(startDate);
                        bill.setEndDate(endDate);
                        bill.setCostAmt(costAmt);
                        bill.setFinishAmt(initAmt);
                        bill.setTransferAmt(initAmt);
                        bill.setFreeAmt(initAmt);
                        bill.setFeeLateAmt(initAmt);
                        bill.setFeeLateRatio(0d);
                        bill.setResidualAmt(costAmt);
                        bill.setShouldDealDate(endDate);
                        bill.setDays((int) DateTimeUtil.getIntervalDays(today, endDate)); // 剩余/逾期天数
                        bill.setConfirmState(Constants.BILL_CONFIRM_STATE_NO);  // 状态：未确认
                        bill.setState(Constants.BILL_DEAL_STATE_NO); // 状态：未付款
                        bill.setConfirmer("");
                        bill.setCreateTime(date);
                        bill.setUpdateTime(date);
                        bill.setCreateUserId(user.getUserId());
                        bill.setUpdateUserId(user.getUserId());
                        billsMapper.insertBills(bill);
                    }
                }
                meter.setCompanyCode(companyCode);
                meter.setInitNum(Double.parseDouble(initNum));
                meter.setNowNum(Double.parseDouble(nowNum));
                meter.setUseNum(useNum);
                meter.setStartDate(startDate);
                meter.setEndDate(endDate);
                meter.setMeterNo(meterNo);
                meter.setOperator(operator);
                meter.setCost(costAmt);
                meter.setCreateTime(new Date());
                meter.setUpdateTime(new Date());
                meter.setCreateUserId(user.getUserId());
                meter.setUpdateUserId(user.getUserId());
                meterMapper.insertMeter(meter);
            }
        }
        map.put(Constants.SUCCESS, true);
        map.put(Constants.MSG, "导入成功");
        return map;
    }

}
