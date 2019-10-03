package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.Order;
import com.ruikun.sys.investment.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public void insertOrder() {
		Order order = Order.getInstance();
		System.out.println("订单信息：" + order.getSerialNo()+"|"+order.getTradingTime()+"|"+order.getAmount());
		Order.clear();
	}
}
