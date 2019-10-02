package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.Settel;
import com.ruikun.sys.investment.entity.SettelReport;

/**
 * @Description: 结算记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-16
 */
public interface SettelService {
	 	
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

	/**
	 * 现金流水统计
	 * @param settelReport
	 * @return
	 */
	public List querySettelReport(SettelReport settelReport);

	public List<String> queryFeeTypeList();
}
