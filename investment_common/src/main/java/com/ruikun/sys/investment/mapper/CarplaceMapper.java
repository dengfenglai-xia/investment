package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Carplace;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Description: 车位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-24
 */
public interface CarplaceMapper {
	 	
	/**
	 * @Description: 查询车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-24
     * @return
	 */
	public List<Carplace> queryCarplaceList(Carplace carplace);
	
	/**
	 * @Description: 通过主键查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetailByPrimarykey(Integer placeId);

	/**
	 * @Description: 查询车位数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryCarplaceCount(Carplace carplace);

	/**
	 * @Description: 查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetail(Carplace carplace);
	
	/**
	 * @Description: 新增车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void insertCarplace(Carplace carplace) ;

    /**
     * @Description: 新增工位表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertCarplaceBatch(@Param("list")List placeList) ;

	/**
	 * @Description: 修改车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void updateCarplace(Carplace carplace) ;
	
	/**
	 * @Description: 删除车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void deleteCarplaceByPrimarykey(Integer placeId) ;
	
}
