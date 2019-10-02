package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.FeeTypeMapper;	
import com.ruikun.sys.investment.entity.FeeType;
import com.ruikun.sys.investment.service.FeeTypeService;
 @Service
public class FeeTypeServiceImpl implements FeeTypeService {
	
	@Autowired
	private FeeTypeMapper feeTypeMapper;
	
	/**
	 * @Description: 查询费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public List<FeeType> queryFeeTypeList(FeeType feeType){
		return feeTypeMapper.queryFeeTypeList(feeType);
	}
	
	/**
	 * @Description: 通过主键查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public FeeType queryFeeTypeDetailByPrimarykey(Integer id){
		return feeTypeMapper.queryFeeTypeDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public FeeType queryFeeTypeDetail(FeeType feeType){
		return feeTypeMapper.queryFeeTypeDetail(feeType);
	}
	
	/**
	 * @Description: 新增费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void insertFeeType(FeeType feeType) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		feeType.setCreateUserId(userId); // 创建者ID
		feeType.setUpdateUserId(userId); // 更新者ID
		feeType.setCreateTime(date); // 创建时间
		feeType.setUpdateTime(date); // 更新时间
		feeTypeMapper.insertFeeType(feeType);
	}
	
	/**
	 * @Description: 修改费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void updateFeeType(FeeType feeType) {
		Integer userId = CacheUtils.getUser().getUserId();
		feeType.setUpdateUserId(userId); //更新者ID
		feeType.setUpdateTime(new Date()); //更新时间
		feeTypeMapper.updateFeeType(feeType);
	}
	
	/**
	 * @Description: 删除费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void deleteFeeTypeByPrimarykey(Integer id) {
		feeTypeMapper.deleteFeeTypeByPrimarykey(id);
	}
	
}
