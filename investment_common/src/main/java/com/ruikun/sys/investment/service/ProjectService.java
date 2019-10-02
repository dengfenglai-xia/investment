package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Project;

import java.util.List;

/**
 * @Description: 项目表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface ProjectService {
	 	
	/**
	 * @Description: 查询项目表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Project> queryProjectList(Project project);

	/**
	 * @Description: 查询项目表基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Project> queryProjectBaseList(Project project);

	/**
	 * @Description: 通过主键查询项目表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Project queryProjectDetailByPrimarykey(Integer projectId);
		
	/**
	 * @Description: 查询项目表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Project queryProjectDetail(Project project);
	
	/**
	 * @Description: 新增项目表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertProject(Project project) ;
	
	/**
	 * @Description: 修改项目表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateProject(Project project) ;
	
	/**
	 * @Description: 删除项目表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteProjectByPrimarykey(Integer projectId) ;
	
}
