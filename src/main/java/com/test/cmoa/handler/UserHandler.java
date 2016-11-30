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

		request.setAttribute("queryString", queryString);

		Page<User> page = userService.getPage(pageNo, params);

		request.setAttribute("page", page);

		return "user/list";
	}

	/**
	 * 生成权限菜单
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/navigate") public List<Navigation> navigate(HttpSession
	 * session){ User user = (User) session.getAttribute("user");
	 * List<Navigation> navigations = new ArrayList<Navigation>(); String
	 * contextPath = session.getServletContext().getContextPath(); Navigation
	 * top = new Navigation(Long.MAX_VALUE, "CMOA 功能系统"); navigations.add(top);
	 * Map<Long, Navigation> parentNavigationsMap = new HashMap<Long,
	 * Navigation>(); List<Authority> authorities =
	 * user.getRole().getAuthorities(); for(Authority authority: authorities){
	 * Navigation navigation = new Navigation(authority.getId(),
	 * authority.getDisplayName()); navigation.setUrl(contextPath +
	 * authority.getUrl()); Authority parentAuthority =
	 * authority.getParentAuthority(); if(parentAuthority != null) { Navigation
	 * parentNavigation = parentNavigationsMap.get(parentAuthority.getId());
	 * if(parentNavigation == null){ parentNavigation = new
	 * Navigation(authority.getId(), authority.getDisplayName());
	 * 
	 * parentNavigation.setState("closed");
	 * 
	 * parentNavigationsMap.put(authority.getId(), parentNavigation);
	 * 
	 * top.getChildren().add(parentNavigation); }
	 * parentNavigation.getChildren().add(navigation); } else {
	 * top.getChildren().add(navigation);
	 * parentNavigationsMap.put(authority.getId(), navigation); } } return
	 * navigations; }
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/navigate") public List<Navigation> navigate(HttpSession
	 * session){ User user = (User) session.getAttribute("user");
	 * List<Navigation> navigations = new ArrayList<Navigation>();
	 * 
	 * String contextPath = session.getServletContext().getContextPath();
	 * 
	 * Navigation top = new Navigation(Long.MAX_VALUE,"CMOA 系统");
	 * 
	 * 
	 * List<Navigation> parentNavigations = new ArrayList<Navigation>();
	 * 
	 * List<Authority> authorities = user.getRole().getAuthorities(); for
	 * (Authority authority : authorities) { Navigation navigation = new
	 * Navigation(authority.getId(), authority.getDisplayName());
	 * navigation.setUrl(contextPath + authority.getUrl()); Authority
	 * parentAuthority = authority.getParentAuthority();
	 * 
	 * if (parentAuthority == null){ Navigation parentNavigation = new
	 * Navigation(authority.getId(), authority.getDisplayName());
	 * 
	 * parentNavigation.getChildren().add(navigation);
	 * 
	 * top.getChildren().add(parentNavigation); }
	 * 
	 * } navigations.add(top); return navigations; }
	 */
	@ResponseBody
	@RequestMapping("/navigate")
	public List<Navigation> navigate(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Navigation> navigations = new ArrayList<Navigation>();
		String contextPath = session.getServletContext().getContextPath();

		Navigation top = new Navigation(Long.MAX_VALUE, "CMOA 功能系统");
		navigations.add(top);

		Map<Long, Navigation> parentNavigations = new Hashtable<Long, Navigation>();
		System.out.println(user.getRole().getAuthorities());
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
	}

	/*
	 * public List<Navigation> navigation(HttpSession session) {
	 * List<Navigation> navigations = new ArrayList<Navigation>(); // 登录的用户 User
	 * user = (User) session.getAttribute("user"); // 当前用户拥有的权限 List<Authority>
	 * authorities = user.getRole().getAuthorities();
	 * 
	 * String contextPath = session.getServletContext().getContextPath();
	 * 
	 * Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
	 * navigations.add(top);
	 * 
	 * Map<Long, Navigation> parentNavigations = new HashMap<Long,
	 * Navigation>();
	 * 
	 * for (Authority authority : authorities) { Navigation navigation = new
	 * Navigation(authority.getId(), authority.getDisplayName());
	 * authority.setUrl(contextPath + authority.getUrl());
	 * 
	 * Authority parentAuthority = authority.getParentAuthority(); Navigation
	 * parentNavigation = parentNavigations.get(parentAuthority.getId());
	 * if(parentNavigation == null) { parentNavigation = new
	 * Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
	 * parentNavigation.setState("closed");
	 * 
	 * parentNavigations.put(parentAuthority.getId(), parentNavigation);
	 * top.getChildren().add(parentNavigation); }
	 * 
	 * parentNavigation.getChildren().add(navigation);
	 * 
	 * }
	 * 
	 * return navigations; }
	 */

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

	/**
	 * 1. 使用 SpringMVC 提供的 RedirectAttributes API 可以把 key-value 对重定向的情况下,
	 * 在页面上给予显示. 1). 在目标方法的参数中添加 RedirectAttributes 参数. 2). 具体调用
	 * RedirectAttributes 的 addFlashAttribute(key, val) 来添加键值对. 3). 重定向到目标资源.
	 * 但不能直接重定向到其物理页面. 而需要经过 SpringMVC 处理一下.
	 * <mvc:view-controller path="/index" view-name="index"/> 4). 页面上使用
	 * javascript 和 JSTL 标签结合来显示错误消息.
	 * 
	 * 2. 错误消息如何放在国际化资源文件中. 1). 在 SpringMVC 中配置国际化资源文件 配置
	 * org.springframework.context.support.ResourceBundleMessageSource bean. 且
	 * id 必须为 messageSource 2). 在类路径下新建国际化资源文件, 加入 key-value 对. 3). 在 Handler
	 * 中自动装配 ResourceBundleMessageSource 属性 4). 调用 getMessage(String code,
	 * Object[] args, Locale locale) 方法来获取国际化资源文件中的 value 值. 5). Locale
	 * 可以直接在目标方法中传入.
	 */
	/*@Deprecated
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password, HttpSession session,
			RedirectAttributes attributes, Locale locale) {
		User user = userService.login(name, password);
		if (user == null) {
			String code = "error.user.login";
			String message = messageSource.getMessage(code, null, locale);
			attributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}

		session.setAttribute("user", user);
		return "home/success";
	}*/

	/*
	 * @Autowired private UserService userService;
	 * 
	 * @Autowired private ResourceBundleMessageSource messageSource;
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping("/navigate") public List<Navigation> navigate(HttpSession
	 * session){ User user = (User) session.getAttribute("user");
	 * List<Navigation> navigations = new ArrayList<Navigation>(); String
	 * contextPath = session.getServletContext().getContextPath();
	 * 
	 * Navigation top = new Navigation(Long.MAX_VALUE, "CMOA 功能系统");
	 * navigations.add(top);
	 * 
	 * Map<Long, Navigation> parentNavigations = new HashMap<Long,
	 * Navigation>();
	 * 
	 * for(Authority authority: user.getRole().getAuthorities()){ Navigation
	 * navigation = new Navigation(authority.getId(),
	 * authority.getDisplayName()); navigation.setUrl(contextPath +
	 * authority.getUrl());
	 * 
	 * Authority parentAuthority = authority.getParentAuthority(); Navigation
	 * parentNavigation = parentNavigations.get(parentAuthority.getId());
	 * if(parentNavigation == null){ parentNavigation = new
	 * Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
	 * parentNavigation.setState("closed");
	 * 
	 * parentNavigations.put(parentAuthority.getId(), parentNavigation);
	 * top.getChildren().add(parentNavigation); }
	 * 
	 * parentNavigation.getChildren().add(navigation); }
	 * 
	 * return navigations; }
	 * 
	 * @RequestMapping(value="/shiro-login", method=RequestMethod.POST) public
	 * String login2(@RequestParam(value="name",required=false) String name,
	 * 
	 * @RequestParam(value="password", required=false) String password,
	 * HttpSession session, RedirectAttributes attributes, Locale locale){
	 * Subject currentUser = SecurityUtils.getSubject();
	 * 
	 * if (!currentUser.isAuthenticated()) { UsernamePasswordToken token = new
	 * UsernamePasswordToken(name, password); token.setRememberMe(true); try {
	 * currentUser.login(token); } catch (AuthenticationException ae) { String
	 * code = "error.user.login"; String message =
	 * messageSource.getMessage(code, null, locale);
	 * attributes.addFlashAttribute("message", message); return
	 * "redirect:/index"; } }
	 * 
	 * session.setAttribute("user",
	 * currentUser.getPrincipals().getPrimaryPrincipal()); return
	 * "home/success"; }
	 * 
	 * @RequestMapping(value="/login", method=RequestMethod.POST) public String
	 * login(@RequestParam(value="name",required=false) String name,
	 * 
	 * @RequestParam(value="password", required=false) String password,
	 * HttpSession session, RedirectAttributes attributes, Locale locale){ User
	 * user = userService.login(name, password); if(user == null){ String code =
	 * "error.user.login"; String message = messageSource.getMessage(code, null,
	 * locale); attributes.addFlashAttribute("message", message); return
	 * "redirect:/index"; }
	 * 
	 * session.setAttribute("user", user); return "home/success"; }
	 */

}
