package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.AuditProcess;
import com.ruikun.sys.investment.service.AuditProcessService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
 
/**
 * @Description: 相关操作
 * @author: xiachunyu
 * @date: 2019-07-09
 */
@Controller
public class AuditProcessController {
	
	@Autowired
    private AuditProcessService auditProcessService;  
		
	/**
	 * @Description: 查询信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditProcessList")
	@ResponseBody
	public Map queryAuditProcessList(AuditProcess auditProcess){
		Map map = Maps.newHashMap();
		List<AuditProcess> list = auditProcessService.queryAuditProcessList(auditProcess);
		map.put(Constants.RESULT_DATA,new PageInfo<AuditProcess>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditProcessDetailByPrimarykey/{id}")
	public String queryAuditProcessDetailByPrimarykey(@PathVariable("id")Long id,Model model){
		AuditProcess auditProcess = auditProcessService.queryAuditProcessDetailByPrimarykey(id);
		model.addAttribute("auditProcess", auditProcess);
		return "auditProcessDetail";
	}

	/**
	 * @Description: 查询信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditProcessDetail}")
	public String queryAuditProcessDetail(AuditProcess auditProcess,Model model){
		auditProcess = auditProcessService.queryAuditProcessDetail(auditProcess);
		model.addAttribute("auditProcess", auditProcess);
		return "auditProcessDetail";
	}

	/**
	 * @Description: 查询信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("queryAuditProcessDetailByPrimarykey")
	@ResponseBody
	public AuditProcess queryAuditProcessDetailByPrimarykey(Long id){
		AuditProcess auditProcess = auditProcessService.queryAuditProcessDetailByPrimarykey(id);
		return auditProcess;
	}
	
	/**
	 * @Description: 新增信息
	 * @author: xiachunyu
	 * @date: 2019-07-09
	 */
	@RequestMapping("insertAuditProcess")
	@ResponseBody
	public Map insertAuditProcess(AuditProcess auditProcess){
		Map map = Maps.newHashMap();
		try {
			Integer userId = CacheUtils.getUser().getUserId();
			auditProcess.setCreateUserId(userId); // 创建者ID
			auditProcess.setUpdateUserId(userId); // 更新者ID
			auditProcessService.insertAuditProcess(auditProcess);
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
	@RequestMapping("updateAuditProcess")
	@ResponseBody
	public Map updateAuditProcess(AuditProcess auditProcess){
		Map map = Maps.newHashMap();
		try {		
			auditProcessService.updateAuditProcess(auditProcess);
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
	 * @date: 2019-07-09
	 */
	@RequestMapping("deleteAuditProcess")
	@ResponseBody
	public Map deleteAuditProcess(Long id){
		Map map = Maps.newHashMap();
		try {		
			auditProcessService.deleteAuditProcessByPrimarykey(id);
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
