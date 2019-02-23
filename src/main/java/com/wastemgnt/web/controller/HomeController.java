package com.wastemgnt.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.services.UserService;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class HomeController {

	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(HomeController.class);
	
	@RequestMapping(value = "/startpage", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		return "welcome";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashBoard(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		User user = userService.findByLoginId(name);
		model.addAttribute("login", name);
		boolean isSysAdmin = Utilities.isSystemAdmin(user);
		model.addAttribute("showUsrMgnt", isSysAdmin);
		model.addAttribute("showFoodConfig", isSysAdmin);
		logger.info("This is test message in HomeController");
		return "dashboard";
	}
}
