package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.ContractRent;
import com.ruikun.sys.investment.service.ContractRentService;
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
 * @Description: 合同_周期费用表相关操作
 * @author: xiachunyu
 * @date: 2019-06-27
 */
@Controller
public class ContractRentController {
	
	@Autowired
    private ContractRentService contractRentService;  
		
	/**
	 * @Description: 查询合同_周期费用表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("queryContractRentList")
	@ResponseBody
	public Map queryContractRentList(HttpServletRequest request,ContractRent contractRent){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<ContractRent> list = contractRentService.queryContractRentList(contractRent);
		map.put(Constants.RESULT_DATA,new PageInfo<ContractRent>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("queryContractRentDetailByPrimarykey/{contractCode}")
	public String queryContractRentDetailByPrimarykey(@PathVariable("contractCode")String contractCode,Model model){
		ContractRent contractRent = contractRentService.queryContractRentDetailByPrimarykey(contractCode);
		model.addAttribute("contractRent", contractRent);
		return "contractRentDetail";
	}

	/**
	 * @Description: 查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("queryContractRentDetail}")
	public String queryContractRentDetail(ContractRent contractRent,Model model){
		contractRent = contractRentService.queryContractRentDetail(contractRent);
		model.addAttribute("contractRent", contractRent);
		return "contractRentDetail";
	}

	/**
	 * @Description: 查询合同_周期费用表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("queryContractRentDetailByPrimarykey")
	@ResponseBody
	public ContractRent queryContractRentDetailByPrimarykey(String contractCode){
		ContractRent contractRent = contractRentService.queryContractRentDetailByPrimarykey(contractCode);
		return contractRent;
	}
	
	/**
	 * @Description: 新增合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("insertContractRent")
	@ResponseBody
	public Map insertContractRent(ContractRent contractRent){
		Map map = Maps.newHashMap();
		try {		
			contractRentService.insertContractRent(contractRent);
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
	 * @Description: 修改合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("updateContractRent")
	@ResponseBody
	public Map updateContractRent(ContractRent contractRent){
		Map map = Maps.newHashMap();
		try {		
			contractRentService.updateContractRent(contractRent);
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
	 * @Description: 删除合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	@RequestMapping("deleteContractRent")
	@ResponseBody
	public Map deleteContractRent(ContractRent contractRent){
		Map map = Maps.newHashMap();
		try {		
			contractRentService.deleteContractRent(contractRent);
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
