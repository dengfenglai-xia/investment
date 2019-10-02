package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.BasicData;
import com.ruikun.sys.investment.entity.Channel;
import com.ruikun.sys.investment.entity.Docs;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.BasicDataService;
import com.ruikun.sys.investment.service.ChannelService;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 渠道相关操作
 * @author: xiachunyu
 * @date: 2019-08-01
 */
@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private BasicDataService basicDataService;
    @Autowired
    private DocsService docsService;

    @RequestMapping("toChannelList")
    public String toChannelList(Model model) {
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("sourceList", sourceList);
        return "customer/channelList";
    }

    /**
     * @Description: 查询渠道信息列表
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("queryChannelList")
    @ResponseBody
    public Map queryChannelList(HttpServletRequest request, Channel channel) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        channel.setCompanyCode(user.getCompanyCode());
        PagingUtil.setPageParam(request);
        List<Channel> list = channelService.queryChannelList(channel);
        map.put(Constants.RESULT_DATA, new PageInfo<Channel>(list));
        return map;
    }

    /**
     * @Description: 查询基础信息
     * @author: xiachunyu
     * @date: 2019-04-04 16:25:15
     */
    @RequestMapping("queryChannelBaseList")
    @ResponseBody
    public Map queryChannelBaseList(Channel channel) {
        Map map = Maps.newHashMap();
        List<Channel> list = channelService.queryChannelBaseList(channel);
        map.put("list", list);
        return map;
    }

    /**
     * @Description: 通过主键查询渠道信息详情
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("queryChannelDetailByPrimarykey/{channelId}")
    public String queryChannelDetailByPrimarykey(@PathVariable("channelId") Integer channelId, Model model) {
        Channel channel = channelService.queryChannelDetailByPrimarykey(channelId);
        model.addAttribute("channel", channel);
        return "channelDetail";
    }

    /**
     * @Description: 查询渠道信息详情
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("queryChannelDetail")
    public String queryChannelDetail(Channel channel, Model model) {
        Integer channelId = channel.getChannelId();
        channel = channelService.queryChannelDetail(channel);
        //查询项目文档
        List<Docs> docsList = docsService.queryDocsList(new Docs(channelId, Constants.DOC_TYPE_CHANNEL));
        model.addAttribute("channel", channel);
        model.addAttribute("docsList", docsList);
        return "customer/channelDetail";
    }

    /**
     * @Description: 查询渠道信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("queryChannelDetailByPrimarykey")
    @ResponseBody
    public Channel queryChannelDetailByPrimarykey(Integer channelId) {
        Channel channel = channelService.queryChannelDetailByPrimarykey(channelId);
        return channel;
    }

    /**
     * @Description: 跳转到新增页
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("toAddChannel")
    public String toAddChannel(Model model) {
        //渠道来源
        List<BasicData> sourceList = basicDataService.queryBasicDataList(new BasicData(Constants.DATA_TYPE_CHANNELSOURCE));
        model.addAttribute("sourceList", sourceList);
        return "customer/addChannel";
    }

    /**
     * @Description: 新增渠道信息
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("insertChannel")
    @ResponseBody
    public Map insertChannel(Channel channel) {
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        channel.setCompanyCode(user.getCompanyCode());
        try {
            channelService.insertChannel(channel);
            map.put(Constants.MSG, "添加成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 修改渠道信息
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("updateChannel")
    @ResponseBody
    public Map updateChannel(Channel channel) {
        Map map = Maps.newHashMap();
        try {
            channelService.updateChannel(channel);
            map.put(Constants.MSG, "更新成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除渠道信息
     * @author: xiachunyu
     * @date: 2019-08-01
     */
    @RequestMapping("deleteChannel")
    @ResponseBody
    public Map deleteChannel(Integer channelId) {
        Map map = Maps.newHashMap();
        try {
            Channel channel = new Channel();
            channel.setChannelId(channelId);
            channel.setDelFlag(Constants.DEL_FLAG_DEL);
            channelService.updateChannel(channel);
            map.put(Constants.MSG, "删除成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

}
