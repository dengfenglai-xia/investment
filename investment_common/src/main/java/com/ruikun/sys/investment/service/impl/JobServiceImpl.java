package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.JobMapper;	
import com.ruikun.sys.investment.entity.Job;
import com.ruikun.sys.investment.service.JobService;
 @Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	private JobMapper jobMapper;
	
	/**
	 * @Description: 查询职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Job> queryJobList(Job job){
		return jobMapper.queryJobList(job);
	}
	
	/**
	 * @Description: 通过主键查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Job queryJobDetailByPrimarykey(Integer jobId){
		return jobMapper.queryJobDetailByPrimarykey(jobId);
	}
	
	/**
	 * @Description: 查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Job queryJobDetail(Job job){
		return jobMapper.queryJobDetail(job);
	}
	
	/**
	 * @Description: 新增职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertJob(Job job) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		job.setCreateUserId(userId); // 创建者ID
		job.setUpdateUserId(userId); // 更新者ID
		job.setCreateTime(date); // 创建时间
		job.setUpdateTime(date); // 更新时间
		jobMapper.insertJob(job);
	}
	
	/**
	 * @Description: 修改职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateJob(Job job) {
		Integer userId = CacheUtils.getUser().getUserId();
		job.setUpdateUserId(userId); //更新者ID
		job.setUpdateTime(new Date()); //更新时间
		jobMapper.updateJob(job);
	}
	
	/**
	 * @Description: 删除职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteJobByPrimarykey(Integer jobId) {
		jobMapper.deleteJobByPrimarykey(jobId);
	}
	
}
