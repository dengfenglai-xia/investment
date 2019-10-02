package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Carplace;
import com.ruikun.sys.investment.mapper.CarplaceMapper;
import com.ruikun.sys.investment.service.CarplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class CarplaceServiceImpl implements CarplaceService {
	
	@Autowired
	private CarplaceMapper carplaceMapper;
	
	/**
	 * @Description: 查询车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public List<Carplace> queryCarplaceList(Carplace carplace){
		return carplaceMapper.queryCarplaceList(carplace);
	}
	
	/**
	 * @Description: 通过主键查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetailByPrimarykey(Integer placeId){
		return carplaceMapper.queryCarplaceDetailByPrimarykey(placeId);
	}
	
	/**
	 * @Description: 查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetail(Carplace carplace){
		return carplaceMapper.queryCarplaceDetail(carplace);
	}

	 public int queryCarplaceCount(Carplace carplace){
		 return carplaceMapper.queryCarplaceCount(carplace);
	 }

	/**
	 * @Description: 新增车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void insertCarplace(Carplace carplace) {
		Date date = new Date(); // 当前时间
		String whetherOpen = carplace.getWhetherOpen();
		if(Constants.WHETHER_OPEN_YES.equals(whetherOpen)){ // 如果对外招租，默认空置状态
			carplace.setState(Constants.RENTOUT_STATE_VACANT_ING);
		}else{
			carplace.setState(Constants.RENTOUT_STATE_INIT); // 不对外招租
		}
		carplace.setCreateTime(date); // 创建时间
		carplace.setUpdateTime(date); // 更新时间
		carplaceMapper.insertCarplace(carplace);
	}
	
	/**
	 * @Description: 修改车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void updateCarplace(Carplace carplace) {
		String whetherOpen = carplace.getWhetherOpen();
		if(Constants.ROOM_WHETHER_STREET_YES.equals(whetherOpen)){
			carplace.setState(Constants.RENTOUT_STATE_VACANT_ING);
		}else{
			carplace.setState(Constants.RENTOUT_STATE_INIT);
		}
		carplaceMapper.updateCarplace(carplace);
	}
	
	/**
	 * @Description: 删除车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void deleteCarplaceByPrimarykey(Integer placeId) {
		carplaceMapper.deleteCarplaceByPrimarykey(placeId);
	}
	
}
