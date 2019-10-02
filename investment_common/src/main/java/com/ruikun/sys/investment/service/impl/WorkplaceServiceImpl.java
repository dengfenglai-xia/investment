package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.ContractWorkplace;
import com.ruikun.sys.investment.entity.Room;
import com.ruikun.sys.investment.entity.Workplace;
import com.ruikun.sys.investment.mapper.ContractRoomMapper;
import com.ruikun.sys.investment.mapper.ContractWorkplaceMapper;
import com.ruikun.sys.investment.mapper.WorkplaceMapper;
import com.ruikun.sys.investment.service.WorkplaceService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class WorkplaceServiceImpl implements WorkplaceService {

	@Autowired
	private WorkplaceMapper workplaceMapper;
	@Autowired
	private ContractWorkplaceMapper contractWorkplaceMapper;

	/**
	 * @Description: 查询工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Workplace> queryWorkplaceList(Workplace workplace){
		return workplaceMapper.queryWorkplaceList(workplace);
	}

	 public int queryWorkplaceCount(Workplace workplace){
		 return workplaceMapper.queryWorkplaceCount(workplace);
	 }

	/**
	 * @Description: 通过主键查询工位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Workplace queryWorkplaceDetailByPrimarykey(Integer placeId){
		return workplaceMapper.queryWorkplaceDetailByPrimarykey(placeId);
	}

	/**
	 * @Description: 查询工位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Workplace queryWorkplaceDetail(Workplace workplace){
		return workplaceMapper.queryWorkplaceDetail(workplace);
	}

	/**
	 * @Description: 新增工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertWorkplace(Workplace workplace) {
		Date date = new Date(); // 当前时间
		String whetherOpen = workplace.getWhetherOpen();
		if(Constants.WHETHER_OPEN_YES.equals(whetherOpen)){ // 如果对外招租，默认空置状态
			workplace.setState(Constants.RENTOUT_STATE_VACANT_ING);
		}else{
			workplace.setState(Constants.RENTOUT_STATE_INIT); // 不对外招租
		}
		workplace.setCreateTime(date); // 创建时间
		workplace.setUpdateTime(date); // 更新时间
		workplaceMapper.insertWorkplace(workplace);
	}

	/**
	 * @Description: 修改工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateWorkplace(Workplace workplace) {
		String whetherOpen = workplace.getWhetherOpen();
		if(Constants.ROOM_WHETHER_STREET_YES.equals(whetherOpen)){
			workplace.setState(Constants.RENTOUT_STATE_VACANT_ING);
		}else{
			workplace.setState(Constants.RENTOUT_STATE_INIT);
		}
		Integer userId = CacheUtils.getUser().getUserId();
		workplace.setUpdateUserId(userId); //更新者ID
		workplace.setUpdateTime(new Date()); //更新时间
		workplaceMapper.updateWorkplace(workplace);
	}

	 /**
	  * 更新签约工位状态
	  * @param contractCode
	  * @param version
	  */
	 @Override
	 public void updateWorkplaceState(String contractCode, Integer version, String state) {
		 List<ContractWorkplace> workplaces = contractWorkplaceMapper.queryContractWorkplaceList(new ContractWorkplace(contractCode,version));
		 if(workplaces.size() > 0){
			 Integer[] placeIds = new Integer[workplaces.size()];
			 for (int i = 0; i < workplaces.size(); i++) {
				 placeIds[i] = workplaces.get(i).getPlaceId();
			 }
			 Workplace workplace = new Workplace();
			 workplace.setState(state);
			 workplace.setPlaceIds(placeIds);
			 workplace.setUpdateTime(new Date());
			 workplaceMapper.updateWorkplaceStateBatch(workplace);
		 }
	 }

	 /**
	 * @Description: 删除工位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteWorkplaceByPrimarykey(Integer placeId) {
		workplaceMapper.deleteWorkplaceByPrimarykey(placeId);
	}

}
