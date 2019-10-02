package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.CustomerTemporary;

import java.util.List;
import java.util.Map;

public interface CustomerTemporaryService {

    List<CustomerTemporary> queryCustomerBaseList(CustomerTemporary customerTemporary);

    void insertCustomerTemporary(CustomerTemporary customerTemporary);

    CustomerTemporary queryCustomerDetail(Integer temporaryId);

    int queryCustomerTemporaryNum(CustomerTemporary customerTemporary);

    void updateCustomerTemporary(CustomerTemporary customerTemporary);

    void deleteCustomerTemporary(Integer temporaryId);
}
