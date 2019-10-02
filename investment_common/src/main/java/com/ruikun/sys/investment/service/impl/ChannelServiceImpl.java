package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.Channel;
import com.ruikun.sys.investment.mapper.ChannelMapper;
import com.ruikun.sys.investment.service.ChannelService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class ChannelServiceImpl implements ChannelService {
	
	@Autowired
	private ChannelMapper channelMapper;
	
	/**
	 * @Description: 查询渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public List<Channel> queryChannelList(Channel channel){
		return channelMapper.queryChannelList(channel);
	}

	 public List<Channel> queryChannelBaseList(Channel channel){
		 return channelMapper.queryChannelBaseList(channel);
	 }

	/**
	 * @Description: 通过主键查询渠道信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public Channel queryChannelDetailByPrimarykey(Integer channelId){
		return channelMapper.queryChannelDetailByPrimarykey(channelId);
	}
	
	/**
	 * @Description: 查询渠道信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public Channel queryChannelDetail(Channel channel){
		return channelMapper.queryChannelDetail(channel);
	}
	
	/**
	 * @Description: 新增渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void insertChannel(Channel channel) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		channel.setCreateUserId(userId); // 创建者ID
		channel.setUpdateUserId(userId); // 更新者ID
		channel.setCreateTime(date); // 创建时间
		channel.setUpdateTime(date); // 更新时间
		channelMapper.insertChannel(channel);
	}
	
	/**
	 * @Description: 修改渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void updateChannel(Channel channel) {
		Integer userId = CacheUtils.getUser().getUserId();
		channel.setUpdateUserId(userId); //更新者ID
		channel.setUpdateTime(new Date()); //更新时间
		channelMapper.updateChannel(channel);
	}
	
	/**
	 * @Description: 删除渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void deleteChannelByPrimarykey(Integer channelId) {
		channelMapper.deleteChannelByPrimarykey(channelId);
	}
	
}
