package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.TransferOut;

/**
 * @Description: 结转记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-18
 */
public interface TransferOutService {
	 	
	/**
	 * @Description: 查询结转记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public List<TransferOut> queryTransferOutList(TransferOut transferOut);
	
	/**
	 * @Description: 通过主键查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferOut queryTransferOutDetailByPrimarykey(Long outId);
		
	/**
	 * @Description: 查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferOut queryTransferOutDetail(TransferOut transferOut);
	
	/**
	 * @Description: 新增结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void insertTransferOut(TransferOut transferOut) ;
	
	/**
	 * @Description: 修改结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void updateTransferOut(TransferOut transferOut) ;
	
	/**
	 * @Description: 删除结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void deleteTransferOutByPrimarykey(Long outId) ;
	
}
