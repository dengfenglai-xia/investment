package com.ruikun.sys.investment.controller;

import com.ruikun.sys.investment.entity.Order;
import com.ruikun.sys.investment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	/**
	 * @Description: 跳转到审核配置
	 * @author: xiachunyu
	 * @date: 2019-07-08
	 */
	@RequestMapping("insertOrder")
	@ResponseBody
	public void insertOrder(Order order){
		Order.setInstance(order);
		orderService.insertOrder();
	}

}
