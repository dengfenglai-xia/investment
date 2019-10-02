package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.FeeType;

/**
 * @Description: 费用类别相关操作
 * @author: xiachunyu
 * @date: 2019-08-11
 */
public interface FeeTypeMapper {
	 	
	/**
	 * @Description: 查询费用类别信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public List<FeeType> queryFeeTypeList(FeeType feeType);
	
	/**
	 * @Description: 通过主键查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public FeeType queryFeeTypeDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询费用类别信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public FeeType queryFeeTypeDetail(FeeType feeType);
	
	/**
	 * @Description: 新增费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void insertFeeType(FeeType feeType) ;
	
	/**
	 * @Description: 修改费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void updateFeeType(FeeType feeType) ;
	
	/**
	 * @Description: 删除费用类别信息
	 * @author: xiachunyu
	 * @date: 2019-08-11
	 */
	public void deleteFeeTypeByPrimarykey(Integer id) ;
	
}
