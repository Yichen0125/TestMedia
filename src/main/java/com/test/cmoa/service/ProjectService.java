package com.test.cmoa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.cmoa.dao.i.ProjectMapper;
import com.test.cmoa.entity.Project;
import com.test.cmoa.entity.ProjectGroup;
import com.test.cmoa.entity.User;
import com.test.cmoa.orm.Page;
import com.test.cmoa.orm.PropertyFilter;
import com.test.cmoa.util.DataUtils;

@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	public void save(Project pro) {
		projectMapper.save(pro);
	}
	public String saveProjectGroup(ProjectGroup group) {
		projectMapper.saveProjectGroup(group);
		return group.getGroupnum();
	}
	public Page<Project> getPage(Integer pageNo, Map<String, Object> params) {
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> myBatisParmas = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		long totalElements = projectMapper.getTotalElements(myBatisParmas);

		Page<Project> page = new Page<Project>();
		page.setPageNo(pageNo);
		page.setTotalElements((int) totalElements);

		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();

		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);

		List<Project> content = projectMapper.getContent(myBatisParmas);
		page.setContent(content);

		return page;
	}
}
