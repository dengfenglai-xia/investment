package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.BuildingService;
import com.ruikun.sys.investment.service.CarplaceService;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.service.ProjectService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
 
/**
 * @Description: 车位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-24
 */
@Controller
@RequestMapping("carplace")
public class CarplaceController {

	@Autowired
	private ProjectService projectService;
	@Autowired
    private CarplaceService carplaceService;
	@Autowired
	private DocsService docsService;
	@Autowired
	private BuildingService buildingService;

	/**
	 * @Description: 跳转到车位列表页面
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toCarplaceList")
	public String toCarplaceList(String state,Model model) {
		User user = CacheUtils.getUser();
		Carplace carplace = new Carplace();
		carplace.setCompanyCode(user.getCompanyCode());
		PlaceStatistics placeStatistics = buildingService.queryCarplaceStatistics(carplace);
		model.addAttribute("obj", placeStatistics);
		model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
		model.addAttribute("state", state);
		return "carplace/list";
	}

	/**
	 * @Description: 查询车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	@RequestMapping("queryCarplaceList")
	@ResponseBody
	public Map queryCarplaceList(HttpServletRequest request,Carplace carplace){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		String state = carplace.getState();
		if ("RENTING".equals(state)) { // 在租
			String[] states = {Constants.RENTOUT_STATE_RENT_ING, Constants.RENTOUT_STATE_SOON_EXPIRE};
			carplace.setStates(states);
		} else if ("VACANT".equals(state)) { // 空置
			String[] states = {Constants.RENTOUT_STATE_VACANT_ING};
			carplace.setStates(states);
		} else if ("SOON".equals(state)) { // 即将到期
			String[] states = {Constants.RENTOUT_STATE_SOON_EXPIRE};
			carplace.setStates(states);
		}  else {
			carplace.setStates(null);
		}
		carplace.setCompanyCode(user.getCompanyCode());
		PagingUtil.setPageParam(request);
		List<Carplace> list = carplaceService.queryCarplaceList(carplace);
		map.put(Constants.RESULT_DATA,new PageInfo<Carplace>(list));
        return map;
	}

	/**
	 * @Description: 查询车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	@RequestMapping("queryCarplaceListNoPageing")
	@ResponseBody
	public Map queryCarplaceListNoPageing(Carplace carplace){
		Map map = Maps.newHashMap();
		carplace.setWhetherOpen(Constants.WHETHER_OPEN_YES);
		String[] states = {Constants.RENTOUT_STATE_SOON_EXPIRE,Constants.RENTOUT_STATE_VACANT_ING};
		carplace.setStates(states);
		List<Carplace> list = carplaceService.queryCarplaceList(carplace);
		map.put(Constants.RESULT_DATA, new PageInfo<Carplace>(list));
		return map;
	}

	/**
	 * @Description: 查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryCarplaceDetail/{placeId}")
	public String queryWorkplaceDetail(@PathVariable("placeId") Integer placeId, Model model) {
		Carplace carplace = carplaceService.queryCarplaceDetailByPrimarykey(placeId);
		//查询项目文档
		List<Docs> docsList = docsService.queryDocsList(new Docs(placeId, Constants.DOC_TYPE_WORKPLACE));
		model.addAttribute("docsList", docsList);
		model.addAttribute("carplace", carplace);
		model.addAttribute("RENTOUT_STATE_MAP", Constants.RENTOUT_STATE_MAP);
		model.addAttribute("WHETHER_OPEN_MAP", Constants.WHETHER_OPEN_MAP);
		return "carplace/detail";
	}

	/**
	 * @Description: 跳转到新增车位页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toAddCarplace")
	public String toAddCarplace(Model model) {
		User user = CacheUtils.getUser();
		Project project = new Project();
		project.setCompanyCode(user.getCompanyCode());
		List projectList = projectService.queryProjectBaseList(project);
		model.addAttribute("projectList", projectList);
		return "carplace/add";
	}

	/**
	 * @Description: 新增车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	@RequestMapping("insertCarplace")
	@ResponseBody
	public Map insertCarplace(Carplace carplace){
		Map map = Maps.newHashMap();
		try {
			User user = CacheUtils.getUser();
			carplace.setCompanyCode(user.getCompanyCode());
			carplace.setCreateUserId(user.getUserId()); // 创建者ID
			carplace.setUpdateUserId(user.getUserId()); // 更新者ID
			carplaceService.insertCarplace(carplace);
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
	 * @Description: 跳转到编辑车位页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toEditorCarplace/{placeId}")
	public String toEditorRoom(@PathVariable("placeId") Integer placeId, Model model) {
		Carplace carplace = carplaceService.queryCarplaceDetailByPrimarykey(placeId);
		model.addAttribute("carplace", carplace);
		return "carplace/editor";
	}
	
	/**
	 * @Description: 修改车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	@RequestMapping("updateCarplace")
	@ResponseBody
	public Map updateCarplace(Carplace carplace){
		Map map = Maps.newHashMap();
		try {
			Integer userId = CacheUtils.getUser().getUserId();
			carplace.setUpdateUserId(userId); //更新者ID
			carplace.setUpdateTime(new Date()); //更新时间
			carplaceService.updateCarplace(carplace);
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
	 * @Description: 删除车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	@RequestMapping("deleteCarplace")
	@ResponseBody
	public Map deleteCarplace(Integer placeId){
		Map map = Maps.newHashMap();
		try {		
			carplaceService.deleteCarplaceByPrimarykey(placeId);
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
