package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
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
 * @date: 2019-07-09
 */
@Controller
public class AuditOperController {
	
	@Autowired
    private AuditOperService auditOperService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractRoomService contractRoomService;
	@Autowired
	private ContractCarplaceService contractCarplaceService;
	@Autowired
	private ContractRentService contractRentService;

	@RequestMapping("toAuditList")
	public String toAuditList(){
		return "audit/list";
	}

	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditOperList")
	@ResponseBody
	public Map queryAuditOperList(HttpServletRequest request,AuditOper auditOper){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		auditOper.setUserId(user.getUserId());
		auditOper.setLightenUp(Constants.LIGHTEN_UP_YES);
		PagingUtil.setPageParam(request);
		List<AuditOper> list = auditOperService.queryAuditOperList(auditOper);
		map.put(Constants.RESULT_DATA,new PageInfo<AuditOper>(list));
        return map;
	}

	/**
	 * @Description: 查询合同表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryAuditContractDetail")
	public String queryAuditContractDetail(AuditOper audit,Model model){
		audit = auditOperService.queryAuditOperDetailByPrimarykey(audit.getId());
		String contractCode = audit.getCode();
		Integer version = audit.getVersion();
		Contract contract = contractService.queryContractDetail(new Contract(contractCode,version));
		// 查询合同房间
		List<ContractRoom> roomList = contractRoomService.queryContractRoomList(new ContractRoom(contractCode,version));
		int totalArea = 0;
		for(ContractRoom r:roomList){
			totalArea += r.getBuildArea();
		}
		// 查询合同车位
		List<ContractCarplace> carplaceList = contractCarplaceService.queryContractCarplaceList(new ContractCarplace(contractCode,version));
		// 查询周期费用
		List<ContractRent> rentList = contractRentService.queryContractRentList(new ContractRent(contractCode,version));
		model.addAttribute("contract", contract);
		model.addAttribute("totalArea", totalArea);
		model.addAttribute("roomList", roomList);
		model.addAttribute("rentList", rentList);
		model.addAttribute("carplaceList", carplaceList);
		model.addAttribute("CHARGE_UNIT_MAP", Constants.CHARGE_UNIT_MAP);
		model.addAttribute("ADVANCE_PAY_TYPE_MAP", Constants.ADVANCE_PAY_TYPE_MAP);
		model.addAttribute("audit", audit);
		return "audit/detail";
	}

	/**
	 * @Description: 查询审核记录
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditRecords/{contractCode}/{version}/{id}")
	public String queryAuditRecords(@PathVariable("contractCode") String contractCode,
										@PathVariable("version")Integer version,
											@PathVariable("id")Integer id,Model model){
		AuditOper auditOper = new AuditOper();
		auditOper.setCode(contractCode);
		auditOper.setVersion(version);
		List<AuditOper> recordList = auditOperService.queryAuditRecordList(auditOper);
		model.addAttribute("recordList",recordList);
		model.addAttribute("contractCode",contractCode);
		model.addAttribute("version",version);
		model.addAttribute("id",id);
		return "audit/record";
	}

	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditOperDetailByPrimarykey/{id}")
	public String queryAuditOperDetailByPrimarykey(@PathVariable("id")Long id,Model model){
		AuditOper auditOper = auditOperService.queryAuditOperDetailByPrimarykey(id);
		model.addAttribute("auditOper", auditOper);
		return "auditOperDetail";
	}

	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditOperDetail}")
	public String queryAuditOperDetail(AuditOper auditOper,Model model){
		auditOper = auditOperService.queryAuditOperDetail(auditOper);
		model.addAttribute("auditOper", auditOper);
		return "auditOperDetail";
	}

	/**
	 * @Description: 查询信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditOperDetailByPrimarykey")
	@ResponseBody
	public AuditOper queryAuditOperDetailByPrimarykey(Long id){
		AuditOper auditOper = auditOperService.queryAuditOperDetailByPrimarykey(id);
		return auditOper;
	}
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("insertAuditOper")
	@ResponseBody
	public Map insertAuditOper(AuditOper auditOper){
		Map map = Maps.newHashMap();
		try {		
			auditOperService.insertAuditOper(auditOper);
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
	 * @date: 2019-07-09
	 */
	@RequestMapping("updateAuditOper")
	@ResponseBody
	public Map updateAuditOper(AuditOper auditOper){
		Map map = Maps.newHashMap();
		try {
			auditOperService.updateAuditOper(auditOper);
			map.put(Constants.MSG, "完成审核");
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
	 * @date: 2019-07-09
	 */
	@RequestMapping("deleteAuditOperByPrimarykey")
	@ResponseBody
	public Map deleteAuditOperByPrimarykey(Long id){
		Map map = Maps.newHashMap();
		try {		
			auditOperService.deleteAuditOperByPrimarykey(id);
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
	 * @Description: 查询待审合同列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditOperListForHome")
	@ResponseBody
	public Map queryAuditOperListForHome(HttpServletRequest request,AuditOper auditOper){
		Map map = Maps.newHashMap();
		User user = CacheUtils.getUser();
		auditOper.setUserId(user.getUserId());
		PagingUtil.setPageParam_5(request);
		List<AuditOper> list = auditOperService.queryAuditOperList(auditOper);
		map.put("list", list);
		return map;
	}
	
}
