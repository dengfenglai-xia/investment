package com.ruikun.sys.investment.controller;

import java.util.List;
import java.util.Map;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.ProjectService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.service.SettelService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 结算记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-16
 */
@Controller
public class SettelController {
	
	@Autowired
    private SettelService settelService;
	@Autowired
	private ProjectService projectService;

	/**
	 * @Description: 查询结算记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("querySettelList")
	@ResponseBody
	public Map querySettelList(HttpServletRequest request,Settel settel){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<Settel> list = settelService.querySettelList(settel);
		map.put(Constants.RESULT_DATA,new PageInfo<Settel>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询结算记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("querySettelDetailByPrimarykey/{id}")
	public String querySettelDetailByPrimarykey(@PathVariable("id")Long id,Model model){
		Settel settel = settelService.querySettelDetailByPrimarykey(id);
		model.addAttribute("settel", settel);
		return "settelDetail";
	}

	/**
	 * @Description: 查询结算记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("querySettelDetail}")
	public String querySettelDetail(Settel settel,Model model){
		settel = settelService.querySettelDetail(settel);
		model.addAttribute("settel", settel);
		return "settelDetail";
	}

	/**
	 * @Description: 查询结算记录信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("querySettelDetailByPrimarykey")
	@ResponseBody
	public Settel querySettelDetailByPrimarykey(Long id){
		Settel settel = settelService.querySettelDetailByPrimarykey(id);
		return settel;
	}
	
	/**
	 * @Description: 新增结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("insertSettel")
	@ResponseBody
	public Map insertSettel(Settel settel){
		Map map = Maps.newHashMap();
		try {		
			settelService.insertSettel(settel);
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
	 * @Description: 修改结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("updateSettel")
	@ResponseBody
	public Map updateSettel(Settel settel){
		Map map = Maps.newHashMap();
		try {		
			settelService.updateSettel(settel);
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
	 * @Description: 删除结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	@RequestMapping("deleteSettelByPrimarykey")
	@ResponseBody
	public Map deleteSettelByPrimarykey(Long id){
		Map map = Maps.newHashMap();
		try {		
			settelService.deleteSettelByPrimarykey(id);
			map.put(Constants.MSG, "删除成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}

	/**
	 * TO现金流水统计
	 */
	@RequestMapping("toSettelReport")
	public String toSettelReport(Model model){
		String year = DateTimeUtil.getFormatNowTime("yyyy");
		User user = CacheUtils.getUser();
		Project project = new Project();
		project.setCompanyCode(user.getCompanyCode());
		List projectList = projectService.queryProjectBaseList(project);
		List feeTypeList = settelService.queryFeeTypeList();
		model.addAttribute("feeTypeList",feeTypeList);
		model.addAttribute("year",year);
		model.addAttribute("projectList", projectList);
		return "report/settelReport";
	}

	/**
	 * 现金流水统计
	 * @param settelReport
	 * @return
	 */
	@RequestMapping("querySettelReport")
	@ResponseBody
	public Map querySettelReport(SettelReport settelReport){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		settelReport.setCompanyCode(user.getCompanyCode());
		List list = settelService.querySettelReport(settelReport);
		map.put(Constants.RESULT_DATA,new PageInfo<SettelMonthStatistics>(list));
		return map;
	}

}
