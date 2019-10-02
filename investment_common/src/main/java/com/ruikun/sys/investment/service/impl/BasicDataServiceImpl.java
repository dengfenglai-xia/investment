package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.BasicDataMapper;	
import com.ruikun.sys.investment.entity.BasicData;
import com.ruikun.sys.investment.service.BasicDataService;
 @Service
public class BasicDataServiceImpl implements BasicDataService {
	
	@Autowired
	private BasicDataMapper basicDataMapper;
	
	/**
	 * @Description: 查询基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<BasicData> queryBasicDataList(BasicData basicData){
		return basicDataMapper.queryBasicDataList(basicData);
	}
	
	/**
	 * @Description: 通过主键查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public BasicData queryBasicDataDetailByPrimarykey(Integer bdId){
		return basicDataMapper.queryBasicDataDetailByPrimarykey(bdId);
	}
	
	/**
	 * @Description: 查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public BasicData queryBasicDataDetail(BasicData basicData){
		return basicDataMapper.queryBasicDataDetail(basicData);
	}
	
	/**
	 * @Description: 新增基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertBasicData(BasicData basicData) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		basicData.setCreateUserId(userId); // 创建者ID
		basicData.setUpdateUserId(userId); // 更新者ID
		basicData.setCreateTime(date); // 创建时间
		basicData.setUpdateTime(date); // 更新时间
		basicDataMapper.insertBasicData(basicData);
	}
	
	/**
	 * @Description: 修改基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateBasicData(BasicData basicData) {
		Integer userId = CacheUtils.getUser().getUserId();
		basicData.setUpdateUserId(userId); //更新者ID
		basicData.setUpdateTime(new Date()); //更新时间
		basicDataMapper.updateBasicData(basicData);
	}
	
	/**
	 * @Description: 删除基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteBasicDataByPrimarykey(Integer bdId) {
		basicDataMapper.deleteBasicDataByPrimarykey(bdId);
	}
	
}
