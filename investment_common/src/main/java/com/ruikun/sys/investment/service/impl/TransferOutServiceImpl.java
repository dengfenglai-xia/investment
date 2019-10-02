package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.TransferOutMapper;	
import com.ruikun.sys.investment.entity.TransferOut;
import com.ruikun.sys.investment.service.TransferOutService;
 @Service
public class TransferOutServiceImpl implements TransferOutService {
	
	@Autowired
	private TransferOutMapper transferOutMapper;
	
	/**
	 * @Description: 查询结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public List<TransferOut> queryTransferOutList(TransferOut transferOut){
		return transferOutMapper.queryTransferOutList(transferOut);
	}
	
	/**
	 * @Description: 通过主键查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferOut queryTransferOutDetailByPrimarykey(Long outId){
		return transferOutMapper.queryTransferOutDetailByPrimarykey(outId);
	}
	
	/**
	 * @Description: 查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferOut queryTransferOutDetail(TransferOut transferOut){
		return transferOutMapper.queryTransferOutDetail(transferOut);
	}
	
	/**
	 * @Description: 新增结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void insertTransferOut(TransferOut transferOut) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		transferOut.setCreateUserId(userId); // 创建者ID
		transferOut.setUpdateUserId(userId); // 更新者ID
		transferOut.setCreateTime(date); // 创建时间
		transferOut.setUpdateTime(date); // 更新时间
		transferOutMapper.insertTransferOut(transferOut);
	}
	
	/**
	 * @Description: 修改结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void updateTransferOut(TransferOut transferOut) {
		Integer userId = CacheUtils.getUser().getUserId();
		transferOut.setUpdateUserId(userId); //更新者ID
		transferOut.setUpdateTime(new Date()); //更新时间
		transferOutMapper.updateTransferOut(transferOut);
	}
	
	/**
	 * @Description: 删除结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void deleteTransferOutByPrimarykey(Long outId) {
		transferOutMapper.deleteTransferOutByPrimarykey(outId);
	}
	
}
