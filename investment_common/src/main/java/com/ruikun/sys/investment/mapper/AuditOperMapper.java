package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.AuditOper;

import java.util.List;

/**
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-09
 */
public interface AuditOperMapper {
	 	
	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public List<AuditOper> queryAuditOperList(AuditOper auditOper);

	public List<AuditOper> queryAuditRecordList(AuditOper auditOper);

	public int queryMaxSort(AuditOper auditOper);

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
	public void updateAuditOper(AuditOper auditOper) ;
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void deleteAuditOperByPrimarykey(Long id) ;
	
}
