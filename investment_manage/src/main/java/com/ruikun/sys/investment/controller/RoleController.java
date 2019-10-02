package com.ruikun.sys.investment.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

import com.ruikun.sys.investment.entity.BasicData;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.service.RoleService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 角色表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Controller
public class RoleController {
	
	@Autowired
    private RoleService roleService;

	/**
	 * @Description: 跳转到列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toRoleList")
	public String toRoleList(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "role/list";
	}

	/**
	 * @Description: 查询角色表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryRoleList")
	@ResponseBody
	public Map queryRoleList(HttpServletRequest request,Role role){
		User user = CacheUtils.getUser();
		role.setCompanyCode(user.getCompanyCode());
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<Role> list = roleService.queryRoleList(role);
		map.put(Constants.RESULT_DATA,new PageInfo<Role>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryRoleDetailByPrimarykey/{roleId}")
	public String queryRoleDetailByPrimarykey(@PathVariable("roleId")Integer roleId,Model model){
		Role role = roleService.queryRoleDetailByPrimarykey(roleId);
		model.addAttribute("role", role);
		return "roleDetail";
	}

	/**
	 * @Description: 查询角色表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryRoleDetail}")
	public String queryRoleDetail(Role role,Model model){
		role = roleService.queryRoleDetail(role);
		model.addAttribute("role", role);
		return "roleDetail";
	}

	/**
	 * @Description: 查询角色表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryRoleDetailByPrimarykey")
	@ResponseBody
	public Role queryRoleDetailByPrimarykey(Integer roleId){
		Role role = roleService.queryRoleDetailByPrimarykey(roleId);
		return role;
	}

	/**
	 * @Description: 跳转到新增页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toAddRole")
	public String toAddRole(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "role/add";
	}

	/**
	 * @Description: 新增角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("insertRole")
	@ResponseBody
	public Map insertRole(Role role){
		Map map = Maps.newHashMap();
		try {
			roleService.insertRole(role);
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
	@RequestMapping("toUpdateRole/{roleId}")
	public String toUpdateRole(@PathVariable("roleId")Integer roleId,Model model){
		Role role = roleService.queryRoleDetailByPrimarykey(roleId);
		model.addAttribute("role",role);
		return "role/editor";
	}

	/**
	 * @Description: 修改角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("updateRole")
	@ResponseBody
	public Map updateRole(Role role){
		Map map = Maps.newHashMap();
		try {		
			roleService.updateRole(role);
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
	 * @Description: 删除角色表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("deleteRole")
	@ResponseBody
	public Map deleteRole(Role role){
		Map map = Maps.newHashMap();
		try {
			role.setUpdateTime(new Date());
			role.setDelFlag(Constants.DEL_FLAG_DEL);
			roleService.updateRole(role);
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
