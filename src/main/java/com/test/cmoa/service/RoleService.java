package com.test.cmoa.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.cmoa.dao.i.RoleMapper;
import com.test.cmoa.entity.Role;
import com.test.cmoa.orm.Page;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Transactional(readOnly = true)
	public Page<Role> getPage(int pageNo) {
		Page<Role> page = new Page<Role>();
		page.setPageNo(pageNo);
		long totalElements = roleMapper.getTotalElements();
		page.setTotalElements((int) totalElements);

		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;

		List<Role> content = roleMapper.getContent(fromIndex, endIndex);
		page.setContent(content);
		return page;
	}

	@Transactional
	public void delete(Integer id) {
		roleMapper.delete(id);
	}

	@Transactional
	public void create(Role role) {
		roleMapper.create(role);
	}

	@Transactional(readOnly = true)
	public Role getById(Integer id) {
		Role role = roleMapper.getById(id);
		return role;
	}

	public List<Role> getAllList() {
		return roleMapper.getAllList();
	}

	public void deleteRoleAuthorityById(Long id) {
		roleMapper.deleteRoleAuthorityById(id);
	}

	public void saveRoleAuthority(HashMap<String, Object> params) {
		roleMapper.saveRoleAuthority(params);
	}
}
