package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.AuditProcess;
import com.ruikun.sys.investment.mapper.AuditProcessMapper;
import com.ruikun.sys.investment.service.AuditProcessService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class AuditProcessServiceImpl implements AuditProcessService {
	
	@Autowired
	private AuditProcessMapper auditProcessMapper;
	
	/**
	 * @Description: 查询信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public List<AuditProcess> queryAuditProcessList(AuditProcess auditProcess){
		return auditProcessMapper.queryAuditProcessList(auditProcess);
	}
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditProcess queryAuditProcessDetailByPrimarykey(Long id){
		return auditProcessMapper.queryAuditProcessDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditProcess queryAuditProcessDetail(AuditProcess auditProcess){
		return auditProcessMapper.queryAuditProcessDetail(auditProcess);
	}
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void insertAuditProcess(AuditProcess auditProcess) {
		Date date = new Date(); // 当前时间
		auditProcess.setCreateTime(date);
		auditProcess.setUpdateTime(date);
		int maxSort = auditProcessMapper.queryMaxSort(auditProcess);
		auditProcess.setSort(maxSort+1);
		auditProcessMapper.insertAuditProcess(auditProcess);
	}
	
	/**
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void updateAuditProcess(AuditProcess auditProcess) {
		Integer userId = CacheUtils.getUser().getUserId();
		auditProcess.setUpdateUserId(userId); //更新者ID
		auditProcess.setUpdateTime(new Date()); //更新时间
		auditProcessMapper.updateAuditProcess(auditProcess);
	}
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void deleteAuditProcessByPrimarykey(Long id) {
		auditProcessMapper.deleteAuditProcessByPrimarykey(id);
	}
	
}
