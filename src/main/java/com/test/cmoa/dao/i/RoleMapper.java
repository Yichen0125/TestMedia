package com.test.cmoa.dao.i;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.cmoa.entity.Role;

public interface RoleMapper {

	long getTotalElements();

	List<Role> getContent(@Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);

	void delete(Integer id);

	void create(Role role);

	Role getById(Integer id);

	List<Role> getAllList();

	void deleteRoleAuthorityById(Long id);

	void saveRoleAuthority(HashMap<String, Object> params);
}
