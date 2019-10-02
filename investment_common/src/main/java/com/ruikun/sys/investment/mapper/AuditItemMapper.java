package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.AuditItem;

/**
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
public interface AuditItemMapper {
	 	
	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<AuditItem> queryAuditItemList(AuditItem auditItem);
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public AuditItem queryAuditItemDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public AuditItem queryAuditItemDetail(AuditItem auditItem);
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertAuditItem(AuditItem auditItem) ;
	
	/**
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateAuditItem(AuditItem auditItem) ;
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteAuditItemByPrimarykey(Integer id) ;
	
}
