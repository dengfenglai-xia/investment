package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.DeptMapper;	
import com.ruikun.sys.investment.entity.Dept;
import com.ruikun.sys.investment.service.DeptService;
 @Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptMapper deptMapper;
	
	/**
	 * @Description: 查询部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<Dept> queryDeptList(Dept dept){
		return deptMapper.queryDeptList(dept);
	}
	
	/**
	 * @Description: 通过主键查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Dept queryDeptDetailByPrimarykey(Integer deptId){
		return deptMapper.queryDeptDetailByPrimarykey(deptId);
	}
	
	/**
	 * @Description: 查询部门表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public Dept queryDeptDetail(Dept dept){
		return deptMapper.queryDeptDetail(dept);
	}
	
	/**
	 * @Description: 新增部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertDept(Dept dept) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		dept.setCreateUserId(userId); // 创建者ID
		dept.setUpdateUserId(userId); // 更新者ID
		dept.setCreateTime(date); // 创建时间
		dept.setUpdateTime(date); // 更新时间
		deptMapper.insertDept(dept);
	}
	
	/**
	 * @Description: 修改部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateDept(Dept dept) {
		Integer userId = CacheUtils.getUser().getUserId();
		dept.setUpdateUserId(userId); //更新者ID
		dept.setUpdateTime(new Date()); //更新时间
		deptMapper.updateDept(dept);
	}
	
	/**
	 * @Description: 删除部门表信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteDeptByPrimarykey(Integer deptId) {
		deptMapper.deleteDeptByPrimarykey(deptId);
	}
	
}
