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
import com.ruikun.sys.investment.entity.ContractIncreaseRate;
import com.ruikun.sys.investment.service.ContractIncreaseRateService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 合同递增率表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Controller
public class ContractIncreaseRateController {
	
	@Autowired
    private ContractIncreaseRateService contractIncreaseRateService;  
		
	/**
	 * @Description: 查询合同递增率表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractIncreaseRateList")
	@ResponseBody
	public Map queryContractIncreaseRateList(HttpServletRequest request,ContractIncreaseRate contractIncreaseRate){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<ContractIncreaseRate> list = contractIncreaseRateService.queryContractIncreaseRateList(contractIncreaseRate);
		map.put(Constants.RESULT_DATA,new PageInfo<ContractIncreaseRate>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractIncreaseRateDetailByPrimarykey/{id}")
	public String queryContractIncreaseRateDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		ContractIncreaseRate contractIncreaseRate = contractIncreaseRateService.queryContractIncreaseRateDetailByPrimarykey(id);
		model.addAttribute("contractIncreaseRate", contractIncreaseRate);
		return "contractIncreaseRateDetail";
	}

	/**
	 * @Description: 查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractIncreaseRateDetail}")
	public String queryContractIncreaseRateDetail(ContractIncreaseRate contractIncreaseRate,Model model){
		contractIncreaseRate = contractIncreaseRateService.queryContractIncreaseRateDetail(contractIncreaseRate);
		model.addAttribute("contractIncreaseRate", contractIncreaseRate);
		return "contractIncreaseRateDetail";
	}

	/**
	 * @Description: 查询合同递增率表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractIncreaseRateDetailByPrimarykey")
	@ResponseBody
	public ContractIncreaseRate queryContractIncreaseRateDetailByPrimarykey(Integer id){
		ContractIncreaseRate contractIncreaseRate = contractIncreaseRateService.queryContractIncreaseRateDetailByPrimarykey(id);
		return contractIncreaseRate;
	}
	
	/**
	 * @Description: 新增合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("insertContractIncreaseRate")
	@ResponseBody
	public Map insertContractIncreaseRate(ContractIncreaseRate contractIncreaseRate){
		Map map = Maps.newHashMap();
		try {		
			contractIncreaseRateService.insertContractIncreaseRate(contractIncreaseRate);
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
	 * @Description: 修改合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("updateContractIncreaseRate")
	@ResponseBody
	public Map updateContractIncreaseRate(ContractIncreaseRate contractIncreaseRate){
		Map map = Maps.newHashMap();
		try {		
			contractIncreaseRateService.updateContractIncreaseRate(contractIncreaseRate);
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
	 * @Description: 删除合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("deleteContractIncreaseRateByPrimarykey")
	@ResponseBody
	public Map deleteContractIncreaseRateByPrimarykey(Integer id){
		Map map = Maps.newHashMap();
		try {		
			contractIncreaseRateService.deleteContractIncreaseRateByPrimarykey(id);
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
