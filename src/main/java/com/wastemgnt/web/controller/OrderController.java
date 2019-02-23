package com.wastemgnt.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.FoodPantryAddress;
import com.wastemgnt.service.entity.FoodPantryFoodItemLimit;
import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderHasFoodItem;
import com.wastemgnt.service.entity.OrderPantryHasFoodItem;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.services.FoodNameService;
import com.wastemgnt.service.services.FoodPantryAddressService;
import com.wastemgnt.service.services.FoodPantryFoodItemLimitService;
import com.wastemgnt.service.services.OrderHasFoodItemsService;
import com.wastemgnt.service.services.OrderPantryHasFoodItemService;
import com.wastemgnt.service.services.OrderService;
import com.wastemgnt.service.services.StateCityConfigService;
import com.wastemgnt.service.services.UserService;
import com.wastemgnt.service.services.UserServiceImpl;
import com.wastemgnt.web.model.CompletedOrderList;
import com.wastemgnt.web.model.FilterType;
import com.wastemgnt.web.model.OrderFoodItem;
import com.wastemgnt.web.model.OrderForm;
import com.wastemgnt.web.model.OrderPantryHasFoodItemModel;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private FoodNameService foodService;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserService service;
	@Autowired
	private OrderHasFoodItemsService fItemsService;
	@Autowired
	private FoodPantryAddressService addressService;
	@Autowired
	private StateCityConfigService stateConfig;

	@Autowired
	private OrderPantryHasFoodItemService pantryHasFoodItemService;

	@Autowired
	private FoodPantryFoodItemLimitService foodPantryFoodItemLimitService;

	@Value("${mail.passsword}")
	private String password;

	@Value("${mail.from}")
	private String fromEmail;
	
	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
	public String showOrders(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		model.addAttribute("ordersList", getLoggedUserOrders(model, name));
		return "orders";
	}

	private List<Order> getLoggedUserOrders(Model model, String userName) {
		List<Order> orders = new ArrayList<>();
		User loggedUser = userService.findByUserName(userName);
		if (Utilities.isUserHasRole(loggedUser, "CUSTOMER")) {
			model.addAttribute("showOrderLinks", true);
		}
		Sort s = new Sort(Sort.Direction.DESC, "createdOn");
		for (Order o : orderService.findAll(s)) {
			boolean isAssignee = null != o.getAssignee() && o.getAssignee().equals(loggedUser.getId());
			boolean isCreatedBy = o.getCreatedBy().equals(userName);
			boolean isSysAdmin = Utilities.isUserHasPermission(loggedUser, "ROLE_SYSADMIN");
			if (isAssignee || isCreatedBy || isSysAdmin) {
				orders.add(o);
			}
		}
		return orders;
	}

	@RequestMapping(value = "/admin/orders/create", method = RequestMethod.GET)
	public String createOrder(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		Map<String, String> map = new LinkedHashMap<>();
		List<FoodName> fNames = foodService.findAll().stream().sorted(Comparator.comparing(FoodName::getFoodName))
				.collect(Collectors.toList());
		for (FoodName fName : fNames) {
			map.put(fName.getFoodName().trim(), "Value should not be exceed " + fName.getLimit());
		}
		model.addAttribute("map", map);
		model.addAttribute("fNames", fNames);
		model.addAttribute("stateList", stateConfig.findAll());
		model.addAttribute("entity",
				null == model.asMap().get("entity") ? new OrderForm() : model.asMap().get("entity"));
		return "createOrder";
	}

	@RequestMapping(value = "/admin/orders/save", method = RequestMethod.POST)
	@Transactional
	public String saveOrder(Model model, @ModelAttribute("entity") OrderForm entity) {

		try {
			if (entity.getOrderFoodItem().getFoodName().getLimit() < entity.getOrderFoodItem().getValue().intValue()) {
				model.addAttribute("error", entity.getOrderFoodItem().getFoodName().getFoodName()
						+ " Should not exceed " + entity.getOrderFoodItem().getFoodName().getLimit());
				model.addAttribute("entity", entity);
				return createOrder(model);
			}
			if (null != entity.getOrderFoodItem().getFoodName()) {
				String name = Utilities.getLoggedInUserName(model);
				Order o = new Order();
				o.setCreatedBy(name);
				o.setUpdatedBy(name);
				o.setCreatedOn(new Date());
				o.setUpdatedOn(new Date());
				o.setPickupLocation(entity.getPickupLocation());
				o.setDescription(entity.getDescription());
				o.setStatus("NEW");
				o.setStateConfig(entity.getStateConfig());
				o = orderService.save(o);
				entity.getOrderFoodItem().setOrder(o);
				OrderHasFoodItem fItem = new OrderHasFoodItem(entity.getOrderFoodItem());
				fItemsService.save(fItem);
				return "redirect:/admin/orders/detail?pId=" + o.getId();
			}
		} catch (Exception ex) {
		}
		model.addAttribute("entity", entity);
		model.addAttribute("error", "Please fill the mandatory fields");
		return createOrder(model);
	}

	@RequestMapping(value = "/admin/orders/detail", method = RequestMethod.GET)
	public String detailOrder(Model model, @RequestParam("pId") Long pId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		// just sorting according to ascending order.
		List<FoodName> fNames = foodService.findAll().stream().sorted(Comparator.comparing(FoodName::getFoodName))
				.collect(Collectors.toList());
		User loggedUser = userService.findByUserName(name);
		OrderPantryHasFoodItem pantryHasFoodItem = new OrderPantryHasFoodItem();
		Order order = orderService.findById(pId).get();

		// below is to show the customer can edit the food details before it is
		// assigned.
		if (Utilities.isUserHasRole(loggedUser, "CUSTOMER")) {
			model.addAttribute("showOrderLinks", order.getStatus().equals("NEW"));
		}
		Map<String, String> map = new LinkedHashMap<>();
		for (FoodName fName : fNames) {
			map.put(fName.getFoodName(), "Value should not be exceeded " + fName.getLimit());
		}
		model.addAttribute("map", map);
		model.addAttribute("fTypes", fNames);
		model.addAttribute("address", addressService.findAll());

		List<OrderPantryHasFoodItemModel> l = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		try {
			for (OrderPantryHasFoodItem item : pantryHasFoodItemService.getListByOrder(order.getId())) {
				FoodPantryAddress address = addressService.findById(item.getAddress()).get();
				OrderHasFoodItem ohasFItem = fItemsService.findById(item.getFoodItem()).get();
				FoodName foodName = foodService.findById(ohasFItem.getFoodName()).get();
				FoodPantryFoodItemLimit limitEntity = foodPantryFoodItemLimitService
						.getByAddressAndFoodName(item.getAddress(), foodName.getId());
				OrderPantryHasFoodItemModel m = new OrderPantryHasFoodItemModel(item.getId(), foodName.getFoodName(),
						address.getAddress(), limitEntity.getMaxAllowedFood(), limitEntity.getAvailableLimit());

				// Appending the address to sb so that it can be used in frontend. If there are
				// multiple locations for particular location, it is appending using NL
				sb.append(address.getAddress()).append(", ")
						.append(null != address.getStateConfig() ? address.getStateConfig().getCity() : "").append(", ")
						.append(null != address.getStateConfig() ? address.getStateConfig().getState() : "")
						.append("<NL>"); // 200 carpenter Street, warrensburg, Missouri <NL> wood chapel address.
				l.add(m);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		model.addAttribute("orderhasFItems", l);

		User u = null;
		// checking if order is assigned, that the user details and fetch the order
		// assigned to that that user.
		if (null != order.getAssignee())
			u = service.findById(order.getAssignee()).get();
		// Create orderForm to old values for front end
		OrderForm entity = new OrderForm(order, u);
		List<OrderFoodItem> itemsList = new ArrayList<>();
		for (OrderHasFoodItem it : fItemsService.findByOrderId(pId)) {
			FoodName foodName = fNames.stream().filter(fName -> fName.getId().equals(it.getFoodName())).findFirst()
					.get();
			// setting those order details to itemsList
			itemsList.add(new OrderFoodItem(it, order, foodName));
		}
		// setting the itemlist to the orderform
		entity.setFoodItems(itemsList);

		// Adding address details to the model
		if (!sb.toString().isEmpty()) {
			model.addAttribute("toMapAddres", sb.toString());
		}
		if (null != order.getStateConfig()) {
			model.addAttribute("fromMapAddres", order.getPickupLocation() + ", " + order.getStateConfig().getCity()
					+ ", " + order.getStateConfig().getState());
		}
		model.addAttribute("entity", null == model.asMap().get("entity") ? entity : model.asMap().get("entity"));
		model.addAttribute("pantryHasFoodItem", pantryHasFoodItem);
		model.addAttribute("showMap", Utilities.isUserHasRole(loggedUser, "DELIVERY_ASSOCIATE"));
		return "detailOrder";
	}

	@RequestMapping(value = "/admin/orders/deleteFI", method = RequestMethod.GET)
	public String detailDeleteFI(Model model, @RequestParam("pId") Long pId, @RequestParam("dId") Long dId) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		User loggedUser = userService.findByUserName(name);
		if (Utilities.isUserHasRole(loggedUser, "CUSTOMER"))
			fItemsService.deleteById(pId);
		return "redirect:/admin/orders/detail?pId=" + dId;
	}

	// Show Edit Order Page for the requested Order Id
	@RequestMapping(value = "/admin/orders/edit", method = RequestMethod.GET)
	public String editOrder(Model model, @RequestParam("pId") Long pId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		User loggedUser = userService.findByUserName(name);
		Order order = orderService.findById(pId).get();

		if (Utilities.isUserHasPermission(loggedUser, "ROLE_SYSADMIN")) {
			List<User> users = userService.getUsersByRole().stream().filter(user -> user.isUserEnabled())
					.collect(Collectors.toList());
			model.addAttribute("users", users);
			model.addAttribute("statusList", Utilities.getStatuses());
			// to show Assign Items to Pantry button on the screen.
			model.addAttribute("showPantryLink", true);
		} else if (Utilities.isUserHasRole(loggedUser, "DELIVERY_ASSOCIATE")) {
			model.addAttribute("statusList", Utilities.getDRStatuses());
		} else {
			model.addAttribute("showOrderLinks", order.getStatus().equals("NEW"));
			model.addAttribute("stateList", stateConfig.findAll());
		}

		List<FoodName> fNames = foodService.findAll().stream().sorted(Comparator.comparing(FoodName::getFoodName))
				.collect(Collectors.toList());
		model.addAttribute("fTypes", fNames);
		model.addAttribute("address", addressService.findAll());

		User u = null;
		if (null != order.getAssignee()) {
			u = service.findById(order.getAssignee()).get();
		}
		OrderForm entity = new OrderForm(order, u);
		List<OrderFoodItem> itemsList = new ArrayList<>();
		Map<String, String> limitValMap = new HashMap<>();
		// for fetching each food pantry limit
		for (FoodPantryFoodItemLimit item : foodPantryFoodItemLimitService.findAll()) {
			if (null == item.getAvailableLimit() || item.getAvailableLimit() > 0) {
				limitValMap.put(
						addressService.findById(item.getFooddPantryAddress()).get().getName() + "_"
								+ foodService.findById(item.getFoodItem()).get().getFoodName(),
						"Value Should not exceed " + (null == item.getAvailableLimit() ? item.getMaxAllowedFood()
								: item.getAvailableLimit()));
			}
		}
		List<OrderHasFoodItem> itemList = fItemsService.findByOrderId(pId);
		for (OrderHasFoodItem it : itemList) {
			FoodName foodName = fNames.stream().filter(fName -> fName.getId().equals(it.getFoodName())).findFirst()
					.get();
			itemsList.add(new OrderFoodItem(it, order, foodName));
		}

		List<OrderPantryHasFoodItemModel> l = new ArrayList<>();
		try {
			for (OrderPantryHasFoodItem item : pantryHasFoodItemService.getListByOrder(order.getId())) {
				FoodPantryAddress address = addressService.findById(item.getAddress()).get();
				OrderHasFoodItem ohasFItem = fItemsService.findById(item.getFoodItem()).get();
				FoodName foodName = foodService.findById(ohasFItem.getFoodName()).get();
				FoodPantryFoodItemLimit limitEntity = foodPantryFoodItemLimitService
						.getByAddressAndFoodName(item.getAddress(), foodName.getId());
				OrderPantryHasFoodItemModel m = new OrderPantryHasFoodItemModel(item.getId(), foodName.getFoodName(),
						address.getAddress(), limitEntity.getMaxAllowedFood(), limitEntity.getAvailableLimit());
				l.add(m);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		model.addAttribute("orderhasFItems", l);
		entity.setFoodItems(itemsList);
		model.addAttribute("entity", entity);
		model.addAttribute("itemList", itemList);
		model.addAttribute("limitValMap", limitValMap);
		return "editOrder";
	}

	@RequestMapping(value = "/admin/orders/update", method = RequestMethod.POST)
	public String updateOrder(Model model, @ModelAttribute("entity") OrderForm entity) {
		Order order = orderService.findById(entity.getId()).get();
		if (Utilities.isSystemAdmin(userService.findByUserName(Utilities.getLoggedInUserName(model)))) {
			OrderHasFoodItem ohasFItem = fItemsService.findById(entity.getOrderPantryHasFoodItem().getFoodItem()).get();
			FoodName foodName = foodService.findById(ohasFItem.getFoodName()).get();
			FoodPantryFoodItemLimit limitEntity = foodPantryFoodItemLimitService
					.getByAddressAndFoodName(entity.getOrderPantryHasFoodItem().getAddress(), foodName.getId());
			if (null != limitEntity && limitEntity.getAvailableLimit() > 0) {
				entity.getOrderPantryHasFoodItem().setOrder(order.getId());
				if (limitEntity.getAvailableLimit() >= entity.getOrderPantryHasFoodItem().getValue()) {
					long diff = limitEntity.getAvailableLimit() - entity.getOrderPantryHasFoodItem().getValue();
					limitEntity.setAvailableLimit(diff);
				} else {
					model.addAttribute("error", entity.getOrderFoodItem().getFoodName().getFoodName()
							+ " Should not exceed " + entity.getOrderFoodItem().getFoodName().getLimit());
					model.addAttribute("entity", entity);
					return detailOrder(model, order.getId());
				}
				pantryHasFoodItemService.save(entity.getOrderPantryHasFoodItem());
				foodPantryFoodItemLimitService.save(limitEntity);
				return "redirect:/admin/orders/detail?pId=" + order.getId();
			} else {
				model.addAttribute("error", "This Food Item is not allowed for this Depot");
			}
			model.addAttribute("entity", entity);
			return detailOrder(model, order.getId());
		} else {
			if (null == fItemsService.findByOrderIdAndFoodName(order.getId(),
					entity.getOrderFoodItem().getFoodName().getId())) {
				if (entity.getOrderFoodItem().getFoodName().getLimit() >= entity.getOrderFoodItem().getValue()
						.intValue()) {
					entity.getOrderFoodItem().setOrder(order);
					fItemsService.save(new OrderHasFoodItem(entity.getOrderFoodItem()));
					return "redirect:/admin/orders/detail?pId=" + order.getId();
				} else {
					model.addAttribute("error", entity.getOrderFoodItem().getFoodName().getFoodName()
							+ " Should not exceed " + entity.getOrderFoodItem().getFoodName().getLimit());
				}
			} else {
				model.addAttribute("error",
						entity.getOrderFoodItem().getFoodName().getFoodName() + " item is already added");
			}
			return detailOrder(model, order.getId());
		}
	}

	// This method for admin flow when admin updates the location and assign it to
	// the DA.
	@RequestMapping(value = "/admin/orders/updateOrderDetails", method = RequestMethod.POST)
	@Transactional
	public String updateOrderDetails(Model model, @ModelAttribute("entity") OrderForm entity) {
		Order order = orderService.findById(entity.getId()).get();
		String loginId = Utilities.getLoggedInUserName(model);
		User loggedUser = userService.findByUserName(loginId);
		order.setUpdatedBy(loginId);
		order.setUpdatedOn(new Date());
			
		/** get the created user  details**/
		User createdUser = userService.findByUserName(order.getCreatedBy());
		logger.info("Created User --> " + createdUser +  " " + createdUser.getEmail());
		
		boolean isAssigneeUpdated = (null == order.getAssignee() && null != entity.getAssignee());
		logger.info("initially value is " + isAssigneeUpdated);
		if (null != entity.getAssignee())
			order.setAssignee(entity.getAssignee().getId());
		if (null != entity.getStatus()) {
			if(Utilities.isSystemAdmin(loggedUser)) {
				if("NEW".equals(entity.getStatus())) {
				model.addAttribute("error", "Status cannot be NEW");
				model.addAttribute("entity", entity);
				return editOrder(model, order.getId());
				}
			}
			order.setStatus(entity.getStatus());
		}
		if (null != entity.getAddress() && null != entity.getAddress().getId())
			order.setAddress(entity.getAddress());
		if (null != entity.getStateConfig() && null != entity.getStateConfig().getId())
			order.setStateConfig(entity.getStateConfig());
		if (null != entity.getOrderPantryHasFoodItem().getFoodItem()
				&& null != entity.getOrderPantryHasFoodItem().getAddress()) {

			//to check if there is an entry for the particular food item in order_pantry_has_food_item, for first time list will be empty
			List<OrderPantryHasFoodItem> list = pantryHasFoodItemService.getListByOrderAndFoodItemAndAddress(
					order.getId(), entity.getOrderPantryHasFoodItem().getFoodItem());
			OrderHasFoodItem ohasFItem = fItemsService.findById(entity.getOrderPantryHasFoodItem().getFoodItem()).get();
			
			//adding the current food item value assigned to pantry to check the count.
			long l = entity.getOrderPantryHasFoodItem().getValue();
			for (OrderPantryHasFoodItem item : list) {
				l += item.getValue(); 
			}
			
			if (l <= ohasFItem.getValue()) {
				FoodPantryFoodItemLimit limitEntity = foodPantryFoodItemLimitService.getByAddressAndFoodName(
						entity.getOrderPantryHasFoodItem().getAddress(), ohasFItem.getFoodName());
				if (null != limitEntity) {
					limitEntity
							.setAvailableLimit(null == limitEntity.getAvailableLimit() ? limitEntity.getMaxAllowedFood()
									: limitEntity.getAvailableLimit());
					limitEntity = foodPantryFoodItemLimitService.save(limitEntity);
					if (null != limitEntity.getAvailableLimit()) {
						entity.getOrderPantryHasFoodItem().setOrder(order.getId());
						if (limitEntity.getAvailableLimit() >= entity.getOrderPantryHasFoodItem().getValue()) {
							long diff = limitEntity.getAvailableLimit() - entity.getOrderPantryHasFoodItem().getValue();
							limitEntity.setAvailableLimit(diff);
						} else {
							model.addAttribute("error",
									foodService.findById(limitEntity.getFoodItem()).get().getFoodName()
											+ " Should not exceed " + limitEntity.getAvailableLimit());
							model.addAttribute("entity", entity);
							return editOrder(model, order.getId());
						}
						pantryHasFoodItemService.save(entity.getOrderPantryHasFoodItem());
						limitEntity = foodPantryFoodItemLimitService.save(limitEntity);
						orderService.save(order);
						if (isAssigneeUpdated) {
							logger.info("assignee is assigned " + isAssigneeUpdated);
							
							StringBuffer sb= new StringBuffer();
							List<OrderHasFoodItem> foodList = fItemsService.findByOrderId(entity.getId());
							
							for(OrderHasFoodItem food:foodList){
								FoodName f = foodService.findById(food.getFoodName()).get();
								sb.append(f.getFoodName() +", ");
							}
							
							String  msg ="Order '" + entity.getId() +"' is assigned to you! Please login and check and your orders."
									+ "You need to collect " + sb + "from the user.";
							String userMessage= "Your order is received and you will be soon contacted by our Delivery Associate ' " + entity.getAssignee().getFirstName() +
						            " " +entity.getAssignee().getLastName() + " '" +
						            "Contact number is " + entity.getAssignee().getPhone();
														
							Utilities.sendMail(fromEmail, entity.getAssignee().getEmail(), msg ,
									"Order Assigned", password);
							Utilities.sendMail(fromEmail, createdUser.getEmail(), userMessage, "Order Assigned", password);
						}
					} else {
						model.addAttribute("error", entity.getOrderFoodItem().getFoodName().getFoodName()
								+ " Should not exceed " + entity.getOrderFoodItem().getFoodName().getLimit());
						model.addAttribute("entity", entity);
						return editOrder(model, order.getId());
					}
					return "redirect:/admin/orders/edit?pId=" + order.getId();
				} else {
					model.addAttribute("error", "This Food Item is not allowed for this Depot");
				}
			} else {
				model.addAttribute("error", "All the Food items are either assigned to Pantry, No Items are left over or you are exceeding the count of orders");
				model.addAttribute("entity", entity);
				return editOrder(model, order.getId());
			}
		}
		orderService.save(order);
		return "redirect:/admin/orders";
	}

	@RequestMapping(value = "/admin/orders/getHintTxt", method = RequestMethod.POST)
	public void testmethod(Model model, @ModelAttribute("type") FilterType type,
			@ModelAttribute("type1") FilterType type1) {

	}


	//for delivered list
	@RequestMapping(value = "/admin/deliveredList", method = RequestMethod.GET)
	public String deliveredItemList(Model model, @ModelAttribute("type") FilterType type,
			@ModelAttribute("type1") FilterType type1) {
		

		String userName = Utilities.getLoggedInUserName(model);
		
		
		
		//User loggedUser = userService.findByUserName(userName);
		
		//boolean isDeliveryAssociate = Utilities.isDeliveryAssociate(loggedUser);
		
		Calendar dt = Calendar.getInstance();
		List<String> filterType = Arrays.asList("daily", "weekly");
		if (null == type) {
			type = new FilterType();
		}
		model.addAttribute("login", userName);
		model.addAttribute("filterType", filterType);
		model.addAttribute("type", type);
		if ("daily".equals(type.getType())) {
			dt.set(Calendar.DATE, dt.get(Calendar.DATE));
		} else if ("weekly".equals(type.getType())) {
			dt.set(Calendar.DATE, dt.get(Calendar.DATE) - 7);
		}
		
		dt.set(Calendar.HOUR, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		dt.set(Calendar.AM_PM, 0);
		
		logger.info("Date is " + dt.getTime());
		logger.info("LoggedUser -------------> " + userName);
		
		List<Order> fList = orderService.findByDate(dt.getTime(),userName);
		
		//to add the filtered list
		List<CompletedOrderList> cList = new ArrayList();
		
		for(Order o:fList){
			
			if((o.getStatus().equalsIgnoreCase("ORDER_DELIVERED")) && (o.getUpdatedBy().equalsIgnoreCase(userName))) {
				
				
				CompletedOrderList l= new CompletedOrderList();
				l.setOrderId(o.getId());
				l.setDeliveredStatus(o.getStatus());
				l.setUpdatedDate(o.getUpdatedOn());
				cList.add(l);
				logger.info("List ---> " + l.toString());
			}
		}
		
		model.addAttribute("cList",cList);
		logger.info("Date is " + dt.getTime());
		
		return "deliveredItemList";
	}



}
