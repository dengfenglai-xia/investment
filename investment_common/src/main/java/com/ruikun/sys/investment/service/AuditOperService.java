package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.AuditOper;

import java.util.List;

/**
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-09
 */
public interface AuditOperService {

	/**
	 * 生成审核流
	 * @param contractCode
	 * @param version
	 */
	public void generateAuditOper(String contractCode, Integer version) throws Exception;

	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public List<AuditOper> queryAuditOperList(AuditOper auditOper);

	/**
	 * @Description: 查询审核记录
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public List<AuditOper> queryAuditRecordList(AuditOper auditOper);

	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditOper queryAuditOperDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditOper queryAuditOperDetail(AuditOper auditOper);
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void insertAuditOper(AuditOper auditOper) ;
	
	/**
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void updateAuditOper(AuditOper auditOper) throws Exception;
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void deleteAuditOperByPrimarykey(Long id) ;
	
}
