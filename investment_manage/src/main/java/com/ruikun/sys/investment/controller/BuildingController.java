package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.DownLoadFileUtil;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.ruikun.sys.investment.utils.ReadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description: 楼宇表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
@RequestMapping("building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private CarplaceService carplaceService;
    @Autowired
    private DocsService docsService;

    /**
     * @Description: 跳转到楼宇列表页面
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toBuildingList/{flag}")
    public String toBuildingList(@PathVariable("flag") String flag, Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        if (flag.equals("1")) {
            Room room = new Room();
            room.setCompanyCode(companyCode);
            PlaceStatistics placeStatistics = buildingService.queryRoomStatistics(room);
            model.addAttribute("obj", placeStatistics);
            return "building/roomList";
        } else if (flag.equals("2")) {
            Workplace workplace = new Workplace();
            workplace.setCompanyCode(companyCode);
            PlaceStatistics placeStatistics = buildingService.queryWorkplaceStatistics(workplace);
            model.addAttribute("obj", placeStatistics);
            return "building/workplaceList";
        } else {
            Carplace carplace = new Carplace();
            carplace.setCompanyCode(companyCode);
            PlaceStatistics placeStatistics = buildingService.queryCarplaceStatistics(carplace);
            model.addAttribute("obj", placeStatistics);
            return "building/carplaceList";
        }
    }

    /**
     * @Description: 查询楼宇表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryBuildingList/{flag}")
    @ResponseBody
    public Map queryBuildingList(HttpServletRequest request, Building building,
                                 @PathVariable("flag") String flag){
        Map map = Maps.newHashMap();
        List<Building> list = null;
        try {
            User user = CacheUtils.getUser();
            building.setCompanyCode(user.getCompanyCode());
            PagingUtil.setPageParam(request);
            list = buildingService.queryBuildingList(building,flag);
            map.put(Constants.RESULT_DATA, new PageInfo<Building>(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @Description: 查询楼预基础信息
     * @author: xiachunyu
     * @date: 2019-04-04 16:25:15
     */
    @RequestMapping("queryBuildingBaseList")
    @ResponseBody
    public Map queryBuildingBaseList(Building building) {
        Map map = Maps.newHashMap();
        List<Building> list = buildingService.queryBuildingBaseList(building);
        map.put("list", list);
        return map;
    }

    /**
     * @Description: 通过主键查询楼宇表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryBuildingDetailsByPrimarykey/{buildingId}")
    public String queryBuildingDetailsByPrimarykey(@PathVariable("buildingId") Integer buildingId, Model model) {
        Building building = buildingService.queryBuildingDetailByPrimarykey(buildingId);
        model.addAttribute("building", building);
        return "buildingDetail";
    }

    /**
     * @Description: 查询楼宇表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryBuildingDetail/{buildingId}")
    public String queryBuildingDetail(@PathVariable("buildingId") Integer buildingId, Model model) {
        Building building = buildingService.queryBuildingDetailByPrimarykey(buildingId);
        int roomCount = roomService.queryRoomCount(new Room(buildingId));
        Workplace workplace = new Workplace();
        workplace.setBuildingId(buildingId);
        int workplaceCount = workplaceService.queryWorkplaceCount(workplace);
        Carplace carplace = new Carplace();
        carplace.setBuildingId(buildingId);
        int carplaceCount = carplaceService.queryCarplaceCount(carplace);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(buildingId, Constants.DOC_TYPE_BUILDING));
        model.addAttribute("docsList", docsList);
        model.addAttribute("building", building);
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("workplaceCount", workplaceCount);
        model.addAttribute("carplaceCount", carplaceCount);
        return "building/detail";
    }

    /**
     * @Description: 查询楼宇表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryBuildingDetailByPrimarykey")
    @ResponseBody
    public Building queryBuildingDetailByPrimarykey(Integer buildingId) {
        Building building = buildingService.queryBuildingDetailByPrimarykey(buildingId);
        return building;
    }

    /**
     * @Description: 跳转到新增项目页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddBuilding")
    public String toAddBuilding(Model model) {
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List list = projectService.queryProjectBaseList(project);
        model.addAttribute("list", list);
        return "building/add";
    }

    /**
     * @Description: 新增楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertBuilding")
    @ResponseBody
    public Map insertBuilding(Building building) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        try {
            Building detail = new Building();
            detail.setCompanyCode(companyCode);
            detail.setBuildingName(building.getBuildingName());
            detail = buildingService.queryBuildingDetail(detail);
            if (detail != null) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "楼宇名称已存在，不能重复添加");
            } else {
                building.setCompanyCode(companyCode);
                building.setCreateUserId(user.getUserId()); // 创建者ID
                building.setUpdateUserId(user.getUserId()); // 更新者ID
                buildingService.insertBuilding(building);
                map.put(Constants.MSG, "添加成功");
                map.put(Constants.SUCCESS, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 跳转到新增项目页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditBuilding/{buildingId}")
    public String toEditBuilding(@PathVariable("buildingId") Integer buildingId, Model model) {
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List list = projectService.queryProjectBaseList(project);
        Building building = buildingService.queryBuildingDetailByPrimarykey(buildingId);
        model.addAttribute("list", list);
        model.addAttribute("building", building);
        return "building/edit";
    }

    /**
     * @Description: 修改楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateBuilding")
    @ResponseBody
    public Map updateBuilding(Building building) {
        Map map = Maps.newHashMap();
        int num = roomService.queryRoomCount(new Room(building.getBuildingId()));
        if (num > 0) {
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "当前楼宇下已有房源，暂不能修改");
            return map;
        }
        try {
            buildingService.updateBuilding(building);
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
     * @Description: 删除楼宇表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteBuilding")
    @ResponseBody
    public Map deleteBuilding(Integer buildingId) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            int num = roomService.queryRoomCount(new Room(buildingId));
            if (num > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前楼宇下存在房源，暂不能删除");
            } else {
                Building building = new Building();
                building.setUpdateUserId(userId);
                building.setDelFlag(Constants.DEL_FLAG_DEL);
                buildingService.deleteBuildingByPrimarykey(buildingId);
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

    /**
     * @Description: 下载模板
     * @author: xiachunyu
     * @date: 2019-03-23 16:39:49
     */
    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        // 模板根路径
        String path = ReadConfig.getProperty("TEMPLATE_ROOT_PATH");
        path = path + "/space_info_template.xlsx";
        try {
            DownLoadFileUtil.downloadFile(request, response, path, "空间信息导入模板.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 导入数据
     * @author: xiachunyu
     * @date: 2019-06-19
     */
    @RequestMapping("importInfo")
    @ResponseBody
    public Map importInfo(Building building, @RequestParam("file") MultipartFile file) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            building.setCreateUserId(user.getUserId());
            building.setUpdateUserId(user.getUserId());
            building.setCompanyCode(user.getCompanyCode());
            map = buildingService.importBuildingInfo(building, file);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 跳转到楼层页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toFloors/{buildingId}")
    public String toFloors(@PathVariable("buildingId") Integer buildingId, Model model) {
        model.addAttribute("buildingId", buildingId);
        return "building/floors";
    }

    /**
     * @Description: 跳转到房源页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toRooms/{buildingId}")
    public String toRooms(@PathVariable("buildingId") Integer buildingId, Model model) {
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("ROOM_DECORATE_MAP", Constants.ROOM_DECORATE_MAP);
        model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
        model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
        return "building/rooms";
    }

    /**
     * @Description: 跳转到工位页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toWorkplaces/{buildingId}")
    public String toWorkplaces(@PathVariable("buildingId") Integer buildingId, Model model) {
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("ROOM_DECORATE_MAP", Constants.ROOM_DECORATE_MAP);
        model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
        return "building/workplaces";
    }

    /**
     * @Description: 跳转到车位页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toCarplaces/{buildingId}")
    public String toCarplaces(@PathVariable("buildingId") Integer buildingId, Model model) {
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("ROOM_DECORATE_MAP", Constants.ROOM_DECORATE_MAP);
        model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
        return "building/carplaces";
    }
}
