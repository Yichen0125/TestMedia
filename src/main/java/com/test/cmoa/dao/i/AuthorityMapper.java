package com.test.cmoa.dao.i;

import java.util.List;

import com.test.cmoa.entity.Authority;

public interface AuthorityMapper {

	List<Authority> getparents();

	List<Authority> getSubAuthorities(Long id);

}
