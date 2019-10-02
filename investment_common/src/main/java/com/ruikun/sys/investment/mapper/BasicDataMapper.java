package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.BasicData;

/**
 * @Description: 基础数据表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface BasicDataMapper {
	 	
	/**
	 * @Description: 查询基础数据表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<BasicData> queryBasicDataList(BasicData basicData);
	
	/**
	 * @Description: 通过主键查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public BasicData queryBasicDataDetailByPrimarykey(Integer bdId);
		
	/**
	 * @Description: 查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public BasicData queryBasicDataDetail(BasicData basicData);
	
	/**
	 * @Description: 新增基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertBasicData(BasicData basicData) ;
	
	/**
	 * @Description: 修改基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateBasicData(BasicData basicData) ;
	
	/**
	 * @Description: 删除基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteBasicDataByPrimarykey(Integer bdId) ;
	
}
