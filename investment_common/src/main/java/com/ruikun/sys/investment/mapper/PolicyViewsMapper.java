package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Policy;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PolicyViewsMapper {

	public List<Policy> queryIndustryList1();

	public List<Policy> queryIndustryList2();

	public List<Policy> querySourceList1();

	public List<Policy> querySourceList2();

    public List<Policy> queryTop5List1(@Param("type") String type);

    public List<Policy> queryTop5List2(@Param("type") String type);

    public List<Policy> queryTendencyList(Map map);

    public List<Policy> queryReasonList();

}
