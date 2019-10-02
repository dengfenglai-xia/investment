package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.TransferEnter;
import com.ruikun.sys.investment.entity.TransferRecord;

/**
 * @Description: 结转记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-18
 */
public interface TransferEnterMapper {
	 	
	/**
	 * @Description: 查询结转记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public List<TransferEnter> queryTransferEnterList(TransferEnter transferEnter);

	public List<TransferRecord> queryTransferRecordList(TransferRecord transferRecord);

	public List<TransferRecord> querytransferInfoList(TransferRecord transferRecord);

	/**
	 * @Description: 通过主键查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferEnter queryTransferEnterDetailByPrimarykey(Long enterId);
		
	/**
	 * @Description: 查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public TransferEnter queryTransferEnterDetail(TransferEnter transferEnter);
	
	/**
	 * @Description: 新增结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void insertTransferEnter(TransferEnter transferEnter) ;
	
	/**
	 * @Description: 修改结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void updateTransferEnter(TransferEnter transferEnter) ;
	
	/**
	 * @Description: 删除结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	public void deleteTransferEnterByPrimarykey(Long enterId) ;
	
}
