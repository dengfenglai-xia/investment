package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Dept;
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.DeptService;
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
 * @Description: 部门表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Controller
public class DeptController {
	
	@Autowired
    private DeptService deptService;

	/**
	 * @Description: 跳转到列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toDeptList")
	public String toDeptList(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "dept/list";
	}

	/**
	 * @Description: 查询部门表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryDeptList")
	@ResponseBody
	public Map queryDeptList(HttpServletRequest request,Dept dept){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		dept.setCompanyCode(user.getCompanyCode());
		PagingUtil.setPageParam(request);
		List<Dept> list = deptService.queryDeptList(dept);
		map.put(Constants.RESULT_DATA,new PageInfo<Dept>(list));
        return map;
	}

	/**
	 * @Description: 查询部门表信息列表
	 * @author: xiachunyu
	 * @date: 2019-04-04 16:25:15
	 */
	@RequestMapping("queryDeptBaseList")
	@ResponseBody
	public Map queryDeptBaseList(Dept dept) {
		Map map = Maps.newHashMap();
		List<Dept> list = deptService.queryDeptList(dept);
		map.put("list", list);
		return map;
	}

	/**
	 * @Description: 通过主键查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryDeptDetailByPrimarykey/{deptId}")
	public String queryDeptDetailByPrimarykey(@PathVariable("deptId")Integer deptId,Model model){
		Dept dept = deptService.queryDeptDetailByPrimarykey(deptId);
		model.addAttribute("dept", dept);
		return "deptDetail";
	}

	/**
	 * @Description: 查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryDeptDetail}")
	public String queryDeptDetail(Dept dept,Model model){
		dept = deptService.queryDeptDetail(dept);
		model.addAttribute("dept", dept);
		return "deptDetail";
	}

	/**
	 * @Description: 查询部门表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryDeptDetailByPrimarykey")
	@ResponseBody
	public Dept queryDeptDetailByPrimarykey(Integer deptId){
		Dept dept = deptService.queryDeptDetailByPrimarykey(deptId);
		return dept;
	}

	/**
	 * @Description: 跳转到新增页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toAddDept")
	public String toAddDept(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "dept/add";
	}

	/**
	 * @Description: 新增部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("insertDept")
	@ResponseBody
	public Map insertDept(Dept dept){
		Map map = Maps.newHashMap();
		try {		
			deptService.insertDept(dept);
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
	 * @Description: 跳转到编辑页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toUpdateDept/{deptId}")
	public String toUpdateDept(@PathVariable("deptId")Integer deptId,Model model){
		Dept dept = deptService.queryDeptDetailByPrimarykey(deptId);
		model.addAttribute("dept",dept);
		return "dept/editor";
	}

	/**
	 * @Description: 修改部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("updateDept")
	@ResponseBody
	public Map updateDept(Dept dept){
		Map map = Maps.newHashMap();
		try {		
			deptService.updateDept(dept);
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
	 * @Description: 删除部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("deleteDept")
	@ResponseBody
	public Map deleteDept(Dept dept){
		Map map = Maps.newHashMap();
		try {
			dept.setUpdateTime(new Date());
			dept.setDelFlag(Constants.DEL_FLAG_DEL);
			deptService.updateDept(dept);
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
