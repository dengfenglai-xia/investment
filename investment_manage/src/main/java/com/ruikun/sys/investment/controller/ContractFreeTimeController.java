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
import com.ruikun.sys.investment.entity.ContractFreeTime;
import com.ruikun.sys.investment.service.ContractFreeTimeService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 合同免租期表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Controller
public class ContractFreeTimeController {
	
	@Autowired
    private ContractFreeTimeService contractFreeTimeService;  
		
	/**
	 * @Description: 查询合同免租期表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractFreeTimeList")
	@ResponseBody
	public Map queryContractFreeTimeList(HttpServletRequest request,ContractFreeTime contractFreeTime){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<ContractFreeTime> list = contractFreeTimeService.queryContractFreeTimeList(contractFreeTime);
		map.put(Constants.RESULT_DATA,new PageInfo<ContractFreeTime>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractFreeTimeDetailByPrimarykey/{id}")
	public String queryContractFreeTimeDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		ContractFreeTime contractFreeTime = contractFreeTimeService.queryContractFreeTimeDetailByPrimarykey(id);
		model.addAttribute("contractFreeTime", contractFreeTime);
		return "contractFreeTimeDetail";
	}

	/**
	 * @Description: 查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractFreeTimeDetail}")
	public String queryContractFreeTimeDetail(ContractFreeTime contractFreeTime,Model model){
		contractFreeTime = contractFreeTimeService.queryContractFreeTimeDetail(contractFreeTime);
		model.addAttribute("contractFreeTime", contractFreeTime);
		return "contractFreeTimeDetail";
	}

	/**
	 * @Description: 查询合同免租期表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractFreeTimeDetailByPrimarykey")
	@ResponseBody
	public ContractFreeTime queryContractFreeTimeDetailByPrimarykey(Integer id){
		ContractFreeTime contractFreeTime = contractFreeTimeService.queryContractFreeTimeDetailByPrimarykey(id);
		return contractFreeTime;
	}
	
	/**
	 * @Description: 新增合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("insertContractFreeTime")
	@ResponseBody
	public Map insertContractFreeTime(ContractFreeTime contractFreeTime){
		Map map = Maps.newHashMap();
		try {		
			contractFreeTimeService.insertContractFreeTime(contractFreeTime);
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
	 * @Description: 修改合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("updateContractFreeTime")
	@ResponseBody
	public Map updateContractFreeTime(ContractFreeTime contractFreeTime){
		Map map = Maps.newHashMap();
		try {		
			contractFreeTimeService.updateContractFreeTime(contractFreeTime);
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
	 * @Description: 删除合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("deleteContractFreeTimeByPrimarykey")
	@ResponseBody
	public Map deleteContractFreeTimeByPrimarykey(Integer id){
		Map map = Maps.newHashMap();
		try {		
			contractFreeTimeService.deleteContractFreeTimeByPrimarykey(id);
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
