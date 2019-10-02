package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.Dept;

/**
 * @Description: 部门表相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
public interface DeptService {
	 	
	/**
	 * @Description: 查询部门表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Dept> queryDeptList(Dept dept);
	
	/**
	 * @Description: 通过主键查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Dept queryDeptDetailByPrimarykey(Integer deptId);
		
	/**
	 * @Description: 查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Dept queryDeptDetail(Dept dept);
	
	/**
	 * @Description: 新增部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertDept(Dept dept) ;
	
	/**
	 * @Description: 修改部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateDept(Dept dept) ;
	
	/**
	 * @Description: 删除部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteDeptByPrimarykey(Integer deptId) ;
	
}
