package com.wastemgnt.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderHasFoodItem;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.services.FoodNameService;
import com.wastemgnt.service.services.OrderHasFoodItemsService;
import com.wastemgnt.service.services.OrderService;
import com.wastemgnt.service.services.UserServiceImpl;
import com.wastemgnt.web.model.FilterType;
import com.wastemgnt.web.model.OrderFoodItemWrapper;
import com.wastemgnt.web.utils.Utilities;

@Controller
public class FoodMetricsController {

	@Autowired
	private OrderHasFoodItemsService service;

	@Autowired
	private FoodNameService foodService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserServiceImpl userService;
	
	private static final Logger logger = LogManager.getLogger(FoodMetricsController.class);

	@RequestMapping(value = "/admin/metrics", method = RequestMethod.GET)
	public String showOrders(Model model, @ModelAttribute("type") FilterType type) {

		String userName = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", userName);
		User loggedUser = userService.findByUserName(userName);
		Calendar dt = Calendar.getInstance();
		List<String> filterType = Arrays.asList("daily", "weekly", "monthly");
		if (null == type) {
			type = new FilterType();
		}
		model.addAttribute("filterType", filterType);
		model.addAttribute("type", type);
		if ("daily".equals(type.getType())) {
			dt.set(Calendar.DATE, dt.get(Calendar.DATE));
		} else if ("weekly".equals(type.getType())) {
			dt.set(Calendar.DATE, dt.get(Calendar.DATE) - 7);
		} else if ("monthly".equals(type.getType())) {
			dt.set(Calendar.MONTH, dt.get(Calendar.MONTH) - 1);
		} else {
			dt.set(Calendar.DATE, dt.get(Calendar.DATE));
		}
		dt.set(Calendar.HOUR, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		dt.set(Calendar.AM_PM, 0);
		logger.info("Date is " + dt.getTime());
		
		List<OrderHasFoodItem> fList = service.findByDate(dt.getTime());
		//fetch all foodnames from food_name
		List<FoodName> fnList = foodService.findAll();
		List<OrderFoodItemWrapper> list = new ArrayList<>();
		Set<Long> set = new HashSet<>();
		for (Order o : orderService.findAll()) {
			boolean isAssignee = null != o.getAssignee() && o.getAssignee().equals(loggedUser.getId());
			boolean isCreatedBy = o.getCreatedBy().equals(userName);
			boolean isSysAdmin = Utilities.isUserHasPermission(loggedUser, "ROLE_SYSADMIN");
			if (isAssignee || isCreatedBy || isSysAdmin) {
				set.add(o.getId());
			}
		}
		
		for (FoodName fn : fnList) {
			//filtering on the flist to get the food name desc from fnlist.
			List<OrderHasFoodItem> ls = fList.stream().filter(en -> en.getFoodName().equals(fn.getId()))
					.collect(Collectors.toList());
			Double val = 0.0;
			for (OrderHasFoodItem it : ls) {
				if (set.contains(it.getOrder()))
					val += it.getValue();
			}
			if (val > 0)
				list.add(new OrderFoodItemWrapper(val.toString(), fn));
		}

		model.addAttribute("metricsList", list);
		return "metrics";
	}

}
