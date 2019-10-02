package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.service.BuildingService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.CommonUtil;
import com.ruikun.sys.investment.utils.ImportExcelUtil;
import com.ruikun.sys.investment.utils.RegularUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private FloorMapper floorMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private WorkplaceMapper workplaceMapper;
    @Autowired
    private CarplaceMapper carplaceMapper;

    /**
     * @Description: 查询楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Building> queryBuildingList(Building building, String flag) throws Exception {
        List<Building> buildingList = buildingMapper.queryBuildingList(building);
        if (flag.equals("1")) { // 房源
            double totalArea = 0d;
            int totalNum = 0;
            double enableArea = 0d;
            int enableNum = 0;
            double rentArea = 0d;
            int rentNum = 0;
            double vacantArea = 0d;
            int vacantNum = 0;
            for (Building b : buildingList) {
                totalNum = 0;
                enableNum = 0;
                rentNum = 0;
                vacantNum = 0;

                Room room = new Room();
                room.setBuildingId(b.getBuildingId());
                List<Room> roomList = roomMapper.queryRoomList(room);
                for (Room r : roomList) {
                    totalArea += r.getBuildArea();
                    totalNum++;
                    String state = r.getState();
                    if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                        enableArea += r.getBuildArea();
                        enableNum++;
                        if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                                || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                            rentArea += r.getBuildArea();
                            rentNum++;
                        }
                        if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                            vacantArea += r.getBuildArea();
                            vacantNum++;
                        }
                    }
                }
                Contract contract = new Contract();
                contract.setBuildingId(room.getBuildingId());
                contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
                // 合同数量
                int contractNum = contractMapper.queryContractCount(contract);
                // 客户数量
                int customerNum = contractMapper.queryCustomerCount(contract);
                // 计算在租均价
                BigDecimal sumDayPrice = contractMapper.querySumDayPrice(contract);
                Double sumArea = contractMapper.querySumArea(contract);
                BigDecimal avgPrice = new BigDecimal("0");
                if (sumArea != 0) {
                    avgPrice = sumDayPrice.divide(new BigDecimal(sumArea), 2, RoundingMode.HALF_UP);
                }
                b.setTotalNum(totalNum);
                b.setEnableNum(enableNum);
                b.setRentNum(rentNum);
                b.setVacantNum(vacantNum);
                b.setAvgPrice(avgPrice);
                b.setContractNum(contractNum);
                b.setCustomerNum(customerNum);
            }
        } else if (flag.equals("2")) { // 工位
            for (Building b : buildingList) {
                int totalNum = 0;
                int enableNum = 0;
                int rentNum = 0;
                int vacantNum = 0;
                Workplace workPlace = new Workplace();
                workPlace.setBuildingId(b.getBuildingId());
                List<Workplace> workplaceList = workplaceMapper.queryWorkplaceList(workPlace);
                for (Workplace w : workplaceList) {
                    totalNum++;
                    String state = w.getState();
                    if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                        enableNum++;
                        if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                                || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                            rentNum++;
                        }
                        if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                            vacantNum++;
                        }
                    }
                }
                b.setTotalNum(totalNum);
                b.setEnableNum(enableNum);
                b.setRentNum(rentNum);
                b.setVacantNum(vacantNum);
            }
        } else if (flag.equals("3")) { // 车位
            for (Building b : buildingList) {
                int totalNum = 0;
                int enableNum = 0;
                int rentNum = 0;
                int vacantNum = 0;
                Carplace carplace = new Carplace();
                carplace.setBuildingId(b.getBuildingId());
                List<Carplace> carplaceList = carplaceMapper.queryCarplaceList(carplace);
                for (Carplace c : carplaceList) {
                    totalNum++;
                    String state = c.getState();
                    if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                        enableNum++;
                        if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                                || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                            rentNum++;
                        }
                        if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                            vacantNum++;
                        }
                    }
                }
                b.setTotalNum(totalNum);
                b.setEnableNum(enableNum);
                b.setRentNum(rentNum);
                b.setVacantNum(vacantNum);
            }
        } else {
            throw new Exception("参数错误");
        }
        return buildingList;
    }

    @Override
    public PlaceStatistics queryRoomStatistics(Room room) {
        double totalArea = 0d;
        int totalNum = 0;
        double enableArea = 0d;
        int enableNum = 0;
        double rentArea = 0d;
        int rentNum = 0;
        double vacantArea = 0d;
        int vacantNum = 0;
        PlaceStatistics placeStatistics = new PlaceStatistics();
        List<Room> roomList = roomMapper.queryRoomList(room);
        for (Room r : roomList) {
            totalArea += r.getBuildArea();
            totalNum++;
            String state = r.getState();
            if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                enableArea += r.getBuildArea();
                enableNum++;
                if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                        || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                    rentArea += r.getBuildArea();
                    rentNum++;
                }
                if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                    vacantArea += r.getBuildArea();
                    vacantNum++;
                }
            }
        }
        Contract contract = new Contract();
        contract.setCompanyCode(room.getCompanyCode());
        contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
        // 合同数量
        int contractNum = contractMapper.queryContractCount(contract);
        // 客户数量
        int customerNum = contractMapper.queryCustomerCount(contract);
        // 计算在租均价
        BigDecimal sumDayPrice = contractMapper.querySumDayPrice(contract);
        Double sumArea = contractMapper.querySumArea(contract);
        BigDecimal avgPrice = new BigDecimal("0");
        if (sumArea != 0) {
            avgPrice = sumDayPrice.divide(new BigDecimal(sumArea), 2, RoundingMode.HALF_UP);
        }
        placeStatistics.setTotalArea(totalArea);
        placeStatistics.setTotalNum(totalNum);
        placeStatistics.setEnableArea(enableArea);
        placeStatistics.setEnableNum(enableNum);
        placeStatistics.setRentArea(rentArea);
        placeStatistics.setRentNum(rentNum);
        placeStatistics.setVacantArea(vacantArea);
        placeStatistics.setVacantNum(vacantNum);
        if (enableArea == 0) {
            placeStatistics.setRentRate(0d);
        } else {
            placeStatistics.setRentRate(rentArea / enableArea * 100);
        }
        placeStatistics.setContractNum(contractNum);
        placeStatistics.setCustomerNum(customerNum);
        placeStatistics.setAvgPrice(avgPrice);
        return placeStatistics;
    }

    @Override
    public PlaceStatistics queryWorkplaceStatistics(Workplace workPlace) {
        int totalNum = 0;
        int enableNum = 0;
        int rentNum = 0;
        int vacantNum = 0;
        PlaceStatistics placeStatistics = new PlaceStatistics();
        List<Workplace> workplaceList = workplaceMapper.queryWorkplaceList(workPlace);
        for (Workplace w : workplaceList) {
            totalNum++;
            String state = w.getState();
            if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                enableNum++;
                if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                        || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                    rentNum++;
                }
                if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                    vacantNum++;
                }
            }
        }
        placeStatistics.setTotalNum(totalNum);
        placeStatistics.setEnableNum(enableNum);
        placeStatistics.setRentNum(rentNum);
        placeStatistics.setVacantNum(vacantNum);
        return placeStatistics;
    }

    @Override
    public PlaceStatistics queryCarplaceStatistics(Carplace carplace) {
        int totalNum = 0;
        int enableNum = 0;
        int rentNum = 0;
        int vacantNum = 0;
        PlaceStatistics placeStatistics = new PlaceStatistics();
        List<Carplace> carplaceList = carplaceMapper.queryCarplaceList(carplace);
        for (Carplace c : carplaceList) {
            totalNum++;
            String state = c.getState();
            if (!Constants.RENTOUT_STATE_INIT.equals(state)) {
                enableNum++;
                if (Constants.RENTOUT_STATE_RENT_ING.equals(state)
                        || Constants.RENTOUT_STATE_SOON_EXPIRE.equals(state)) {
                    rentNum++;
                }
                if (Constants.RENTOUT_STATE_VACANT_ING.equals(state)) {
                    vacantNum++;
                }
            }
        }
        placeStatistics.setTotalNum(totalNum);
        placeStatistics.setEnableNum(enableNum);
        placeStatistics.setRentNum(rentNum);
        placeStatistics.setVacantNum(vacantNum);
        return placeStatistics;
    }

    @Override
    public List<Building> queryBuildingBaseList(Building building) {
        return buildingMapper.queryBuildingBaseList(building);
    }

    /**
     * @Description: 查询楼宇数量
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public int queryBuildingCount(Building building) {
        return buildingMapper.queryBuildingCount(building);
    }

    /**
     * @Description: 通过主键查询楼宇表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Building queryBuildingDetailByPrimarykey(Integer buildingId) {
        return buildingMapper.queryBuildingDetailByPrimarykey(buildingId);
    }

    /**
     * @Description: 查询楼宇表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Building queryBuildingDetail(Building building) {
        return buildingMapper.queryBuildingDetail(building);
    }

    /**
     * @Description: 新增楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Transactional
    public void insertBuilding(Building building) {
        // 插入楼宇信息
        Date date = new Date(); // 当前时间
        building.setCreateTime(date); // 创建时间
        building.setUpdateTime(date); // 更新时间
        buildingMapper.insertBuilding(building);
        // 批量插入楼层信息
        List<Floor> floorList = building.getFloorList();
        if (floorList != null) {
            for (Floor f : floorList) {
                f.setBuildingId(building.getBuildingId());
                f.setBuildingName(building.getBuildingName());
                f.setProjectId(building.getProjectId());
                f.setProjectName(building.getProjectName());
                f.setCreateTime(date);
                f.setUpdateTime(date);
                f.setCreateUserId(building.getCreateUserId());
                f.setUpdateUserId(building.getUpdateUserId());
            }
            floorMapper.insertFloorBatch(floorList);
        }

    }

    /**
     * @Description: 修改楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateBuilding(Building building) {
        Integer userId = CacheUtils.getUser().getUserId();
        building.setUpdateUserId(userId); //更新者ID
        building.setUpdateTime(new Date()); //更新时间
        buildingMapper.updateBuilding(building);
    }

    /**
     * @Description: 删除楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteBuildingByPrimarykey(Integer buildingId) {
        buildingMapper.deleteBuildingByPrimarykey(buildingId);
    }

    /**
     * @Description: 导入数据
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Transactional
    public Map importBuildingInfo(Building building, MultipartFile file) throws IOException {
        Integer buildingId = building.getBuildingId();
        Integer userId = building.getCreateUserId();
        Map map = Maps.newHashMap();
        Workbook wb = ImportExcelUtil.getWorkbok(file);
        if (wb != null) {
            // 第一个sheet
            Sheet sheet_1 = wb.getSheetAt(0);
            // 房源信息表头标识
            boolean title1 = false;
            //获取最后一行数据所在行数（从0开始）
            int rownum = sheet_1.getLastRowNum();
            // 数据起始行
            int startRow = 0;
            // 数据起始列
            int startColnum = 0;
            // 移动列
            int moveColnum = 0;
            Cell cell = null;
            String cellData = null;
            if (rownum != 0) {
                // 计算业务数据起始位置
                outer:
                for (int i = 0; i <= rownum; i++) {
                    Row row = sheet_1.getRow(i);
                    if (row == null) continue;
                    int colnum = row.getLastCellNum();
                    for (int j = 0; j < colnum; j++) {
                        cell = row.getCell(j);
                        cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                        // 确定数据其实行和起始列,跳出最外层循环
                        if (cellData.trim().equals("楼层")) {
                            startRow = i;
                            startColnum = j;
                            title1 = true;
                            break outer;
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "房源信息不能为空");
                return map;
            }
            // 校验表头是否被修改
            if (title1 == false) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "房源信息：请勿修改表头信息");
                return map;
            }

            // 查询楼宇详情
            building = buildingMapper.queryBuildingDetailByPrimarykey(buildingId);
            // 查询楼层信息
            List<String> floorNos = floorMapper.queryFloorNoList(new Floor(buildingId));
            // 查询房间信息
            List<String> roomNos = roomMapper.queryRoomNoList(new Room(buildingId));
            // 楼层集合
            HashSet hashFloorNos = Sets.newHashSet();
            List<Room> roomList = Lists.newArrayList();
            Date date = new Date();
            // 读取业务数据
            for (int i = startRow + 1; i <= rownum; i++) {
                Room r = new Room();
                r.setCompanyCode(building.getCompanyCode());
                r.setBuildingId(buildingId);
                r.setBuildingName(building.getBuildingName());
                r.setProjectId(building.getProjectId());
                r.setProjectName(building.getProjectName());
                r.setCreateTime(date);
                r.setUpdateTime(date);
                r.setCreateUserId(userId);
                r.setUpdateUserId(userId);
                //读取并设置房源信息
                Row row = sheet_1.getRow(i);
                if (row == null) continue;
                moveColnum = startColnum;
                // 楼层
                cell = row.getCell(moveColnum++);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                cellData = (int) Double.parseDouble(cellData) + "";
                if (StringUtils.isEmpty(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，楼层不能为空");
                    return map;
                } else {
                    if (!RegularUtil.isNumeric(cellData)) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，楼层数据格式不正确");
                        return map;
                    }
                }
                // 将数据库中不存在的楼层号，存入集合
                if (!floorNos.contains(cellData.trim())) {
                    hashFloorNos.add(cellData.trim());
                }
                r.setFloorNo(cellData.trim());

                // 房间号
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                cellData = (int) Double.parseDouble(cellData) + "";
                if (StringUtils.isEmpty(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，房间号不能为空");
                    return map;
                } else {
                    if (!RegularUtil.isNumeric(cellData)) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，房间号数据格式不正确");
                        return map;
                    }
                    if (roomNos.contains(cellData)) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，房间号在系统中已存在");
                        return map;
                    }
                }
                r.setRoomNo(cellData.trim());

                // 使用面积
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isNotEmpty(cellData)
                        && !RegularUtil.isTwoPoints(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，使用面积数据格式不正确");
                    return map;
                }
                r.setUseArea(new Double(cellData));

                // 建筑面积
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isNotEmpty(cellData)
                        && !RegularUtil.isTwoPoints(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，建筑面积数据格式不正确");
                    return map;
                }
                r.setBuildArea(new Double(cellData));

                // 用途
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                r.setPurpose(cellData);

                // 是否对外招租
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isEmpty(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，是否对外招租不能为空");
                    return map;
                } else {
                    if (!Constants.WHETHER_OPEN_MAP.containsValue(cellData)) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，是否对外招租数据格式不正确");
                        return map;
                    }
                }
                if ("是".equals(cellData)) {
                    r.setWhetherOpen(Constants.WHETHER_OPEN_YES);
                    r.setState(Constants.RENTOUT_STATE_VACANT_ING);
                } else {
                    r.setWhetherOpen(Constants.WHETHER_OPEN_NO);
                    r.setState(Constants.RENTOUT_STATE_INIT);
                }

                // 租金底价
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (cellData != null && StringUtils.isNotEmpty(cellData)) {
                    if (RegularUtil.isTwoPoints(cellData)) {
                        r.setBottomPrice(new BigDecimal(cellData));
                    } else {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，租金底价数据格式不正确");
                        return map;
                    }
                } else {
                    r.setBottomPrice(new BigDecimal("0"));
                }

                // 底价单位
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isNotEmpty(cellData)) {
                    if ("元/㎡·天".equals(cellData)) {
                        r.setChargeUnit("1");
                    } else if ("元/㎡·月".equals(cellData)) {
                        r.setChargeUnit("2");
                    } else if ("元/天".equals(cellData)) {
                        r.setChargeUnit("3");
                    } else if ("元/月".equals(cellData)) {
                        r.setChargeUnit("4");
                    }else {
                        r.setChargeUnit("5");
                    }
                }else{
                    r.setChargeUnit("1");
                }

                // 装修
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isNotEmpty(cellData)
                        && !Constants.ROOM_DECORATE_MAP.containsValue(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，装修数据格式不正确");
                    return map;
                }
                if (Constants.ROOM_DECORATE_MAP.get(Constants.ROOM_DECORATE_NOTHING).equals(cellData)) {
                    r.setDecorate(Constants.ROOM_DECORATE_NOTHING);
                } else if (Constants.ROOM_DECORATE_MAP.get(Constants.ROOM_DECORATE_SIMPLE).equals(cellData)) {
                    r.setDecorate(Constants.ROOM_DECORATE_SIMPLE);
                } else {
                    r.setDecorate(Constants.ROOM_DECORATE_SENIOR);
                }

                // 朝向
                /*cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                r.setOrientation(cellData);*/

                // 是否临街
                cell = row.getCell(moveColnum++);
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (StringUtils.isNotEmpty(cellData)
                        && !Constants.ROOM_WHETHER_STREET_MAP.containsValue(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "房源信息：第" + (i + 1) + "行，是否临街数据格式不正确");
                    return map;
                }
                if (Constants.ROOM_WHETHER_STREET_MAP.get(Constants.ROOM_WHETHER_STREET_YES).equals(cellData)) {
                    r.setWhetherStreet(Constants.ROOM_WHETHER_STREET_YES);
                } else {
                    r.setWhetherStreet(Constants.ROOM_WHETHER_STREET_NO);
                }

                // 配套设施
