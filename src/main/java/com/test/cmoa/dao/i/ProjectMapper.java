package com.test.cmoa.dao.i;

import java.util.List;
import java.util.Map;

import com.test.cmoa.entity.Project;
import com.test.cmoa.entity.ProjectGroup;

public interface ProjectMapper {

	void batchInsertProject(List<Project> params);

	void save(Project pro);

	void saveProjectGroup(ProjectGroup group);

	long getTotalElements(Map<String, Object> myBatisParmas);

	List<Project> getContent(Map<String, Object> myBatisParmas);

	
}
