package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.AuditItem;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.AuditItemService;
import com.ruikun.sys.investment.service.UserService;
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
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-08
 */
@Controller
public class AuditItemController {
	
	@Autowired
    private AuditItemService auditItemService;
	@Autowired
	private UserService userService;

	/**
	 * @Description: 跳转到审核配置
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toAuditConfig")
	public String toAuditConfig(Model model){
		List<AuditItem> itemList = auditItemService.queryAuditItemList(null);
		User user = CacheUtils.getUser();
		model.addAttribute("itemList", itemList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("companyCode", user.getCompanyCode());
		return "audit/config";
	}

	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryAuditItemList")
	@ResponseBody
	public Map queryAuditItemList(HttpServletRequest request,AuditItem auditItem){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<AuditItem> list = auditItemService.queryAuditItemList(auditItem);
		map.put(Constants.RESULT_DATA,new PageInfo<AuditItem>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryAuditItemDetailByPrimarykey/{id}")
	public String queryAuditItemDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		AuditItem auditItem = auditItemService.queryAuditItemDetailByPrimarykey(id);
		model.addAttribute("auditItem", auditItem);
		return "auditItemDetail";
	}

	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryAuditItemDetail}")
	public String queryAuditItemDetail(AuditItem auditItem,Model model){
		auditItem = auditItemService.queryAuditItemDetail(auditItem);
		model.addAttribute("auditItem", auditItem);
		return "auditItemDetail";
	}

	/**
	 * @Description: 查询信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("queryAuditItemDetailByPrimarykey")
	@ResponseBody
	public AuditItem queryAuditItemDetailByPrimarykey(Integer id){
		AuditItem auditItem = auditItemService.queryAuditItemDetailByPrimarykey(id);
		return auditItem;
	}
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("insertAuditItem")
	@ResponseBody
	public Map insertAuditItem(AuditItem auditItem){
		Map map = Maps.newHashMap();
		try {		
			auditItemService.insertAuditItem(auditItem);
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
	 * @Description: 修改信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("updateAuditItem")
	@ResponseBody
	public Map updateAuditItem(AuditItem auditItem){
		Map map = Maps.newHashMap();
		try {
			Integer userId = CacheUtils.getUser().getUserId();
			auditItemService.updateAuditItem(auditItem);
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
	 * @Description: 删除信息
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("deleteAuditItemByPrimarykey")
	@ResponseBody
	public Map deleteAuditItemByPrimarykey(Integer id){
		Map map = Maps.newHashMap();
		try {		
			auditItemService.deleteAuditItemByPrimarykey(id);
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
