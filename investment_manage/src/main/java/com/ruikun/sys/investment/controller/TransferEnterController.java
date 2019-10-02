package com.ruikun.sys.investment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.ruikun.sys.investment.entity.TransferRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.TransferEnter;
import com.ruikun.sys.investment.service.TransferEnterService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 结转记录相关操作
 * @author: xiachunyu
 * @date: 2019-07-18
 */
@Controller
public class TransferEnterController {
	
	@Autowired
    private TransferEnterService transferEnterService;  
		
	/**
	 * @Description: 查询结转记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferEnterList")
	@ResponseBody
	public Map queryTransferEnterList(HttpServletRequest request,TransferEnter transferEnter){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<TransferEnter> list = transferEnterService.queryTransferEnterList(transferEnter);
		map.put(Constants.RESULT_DATA,new PageInfo<TransferEnter>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferEnterDetailByPrimarykey/{enterId}")
	public String queryTransferEnterDetailByPrimarykey(@PathVariable("enterId")Long enterId,Model model){
		TransferEnter transferEnter = transferEnterService.queryTransferEnterDetailByPrimarykey(enterId);
		model.addAttribute("transferEnter", transferEnter);
		return "transferEnterDetail";
	}

	/**
	 * @Description: 查询结转记录信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferEnterDetail}")
	public String queryTransferEnterDetail(TransferEnter transferEnter,Model model){
		transferEnter = transferEnterService.queryTransferEnterDetail(transferEnter);
		model.addAttribute("transferEnter", transferEnter);
		return "transferEnterDetail";
	}

	/**
	 * @Description: 查询结转记录信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("queryTransferEnterDetailByPrimarykey")
	@ResponseBody
	public TransferEnter queryTransferEnterDetailByPrimarykey(Long enterId){
		TransferEnter transferEnter = transferEnterService.queryTransferEnterDetailByPrimarykey(enterId);
		return transferEnter;
	}
	
	/**
	 * @Description: 新增结转记录信息
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("insertTransferEnter")
	@ResponseBody
	public Map insertTransferEnter(TransferEnter transferEnter){
		Map map = Maps.newHashMap();
		try {		
			transferEnterService.insertTransferEnter(transferEnter);
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
	@RequestMapping("updateTransferEnter")
	@ResponseBody
	public Map updateTransferEnter(TransferEnter transferEnter){
		Map map = Maps.newHashMap();
		try {		
			transferEnterService.updateTransferEnter(transferEnter);
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
	@RequestMapping("deleteTransferEnterByPrimarykey")
	@ResponseBody
	public Map deleteTransferEnterByPrimarykey(Long enterId){
		Map map = Maps.newHashMap();
		try {		
			transferEnterService.deleteTransferEnterByPrimarykey(enterId);
			map.put(Constants.MSG, "删除成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}

	/**
	 * @Description: 查询结转记录信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-18
	 */
	@RequestMapping("querytransferInfoList")
	@ResponseBody
	public List querytransferInfoList(TransferRecord transferRecord){
		return transferEnterService.querytransferInfoList(transferRecord);
	}

}
