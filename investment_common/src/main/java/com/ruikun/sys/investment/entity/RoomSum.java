package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoomSum {

    /**
     * 楼层ID
     */
    private Integer floorId;

    /**
     * 楼层号
     */
    private Integer floorNo;

    /**
     * 总面积
     */
    private double totalArea;

    /**
     * 房间集合
     */
    List<Room> roomList;

}
