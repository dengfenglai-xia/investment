package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Floor;
import com.ruikun.sys.investment.entity.Room;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.FloorService;
import com.ruikun.sys.investment.service.RoomService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 楼层表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Slf4j
@Controller
@RequestMapping("floor")
public class FloorController {

    @Autowired
    private FloorService floorService;
    @Autowired
    private RoomService roomService;

    /**
     * @Description: 查询楼层表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryFloorList")
    @ResponseBody
    public Map queryFloorList(HttpServletRequest request, Floor floor) {
        Map map = Maps.newHashMap();
        PagingUtil.setPageParam(request);
        List<Floor> list = floorService.queryFloorList(floor);
        map.put(Constants.RESULT_DATA, new PageInfo<Floor>(list));
        return map;
    }

    /**
     * @Description: 查询楼层基础信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryFloorBaseList")
    @ResponseBody
    public Map queryFloorBaseList(Floor floor) {
        Map map = Maps.newHashMap();
        List<Floor> list = floorService.queryFloorList(floor);
        map.put("list", list);
        return map;
    }

    /**
     * @Description: 通过主键查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryFloorDetailByPrimarykey/{floorId}")
    public String queryFloorDetailByPrimarykey(@PathVariable("floorId") Integer floorId, Model model) {
        Floor floor = floorService.queryFloorDetailByPrimarykey(floorId);
        model.addAttribute("floor", floor);
        return "floorDetail";
    }

    /**
     * @Description: 查询楼层表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryFloorDetail}")
    public String queryFloorDetail(Floor floor, Model model) {
        floor = floorService.queryFloorDetail(floor);
        model.addAttribute("floor", floor);
        return "floorDetail";
    }

    /**
     * @Description: 查询楼层表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryFloorDetailByPrimarykey")
    @ResponseBody
    public Floor queryFloorDetailByPrimarykey(Integer floorId) {
        Floor floor = floorService.queryFloorDetailByPrimarykey(floorId);
        return floor;
    }

    /**
     * @Description: 新增楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertFloor")
    @ResponseBody
    public Map insertFloor(HttpServletRequest request, Floor floor) {
        Map map = Maps.newHashMap();
        String type = request.getParameter("type");
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            floor.setCreateUserId(userId); // 创建者ID
            floor.setUpdateUserId(userId); // 更新者ID
            if ("single".equals(type)) {
                Floor detail = floorService.queryFloorDetail(floor);
                if (detail != null) {
                    map.put(Constants.MSG, "此楼层已存在，不能重复添加");
                    map.put(Constants.SUCCESS, false);
                    return map;
                }
                floorService.insertFloor(floor);
            } else if ("multiple".equals(type)) {
                floorService.insertFloorBatch(floor);
            } else {
                log.debug("添加楼层：类型参数异常");
                throw new Exception("参数错误");
            }
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
     * @Description: 修改楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateFloor")
    @ResponseBody
    public Map updateFloor(Floor floor) {
        Map map = Maps.newHashMap();
        try {
            floorService.updateFloor(floor);
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
     * @Description: 批量更新项目状态
     * @author: xiachunyu
     * @date: 2019-03-12
     */
    @RequestMapping("updateFloorImgBatch")
    @ResponseBody
    public Map updateFloorImgBatch(String imgs, @RequestParam("ids[]") String[] ids) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            Floor floor = new Floor();
            floor.setIds(ids);
            floor.setImgs(imgs);
            floor.setUpdateUserId(user.getUserId());
            floor.setUpdateTime(new Date());
            floorService.updateFloorImgBatch(floor);
            map.put(Constants.MSG, "更新成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常,请重试");
        }
        return map;
    }

    /**
     * @Description: 删除楼层表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteFloor")
    @ResponseBody
    public Map deleteFloor(Integer floorId) {
        Map map = Maps.newHashMap();
        try {
            Integer userId = CacheUtils.getUser().getUserId();
            Floor floor = new Floor();
            floor.setFloorId(floorId);
            floor.setUpdateUserId(userId);
            floor.setUpdateTime(new Date());
            floor.setDelFlag(Constants.DEL_FLAG_DEL);

            Room room = new Room();
            room.setFloorId(floorId);
            int count = roomService.queryRoomCount(room);
            if(count > 0){
                map.put(Constants.MSG, "当前楼层下存在房间，暂时不能删除");
                map.put(Constants.SUCCESS, false);
                return map;
            }
            floorService.updateFloor(floor);
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
