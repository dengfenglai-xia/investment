package com.ruikun.sys.investment.mapper;

import java.util.List;
import com.ruikun.sys.investment.entity.Meter;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * @Description: 抄表数据相关操作
 * @author: xiachunyu
 * @date: 2019-09-26
 */
public interface MeterMapper {
	 	
	/**
	 * @Description: 查询抄表数据信息列表
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public List<Meter> queryMeterList(Meter meter);
	
	/**
	 * @Description: 通过主键查询抄表数据信息详情
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public Meter queryMeterDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询抄表数据信息详情
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public Meter queryMeterDetail(Meter meter);
	
	/**
	 * @Description: 新增抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public void insertMeter(Meter meter) ;

	/**
	 * @Description: 修改抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public void updateMeter(Meter meter) ;
	
	/**
	 * @Description: 删除抄表数据信息
	 * @author: xiachunyu
	 * @date: 2019-09-26
	 */
	public void deleteMeterByPrimarykey(Integer id) ;
	
}
