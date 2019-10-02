package com.ruikun.sys.investment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.TransferOut;
import com.ruikun.sys.investment.service.TransferOutService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 结转记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-18
 */
@Controller
public class TransferOutController {
	
	@Autowired
    private TransferOutService transferOutService;  
		
	/**
	 * @Description: 查询结转记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferOutList")
	@ResponseBody
	public Map queryTransferOutList(HttpServletRequest request,TransferOut transferOut){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<TransferOut> list = transferOutService.queryTransferOutList(transferOut);
		map.put(Constants.RESULT_DATA,new PageInfo<TransferOut>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferOutDetailByPrimarykey/{outId}")
	public String queryTransferOutDetailByPrimarykey(@PathVariable("outId")Long outId,Model model){
		TransferOut transferOut = transferOutService.queryTransferOutDetailByPrimarykey(outId);
		model.addAttribute("transferOut", transferOut);
		return "transferOutDetail";
	}

	/**
	 * @Description: 查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferOutDetail}")
	public String queryTransferOutDetail(TransferOut transferOut,Model model){
		transferOut = transferOutService.queryTransferOutDetail(transferOut);
		model.addAttribute("transferOut", transferOut);
		return "transferOutDetail";
	}

	/**
	 * @Description: 查询结转记录信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferOutDetailByPrimarykey")
	@ResponseBody
	public TransferOut queryTransferOutDetailByPrimarykey(Long outId){
		TransferOut transferOut = transferOutService.queryTransferOutDetailByPrimarykey(outId);
		return transferOut;
	}
	
	/**
	 * @Description: 新增结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("insertTransferOut")
	@ResponseBody
	public Map insertTransferOut(TransferOut transferOut){
		Map map = Maps.newHashMap();
		try {		
			transferOutService.insertTransferOut(transferOut);
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
	 * @Description: 修改结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("updateTransferOut")
	@ResponseBody
	public Map updateTransferOut(TransferOut transferOut){
		Map map = Maps.newHashMap();
		try {		
			transferOutService.updateTransferOut(transferOut);
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
	 * @Description: 删除结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("deleteTransferOutByPrimarykey")
	@ResponseBody
	public Map deleteTransferOutByPrimarykey(Long outId){
		Map map = Maps.newHashMap();
		try {		
			transferOutService.deleteTransferOutByPrimarykey(outId);
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
