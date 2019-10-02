package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Job;
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.JobService;
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
 * @Description: 职务表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Controller
public class JobController {
	
	@Autowired
    private JobService jobService;

	/**
	 * @Description: 跳转到列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toJobList")
	public String toJobList(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "job/list";
	}

	/**
	 * @Description: 查询职务表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryJobList")
	@ResponseBody
	public Map queryJobList(HttpServletRequest request,Job job){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		job.setCompanyCode(user.getCompanyCode());
		PagingUtil.setPageParam(request);
		List<Job> list = jobService.queryJobList(job);
		map.put(Constants.RESULT_DATA,new PageInfo<Job>(list));
        return map;
	}

	/**
	 * @Description: 查询职务表信息列表
	 * @author: xiachunyu
	 * @date: 2019-04-04 16:25:15
	 */
	@RequestMapping("queryJobBaseList")
	@ResponseBody
	public Map queryJobBaseList(Job job) {
		Map map = Maps.newHashMap();
		List<Job> list = jobService.queryJobList(job);
		map.put("list", list);
		return map;
	}

	/**
	 * @Description: 通过主键查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryJobDetailByPrimarykey/{jobId}")
	public String queryJobDetailByPrimarykey(@PathVariable("jobId")Integer jobId,Model model){
		Job job = jobService.queryJobDetailByPrimarykey(jobId);
		model.addAttribute("job", job);
		return "jobDetail";
	}

	/**
	 * @Description: 查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryJobDetail}")
	public String queryJobDetail(Job job,Model model){
		job = jobService.queryJobDetail(job);
		model.addAttribute("job", job);
		return "jobDetail";
	}

	/**
	 * @Description: 查询职务表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryJobDetailByPrimarykey")
	@ResponseBody
	public Job queryJobDetailByPrimarykey(Integer jobId){
		Job job = jobService.queryJobDetailByPrimarykey(jobId);
		return job;
	}

	/**
	 * @Description: 跳转到新增页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toAddJob")
	public String toAddJob(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "job/add";
	}

	/**
	 * @Description: 新增职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("insertJob")
	@ResponseBody
	public Map insertJob(Job job){
		Map map = Maps.newHashMap();
		try {		
			jobService.insertJob(job);
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
	@RequestMapping("toUpdateJob/{jobId}")
	public String toUpdateJob(@PathVariable("jobId")Integer jobId,Model model){
		Job job = jobService.queryJobDetailByPrimarykey(jobId);
		model.addAttribute("job",job);
		return "job/editor";
	}

	/**
	 * @Description: 修改职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("updateJob")
	@ResponseBody
	public Map updateJob(Job job){
		Map map = Maps.newHashMap();
		try {		
			jobService.updateJob(job);
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
	 * @Description: 删除职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("deleteJob")
	@ResponseBody
	public Map deleteJob(Job job){
		Map map = Maps.newHashMap();
		try {
			job.setUpdateTime(new Date());
			job.setDelFlag(Constants.DEL_FLAG_DEL);
			jobService.updateJob(job);
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
