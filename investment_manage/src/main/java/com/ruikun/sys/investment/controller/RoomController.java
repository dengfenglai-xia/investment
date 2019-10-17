package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 房间表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
@RequestMapping("room")
public class RoomController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ContractRoomService contractRoomService;
    @Autowired
    private CustomerTemporaryService customerTemporaryService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FloorService floorService;

    /**
     * @Description: 跳转到房间列表页面
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toRoomList")
    public String toRoomList(String state, Model model) {
        User user = CacheUtils.getUser();
        Room room = new Room();
        room.setCompanyCode(user.getCompanyCode());
        PlaceStatistics placeStatistics = buildingService.queryRoomStatistics(room);
        model.addAttribute("obj", placeStatistics);
        model.addAttribute("ROOM_DECORATE_MAP", Constants.ROOM_DECORATE_MAP);
        model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("state", state);
        return "room/list";
    }

    /**
     * @Description: 查询房间表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryRoomList")
    @ResponseBody
    public Map queryRoomList(HttpServletRequest request, Room room) {
        Map map = Maps.newHashMap();
        String state = room.getState();
        if ("RENTING".equals(state)) { // 在租
            String[] states = {Constants.RENTOUT_STATE_RENT_ING, Constants.RENTOUT_STATE_SOON_EXPIRE};
            room.setStates(states);
        } else if ("VACANT".equals(state)) { // 空置
            String[] states = {Constants.RENTOUT_STATE_VACANT_ING};
            room.setStates(states);
        } else if ("SOON".equals(state)) { // 即将到期
            String[] states = {Constants.RENTOUT_STATE_SOON_EXPIRE};
            room.setStates(states);
        } else {
            room.setStates(null);
        }
        User user = CacheUtils.getUser();
        room.setCompanyCode(user.getCompanyCode());
        PagingUtil.setPageParam(request);
        List<Room> list = roomService.queryRoomList(room);
        map.put(Constants.RESULT_DATA, new PageInfo<Room>(list));
        return map;
    }

    /**
     * @Description: 查询房间表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryRoomSumList")
    public String queryRoomSumList(HttpServletRequest request, Room room, Model model) {
        User user = CacheUtils.getUser();
        String startDateRange = request.getParameter("startDateRange"); //开始时间范围
        String endDateRange = request.getParameter("endDateRange"); //结束时间范围
        if (StringUtils.isNotEmpty(startDateRange)) {
            room.setStartDateRange1(startDateRange.split("~")[0].trim());
            room.setStartDateRange2(startDateRange.split("~")[1].trim());
        }
        if (StringUtils.isNotEmpty(endDateRange)) {
            room.setEndDateRange1(endDateRange.split("~")[0].trim());
            room.setEndDateRange2(endDateRange.split("~")[1].trim());
        }
        String companyCode = user.getCompanyCode();
        Building building = new Building();
        building.setCompanyCode(companyCode);
        List<Building> buildingList = buildingService.queryBuildingBaseList(building);
        Integer buildingId = room.getBuildingId();
        if (buildingId == null && buildingList.size() > 0) {
            buildingId = buildingList.get(0).getBuildingId();
            room.setBuildingId(buildingId);
        }
        room.setCompanyCode(companyCode);
        List<RoomSum> roomSumList = roomService.queryRoomSumList(room);
        PlaceStatistics placeStatistics = roomService.queryRoomSumDetail(room);
        model.addAttribute("room", room);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("startDateRange", startDateRange);
        model.addAttribute("endDateRange", endDateRange);
        model.addAttribute("roomSumList", roomSumList);
        model.addAttribute("obj", placeStatistics);
        return "room/show";
    }

    /**
     * @Description: 查询全部房间列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryRoomListNoPageing")
    @ResponseBody
    public Map queryRoomListNoPageing(Room room) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        room.setCompanyCode(user.getCompanyCode());
        room.setWhetherOpen(Constants.WHETHER_OPEN_YES);
        List<Room> list = roomService.queryRoomList(room);
        map.put(Constants.RESULT_DATA, new PageInfo<Room>(list));
        return map;
    }

    /**
     * @Description: 查询楼层基础信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryRoomBaseList")
    @ResponseBody
    public Map queryRoomBaseList(Room room) {
        Map map = Maps.newHashMap();
        List<Room> list = roomService.queryRoomBaseList(room);
        map.put("list", list);
        return map;
    }

    /**
     * @Description: 查询房间表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryRoomDetail/{roomId}")
    public String queryRoomDetail(@PathVariable("roomId") Integer roomId, Model model) {
        User user = CacheUtils.getUser();
        Room room = roomService.queryRoomDetailByPrimarykey(roomId);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(roomId, Constants.DOC_TYPE_ROOM));
        ContractRoom contractRoom = new ContractRoom();
        contractRoom.setRoomId(roomId);
        List<Contract> contractList = contractRoomService.queryContractList(roomId);
        CustomerTemporary customerTemporary = new CustomerTemporary();
        customerTemporary.setCompanyCode(user.getCompanyCode());
        customerTemporary.setRoomId(roomId);
        List<CustomerTemporary> customerList = customerTemporaryService.queryCustomerBaseList(customerTemporary);
        model.addAttribute("room", room);
        model.addAttribute("docsList", docsList);
        model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
        model.addAttribute("ROOM_DECORATE_MAP", Constants.ROOM_DECORATE_MAP);
        model.addAttribute("ROOM_WHETHER_STREET_MAP", Constants.ROOM_WHETHER_STREET_MAP);
        model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
        model.addAttribute("MAP_CUSTOMER_STATE", Constants.MAP_CUSTOMER_STATE);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("contractList", contractList);
        model.addAttribute("customerList", customerList);
        return "room/detail";
    }

    /**
     * @Description: 跳转到新增房间页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddRoom")
    public String toAddRoom(Model model) {
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List projectList = projectService.queryProjectBaseList(project);
        //楼宇朝向
        List<BasicData> orientationList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_ORIENTATION));
        //配套设施
        List<BasicData> facilitiesList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_FACILITIES));
        //房源标签
        List<BasicData> tagsList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_TAGS));
        model.addAttribute("projectList", projectList);
        model.addAttribute("orientationList", orientationList);
        model.addAttribute("facilitiesList", facilitiesList);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        model.addAttribute("tagsList", tagsList);
        return "room/add";
    }

    /**
     * @Description: 新增房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertRoom")
    @ResponseBody
    public Map insertRoom(Room room) {
        Map map = Maps.newHashMap();
        try {
            List<Room> roomList = roomService.queryRoomBaseList(room);
            if (roomList.size() > 0) {
                map.put(Constants.MSG, "房间" + room.getRoomNo() + "已存在，不能重复添加");
                map.put(Constants.SUCCESS, false);
                return map;
            }
            User user = CacheUtils.getUser();
            room.setCompanyCode(user.getCompanyCode()); // 公司编号
            room.setCreateUserId(user.getUserId()); // 创建者ID
            room.setUpdateUserId(user.getUserId()); // 更新者ID
            roomService.insertRoom(room);
            map.put(Constants.MSG, "添加成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 跳转到编辑房间页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditorRoom/{roomId}")
    public String toEditorRoom(@PathVariable("roomId") Integer roomId, Model model) {
        User user = CacheUtils.getUser();
        Room room = roomService.queryRoomDetailByPrimarykey(roomId);
        Building building = new Building();
        building.setCompanyCode(user.getCompanyCode());
        building.setProjectId(room.getProjectId());
        List<Building> buildingList = buildingService.queryBuildingBaseList(building);
        Integer buildingId = buildingList.get(0).getBuildingId();
        Floor floor = new Floor();
        floor.setBuildingId(buildingId);
        List<Floor> floorList = floorService.queryFloorList(floor);
        //楼宇朝向
        List<BasicData> orientationList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_ORIENTATION));
        //配套设施
        List<BasicData> facilitiesList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_FACILITIES));
        //房源标签
        List<BasicData> tagsList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_TAGS));
        model.addAttribute("room", room);
        model.addAttribute("orientationList", orientationList);
        model.addAttribute("facilitiesList", facilitiesList);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("buildingList", buildingList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        return "room/editor";
    }

    /**
     * @Description: 修改房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateRoom")
    @ResponseBody
    public Map updateRoom(Room room) {
        Map map = Maps.newHashMap();
        try {
            ContractRoom contractRoom = new ContractRoom();
            contractRoom.setRoomId(room.getRoomId());
            List<ContractRoom> contractRoomList = contractRoomService.queryContractRoomList(contractRoom);
            if (contractRoomList.size() > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前房间存在关联合同，不能修改");
                return map;
            }
            roomService.updateRoom(room);
            map.put(Constants.MSG, "更新成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteRoom")
    @ResponseBody
    public Map deleteRoom(Integer roomId) {
        Map map = Maps.newHashMap();
        try {
            ContractRoom contractRoom = new ContractRoom();
            contractRoom.setRoomId(roomId);
            List<ContractRoom> contractRoomList = contractRoomService.queryContractRoomList(contractRoom);
            if (contractRoomList.size() > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前房间存在关联合同，不能删除");
            } else {
                Integer userId = CacheUtils.getUser().getUserId();
                Room room = new Room();
                room.setRoomId(roomId);
                room.setUpdateUserId(userId);
                room.setDelFlag(Constants.DEL_FLAG_DEL);
                roomService.updateRoom(room);
                map.put(Constants.MSG, "删除成功");
                map.put(Constants.SUCCESS, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

}
