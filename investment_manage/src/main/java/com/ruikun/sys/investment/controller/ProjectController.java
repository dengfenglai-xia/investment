package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
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
 * @Description: 项目表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private CarplaceService carplaceService;

    /**
     * @Description: 跳转到项目列表页面
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toProjectList")
    public String toProjectList(Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        List<BasicData> businessModeList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_BUSINESSMODE));
        Room room = new Room();
        room.setCompanyCode(companyCode);
        List<Room> roomList = roomService.queryRoomList(room);
        int roomNum = roomList.size();
        double totalArea = 0d;
        for (Room r : roomList) {
            totalArea += r.getBuildArea();
        }

        Building building = new Building();
        building.setCompanyCode(companyCode);
        int buildingNum = buildingService.queryBuildingCount(building);

        Workplace workplace = new Workplace();
        workplace.setCompanyCode(companyCode);
        int workplaceNum = workplaceService.queryWorkplaceCount(workplace);

        Carplace carplace = new Carplace();
        carplace.setCompanyCode(companyCode);
        int carplaceNum = carplaceService.queryCarplaceCount(carplace);

        model.addAttribute("buildingNum", buildingNum);
        model.addAttribute("roomNum", roomNum);
        model.addAttribute("totalArea", totalArea);
        model.addAttribute("workplaceNum", workplaceNum);
        model.addAttribute("carplaceNum", carplaceNum);
        model.addAttribute("businessModeList", businessModeList);
        return "project/list";
    }

    /**
     * @Description: 查询项目表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryProjectList")
    @ResponseBody
    public Map queryProjectList(HttpServletRequest request, Project project) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        project.setCompanyCode(user.getCompanyCode());
        PagingUtil.setPageParam(request);
        List<Project> list = projectService.queryProjectList(project);
        map.put(Constants.RESULT_DATA, new PageInfo<Project>(list));
        return map;
    }

    /**
     * @Description: 查询项目表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryProjectDetail/{projectId}")
    public String queryProjectDetails(@PathVariable("projectId") Integer projectId, Model model) {
        Project project = projectService.queryProjectDetailByPrimarykey(projectId);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(projectId, Constants.DOC_TYPE_PROJECT));
        model.addAttribute("project", project);
        model.addAttribute("docsList", docsList);
        return "project/detail";
    }

    /**
     * @Description: 加载地理地图
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("showMap")
    public String showMap(String longitude, String latitude, String address, Model model) {
        model.addAttribute("longitude", longitude);
        model.addAttribute("latitude", latitude);
        model.addAttribute("address", address);
        return "project/map";
    }

    /**
     * @Description: 跳转到新增项目页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddProject")
    public String toAddProject(Model model) {
        //项目业态
        List<BasicData> businessModeList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_BUSINESSMODE));
        User user = CacheUtils.getUser();
        model.addAttribute("businessModeList", businessModeList);
        model.addAttribute("company", user.getCompany());
        return "project/add";
    }

    /**
     * @Description: 跳转到新增项目页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditProject/{projectId}")
    public String toEditProject(@PathVariable("projectId") Integer projectId,Model model) {
        User user = CacheUtils.getUser();
        //项目业态
        List<BasicData> businessModeList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_BUSINESSMODE));
        Project project = projectService.queryProjectDetailByPrimarykey(projectId);
        model.addAttribute("businessModeList", businessModeList);
        model.addAttribute("project", project);
        model.addAttribute("company", user.getCompany());
        return "project/editor";
    }

    /**
     * 地图选址
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toMap")
    public String toMap(HttpServletRequest request, Model model) {
        String address = request.getParameter("address");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        model.addAttribute("address", address);
        model.addAttribute("longitude", longitude);
        model.addAttribute("latitude", latitude);
        return "project/map";
    }

    /**
     * @Description: 新增项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertProject")
    @ResponseBody
    public Map insertProject(Project project) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        try {
            Project detail = new Project();
            detail.setCompanyCode(companyCode);
            detail.setProjectName(project.getProjectName());
            detail = projectService.queryProjectDetail(detail);
            if (detail != null) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "项目名称已存在，不能重复添加");
            } else {
                project.setCompanyCode(companyCode);
                project.setCreateUserId(user.getUserId()); // 创建者ID
                project.setUpdateUserId(user.getUserId()); // 更新者ID
                projectService.insertProject(project);
                map.put(Constants.MSG, "创建成功");
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
     * @Description: 修改项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateProject")
    @ResponseBody
    public Map updateProject(Project project) {
        Map map = Maps.newHashMap();
        try {
            projectService.updateProject(project);
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
     * @Description: 删除项目表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteProject")
    @ResponseBody
    public Map deleteProject(Integer projectId) {
        Map map = Maps.newHashMap();
        try {
            int buildingNum = buildingService.queryBuildingCount(new Building(projectId));
            if (buildingNum > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前项目下存在楼宇，暂不能删除");
                return map;
            }
            Workplace workplace = new Workplace();
            workplace.setProjectId(projectId);
            int workplaceNum = workplaceService.queryWorkplaceCount(workplace);
            if (workplaceNum > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前项目下存在工位，暂不能删除");
                return map;
            }
            Carplace carplace = new Carplace();
            carplace.setProjectId(projectId);
            int carplaceNum = carplaceService.queryCarplaceCount(carplace);
            if (carplaceNum > 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MSG, "当前项目下存在车位，暂不能删除");
                return map;
            }
            Integer userId = CacheUtils.getUser().getUserId();
            Project project = new Project();
            project.setProjectId(projectId);
            project.setUpdateUserId(userId);
            project.setDelFlag(Constants.DEL_FLAG_DEL);
            projectService.updateProject(project);
            map.put(Constants.MSG, "删除成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

}
