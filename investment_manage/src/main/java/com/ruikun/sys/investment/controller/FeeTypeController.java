package com.ruikun.sys.investment.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.ruikun.sys.investment.entity.Job;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.FeeType;
import com.ruikun.sys.investment.service.FeeTypeService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
 
/**
 * @Description: 费用类别相关操作
 * @author: xiachunyu
 * @date: 2019-08-11
 */
@Controller
public class FeeTypeController {
	
	@Autowired
    private FeeTypeService feeTypeService;

	/**
	 * @Description: 跳转到列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toFeeTypeList")
	public String toFeeTypeList(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "feeType/list";
	}

	/**
	 * @Description: 查询费用类别信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("queryFeeTypeList")
	@ResponseBody
	public Map queryFeeTypeList(HttpServletRequest request,FeeType feeType){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<FeeType> list = feeTypeService.queryFeeTypeList(feeType);
		map.put(Constants.RESULT_DATA,new PageInfo<FeeType>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("queryFeeTypeDetailByPrimarykey/{id}")
	public String queryFeeTypeDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		FeeType feeType = feeTypeService.queryFeeTypeDetailByPrimarykey(id);
		model.addAttribute("feeType", feeType);
		return "feeTypeDetail";
	}

	/**
	 * @Description: 查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("queryFeeTypeDetail}")
	public String queryFeeTypeDetail(FeeType feeType,Model model){
		feeType = feeTypeService.queryFeeTypeDetail(feeType);
		model.addAttribute("feeType", feeType);
		return "feeTypeDetail";
	}

	/**
	 * @Description: 查询费用类别信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("queryFeeTypeDetailByPrimarykey")
	@ResponseBody
	public FeeType queryFeeTypeDetailByPrimarykey(Integer id){
		FeeType feeType = feeTypeService.queryFeeTypeDetailByPrimarykey(id);
		return feeType;
	}

	/**
	 * @Description: 跳转到新增页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toAddFeeType")
	public String toAddFeeType(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("companyCode", user.getCompanyCode());
		return "feeType/add";
	}

	/**
	 * @Description: 新增费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("insertFeeType")
	@ResponseBody
	public Map insertFeeType(FeeType feeType){
		Map map = Maps.newHashMap();
		try {		
			feeTypeService.insertFeeType(feeType);
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
	 * @Description: 跳转到编辑页面
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("toUpdateFeeType/{id}")
	public String toUpdateFeeType(@PathVariable("id")Integer id,Model model){
		FeeType feeType = feeTypeService.queryFeeTypeDetailByPrimarykey(id);
		model.addAttribute("feeType",feeType);
		return "feeType/editor";
	}

	/**
	 * @Description: 修改费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("updateFeeType")
	@ResponseBody
	public Map updateFeeType(FeeType feeType){
		Map map = Maps.newHashMap();
		try {		
			feeTypeService.updateFeeType(feeType);
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
	 * @Description: 删除费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	@RequestMapping("deleteFeeType")
	@ResponseBody
	public Map deleteFeeType(FeeType feeType){
		Map map = Maps.newHashMap();
		try {
			feeType.setUpdateTime(new Date());
			feeType.setDelFlag(Constants.DEL_FLAG_DEL);
			feeTypeService.updateFeeType(feeType);
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
