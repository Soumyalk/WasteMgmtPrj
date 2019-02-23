package com.wastemgnt.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.FoodPantryAddress;
import com.wastemgnt.service.entity.FoodPantryFoodItemLimit;
import com.wastemgnt.service.entity.StateCityConfig;
import com.wastemgnt.service.services.FoodNameService;
import com.wastemgnt.service.services.FoodPantryAddressService;
import com.wastemgnt.service.services.FoodPantryFoodItemLimitService;
import com.wastemgnt.service.services.StateCityConfigService;
import com.wastemgnt.web.model.FoodPantryFoodItemLimitModel;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class AddressConfigController {

	@Autowired
	private FoodPantryAddressService addressService;

	@Autowired
	private StateCityConfigService stateConfigService;

	@Autowired
	private FoodPantryFoodItemLimitService foodItemLimitService;

	@Autowired
	private FoodNameService foodNameService;

	@RequestMapping(value = "/admin/address/list", method = RequestMethod.GET)
	public String loadAll(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		model.addAttribute("addresses", addressService.findAll());
		return "addressList";
	}

	@RequestMapping(value = "/admin/address/state/list", method = RequestMethod.GET)
	public String loadStateAll(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		model.addAttribute("stList", stateConfigService.findAll());
		return "stateList";
	}

	@RequestMapping(value = "/admin/address/state/new", method = RequestMethod.GET)
	public String createNewState(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		StateCityConfig st = new StateCityConfig();
		model.addAttribute("entity", st);
		model.addAttribute("stList", stateConfigService.findAll());
		return "stateNew";
	}

	@RequestMapping(value = "/admin/address/state/edit", method = RequestMethod.GET)
	public String editState(Model model, @RequestParam("id") Long id) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		StateCityConfig st = stateConfigService.findById(id).get();
		model.addAttribute("entity", st);
		return "stateEdit";
	}

	@RequestMapping(value = "/admin/address/detail", method = RequestMethod.GET)
	public String detailAddress(Model model, @RequestParam("id") Long id) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodPantryAddress st = addressService.findById(id).get();
		model.addAttribute("entity", st);
		model.addAttribute("fnLimitList", foodItemLimitService.findAll());
		return "detailAddress";
	}

	@RequestMapping(value = "/admin/address/state/save", method = RequestMethod.POST)
	public String saveState(Model model, @ModelAttribute("entity") StateCityConfig entity) {
		stateConfigService.save(entity);
		return "redirect:/admin/address/state/list";
	}

	@RequestMapping(value = "/admin/address/new", method = RequestMethod.GET)
	public String createNew(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodPantryAddress address = new FoodPantryAddress();
		model.addAttribute("entity", address);
		model.addAttribute("stList", stateConfigService.findAll());
		return "addressNew";
	}

	@RequestMapping(value = "/admin/address/fooditemlimit/list", method = RequestMethod.GET)
	public String loadFoodItemAll(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<FoodPantryFoodItemLimitModel> list = new ArrayList<>();
		for (FoodPantryFoodItemLimit item : foodItemLimitService.findAll()) {
			String address = addressService.findById(item.getFooddPantryAddress()).get().getName();
			String foodName = foodNameService.findById(item.getFoodItem()).get().getFoodName();
			list.add(new FoodPantryFoodItemLimitModel(foodName, address, item.getId(), item.getMaxAllowedFood()));
		}
		model.addAttribute("entities", list);
		return "foodItemLimitList";
	}



	@RequestMapping(value = "/admin/address/fooditemlimit/new", method = RequestMethod.GET)
	public String createFoodItemLimit(Model model) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		model.addAttribute("entity",
				null != model.asMap().get("entity") ? model.asMap().get("entity") : new FoodPantryFoodItemLimit());
		model.addAttribute("fnList", foodNameService.findAll());
		model.addAttribute("addrList", addressService.findAll());
		return "foodItemLimitNew";
	}

	@RequestMapping(value = "/admin/address/fooditemlimit/edit", method = RequestMethod.GET)
	public String editFoodItemLimit(Model model, @ModelAttribute("id") Long id) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodPantryFoodItemLimit entity = foodItemLimitService.findById(id).get();
		model.addAttribute("entity", null == model.asMap().get("entity") ? entity : model.asMap().get("entity"));
		model.addAttribute("fnList", foodNameService.findAll());
		model.addAttribute("addrList", addressService.findAll());
		return "foodItemLimitEdit";
	}

	@RequestMapping(value = "/admin/address/fooditemlimit/update", method = RequestMethod.POST)
	public String updateFoodItemLimit(Model model, @ModelAttribute("entity") FoodPantryFoodItemLimit updatedEntity) {
		FoodPantryFoodItemLimit entity = foodItemLimitService.findById(updatedEntity.getId()).get();
		if (updatedEntity.getAvailableLimit() <= updatedEntity.getMaxAllowedFood()) {
			entity.setMaxAllowedFood(updatedEntity.getMaxAllowedFood());
			entity.setAvailableLimit(updatedEntity.getAvailableLimit());
			foodItemLimitService.save(entity);
			return "redirect:/admin/address/fooditemlimit/list";
		} else {
			model.addAttribute("error", "Allowed max value should  grater than available limit");
			return editFoodItemLimit(model, updatedEntity.getId());
		}
	}

	@RequestMapping(value = "/admin/address/fooditemlimit/save", method = RequestMethod.POST)
	public String saveFoodItemLimit(Model model, @ModelAttribute("entity") FoodPantryFoodItemLimit entity) {
		if (null == foodItemLimitService.getByAddressAndFoodName(entity.getFooddPantryAddress(),
				entity.getFoodItem())) {
			entity.setAvailableLimit(entity.getMaxAllowedFood());
			foodItemLimitService.save(entity);
			return "redirect:/admin/address/fooditemlimit/list";
		} else {
			model.addAttribute("error", "Already Exists");
			model.addAttribute("entity", entity);
			return createFoodItemLimit(model);
		}
	}

	@RequestMapping(value = "/admin/address/save", method = RequestMethod.POST)
	public String saveAddress(Model model, @ModelAttribute("entity") FoodPantryAddress entity) {
		addressService.save(entity);
		return "redirect:/admin/address/list";
	}

	@RequestMapping(value = "/admin/address/edit", method = RequestMethod.GET)
	public String editAddress(Model model, @RequestParam("id") Long id) {
		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodPantryAddress address = addressService.findById(id).get();
		model.addAttribute("entity", address);
		model.addAttribute("stList", stateConfigService.findAll());
		return "addressEdit";
	}

	@RequestMapping(value = "/admin/address/delete", method = RequestMethod.GET)
	public String deleteAddress(Model model, @RequestParam("id") Long id) {
		addressService.deleteById(id);
		return "redirect:/admin/address/list";
	}

	@RequestMapping(value = "/admin/address/state/delete", method = RequestMethod.GET)
	public String deleteState(Model model, @RequestParam("id") Long id) {
		addressService.deleteById(id);
		return "redirect:/admin/address/state/list";
	}

}
