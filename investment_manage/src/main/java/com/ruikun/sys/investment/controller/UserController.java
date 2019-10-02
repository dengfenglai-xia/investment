package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Dept;
import com.ruikun.sys.investment.entity.Job;
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.DeptService;
import com.ruikun.sys.investment.service.JobService;
import com.ruikun.sys.investment.service.RoleService;
import com.ruikun.sys.investment.service.UserService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.CommonUtil;
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
 * @Description: 用户表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private JobService jobService;
	@Autowired
	private RoleService roleService;

	/**
	 * @Description: 跳转到公司列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toCompanyList")
	public String toCompanyList(){
		return "company/list";
	}

	/**
	 * @Description: 查询统计数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryUserCount")
	@ResponseBody
	public Map queryUserCount(){
		Map map = Maps.newHashMap();
		User user = new User();
		user.setAccountType(Constants.ACCOUNT_TYPE_TRY);
		int num1 = userService.queryUserCount(user);
		user.setAccountType(Constants.ACCOUNT_TYPE_FORMAL);
		int num2 = userService.queryUserCount(user);
		map.put("num1",num1);
		map.put("num2",num2);
		map.put("total",num1+num2);
		return map;
	}

	/**
	 * @Description: 查询公司列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryCompanyList")
	@ResponseBody
	public Map queryCompanyList(HttpServletRequest request,User user){
		Map map = Maps.newHashMap();
		user.setUserType(Constants.USER_TYPE_COMPANY_ADMIN);
		PagingUtil.setPageParam(request);
		List<User> list = userService.queryUserList(user);
		map.put(Constants.RESULT_DATA,new PageInfo<User>(list));
		return map;
	}

	/**
	 * @Description: 查询公司列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryCompanyBaseList")
	@ResponseBody
	public Map queryCompanyBaseList(){
		Map map = Maps.newHashMap();
		User user = new User();
		user.setUserType(Constants.USER_TYPE_COMPANY_ADMIN);
		List<User> list = userService.queryUserList(user);
		map.put("list", list);
		return map;
	}

	/**
	 * @Description: 跳转到新增公司页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toAddCompany")
	public String toAddCompany(Model model){
		String companyCode = CommonUtil.getBusinessCode(Constants.COMPANY_CODE);
		model.addAttribute("companyCode",companyCode);
		return "company/add";
	}

	/**
	 * @Description: 新增公司
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("insertCompany")
	@ResponseBody
	public Map insertCompany(User user){
		Map map = Maps.newHashMap();
		try {
			User u = CacheUtils.getUser();
			user.setCreateUserId(u.getUserId()); // 创建者ID
			user.setUpdateUserId(u.getUserId()); // 更新者ID
			user.setCompanyCode(u.getCompanyCode());
			user.setCompany(u.getCompany());
			userService.insertCompany(user);
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
	 * @Description: 跳转到编辑公司页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toEditorCompany/{userId}")
	public String toEditorCompany(@PathVariable("userId")Integer userId,Model model){
		User user = userService.queryUserDetailByPrimarykey(userId);
		model.addAttribute("obj",user);
		return "company/editor";
	}

	/**
	 * @Description: 查询公司详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryCompanyDetail/{userId}")
	public String queryCompanyDetail(@PathVariable("userId")Integer userId,Model model){
		User user = userService.queryUserDetailByPrimarykey(userId);
		model.addAttribute("obj",user);
		return "company/detail";
	}

	/**
	 * @Description: 跳转到用户列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toUserList")
	public String toUserList(Model model){
		User user = CacheUtils.getUser();
		String companyCode = user.getCompanyCode();
		Dept dept = new Dept();
		dept.setCompanyCode(companyCode);
		List<Dept> deptList = deptService.queryDeptList(dept);
		model.addAttribute("deptList", deptList);
		return "user/list";
	}

	/**
	 * @Description: 查询用户表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryUserList")
	@ResponseBody
	public Map queryUserList(HttpServletRequest request,User user){
		Map map = Maps.newHashMap();
		String companyCode = CacheUtils.getUser().getCompanyCode();
		user.setCompanyCode(companyCode);
		user.setUserType(Constants.USER_TYPE_COMPANY_STAFF);
		PagingUtil.setPageParam(request);
		List<User> list = userService.queryUserList(user);
		map.put(Constants.RESULT_DATA,new PageInfo<User>(list));
        return map;
	}

	/**
	 * @Description: 跳转到新增用户页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toAddUser")
	public String toAddUser(Model model){
		User user = CacheUtils.getUser();
		String companyCode = user.getCompanyCode();
		Dept dept = new Dept();
		dept.setCompanyCode(companyCode);
		List<Dept> deptList = deptService.queryDeptList(dept);
		Job job = new Job();
		job.setCompanyCode(companyCode);
		List<Job> jobList = jobService.queryJobList(job);
		Role role = new Role();
		role.setCompanyCode(companyCode);
		List<Role> roleList = roleService.queryRoleList(role);
		model.addAttribute("deptList", deptList);
		model.addAttribute("jobList", jobList);
		model.addAttribute("roleList", roleList);
		return "user/add";
	}

	/**
	 * @Description: 新增用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("insertUser")
	@ResponseBody
	public Map insertUser(User user){
		Map map = Maps.newHashMap();
		try {
			User u = CacheUtils.getUser();
			user.setCreateUserId(u.getUserId()); // 创建者ID
			user.setUpdateUserId(u.getUserId()); // 更新者ID
			user.setCompanyCode(u.getCompanyCode());
			user.setCompany(u.getCompany());
			userService.insertUser(user);
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
	 * @Description: 查询用户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryUserDetail/{userId}")
	public String queryUserDetail(@PathVariable("userId")Integer userId,Model model){
		User user = userService.queryUserDetailByPrimarykey(userId);
		model.addAttribute("obj",user);
		return "user/detail";
	}

	/**
	 * @Description: 跳转到编辑用户页
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("toEditorUser/{userId}")
	public String toEditorUser(@PathVariable("userId")Integer userId,Model model){
		User obj = userService.queryUserDetailByPrimarykey(userId);
		User user = CacheUtils.getUser();
		String companyCode = user.getCompanyCode();
		Dept dept = new Dept();
		dept.setCompanyCode(companyCode);
		List<Dept> deptList = deptService.queryDeptList(dept);
		Job job = new Job();
		job.setCompanyCode(companyCode);
		List<Job> jobList = jobService.queryJobList(job);
		Role role = new Role();
		role.setCompanyCode(companyCode);
		List<Role> roleList = roleService.queryRoleList(role);
		model.addAttribute("deptList", deptList);
		model.addAttribute("jobList", jobList);
		model.addAttribute("roleList", roleList);
		model.addAttribute("obj",obj);
		return "user/editor";
	}

	/**
	 * @Description: 修改用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("updateUser")
	@ResponseBody
	public Map updateUser(User user){
		Map map = Maps.newHashMap();
		try {
			Integer userId = CacheUtils.getUser().getUserId();
			user.setUpdateUserId(userId); //更新者ID
			userService.updateUser(user);
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
	 * @Description: 删除用户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public Map deleteUser(User user){
		Map map = Maps.newHashMap();
		try {		
			Integer userId = CacheUtils.getUser().getUserId();
			user.setUpdateUserId(userId); //更新者ID
			user.setDelFlag(Constants.DEL_FLAG_DEL);
			userService.updateUser(user);
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
