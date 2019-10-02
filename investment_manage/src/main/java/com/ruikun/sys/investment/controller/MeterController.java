package com.ruikun.sys.investment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruikun.sys.investment.entity.Building;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.BuildingService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.ExportExcelUtil;
import com.ruikun.sys.investment.utils.ReadConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Meter;
import com.ruikun.sys.investment.service.MeterService;
import com.ruikun.sys.investment.utils.PagingUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 抄表数据相关操作
 * @author: xiachunyu
 * @date: 2019-09-26
 */
@Controller
public class MeterController {

	@Autowired
    private MeterService meterService;
	@Autowired
	private BuildingService buildingService;

	@RequestMapping("toEnergyList/{type}")
	public String toEnergyList(@PathVariable("type")String type,Model model){
		User user = CacheUtils.getUser();
		String companyCode = user.getCompanyCode();
		Building building = new Building();
		building.setCompanyCode(companyCode);
		List<Building> buildingList = buildingService.queryBuildingBaseList(building);
		model.addAttribute("buildingList",buildingList);
		if("1".equals(type)){
			return "meter/electricList";
		}else if("2".equals(type)){
			return "meter/waterList";
		}else{
			return "meter/gasList";
		}
	}

	/**
	 * @Description: 查询抄表数据信息列表
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("queryMeterList")
	@ResponseBody
	public Map queryMeterList(HttpServletRequest request,Meter meter){
		Map map = Maps.newHashMap();
		String dateRange = meter.getDateRange();
		if (StringUtils.isNotEmpty(dateRange)) {
			meter.setStartDate(dateRange.split("~")[0].trim());
			meter.setEndDate(dateRange.split("~")[1].trim());
		}
		PagingUtil.setPageParam(request);
		List<Meter> list = meterService.queryMeterList(meter);
		map.put(Constants.RESULT_DATA,new PageInfo<Meter>(list));
        return map;
	}

	/**
	 * @Description: 通过主键查询抄表数据信息详情
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("queryMeterDetailByPrimarykey/{id}")
	public String queryMeterDetailByPrimarykey(@PathVariable("id")Integer id,Model model){
		Meter meter = meterService.queryMeterDetailByPrimarykey(id);
		model.addAttribute("meter", meter);
		return "meterDetail";
	}

	/**
	 * @Description: 查询抄表数据信息详情
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("queryMeterDetail}")
	public String queryMeterDetail(Meter meter,Model model){
		meter = meterService.queryMeterDetail(meter);
		model.addAttribute("meter", meter);
		return "meterDetail";
	}

	/**
	 * @Description: 查询抄表数据信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("queryMeterDetailByPrimarykey")
	@ResponseBody
	public Meter queryMeterDetailByPrimarykey(Integer id){
		Meter meter = meterService.queryMeterDetailByPrimarykey(id);
		return meter;
	}

	/**
	 * @Description: 新增抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("insertMeter")
	@ResponseBody
	public Map insertMeter(Meter meter){
		Map map = Maps.newHashMap();
		try {
			meterService.insertMeter(meter);
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
	 * @Description: 修改抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("updateMeter")
	@ResponseBody
	public Map updateMeter(Meter meter){
		Map map = Maps.newHashMap();
		try {
			meterService.updateMeter(meter);
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
	 * @Description: 删除抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	@RequestMapping("deleteMeterByPrimarykey")
	@ResponseBody
	public Map deleteMeterByPrimarykey(Integer id){
		Map map = Maps.newHashMap();
		try {
			meterService.deleteMeterByPrimarykey(id);
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
	 * @Description: 下载模板
	 * @author: xiachunyu
	 * @date: 2019-03-23 16:39:49
	 */
	@RequestMapping("meter/download")
	public void download(String type, HttpServletResponse response) {
		Map params = Maps.newHashMap();
		User user = CacheUtils.getUser();
		String companyCode = user.getCompanyCode();
		Building building = new Building();
		building.setCompanyCode(companyCode);
		List<Building> buildingList = buildingService.queryBuildingBaseList(building);
		params.put("buildingList", buildingList);
		// 模板根路径
		String path = ReadConfig.getProperty("TEMPLATE_ROOT_PATH");
		path = path + "/meter_template.xlsx";
		String fileName = "";
		if("1".equals(type)){
			fileName = "用电抄表模板.xlsx";
		}
		if("2".equals(type)){
			fileName = "用水抄表模板.xlsx";
		}
		if("3".equals(type)){
			fileName = "用气抄表模板.xlsx";
		}
		byte[] excelContent = ExportExcelUtil.genSingleExcelFileData(path, params);
		try {
			ExportExcelUtil.outputFileData(response, excelContent, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 导入数据
	 * @author: xiachunyu
	 * @date: 2019-06-19
	 */
	@RequestMapping("meter/importInfo")
	@ResponseBody
	public Map importInfo(String type, @RequestParam("file") MultipartFile file) {
		Map map = Maps.newHashMap();
		try {
			User user = CacheUtils.getUser();
			Meter meter = new Meter();
			meter.setCreateUserId(user.getUserId());
			meter.setUpdateUserId(user.getUserId());
			meter.setType(type);
			meter.setCompanyCode(user.getCompanyCode());
			map = meterService.importInfo(meter, file);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
		return map;
	}

}
