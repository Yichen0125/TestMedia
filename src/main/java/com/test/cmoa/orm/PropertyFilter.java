package com.test.cmoa.orm;

import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 属性过滤器. 封装了属性的查询信息
 */
public class PropertyFilter {

	// 属性名
	private String propertyName;
	// 属性值
	private Object propertyVal;
	// 目标属性的类型
	private Class propertyType;
	// 比较的方式
	private MatchType matchType;

	public PropertyFilter(String fieldName, Object fieldValue) {
		// LIKES_title
		String str1 = StringUtils.substringBefore(fieldName, "_"); // LIKES
		String matchTypeCode = StringUtils.substring(str1, 0, str1.length() - 1); // LIKE
		String propertyTypeCode = StringUtils.substring(str1, str1.length() - 1); // S

		this.matchType = Enum.valueOf(MatchType.class, matchTypeCode); // LIKE
																		// 对应的枚举对象
		PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
		this.propertyType = propertyType.getPropertyType();

		this.propertyName = StringUtils.substringAfterLast(fieldName, "_");

		this.propertyVal = fieldValue;
	}

	public enum MatchType {
		EQ, GT, GE, LT, LE, LIKE;
	}

	public enum PropertyType {
		I(Integer.class), F(Float.class), S(String.class), D(Date.class), O(Object.class);

		private Class propertyType;

		private PropertyType(Class propertyType) {
			this.propertyType = propertyType;
		}

		public Class getPropertyType() {
			return propertyType;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}

}
