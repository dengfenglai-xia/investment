package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Floor;

import java.util.List;

/**
 * @Description: 楼层表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface FloorService {

    /**
     * @Description: 查询楼层表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Floor> queryFloorList(Floor floor);

    /**
     * @Description: 查询楼层基础信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Floor> queryFloorBaseList(Floor floor);

    /**
     * @Description: 通过主键查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Floor queryFloorDetailByPrimarykey(Integer floorId);

    /**
     * @Description: 查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Floor queryFloorDetail(Floor floor);

    /**
     * @Description: 新增楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertFloor(Floor floor);

    /**
     * @Description: 批量新增楼层信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertFloorBatch(Floor floor);

    /**
     * @Description: 修改楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateFloor(Floor floor);

    /**
     * @Description: 删除楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteFloorByPrimarykey(Integer floorId);


    /**
     * @Description: 批量更新平面图
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateFloorImgBatch(Floor floor);

}
