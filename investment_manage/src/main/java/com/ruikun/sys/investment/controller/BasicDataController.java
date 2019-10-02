package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.BasicData;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.BasicDataService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.DateTimeUtil;
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
 * @Description: 基础数据表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class BasicDataController {
	
	@Autowired
    private BasicDataService basicDataService;

	/*---------------------------- 行业分类start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toIndustryList")
	public String toIndustryList(){
		return "basicData/industry/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddIndustry")
	public String toAddIndustry(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/industry/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateIndustry/{bdId}")
	public String toUpdateIndustry(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/industry/editor";
	}
	/*---------------------------- 行业分类end ----------------------------*/

	/*-------------------------- 项目业态start --------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toBusinessModeList")
	public String toBusinessModeList(){
		return "basicData/businessMode/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddBusinessMode")
	public String toAddBusinessMode(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/businessMode/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateBusinessMode/{bdId}")
	public String toUpdateBusinessMode(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/businessMode/editor";
	}
	/*--------------------------- 项目业态end ---------------------------*/

	/*-------------------------- 楼宇朝向start --------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toOrientationList")
	public String toOrientationList(){
		return "basicData/orientation/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddOrientation")
	public String toAddOrientation(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/orientation/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateOrientation/{bdId}")
	public String toUpdateOrientation(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/orientation/editor";
	}
	/*--------------------------- 楼宇朝向end ---------------------------*/

	/*---------------------------- 租期start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toLeaseTermList")
	public String toLeaseTermList(){
		return "basicData/leaseTerm/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddLeaseTerm")
	public String toAddLeaseTerm(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/leaseTerm/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateLeaseTerm/{bdId}")
	public String toUpdateLeaseTerm(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/leaseTerm/editor";
	}
	/*----------------------------- 租期end -----------------------------*/


	/*---------------------------- 渠道来源start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toChannelSourceList")
	public String toChannelSourceList(){
		return "basicData/channelSource/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddChannelSource")
	public String toAddChannelSource(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/channelSource/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateChannelSource/{bdId}")
	public String toUpdateChannelSource(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/channelSource/editor";
	}
	/*----------------------------- 渠道来源end -----------------------------*/

	/*---------------------------- 配套设施start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toFacilitiesList")
	public String toFacilitiesList(){
		return "basicData/facilities/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddFacilities")
	public String toAddFacilities(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/facilities/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateFacilities/{bdId}")
	public String toUpdateFacilities(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/facilities/editor";
	}
	/*---------------------------- 配套设施end ----------------------------*/

	/*---------------------------- 标签start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toTagsList")
	public String toTagsList(){
		return "basicData/tags/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddTags")
	public String toAddTags(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/tags/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateTags/{bdId}")
	public String toUpdateTags(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/tags/editor";
	}
	/*---------------------------- 标签end ----------------------------*/


	/*---------------------------- 跟进方式start ----------------------------*/
	/**
	 * @Description: 跳转到基础数据列表页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toFollowTypeList")
	public String toFollowTypeList(){
		return "basicData/followType/list";
	}

	/**
	 * @Description: 跳转到添加页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toAddFollowType")
	public String toAddFollowType(Model model){
		User user = CacheUtils.getUser();
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("currentDate", DateTimeUtil.getFormatDate());
		return "basicData/followType/add";
	}

	/**
	 * @Description: 跳转到修改页
	 * @author: xiachunyu
	 * @date: 2019-06-03
	 */
	@RequestMapping("toUpdateFollowType/{bdId}")
	public String toUpdateFollowType(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData",basicData);
		return "basicData/followType/editor";
	}
	/*---------------------------- 跟进方式end ----------------------------*/

	/**
	 * @Description: 查询基础数据表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryBasicDataList")
	@ResponseBody
	public Map queryBasicDataList(HttpServletRequest request,BasicData basicData){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<BasicData> list = basicDataService.queryBasicDataList(basicData);
		map.put(Constants.RESULT_DATA,new PageInfo<BasicData>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryBasicDataDetailByPrimarykey/{bdId}")
	public String queryBasicDataDetailByPrimarykey(@PathVariable("bdId")Integer bdId,Model model){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		model.addAttribute("basicData", basicData);
		return "basicDataDetail";
	}

	/**
	 * @Description: 查询基础数据表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryBasicDataDetail}")
	public String queryBasicDataDetail(BasicData basicData,Model model){
		basicData = basicDataService.queryBasicDataDetail(basicData);
		model.addAttribute("basicData", basicData);
		return "basicDataDetail";
	}

	/**
	 * @Description: 查询基础数据表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("queryBasicDataDetailByPrimarykey")
	@ResponseBody
	public BasicData queryBasicDataDetailByPrimarykey(Integer bdId){
		BasicData basicData = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
		return basicData;
	}

	/**
	 * @Description: 新增基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("insertBasicData")
	@ResponseBody
	public Map insertBasicData(BasicData basicData){
		Map map = Maps.newHashMap();
		try {
			if(basicDataService.queryBasicDataDetail(basicData) != null){
				map.put(Constants.SUCCESS, false);
				map.put(Constants.MSG, "名称已存在，不能重复添加");
				return map;
			}
			basicDataService.insertBasicData(basicData);
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
	 * @Description: 修改基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("updateBasicData")
	@ResponseBody
	public Map updateBasicData(BasicData basicData){
		Map map = Maps.newHashMap();
		try {
			Integer bdId = basicData.getBdId();
			String bdName = basicData.getBdName();
			String bdType = basicData.getBdType();
			BasicData basicData_temp = basicDataService.queryBasicDataDetailByPrimarykey(bdId);
			// 如果修改后的名称和原名称一样，则不判断名称是否被占用
			if(bdName.equals(basicData_temp.getBdName())){
				basicDataService.updateBasicData(basicData);
				map.put(Constants.MSG, "修改成功");
				map.put(Constants.SUCCESS, true);
			}else{
				basicData_temp = new BasicData();
				basicData_temp.setBdType(bdType);
				basicData_temp.setBdName(bdName);
				if(basicDataService.queryBasicDataDetail(basicData_temp) != null){
					map.put(Constants.SUCCESS, false);
					map.put(Constants.MSG, "名称已被占用，修改失败");
				}else{
					basicDataService.updateBasicData(basicData);
					map.put(Constants.MSG, "修改成功");
					map.put(Constants.SUCCESS, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}

	/**
	 * @Description: 删除基础数据表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	@RequestMapping("deleteBasicData")
	@ResponseBody
	public Map deleteBasicData(Integer bdId){
		Map map = Maps.newHashMap();
		BasicData basicData = new BasicData();
		basicData.setBdId(bdId);
		basicData.setDelFlag(Constants.DEL_FLAG_DEL);
		try {
			basicDataService.updateBasicData(basicData);
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
