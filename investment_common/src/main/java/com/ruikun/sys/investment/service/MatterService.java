package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.Matter;

/**
 * @Description: 事项相关操作
 * @author: xiachunyu
 * @date: 2019-08-06
 */
public interface MatterService {
	 	
	/**
	 * @Description: 查询事项信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public List<Matter> queryMatterList(Matter matter);
	
	/**
	 * @Description: 通过主键查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public Matter queryMatterDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public Matter queryMatterDetail(Matter matter);
	
	/**
	 * @Description: 新增事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void insertMatter(Matter matter) ;
	
	/**
	 * @Description: 修改事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void updateMatter(Matter matter) ;
	
	/**
	 * @Description: 删除事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void deleteMatterByPrimarykey(Integer id) ;
	
}
