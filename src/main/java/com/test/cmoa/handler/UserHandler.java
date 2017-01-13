package com.test.cmoa.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.test.cmoa.entity.Authority;
import com.test.cmoa.entity.Role;
import com.test.cmoa.entity.User;
import com.test.cmoa.orm.Navigation;
import com.test.cmoa.orm.Page;
import com.test.cmoa.service.RoleService;
import com.test.cmoa.service.UserService;
import com.test.cmoa.util.DataUtils;

@RequestMapping("/user")
@Controller
public class UserHandler {
	private static Map<Integer, String> allStatus = new HashMap<Integer, String>();

	static {
		allStatus.put(1, "有效");
		allStatus.put(0, "无效");
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	/*@Autowired
	private ResourceBundleMessageSource messageSource;*/

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") Integer id) {

		userService.deleteUser(id);

		return "1";
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String updateUser(User user) {

		userService.updateUser(user);

		return "redirect:/user/list";
	}

	@RequestMapping("/toEditUI/{id}")
	public String toEditUI(@PathVariable("id") Integer id, Map<String, Object> map) {

		List<Role> roles = roleService.getAllList();
		map.put("roles", roles);

		User user = userService.getUserById(id);
		map.put("user", user);
		map.put("allStatus", allStatus);

		return "user/input";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(User user) {

		userService.saveUser(user);

		return "redirect:/user/list";
	}

	@RequestMapping("/toAddUI")
	public String toAddUI(Map<String, Object> map) {

		List<Role> roles = roleService.getAllList();
		map.put("roles", roles);

		User user = new User();
		map.put("user", user);

		map.put("allStatus", allStatus);
		return "user/input";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			HttpServletRequest request) {
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		System.out.println();
		request.setAttribute("queryString", queryString);

		Page<User> page = userService.getPage(pageNo, params);

		request.setAttribute("page", page);

		return "user/list";
	}
	/**
	 * 创建全局变量  Map  存储每个到达这里的Auth...   此时已经没有父权限了
	 *  1. 在数据库加 free 字段  来判断此字段是否为空 --存储3级目录id 
	 *  2. 遍历Auth 如果 free 字段不为空  则在 Map中  根据id - key 值 找到到对应的 Navigation 
	 *  3. Navigation.getChild.add()
	 */
	@ResponseBody
	@RequestMapping("/navigate")
	public List<Navigation> navigate(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Navigation> navigations = new ArrayList<Navigation>();
		String contextPath = session.getServletContext().getContextPath();

		Navigation top = new Navigation(Long.MAX_VALUE, "CMOA 功能系统");
		navigations.add(top);
		
		Map<Long,Navigation> freeNavigations = new Hashtable<Long, Navigation>();
		List<Navigation> navigationList = new ArrayList<Navigation>();
		
		Map<Long, Navigation> parentNavigations = new Hashtable<Long, Navigation>();
		for (Authority authority : user.getRole().getAuthorities()) {
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			navigation.setUrl(contextPath + authority.getUrl());

			Authority parentAuthority = authority.getParentAuthority();
			if (parentAuthority != null) {
				
				//将authority加到navigationList 
				//遍历 创建navigation对象 然后判断
				//判断authority的 freej字段是否为空
				//如果不为空  则
				//Navigation navigation = freeNavigations.get(authority.getId())
				//然后判断navigation是否为空   为空 则表示为三级菜单 创建对象后  加到Map中
				//不为空  则直接getCHildren。add
				Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
				if (parentNavigation == null) {
					parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
					parentNavigation.setState("closed");

					parentNavigations.put(parentAuthority.getId(), parentNavigation);
					top.getChildren().add(parentNavigation);
				}
				
				parentNavigation.getChildren().add(navigation);
			}
		}
		

		return navigations;
	}
	
	
	/*@ResponseBody
	@RequestMapping("/navigate")
	public List<Navigation> navigate(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Navigation> navigations = new ArrayList<Navigation>();
		String contextPath = session.getServletContext().getContextPath();

		Navigation top = new Navigation(Long.MAX_VALUE, "CMOA 功能系统");
		navigations.add(top);

		Map<Long, Navigation> parentNavigations = new Hashtable<Long, Navigation>();
		for (Authority authority : user.getRole().getAuthorities()) {
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			navigation.setUrl(contextPath + authority.getUrl());

			Authority parentAuthority = authority.getParentAuthority();
			if (parentAuthority != null) {
				
				Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
				if (parentNavigation == null) {
					parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
					parentNavigation.setState("closed");

					parentNavigations.put(parentAuthority.getId(), parentNavigation);
					top.getChildren().add(parentNavigation);
				}
				
				parentNavigation.getChildren().add(navigation);
			}
		}
		

		return navigations;
	}*/

	@RequestMapping(value = "/shiro-login", method = RequestMethod.POST)
	public String loginForShiro(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password, HttpSession session,
			RedirectAttributes attributes, Locale locale) {

		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {

			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			token.setRememberMe(true);

			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				/*String code = "error.user.login";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);*/
				return "redirect:/index";
			}
		}
			session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}

}
