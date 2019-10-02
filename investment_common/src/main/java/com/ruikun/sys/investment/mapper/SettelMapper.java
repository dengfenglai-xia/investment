package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.ContractSummaryInfo;
import com.ruikun.sys.investment.entity.Settel;
import com.ruikun.sys.investment.entity.SettelReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 结算记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-16
 */
public interface SettelMapper {
	 	
	/**
	 * @Description: 查询结算记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public List<Settel> querySettelList(Settel settel);
	
	/**
	 * @Description: 通过主键查询结算记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public Settel querySettelDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询结算记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public Settel querySettelDetail(Settel settel);
	
	/**
	 * @Description: 新增结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public void insertSettel(Settel settel) ;

	/**
	 * @Description: 新增结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public void insertSettelBatch(@Param("list")List list) ;

	/**
	 * @Description: 修改结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public void updateSettel(Settel settel) ;
	
	/**
	 * @Description: 删除结算记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-16
	 */
	public void deleteSettelByPrimarykey(Long id) ;

	public List<SettelReport> querySettelReport(SettelReport settelReport);

	public List<Settel> queryContractSummarySettel(ContractSummaryInfo contractSummaryInfo) ;

	public List<String> queryFeeTypeList();
}
