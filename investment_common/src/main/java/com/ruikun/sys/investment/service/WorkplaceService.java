package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Workplace;

import java.util.List;

/**
 * @Description: 工位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface WorkplaceService {
	 	
	/**
	 * @Description: 查询工位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Workplace> queryWorkplaceList(Workplace workplace);

	/**
	 * @Description: 查询工位表数
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryWorkplaceCount(Workplace workplace);

	/**
	 * @Description: 通过主键查询工位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Workplace queryWorkplaceDetailByPrimarykey(Integer placeId);
		
	/**
	 * @Description: 查询工位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Workplace queryWorkplaceDetail(Workplace workplace);

	/**
	 * @Description: 新增工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertWorkplace(Workplace workplace) ;
	
	/**
	 * @Description: 修改工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateWorkplace(Workplace workplace) ;

	/**
	 * 更新签约工位状态
	 * @param contractCode
	 * @param version
	 */
	public void updateWorkplaceState(String contractCode, Integer version, String state);

	/**
	 * @Description: 删除工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteWorkplaceByPrimarykey(Integer placeId) ;
	
}
