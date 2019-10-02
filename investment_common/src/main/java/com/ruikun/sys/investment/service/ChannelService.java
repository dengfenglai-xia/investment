package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Channel;

import java.util.List;

/**
 * @Description: 渠道相关操作
 * @author: xiachunyu
 * @date: 2019-08-01
 */
public interface ChannelService {
	 	
	/**
	 * @Description: 查询渠道信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public List<Channel> queryChannelList(Channel channel);

	public List<Channel> queryChannelBaseList(Channel channel);

	/**
	 * @Description: 通过主键查询渠道信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public Channel queryChannelDetailByPrimarykey(Integer channelId);
		
	/**
	 * @Description: 查询渠道信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public Channel queryChannelDetail(Channel channel);
	
	/**
	 * @Description: 新增渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void insertChannel(Channel channel) ;
	
	/**
	 * @Description: 修改渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void updateChannel(Channel channel) ;
	
	/**
	 * @Description: 删除渠道信息
	 * @author: xiachunyu
	 * @date: 2019-08-01
	 */
	public void deleteChannelByPrimarykey(Integer channelId) ;
	
}
