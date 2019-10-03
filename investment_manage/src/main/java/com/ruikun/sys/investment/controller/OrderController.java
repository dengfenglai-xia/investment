package com.ruikun.sys.investment.controller;

import com.ruikun.sys.investment.entity.Order;
import com.ruikun.sys.investment.service.OrderService;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

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
	public void insertOrder(String amount,String serialNo){
		Order order = Order.getInstance();
		if(StringUtils.isEmpty(amount)){
			order.setAmount(new BigDecimal("0"));
		}else{
			order.setAmount(new BigDecimal(amount));
		}
		order.setSerialNo(serialNo);
		order.setTradingTime(DateTimeUtil.getFormatDateTime());
		orderService.insertOrder();
	}

}
