package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.ContractReceiptPlan;
import com.ruikun.sys.investment.entity.ContractReceiptPlanSum;
import com.ruikun.sys.investment.service.ContractReceiptPlanService;
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
 * @Description: 合同收款计划表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
@Controller
public class ContractReceiptPlanController {
	
	@Autowired
    private ContractReceiptPlanService contractReceiptPlanService;
	
	/**
	 * @Description: 预览合同收款计划
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("previewReceipt")
	public String previewReceipt(HttpServletRequest request,Model model){
		String sameCode = request.getParameter("sameCode");
		String contractCode = request.getParameter("contractCode");
		Integer version = Integer.parseInt(request.getParameter("version"));
		String operType = request.getParameter("operType");
		List<ContractReceiptPlanSum> planSumList = contractReceiptPlanService.queryContractReceiptPlanSumList(new ContractReceiptPlan(contractCode,version));
		model.addAttribute("planSumList",planSumList);
		model.addAttribute("sameCode",operType);
		model.addAttribute("sameCode",sameCode);
		model.addAttribute("contractCode",contractCode);
		model.addAttribute("version",version);
		model.addAttribute("operType",operType);
		return "contract/receivable";
	}

	/**
	 * @Description: 批量修改合同收款计划
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("updateReceiptBatch")
	@ResponseBody
	public Map updateReceiptBatch(ContractReceiptPlanSum receiptPlanSum){
		Map map = Maps.newHashMap();
		try {
			contractReceiptPlanService.updateContractReceiptPlanBatch(receiptPlanSum.getPlanList());
			map.put(Constants.MSG, "操作成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
		return map;
	}

	/**
	 * @Description: 查询合同收款计划表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractReceiptPlanList")
	@ResponseBody
	public Map queryContractReceiptPlanList(HttpServletRequest request,ContractReceiptPlan contractReceiptPlan){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<ContractReceiptPlan> list = contractReceiptPlanService.queryContractReceiptPlanList(contractReceiptPlan);
		map.put(Constants.RESULT_DATA,new PageInfo<ContractReceiptPlan>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询合同收款计划表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractReceiptPlanDetailByPrimarykey/{id}")
	public String queryContractReceiptPlanDetailByPrimarykey(@PathVariable("id")Long id,Model model){
		ContractReceiptPlan contractReceiptPlan = contractReceiptPlanService.queryContractReceiptPlanDetailByPrimarykey(id);
		model.addAttribute("contractReceiptPlan", contractReceiptPlan);
		return "contractReceiptPlanDetail";
	}

	/**
	 * @Description: 查询合同收款计划表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractReceiptPlanDetail}")
	public String queryContractReceiptPlanDetail(ContractReceiptPlan contractReceiptPlan,Model model){
		contractReceiptPlan = contractReceiptPlanService.queryContractReceiptPlanDetail(contractReceiptPlan);
		model.addAttribute("contractReceiptPlan", contractReceiptPlan);
		return "contractReceiptPlanDetail";
	}

	/**
	 * @Description: 查询合同收款计划表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("queryContractReceiptPlanDetailByPrimarykey")
	@ResponseBody
	public ContractReceiptPlan queryContractReceiptPlanDetailByPrimarykey(Long id){
		ContractReceiptPlan contractReceiptPlan = contractReceiptPlanService.queryContractReceiptPlanDetailByPrimarykey(id);
		return contractReceiptPlan;
	}
	
	/**
	 * @Description: 新增合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("insertContractReceiptPlan")
	@ResponseBody
	public Map insertContractReceiptPlan(ContractReceiptPlan contractReceiptPlan){
		Map map = Maps.newHashMap();
		try {		
			contractReceiptPlanService.insertContractReceiptPlan(contractReceiptPlan);
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
	 * @Description: 修改合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("updateContractReceiptPlan")
	@ResponseBody
	public Map updateContractReceiptPlan(ContractReceiptPlan contractReceiptPlan){
		Map map = Maps.newHashMap();
		try {		
			contractReceiptPlanService.updateContractReceiptPlan(contractReceiptPlan);
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
	 * @Description: 删除合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	@RequestMapping("deleteContractReceiptPlan")
	@ResponseBody
	public Map deleteContractReceiptPlan(String contractCode,Integer version){
		Map map = Maps.newHashMap();
		try {		
			contractReceiptPlanService.deleteContractReceiptPlan(contractCode,version);
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
