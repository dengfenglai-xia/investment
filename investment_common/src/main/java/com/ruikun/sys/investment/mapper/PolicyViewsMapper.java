package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Policy;

import java.util.List;

public interface PolicyViewsMapper {

	public List<Policy> queryIndustryList1();

	public List<Policy> queryIndustryList2();

	public List<Policy> querySourceList1();

	public List<Policy> querySourceList2();
}
