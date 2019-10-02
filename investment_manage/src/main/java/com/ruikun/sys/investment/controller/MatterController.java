package com.ruikun.sys.investment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.ruikun.sys.investment.entity.Building;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Matter;
import com.ruikun.sys.investment.service.MatterService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 事项相关操作
 * @author: xiachunyu
 * @date: 2019-08-06
 */
@Controller
public class MatterController {
	
	@Autowired
    private MatterService matterService;  
		
	/**
	 * @Description: 查询事项信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("queryMatterList")
	@ResponseBody
	public Map queryMatterList(HttpServletRequest request,Matter matter){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		matter.setCreateUserId(user.getUserId());
		PagingUtil.setPageParam(request);
		List<Matter> list = matterService.queryMatterList(matter);
		map.put(Constants.RESULT_DATA,new PageInfo<Matter>(list));
        return map;
	}



	/**
	 * @Description: 查询事项信息列表
	 * @author: xiachunyu
	 * @date: 2019-04-04 16:25:15
	 */
	@RequestMapping("queryMatterListByHome")
	@ResponseBody
	public Map queryMatterListByHome(HttpServletRequest request,Matter matter) {
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		matter.setDealerId(user.getUserId());
		PagingUtil.setPageParam_5(request);
		List<Matter> list = matterService.queryMatterList(matter);
		map.put("list", list);
		return map;
	}

	/**
	 * @Description: 通过主键查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("queryMatterDetailByPrimarykey/{id}")
	public String queryMatterDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		Matter matter = matterService.queryMatterDetailByPrimarykey(id);
		model.addAttribute("matter", matter);
		return "matterDetail";
	}

	/**
	 * @Description: 查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("queryMatterDetail}")
	public String queryMatterDetail(Matter matter,Model model){
		matter = matterService.queryMatterDetail(matter);
		model.addAttribute("matter", matter);
		return "matterDetail";
	}

	/**
	 * @Description: 查询事项信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("queryMatterDetailByPrimarykey")
	@ResponseBody
	public Matter queryMatterDetailByPrimarykey(Integer id){
		Matter matter = matterService.queryMatterDetailByPrimarykey(id);
		return matter;
	}
	
	/**
	 * @Description: 新增事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("insertMatter")
	@ResponseBody
	public Map insertMatter(Matter matter){
		Map map = Maps.newHashMap();
		try {		
			matterService.insertMatter(matter);
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
	 * @Description: 修改事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("updateMatter")
	@ResponseBody
	public Map updateMatter(Matter matter){
		Map map = Maps.newHashMap();
		try {		
			matterService.updateMatter(matter);
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
	 * @Description: 删除事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	@RequestMapping("deleteMatterByPrimarykey")
	@ResponseBody
	public Map deleteMatterByPrimarykey(Integer id){
		Map map = Maps.newHashMap();
		try {		
			matterService.deleteMatterByPrimarykey(id);
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
