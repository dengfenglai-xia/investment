package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Workplace;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Description: 工位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface WorkplaceMapper {
	 	
	/**
	 * @Description: 查询工位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Workplace> queryWorkplaceList(Workplace workplace);

	/**
	 * @Description: 查询工位基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Workplace> queryWorkplaceBaseList(Workplace workplace);

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
	 * @Description: 查询工位数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryWorkplaceCount(Workplace workplace);

	/**
	 * @Description: 新增工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertWorkplace(Workplace workplace) ;

	/**
	 * @Description: 新增工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertWorkplaceBatch(@Param("list")List placeList) ;

	/**
	 * @Description: 修改工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateWorkplace(Workplace workplace) ;

	/**
	 * 更新工位状态
	 */
	public void updateWorkplaceStateBatch(Workplace workplace);

	/**
	 * @Description: 删除工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteWorkplaceByPrimarykey(Integer placeId) ;
	
}
