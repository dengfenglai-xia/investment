package com.ruikun.sys.investment.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ruikun.sys.investment.entity.User;

import java.util.List;
import java.util.Map;

/**
 * JsonUtil转换工具类
 */
public class JsonUtil {
	
	/**
	 * 字符串转json
	 * @return JSONObject
	 */
	public static JSONObject String2Json(String str) {
		return JSON.parseObject(str);
	}
	
	/**
	 * bean转json(此方法会忽略null字段)
	 * @return JSONObject
	 */
	public static JSONObject Bean2Json(Object obj) {
		return JSONObject.parseObject(JSON.toJSONString(obj));
	}
	
	/**
	 * 将List转换成JSONArray
	 * @return JSONObject
	 */
	public static JSONArray List2JsonArray(List list) {
		return JSONArray.parseArray(JSON.toJSONString(list));
	}	
	
	/**
	 * json转bean
	 * 		这是一个例子，具体在业务代码中实现，特别注意bean中字段属性和json数据的key要一致才能完成转换
	 * @return Object
	 */
	public static User Json2Bean(JSONObject obj) {
		return JSON.parseObject(obj.toString(), new TypeReference<User>() {});
	}
	
	/**
	 * jsonArray转beanList
	 * 		这是一个例子，具体在业务代码中实现，特别注意bean中字段属性和json数据的key要一致才能完成转换
	 * @return Object
	 */
	public static List Json2BeanList(JSONArray jsonArray) {
		return JSON.parseObject(jsonArray.toString(), new TypeReference<List<User>>() {});
	}
	
	/**
	 * Map转json
	 * @return JSONObject
	 */
	public static JSONObject Map2Json(Map map) {
		return JSONObject.parseObject(JSON.toJSONString(map));
	}

	/**
	 * Map转json
	 * @return Map
	 */
	public static Map Json2Map(JSONObject obj) {
		return JSONObject.toJavaObject(obj, Map.class);
	}
	
}