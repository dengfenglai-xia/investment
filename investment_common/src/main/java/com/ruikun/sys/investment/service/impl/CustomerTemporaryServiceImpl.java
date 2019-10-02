package com.ruikun.sys.investment.service.impl;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.CustomerTemporaryMapper;
import com.ruikun.sys.investment.mapper.CustomerTemporaryRoomMapper;
import com.ruikun.sys.investment.mapper.FollowHistoryMapper;
import com.ruikun.sys.investment.mapper.FollowNoticeMapper;
import com.ruikun.sys.investment.service.CustomerTemporaryService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerTemporaryServiceImpl implements CustomerTemporaryService {

    @Autowired
    private CustomerTemporaryMapper customerTemporaryMapper;

    @Autowired
    private FollowHistoryMapper followHistoryMapper;

    @Autowired
    private FollowNoticeMapper followNoticeMapper;

    @Autowired
    private CustomerTemporaryRoomMapper customerTemporaryRoomMapper;

    /**
     * 查询临时客户
     **/
    @Override
    public List<CustomerTemporary> queryCustomerBaseList(CustomerTemporary customerTemporary) {
        return customerTemporaryMapper.queryCustomerBaseList(customerTemporary);
    }

    /**
     * 添加临时客户
     **/
    @Override
    public void insertCustomerTemporary(CustomerTemporary customerTemporary) {
        customerTemporaryMapper.insertCustomer(customerTemporary);
        //跟进计划
        FollowNotice followNotice = customerTemporary.getFollowNotice();
        followNotice.setTemporaryId(customerTemporary.getTemporaryId());
        followNotice.setCreateUserId(customerTemporary.getCreateUserId());
        followNotice.setUpdateUserId(customerTemporary.getUpdateUserId());
        followNoticeMapper.insertFollowNotice(followNotice);
        //房间列表
        List<CustomerTemporaryRoom> roomList = customerTemporary.getCustomerTemporaryRoomList();
        if (roomList != null) {
            for (CustomerTemporaryRoom room : roomList) {
                room.setTemporaryId(customerTemporary.getTemporaryId());
                room.setCreateUserId(customerTemporary.getCreateUserId());
                room.setUpdateUserId(customerTemporary.getUpdateUserId());
                customerTemporaryRoomMapper.insertCustomerTemporaryRoom(room);
            }
        }
    }

    /**
     * 查询临时客户详情
     **/
    @Override
    public CustomerTemporary queryCustomerDetail(Integer temporaryId) {
        CustomerTemporary customerTemporary = customerTemporaryMapper.queryCustomerDetail(temporaryId);
        CustomerTemporaryRoom customerTemporaryRoom = new CustomerTemporaryRoom();
        customerTemporaryRoom.setTemporaryId(temporaryId);
        //查询房源
        List<CustomerTemporaryRoom> customerTemporaryRoomList = customerTemporaryRoomMapper.queryCustomerRoomList(customerTemporaryRoom);
        customerTemporary.setCustomerTemporaryRoomList(customerTemporaryRoomList);
        return customerTemporary;
    }

    /**
     * 查询临时客户数量
     **/
    @Override
    public int queryCustomerTemporaryNum(CustomerTemporary customerTemporary) {
        return customerTemporaryMapper.queryCustomerTemporaryNum(customerTemporary);
    }

    /**
     * 修改临时客户
     **/
    @Override
    public void updateCustomerTemporary(CustomerTemporary customerTemporary) {
        User user = CacheUtils.getUser();
        customerTemporaryRoomMapper.deleteCustomerTemporaryRoomByTemporaryId(customerTemporary.getTemporaryId());
        customerTemporaryMapper.updateCustomerTemporary(customerTemporary);
        //房间列表
        List<CustomerTemporaryRoom> customerTemporaryRoomList = customerTemporary.getCustomerTemporaryRoomList();
        if (customerTemporaryRoomList != null) {
            for (CustomerTemporaryRoom customerTemporaryRoom : customerTemporaryRoomList) {
                customerTemporaryRoom.setUpdateUserId(user.getUserId());
                customerTemporaryRoom.setTemporaryId(customerTemporary.getTemporaryId());
                customerTemporaryRoom.setCreateUserId(user.getUserId());
                customerTemporaryRoomMapper.insertCustomerTemporaryRoom(customerTemporaryRoom);
            }
        }
    }

    /**
     * 删除临时客户
     **/
    @Override
    public void deleteCustomerTemporary(Integer temporaryId) {
        customerTemporaryMapper.deleteCustomerTemporary(temporaryId);
    }
}
