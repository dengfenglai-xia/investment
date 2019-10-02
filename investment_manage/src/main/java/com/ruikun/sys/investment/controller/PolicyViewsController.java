package com.ruikun.sys.investment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PolicyViewsController {

	@RequestMapping("toCustomerView")
	public String toCustomerView(){
		return "policyViews/customer";
	}

	@RequestMapping("toRentView")
	public String toRentView(){
		return "policyViews/rent";
	}

}