//                cell = row.getCell(moveColnum++);
//                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
//                if (StringUtils.isNotEmpty(cellData)) {
//                    r.setFacilities(cellData.replaceAll("\\s*", "").replace("，", ","));
//                }

                // 房源标签
//                cell = row.getCell(moveColnum);
//                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
//                if (StringUtils.isNotEmpty(cellData)) {
//                    r.setTags(cellData.replaceAll("\\s*", "").replace("，", ","));
//                }
                roomList.add(r); //存入集合
            }

            // 插入楼层信息
            List<Floor> floorList = Lists.newArrayList();
            Iterator it = hashFloorNos.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                Floor f = new Floor();
                f.setBuildingId(buildingId);
                f.setBuildingName(building.getBuildingName());
                f.setProjectId(building.getProjectId());
                f.setProjectName(building.getProjectName());
                f.setFloorNo(obj.toString());
                f.setFloorName(obj.toString() + '层');
                f.setCreateTime(date);
                f.setUpdateTime(date);
                f.setCreateUserId(userId);
                f.setUpdateUserId(userId);
                floorList.add(f);
            }
            if (floorList.size() > 0) {
                floorMapper.insertFloorBatch(floorList);
            }
            floorList = floorMapper.queryFloorBaseList(new Floor(buildingId));
            // 构建楼层号（key） - 楼层ID（value）形式的map
            Map floorMap = Maps.newHashMap();
            for (Floor floor : floorList) {
                floorMap.put(floor.getFloorNo(), floor.getFloorId());
            }
            // 设置楼层ID,并插入数据库
            for (Room room : roomList) {
                room.setFloorId((Integer) floorMap.get(room.getFloorNo()));
                roomMapper.insertRoom(room);
            }

            roomList = roomMapper.queryRoomBaseList(new Room(buildingId));
            // 构建房间号（key） -{房间ID , 楼层ID , 楼层号}（value）形式的map
            Map roomMap = Maps.newHashMap();
            for (Room room : roomList) {
                String[] info = {room.getRoomId() + "", room.getFloorId() + "", room.getFloorNo()};
                roomMap.put(room.getRoomNo(), info);
            }

            // 第二个sheet
            Sheet sheet_2 = wb.getSheetAt(1);
            // 工位信息表头标识
            boolean title2 = false;
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_2.getLastRowNum();
            // 数据起始行
            startRow = 0;
            // 数据起始列
            startColnum = 0;
            if (rownum != 0) {
                // 计算业务数据起始位置
                outer:
                for (int i = 0; i <= rownum; i++) {
                    Row row = sheet_2.getRow(i);
                    if (row == null) continue;
                    int colnum = row.getLastCellNum();
                    for (int j = 0; j < colnum; j++) {
                        cell = row.getCell(j);
                        cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                        // 确定数据其实行和起始列,跳出最外层循环
                        if (cellData.trim().equals("房间号")) {
                            startRow = i;
                            startColnum = j;
                            title2 = true;
                            break outer;
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "工位信息不能为空");
                return map;
            }

            // 校验表头是否被修改
            if (title2 == false) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "工位信息：请勿修改表头信息");
                return map;
            }

            // 读取业务数据
            List<Workplace> placeList = Lists.newArrayList();
            for (int i = startRow + 1; i <= rownum; i++) {
                //读取并设置房源信息
                Row row = sheet_2.getRow(i);
                if (row == null) continue;
                moveColnum = startColnum;
                // 房间号
                cell = row.getCell(moveColnum++);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                cellData = (int) Double.parseDouble(cellData) + "";
                if (StringUtils.isEmpty(cellData)) {
                    map.put(Constants.SUCCESS, false);
                    map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，房间号不能为空");
                    return map;
                } else {
                    if (!RegularUtil.isNumeric(cellData)) {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，房间号数据格式不正确");
                        return map;
                    }
                    if (!roomMap.containsKey(cellData.trim())) { // 数据库中没有对应的房间号，则跳过此条数据
                        continue;
                    } else {
                        Workplace w = new Workplace();
                        String[] value = (String[]) roomMap.get(cellData.trim());
                        w.setCompanyCode(building.getCompanyCode());
                        w.setRoomId(Integer.parseInt(value[0]));
                        w.setRoomNo(cellData);
                        w.setFloorId(Integer.parseInt(value[1]));
                        w.setFloorNo(value[2]);
                        w.setPlaceNo(cellData.trim());
                        w.setBuildingId(buildingId);
                        w.setBuildingName(building.getBuildingName());
                        w.setProjectId(building.getProjectId());
                        w.setProjectName(building.getProjectName());
                        w.setState(Constants.RENTOUT_STATE_VACANT_ING); //默认空置
                        w.setCreateTime(date);
                        w.setUpdateTime(date);
                        w.setCreateUserId(userId);
                        w.setUpdateUserId(userId);

                        // 工位号
                        cell = row.getCell(moveColnum++);
                        cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                        if (StringUtils.isEmpty(cellData)) {
                            map.put(Constants.SUCCESS, false);
                            map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，工位号不能为空");
                            return map;
                        }
                        w.setPlaceNo(CommonUtil.trimPoint(cellData.trim()));

                        // 是否对外招租
                        cell = row.getCell(moveColnum++);
                        cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                        if (StringUtils.isEmpty(cellData)) {
                            map.put(Constants.SUCCESS, false);
                            map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，是否对外招租不能为空");
                            return map;
                        } else {
                            if (!Constants.WHETHER_OPEN_MAP.containsValue(cellData)) {
                                map.put(Constants.SUCCESS, false);
                                map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，是否对外招租数据格式不正确");
                                return map;
                            }
                        }
                        if (Constants.WHETHER_OPEN_MAP.get(Constants.WHETHER_OPEN_YES).equals(cellData)) {
                            w.setWhetherOpen(Constants.WHETHER_OPEN_YES);
                        } else {
                            w.setWhetherOpen(Constants.WHETHER_OPEN_NO);
                        }

                        // 租金底价
                        cell = row.getCell(moveColnum++);
                        cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                        if (cellData != null && StringUtils.isNotEmpty(cellData)) {
                            if (RegularUtil.isTwoPoints(cellData)) {
                                w.setBottomPrice(new BigDecimal(cellData));
                            } else {
                                map.put(Constants.SUCCESS, false);
                                map.put(Constants.MSG, "工位信息：第" + (i + 1) + "行，租金底价数据格式不正确");
                                return map;
                            }
                        } else {
                            w.setBottomPrice(new BigDecimal("0"));
                        }
                        placeList.add(w);
                    }
                }
            }
            // 查询当前楼宇下全部工位数据
            Workplace workplace = new Workplace();
            workplace.setBuildingId(buildingId);
            List<Workplace> db_placeList = workplaceMapper.queryWorkplaceBaseList(workplace);
            // 移除数据库中房间号和工位号相同的项,即不插入重复项
            for (int i = placeList.size() - 1; i >= 0; i--) {
                String roomNo = placeList.get(i).getRoomNo();
                String placeNo = placeList.get(i).getPlaceNo();
                for (int j = 0; j < db_placeList.size(); j++) {
                    String roomNo_db = db_placeList.get(j).getRoomNo();
                    String placeNo_db = db_placeList.get(j).getPlaceNo();
                    if (roomNo.equals(roomNo_db) && placeNo.equals(placeNo_db)) {
                        placeList.remove(i);
                    }
                }
            }
            // 插入工位
            if (placeList.size() > 0) workplaceMapper.insertWorkplaceBatch(placeList);

            // 第三个sheet
            Sheet sheet_3 = wb.getSheetAt(2);
            //获取最后一行数据所在行数（从0开始）
            rownum = sheet_3.getLastRowNum();
            for (int i = 1; i <= rownum; i++) {
                Carplace carplace = new Carplace();
                //读取并设置车位信息
                Row row = sheet_3.getRow(i);
                if (row == null) continue;
                // 楼层
                cell = row.getCell(0);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                carplace.setFloorNo(CommonUtil.trimPoint(cellData));
                // 车位号
                cell = row.getCell(1);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                carplace.setPlaceNo(CommonUtil.trimPoint(cellData));
                // 是否对外招租
                cell = row.getCell(2);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if ("是".equals(cellData)) {
                    carplace.setWhetherOpen(Constants.WHETHER_OPEN_YES);
                    carplace.setState(Constants.RENTOUT_STATE_VACANT_ING);
                } else {
                    carplace.setWhetherOpen(Constants.WHETHER_OPEN_NO);
                    carplace.setState(Constants.RENTOUT_STATE_INIT);
                }
                // 底价
                cell = row.getCell(3);
                if (cell == null) continue;
                cellData = (String) ImportExcelUtil.getCellFormatValue(cell);
                if (cellData != null && StringUtils.isNotEmpty(cellData)) {
                    if (RegularUtil.isTwoPoints(cellData)) {
                        carplace.setBottomPrice(new BigDecimal(cellData));
                    } else {
                        map.put(Constants.SUCCESS, false);
                        map.put(Constants.MSG, "车位信息：第" + (i + 1) + "行，租金底价数据格式不正确");
                        return map;
                    }
                } else {
                    carplace.setBottomPrice(new BigDecimal("0"));
                }

                carplace.setBottomPrice(new BigDecimal(cellData));
                carplace.setCompanyCode(building.getCompanyCode());
                carplace.setBuildingId(buildingId);
                carplace.setBuildingName(building.getBuildingName());
                carplace.setProjectId(building.getProjectId());
                carplace.setProjectName(building.getProjectName());
                carplace.setCreateTime(date);
                carplace.setUpdateTime(date);
                carplace.setCreateUserId(userId);
                carplace.setUpdateUserId(userId);
                carplaceMapper.insertCarplace(carplace);
            }
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MSG, "导入成功");
        } else {
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }


}
