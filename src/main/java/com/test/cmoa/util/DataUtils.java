package com.test.cmoa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.test.cmoa.orm.PropertyFilter;
import com.test.cmoa.orm.PropertyFilter.MatchType;

public class DataUtils {

	static {
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss" });
		ConvertUtils.register(dateConverter, Date.class);
	}

	/**
	 * 把传入的 params 转为 PropertyFilter 的集合
	 * 
	 * @param filters
	 * @return
	 */
	public static Map<String, Object> parsePropertyFiltersToMyBatisParmas(List<PropertyFilter> filters) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (PropertyFilter filter : filters) {
			String propertyName = filter.getPropertyName();

			Object propertyVal = filter.getPropertyVal();
			Class propertyType = filter.getPropertyType();

			if (propertyType == Date.class && propertyVal == "") {
				params.put(propertyName, null);
				continue;
			}

			propertyVal = ConvertUtils.convert(propertyVal, propertyType);

			MatchType matchType = filter.getMatchType();
			if (matchType == MatchType.LIKE) {
				propertyVal = "%" + propertyVal + "%";
			}

			params.put(propertyName, propertyVal);
		}

		return params;
	}

	/**
	 * 把 RropertyFilter 的集合转为 mybatis 可用的 params
	 * 
	 * @param params
	 * @return
	 */
	public static List<PropertyFilter> parseHandlerParamsToPropertyFilters(Map<String, Object> params) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String fieldName = entry.getKey();
			Object fieldVal = entry.getValue();

			PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
			filters.add(filter);
		}

		return filters;
	}

	public static String encodeParamsToQueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();

			if (val == null || val.toString().trim().equals("")) {
				continue;
			}

			result.append("&").append("search_").append(key).append("=").append(val);
		}

		return result.toString();
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * md5加密方法
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(String source) {

		StringBuilder builder = new StringBuilder();
		char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		try {
			// 获取MD5加密对象
			MessageDigest digest;
			digest = MessageDigest.getInstance("md5");
			// 执行加密操作
			byte[] secret = digest.digest(source.getBytes());

			// 3.将字节数组转换为字符串便于数据库中保存
			// 理论依据：四位2进制数正好是一位16进制数，所以每一个字节的整数都可以表示成一个十六进制数
			for (byte b : secret) {

				// 计算低四位的值
				int lowNum = b & 15;

				// 计算高四位的值
				int highNum = (b >> 4) & 15;

				builder.append(chars[lowNum]).append(chars[highNum]);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}
}
