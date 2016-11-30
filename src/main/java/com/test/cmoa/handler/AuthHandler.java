package com.test.cmoa.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("system")
@Controller
public class AuthHandler {

	@RequestMapping("/auth")
	public String toEditUi() {
		return "auth/edit";
	}

}
