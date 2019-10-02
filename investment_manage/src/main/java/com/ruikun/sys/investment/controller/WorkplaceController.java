package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.BuildingService;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.service.ProjectService;
import com.ruikun.sys.investment.service.WorkplaceService;
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
 * @Description: 工位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
@RequestMapping("workplace")
public class WorkplaceController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private DocsService docsService;
    @Autowired
    private BuildingService buildingService;

    /**
     * @Description: 跳转到工位列表页面
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toWorkplaceList")
    public String toWorkplaceList(String state,Model model) {
        User user = CacheUtils.getUser();
        Workplace workplace = new Workplace();
        workplace.setCompanyCode(user.getCompanyCode());
        PlaceStatistics placeStatistics = buildingService.queryWorkplaceStatistics(workplace);
        model.addAttribute("obj", placeStatistics);
        model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
        model.addAttribute("state", state);
        return "workplace/list";
    }

    /**
     * @Description: 查询工位表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryWorkplaceList")
    @ResponseBody
    public Map queryWorkplaceList(HttpServletRequest request, Workplace workplace) {
        User user = CacheUtils.getUser();
        Map map = Maps.newHashMap();
        workplace.setCompanyCode(user.getCompanyCode());
        String state = workplace.getState();
        if ("RENTING".equals(state)) { // 在租
            String[] states = {Constants.RENTOUT_STATE_RENT_ING, Constants.RENTOUT_STATE_SOON_EXPIRE};
            workplace.setStates(states);
        } else if ("VACANT".equals(state)) { // 空置
            String[] states = {Constants.RENTOUT_STATE_VACANT_ING};
            workplace.setStates(states);
        } else if ("SOON".equals(state)) { // 即将到期
            String[] states = {Constants.RENTOUT_STATE_SOON_EXPIRE};
            workplace.setStates(states);
        } else {
            workplace.setStates(null);
        }
        PagingUtil.setPageParam(request);
        List<Workplace> list = workplaceService.queryWorkplaceList(workplace);
        map.put(Constants.RESULT_DATA, new PageInfo<Workplace>(list));
        return map;
    }

    /**
     * @Description: 查询全部工位列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryWorkplaceListNoPageing")
    @ResponseBody
    public Map queryWorkplaceListNoPageing(Workplace workplace) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        workplace.setCompanyCode(user.getCompanyCode());
        workplace.setWhetherOpen(Constants.WHETHER_OPEN_YES);
        List<Workplace> list = workplaceService.queryWorkplaceList(workplace);
        map.put(Constants.RESULT_DATA, new PageInfo<Workplace>(list));
        return map;
    }

    /**
     * @Description: 查询工位表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryWorkplaceDetail/{placeId}")
    public String queryWorkplaceDetail(@PathVariable("placeId") Integer placeId, Model model) {
        Workplace workplace = workplaceService.queryWorkplaceDetailByPrimarykey(placeId);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(placeId, Constants.DOC_TYPE_WORKPLACE));
        model.addAttribute("docsList", docsList);
        model.addAttribute("workplace", workplace);
        model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
        model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
        return "workplace/detail";
    }

    /**
     * @Description: 跳转到新增工位页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddWorkplace")
    public String toAddWorkplace(Model model) {
        User user = CacheUtils.getUser();
        Project project = new Project();
        project.setCompanyCode(user.getCompanyCode());
        List projectList = projectService.queryProjectBaseList(project);
        model.addAttribute("projectList", projectList);
        return "workplace/add";
    }

    /**
     * @Description: 新增工位表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertWorkplace")
    @ResponseBody
    public Map insertWorkplace(Workplace workplace) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            workplace.setCreateUserId(user.getUserId()); // 创建者ID
            workplace.setUpdateUserId(user.getUserId()); // 更新者ID
            workplace.setCompanyCode(user.getCompanyCode());
            workplaceService.insertWorkplace(workplace);
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
     * @Description: 跳转到编辑工位页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toEditorWorkplace/{placeId}")
    public String toEditorRoom(@PathVariable("placeId") Integer placeId, Model model) {
        Workplace workplace = workplaceService.queryWorkplaceDetailByPrimarykey(placeId);
        model.addAttribute("workplace", workplace);
        return "workplace/editor";
    }

    /**
     * @Description: 修改工位表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateWorkplace")
    @ResponseBody
    public Map updateWorkplace(Workplace workplace) {
        Map map = Maps.newHashMap();
        try {
            workplaceService.updateWorkplace(workplace);
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
     * @Description: 删除工位表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteWorkplace")
    @ResponseBody
    public Map deleteWorkplace(Integer placeId) {
        Map map = Maps.newHashMap();
        try {
            workplaceService.deleteWorkplaceByPrimarykey(placeId);
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
