package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.ContractMapper;
import com.ruikun.sys.investment.mapper.ContractRoomMapper;
import com.ruikun.sys.investment.mapper.RoomMapper;
import com.ruikun.sys.investment.service.RoomService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private ContractRoomMapper contractRoomMapper;
    @Autowired
    private ContractMapper contractMapper;


    /**
     * @Description: 查询房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Room> queryRoomList(Room room) {
        return roomMapper.queryRoomList(room);
    }

    /**
     * 查询楼宇房间信息
     *
     * @param room
     * @return
     */
    @Override
    public List<RoomSum> queryRoomSumList(Room room) {
        List<Room> roomList = roomMapper.queryRoomInfoList(room);
        List<RoomSum> roomSumList = roomMapper.queryRoomSumList(room.getBuildingId());
        for (RoomSum rs : roomSumList) {
            double totalArea = 0d;
            List<Room> rooms = Lists.newArrayList();
            Integer floorId = rs.getFloorId();
            for (Room r : roomList) {
                if (floorId.equals(r.getFloorId())) {
                    rooms.add(r);
                    totalArea+=r.getBuildArea();
                }
            }
            rs.setTotalArea(totalArea);
            rs.setRoomList(rooms);
        }
        return roomSumList;
    }

    /**
     * 查询房间统计信息
     * @param room
     * @return
     */
    @Override
    public PlaceStatistics queryRoomSumDetail(Room room) {
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
        if(sumArea != 0){
            avgPrice = sumDayPrice.divide(new BigDecimal(sumArea),2, RoundingMode.HALF_UP);
        }
        placeStatistics.setTotalArea(totalArea);
        placeStatistics.setTotalNum(totalNum);
        placeStatistics.setEnableArea(enableArea);
        placeStatistics.setEnableNum(enableNum);
        placeStatistics.setRentArea(rentArea);
        placeStatistics.setRentNum(rentNum);
        placeStatistics.setVacantArea(vacantArea);
        placeStatistics.setVacantNum(vacantNum);
        if(enableArea == 0){
            placeStatistics.setRentRate(0d);
        }else{
            placeStatistics.setRentRate(rentArea/enableArea*100);
        }
        placeStatistics.setContractNum(contractNum);
        placeStatistics.setCustomerNum(customerNum);
        placeStatistics.setAvgPrice(avgPrice);
        return placeStatistics;
    }

    @Override
    public List<Room> queryRoomBaseList(Room room) {
        return roomMapper.queryRoomBaseList(room);
    }

    /**
     * @Description: 查询房间数量
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public int queryRoomCount(Room room) {
        return roomMapper.queryRoomCount(room);
    }

    /**
     * @Description: 通过主键查询房间表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Room queryRoomDetailByPrimarykey(Integer roomId) {
        return roomMapper.queryRoomDetailByPrimarykey(roomId);
    }

    /**
     * @Description: 查询房间表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Room queryRoomDetail(Room room) {
        return roomMapper.queryRoomDetail(room);
    }

    /**
     * @Description: 新增房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertRoom(Room room) {
        Date date = new Date(); // 当前时间
        String whetherOpen = room.getWhetherOpen();
        if(Constants.WHETHER_OPEN_YES.equals(whetherOpen)){ // 如果对外招租，默认空置状态
            room.setState(Constants.RENTOUT_STATE_VACANT_ING);
        }else{
            room.setState(Constants.RENTOUT_STATE_INIT); // 不对外招租
        }
        room.setCreateTime(date); // 创建时间
        room.setUpdateTime(date); // 更新时间
        roomMapper.insertRoom(room);
    }

    /**
     * @Description: 修改房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateRoom(Room room) {
        Integer userId = CacheUtils.getUser().getUserId();
        room.setUpdateUserId(userId); //更新者ID
        room.setUpdateTime(new Date()); //更新时间
        roomMapper.updateRoom(room);
    }

    /**
     * 更新签约房间状态
     *
     * @param contractCode
     * @param version
     */
    public void updateRoomState(String contractCode, Integer version, String state) {
        List<ContractRoom> contractRooms = contractRoomMapper.queryContractRoomList(new ContractRoom(contractCode, version));
        if(contractRooms.size() > 0){
            Integer[] roomIds = new Integer[contractRooms.size()];
            for (int i = 0; i < contractRooms.size(); i++) {
                roomIds[i] = contractRooms.get(i).getRoomId();
            }
            Room room = new Room();
            room.setState(state);
            room.setRoomIds(roomIds);
            room.setUpdateTime(new Date());
            roomMapper.updateRoomStateBatch(room);
        }
    }

    /**
     * @Description: 删除房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteRoomByPrimarykey(Integer roomId) {
        roomMapper.deleteRoomByPrimarykey(roomId);
    }

}
