package com.test.cmoa.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.cmoa.entity.Authority;
import com.test.cmoa.entity.Role;
import com.test.cmoa.orm.Page;
import com.test.cmoa.service.AuthorityService;
import com.test.cmoa.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	public String update(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam("id") Long id, @RequestParam("authorities2") List<String> authorities) {
		roleService.deleteRoleAuthorityById(id);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("authorities", authorities);
		roleService.saveRoleAuthority(params);
		return "redirect:/role/list?pageNo" + pageNo;
	}

	@RequestMapping(value = "/create")
	public String create(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			Role role) {
		roleService.create(role);
		return "redirect:/role/list?pageNo" + pageNo;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@RequestParam("pageNo") Integer pageNo, @PathVariable("id") Integer id) {
		roleService.delete(id);
		return "redirect:/role/list?pageNo" + pageNo;
	}

	/*
	 * @RequestMapping(value="/assign/{id}") public String
	 * assign(@PathVariable("id") Integer id, Map<String, Object> map) {
	 * List<Authority> parentAuthorities = authorityService.getAll();
	 * map.put("parentAuthorities", parentAuthorities); Role role =
	 * roleService.getById(id); map.put("role", role); return "role/assign"; }
	 */

	@RequestMapping(value = "/assign/{id}", method = RequestMethod.GET)
	public String assignForm(@PathVariable("id") Long id, Map<String, Object> map, ServletRequest request) {
		int uid = Integer.parseInt(new String(id.toString()));
		Role role = roleService.getById(uid);
		map.put("role", role);
		List<Authority> list = authorityService.getAll();
		map.put("parentAuthorities", list);

		return "/role/assign";
	}

	@RequestMapping("/list")
	public String list(@RequestParam(value = "pageNo", required = false) String pageNoStr, Map<String, Object> map) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		Page<Role> page = roleService.getPage(pageNo);
		map.put("page", page);
		return "role/list";
	}
}
