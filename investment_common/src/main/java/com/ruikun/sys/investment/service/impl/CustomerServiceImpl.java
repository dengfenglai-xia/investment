package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.mapper.*;
import com.ruikun.sys.investment.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractRoomMapper contractRoomMapper;
    @Autowired
    private ContractWorkplaceMapper contractWorkplaceMapper;
    @Autowired
    private ContractCarplaceMapper contractCarplaceMapper;

    /**
     * @Description: 查询客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public List<Customer> queryCustomerList(Customer customer) {
        List<Customer> customerList = customerMapper.queryCustomerList(customer);
        for (Customer c : customerList) {
            Integer customerId = c.getCustomerId();
            Contract contract = new Contract();
            contract.setCustomerId(customerId);
            contract.setStateOne(Constants.CONTRACT_STATE_FORMAL);
            int contractNum = contractMapper.queryContractCount(contract);
            // 查询房源合同
            contract.setContractType(Constants.CONTRACT_TYPE_ROOM);
            List<ContractRoom> roomList = contractMapper.queryContractRoomList(contract);
            // 签约工位数量
            contract.setContractType(Constants.CONTRACT_TYPE_WORKPLACE);
            int workplaceNum = contractWorkplaceMapper.queryContractWorkplaceCount(contract);
            // 签约车位数量
            contract.setContractType(Constants.CONTRACT_TYPE_ROOM);
            int carplaceNum = contractCarplaceMapper.queryContractCarplaceCount(contract);
            double totalArea = 0d;
            for (ContractRoom cr : roomList) {
                totalArea += cr.getBuildArea();
            }
            c.setContractNum(contractNum);
            c.setRoomNum(roomList.size());
            c.setWorkplaceNum(workplaceNum);
            c.setCarplaceNum(carplaceNum);
            c.setTotalArea(totalArea);
        }
        return customerList;
    }

    /**
     * @Description: 查询客户基础信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public List<Customer> queryCustomerBaseList(Customer customer) {
        return customerMapper.queryCustomerBaseList(customer);
    }

    /**
     * @Description: 通过主键查询客户表信息详情
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public Customer queryCustomerDetailByPrimarykey(Integer customerId) {
        return customerMapper.queryCustomerDetailByPrimarykey(customerId);
    }

    /**
     * @Description: 查询客户表信息详情
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public Customer queryCustomerDetail(Customer customer) {
        return customerMapper.queryCustomerDetail(customer);
    }

    /**
     * @Description: 新增客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public void insertCustomer(Customer customer) {
        Date date = new Date(); // 当前时间
        customer.setCreateTime(date); // 创建时间
        customer.setUpdateTime(date); // 更新时间
        customerMapper.insertCustomer(customer);
    }

    /**
     * @Description: 修改客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public void updateCustomer(Customer customer) {
        customer.setUpdateTime(new Date()); //更新时间
        customerMapper.updateCustomer(customer);
    }

    /**
     * @Description: 删除客户表信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public void deleteCustomerByPrimarykey(Integer customerId) {
        customerMapper.deleteCustomerByPrimarykey(customerId);
    }

}
