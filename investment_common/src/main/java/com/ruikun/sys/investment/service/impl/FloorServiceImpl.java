package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.entity.Building;
import com.ruikun.sys.investment.entity.Floor;
import com.ruikun.sys.investment.mapper.BuildingMapper;
import com.ruikun.sys.investment.mapper.FloorMapper;
import com.ruikun.sys.investment.service.FloorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorMapper floorMapper;
    @Autowired
    private BuildingMapper buildingMapper;

    /**
     * @Description: 查询楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public List<Floor> queryFloorList(Floor floor) {
        return floorMapper.queryFloorList(floor);
    }

    /**
     * @Description: 查询楼层基础信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public List<Floor> queryFloorBaseList(Floor floor) {
        return floorMapper.queryFloorBaseList(floor);
    }

    /**
     * @Description: 通过主键查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Floor queryFloorDetailByPrimarykey(Integer floorId) {
        return floorMapper.queryFloorDetailByPrimarykey(floorId);
    }

    /**
     * @Description: 查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public Floor queryFloorDetail(Floor floor) {
        return floorMapper.queryFloorDetail(floor);
    }

    /**
     * @Description: 新增楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertFloor(Floor floor) {
        // 查询楼宇信息
        Building building = new Building();
        building.setBuildingId(floor.getBuildingId());
        building = buildingMapper.queryBuildingDetail(building);

        if (StringUtils.isEmpty(floor.getFloorName())) {
            floor.setBuildingName(floor.getFloorNo() + "层");
        }
        floor.setBuildingId(building.getBuildingId());
        floor.setBuildingName(building.getBuildingName());
        floor.setProjectId(building.getProjectId());
        floor.setProjectName(building.getProjectName());
        Date date = new Date(); // 当前时间
        floor.setCreateTime(date); // 创建时间
        floor.setUpdateTime(date); // 更新时间
        floorMapper.insertFloor(floor);
    }

    /**
     * @Description: 批量新增楼层信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void insertFloorBatch(Floor floor) {
        Date date = new Date(); // 当前时间
        int floorNo1 = Integer.parseInt(floor.getFloorNo1());
        int floorNo2 = Integer.parseInt(floor.getFloorNo2());
        List list = Lists.newArrayList();
        // 查询数据库中此楼宇下的楼层信息
        List<Floor> floorList = floorMapper.queryFloorBaseList(new Floor(floor.getBuildingId()));
        // 查询楼宇信息
        Building building = new Building();
        building.setBuildingId(floor.getBuildingId());
        building = buildingMapper.queryBuildingDetail(building);
        // 判断楼层在数据库中已经存在，如存在，则跳过
        boolean flag = true;
        for (int i = floorNo1; i <= floorNo2; i++) {
            flag = true;
            for (Floor f : floorList) {
                if (f.getFloorNo().equals(i + "")) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                Floor f = new Floor();
                f.setFloorNo(i + "");
                if (StringUtils.isEmpty(floor.getFloorName())) {
                    f.setFloorName(i + "层");
                }else{
                    f.setFloorName(floor.getFloorName());
                }
                f.setBuildingId(building.getBuildingId());
                f.setBuildingName(building.getBuildingName());
                f.setProjectId(building.getProjectId());
                f.setProjectName(building.getProjectName());
                f.setImgs(floor.getImgs());
                f.setCreateTime(date);
                f.setUpdateTime(date);
                f.setCreateUserId(floor.getCreateUserId());
                f.setUpdateUserId(floor.getUpdateUserId());
                list.add(f);
            }
        }
        if(list.size() > 0){
            floorMapper.insertFloorBatch(list);
        }
    }

    /**
     * @Description: 修改楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void updateFloor(Floor floor) {
        floorMapper.updateFloor(floor);
    }

    /**
     * @Description: 删除楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    public void deleteFloorByPrimarykey(Integer floorId) {
        floorMapper.deleteFloorByPrimarykey(floorId);
    }

    /**
     * @Description: 批量更新平面图
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @Override
    public void updateFloorImgBatch(Floor floor) {
        floorMapper.updateFloorImgBatch(floor);
    }

}
