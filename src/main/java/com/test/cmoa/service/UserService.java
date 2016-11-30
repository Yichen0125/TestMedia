package com.test.cmoa.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.cmoa.dao.i.UserMapper;
import com.test.cmoa.entity.User;
import com.test.cmoa.orm.Page;
import com.test.cmoa.orm.PropertyFilter;
import com.test.cmoa.util.DataUtils;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Transactional(readOnly = true)
	public User getByUserName(String username) {
		return userMapper.getByName(username);
	}

	@Transactional(readOnly = true)
	public User login(String name, String password) {
		User user = userMapper.getByName(name);

		if (user != null && user.getEnabled() == 1 && user.getPassword().equals(password)) {
			return user;
		}

		return null;
	}

	public Page<User> getPage(Integer pageNo, Map<String, Object> params) {

		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> myBatisParmas = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		long totalElements = userMapper.getTotalElements(myBatisParmas);

		Page<User> page = new Page<User>();
		page.setPageNo(pageNo);
		page.setTotalElements((int) totalElements);

		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();

		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);

		List<User> content = userMapper.getContent(myBatisParmas);
		page.setContent(content);

		return page;
	}

	public void saveUser(User user) {
		String newpwd = DataUtils.md5(user.getPassword());
		user.setPassword(newpwd);
		userMapper.saveUser(user);
	}

	public User getUserById(Integer id) {
		return userMapper.getUserById(id);
	}

	public void updateUser(User user) {
		String newpwd = DataUtils.md5(user.getPassword());
		user.setPassword(newpwd);

		userMapper.updateUser(user);
	}

	public void deleteUser(Integer id) {

		userMapper.deleteUser(id);
	}

	public List<String> getUserList() {
		return userMapper.getUserList();
	}

}
