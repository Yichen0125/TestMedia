package com.test.cmoa.dao.i;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface BaseMapper<T> {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<T> getContent(Map<String, Object> mybatisParams);

}
