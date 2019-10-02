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
import com.ruikun.sys.investment.entity.ContractCarplace;
import com.ruikun.sys.investment.service.ContractCarplaceService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 合同_车位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-28
 */
@Controller
public class ContractCarplaceController {
	
	@Autowired
    private ContractCarplaceService contractCarplaceService;  
		
	/**
	 * @Description: 查询合同_车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("queryContractCarplaceList")
	@ResponseBody
	public Map queryContractCarplaceList(HttpServletRequest request,ContractCarplace contractCarplace){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<ContractCarplace> list = contractCarplaceService.queryContractCarplaceList(contractCarplace);
		map.put(Constants.RESULT_DATA,new PageInfo<ContractCarplace>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("queryContractCarplaceDetailByPrimarykey/{id}")
	public String queryContractCarplaceDetailByPrimarykey(@PathVariable("id")Long id,Model model){
		ContractCarplace contractCarplace = contractCarplaceService.queryContractCarplaceDetailByPrimarykey(id);
		model.addAttribute("contractCarplace", contractCarplace);
		return "contractCarplaceDetail";
	}

	/**
	 * @Description: 查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("queryContractCarplaceDetail}")
	public String queryContractCarplaceDetail(ContractCarplace contractCarplace,Model model){
		contractCarplace = contractCarplaceService.queryContractCarplaceDetail(contractCarplace);
		model.addAttribute("contractCarplace", contractCarplace);
		return "contractCarplaceDetail";
	}

	/**
	 * @Description: 查询合同_车位表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("queryContractCarplaceDetailByPrimarykey")
	@ResponseBody
	public ContractCarplace queryContractCarplaceDetailByPrimarykey(Long id){
		ContractCarplace contractCarplace = contractCarplaceService.queryContractCarplaceDetailByPrimarykey(id);
		return contractCarplace;
	}
	
	/**
	 * @Description: 新增合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("insertContractCarplace")
	@ResponseBody
	public Map insertContractCarplace(ContractCarplace contractCarplace){
		Map map = Maps.newHashMap();
		try {		
			contractCarplaceService.insertContractCarplace(contractCarplace);
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
	 * @Description: 修改合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("updateContractCarplace")
	@ResponseBody
	public Map updateContractCarplace(ContractCarplace contractCarplace){
		Map map = Maps.newHashMap();
		try {		
			contractCarplaceService.updateContractCarplace(contractCarplace);
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
	 * @Description: 删除合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	@RequestMapping("deleteContractCarplace")
	@ResponseBody
	public Map deleteContractCarplace(ContractCarplace contractCarplace){
		Map map = Maps.newHashMap();
		try {		
			contractCarplaceService.deleteContractCarplace(contractCarplace);
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
