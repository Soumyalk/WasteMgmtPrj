package com.wastemgnt.web.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wastemgnt.service.entity.FoodName;
import com.wastemgnt.service.entity.FoodType;
import com.wastemgnt.service.entity.OrderHasFoodItem;
import com.wastemgnt.service.services.FoodNameService;
import com.wastemgnt.service.services.FoodTypeService;
import com.wastemgnt.service.services.OrderHasFoodItemsService;
import com.wastemgnt.web.model.FoodTypeForm;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class FoodTypeController {

	@Autowired
	private FoodTypeService foodTypeService;

	@Autowired
	private FoodNameService foodNameService;

	@Autowired
	private OrderHasFoodItemsService fItemsService;

	@RequestMapping(value = "/admin/foodtypes", method = RequestMethod.GET)
	public String showFoodTypes(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<FoodType> foodTypes = foodTypeService.findAll();
		model.addAttribute("foodTypes", foodTypes);
		return "foodtypes";
	}

	@RequestMapping(value = "/admin/foodtypes/create", method = RequestMethod.GET)
	public String createFoodType(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodTypeForm entity = new FoodTypeForm();
		model.addAttribute("entity", entity);
		return "createFoodType";
	}

	@RequestMapping(value = "/admin/foodtypes/save", method = RequestMethod.POST)
	public String saveFoodType(Model model, @ModelAttribute("entity") FoodTypeForm entity) {
		FoodType p = new FoodType();
		p.setName(entity.getName());
		p.setCreatedOn(new Date());
		p.setqType(entity.getqType());
		foodTypeService.save(p);
		return "redirect:/admin/foodtypes";
	}

	@RequestMapping(value = "/admin/foodtypes/edit", method = RequestMethod.GET)
	public String editFoodType(Model model, @RequestParam("pId") Long pId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodTypeForm foodTypes = new FoodTypeForm(foodTypeService.findById(pId).get());
		model.addAttribute("entity", foodTypes);
		return "editFoodType";
	}

	@RequestMapping(value = "/admin/foodtypes/update", method = RequestMethod.POST)
	public String updateFoodType(Model model, @ModelAttribute("entity") FoodTypeForm entity) {
		FoodType p = new FoodType();
		p.setId(entity.getId());
		p.setName(entity.getName());
		p.setCreatedOn(new Date());
		p.setqType(entity.getqType());
		foodTypeService.save(p);
		return "redirect:/admin/foodtypes";
	}

	@RequestMapping(value = "/admin/foodtypes/delete", method = RequestMethod.GET)
	public String deleteFoodType(Model model, @RequestParam("pId") Long pId) {
		FoodType type = foodTypeService.findById(pId).get();
		List<FoodName> fNames = foodNameService.findAll().stream().filter(fName -> fName.getFoodType().equals(type))
				.collect(Collectors.toList());
		for (FoodName fName : fNames) {
			for (OrderHasFoodItem item : fItemsService.findByFoodName(fName.getId())) {
				fItemsService.delete(item);
			}
			foodNameService.delete(fName);
		}
		foodTypeService.delete(type);
		return "redirect:/admin/foodtypes";
	}

}
