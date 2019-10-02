package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.AuditProcess;

/**
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-09
 */
public interface AuditProcessService {
	 	
	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public List<AuditProcess> queryAuditProcessList(AuditProcess auditProcess);
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditProcess queryAuditProcessDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public AuditProcess queryAuditProcessDetail(AuditProcess auditProcess);
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void insertAuditProcess(AuditProcess auditProcess) ;
	
	/**
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void updateAuditProcess(AuditProcess auditProcess) ;
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	public void deleteAuditProcessByPrimarykey(Long id) ;
	
}
