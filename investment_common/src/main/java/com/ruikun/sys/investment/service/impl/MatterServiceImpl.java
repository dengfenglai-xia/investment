package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.MatterMapper;	
import com.ruikun.sys.investment.entity.Matter;
import com.ruikun.sys.investment.service.MatterService;
 @Service
public class MatterServiceImpl implements MatterService {
	
	@Autowired
	private MatterMapper matterMapper;
	
	/**
	 * @Description: 查询事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public List<Matter> queryMatterList(Matter matter){
		return matterMapper.queryMatterList(matter);
	}
	
	/**
	 * @Description: 通过主键查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public Matter queryMatterDetailByPrimarykey(Integer id){
		return matterMapper.queryMatterDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询事项信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public Matter queryMatterDetail(Matter matter){
		return matterMapper.queryMatterDetail(matter);
	}
	
	/**
	 * @Description: 新增事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void insertMatter(Matter matter) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		matter.setCreateUserId(userId); // 创建者ID
		matter.setUpdateUserId(userId); // 更新者ID
		matter.setCreateTime(date); // 创建时间
		matter.setUpdateTime(date); // 更新时间
		matterMapper.insertMatter(matter);
	}
	
	/**
	 * @Description: 修改事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void updateMatter(Matter matter) {
		Integer userId = CacheUtils.getUser().getUserId();
		matter.setUpdateUserId(userId); //更新者ID
		matter.setUpdateTime(new Date()); //更新时间
		matterMapper.updateMatter(matter);
	}
	
	/**
	 * @Description: 删除事项信息
	 * @author: xiachunyu
	 * @date: 2019-08-06
	 */
	public void deleteMatterByPrimarykey(Integer id) {
		matterMapper.deleteMatterByPrimarykey(id);
	}
	
}
