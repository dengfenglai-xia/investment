package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.Job;

/**
 * @Description: 职务表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
public interface JobMapper {
	 	
	/**
	 * @Description: 查询职务表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Job> queryJobList(Job job);
	
	/**
	 * @Description: 通过主键查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Job queryJobDetailByPrimarykey(Integer jobId);
		
	/**
	 * @Description: 查询职务表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Job queryJobDetail(Job job);
	
	/**
	 * @Description: 新增职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertJob(Job job) ;
	
	/**
	 * @Description: 修改职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateJob(Job job) ;
	
	/**
	 * @Description: 删除职务表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteJobByPrimarykey(Integer jobId) ;
	
}
