package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.AuditItem;
import com.ruikun.sys.investment.mapper.AuditItemMapper;
import com.ruikun.sys.investment.service.AuditItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class AuditItemServiceImpl implements AuditItemService {
	
	@Autowired
	private AuditItemMapper auditItemMapper;
	
	/**
	 * @Description: 查询信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public List<AuditItem> queryAuditItemList(AuditItem auditItem){
		return auditItemMapper.queryAuditItemList(auditItem);
	}
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public AuditItem queryAuditItemDetailByPrimarykey(Integer id){
		return auditItemMapper.queryAuditItemDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public AuditItem queryAuditItemDetail(AuditItem auditItem){
		return auditItemMapper.queryAuditItemDetail(auditItem);
	}
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void insertAuditItem(AuditItem auditItem) {
		Date date = new Date(); // 当前时间
		auditItemMapper.insertAuditItem(auditItem);
	}
	
	/**
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void updateAuditItem(AuditItem auditItem) {
		auditItemMapper.updateAuditItem(auditItem);
	}
	
	/**
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	public void deleteAuditItemByPrimarykey(Integer id) {
		auditItemMapper.deleteAuditItemByPrimarykey(id);
	}
	
}
