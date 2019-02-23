package com.wastemgnt.web.controller;

import java.util.List;

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
import com.wastemgnt.web.model.FoodNameForm;
import com.wastemgnt.web.utils.Utilities;

@Controller("")
public class FoodItemsController {

	@Autowired
	private FoodNameService foodNameService;

	@Autowired
	private FoodTypeService foodTypeService;

	@Autowired
	private OrderHasFoodItemsService fItemsService;

	@RequestMapping(value = "/admin/foodnames", method = RequestMethod.GET)
	public String showFoodTypes(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		List<FoodName> foodTypes = foodNameService.findAll();
		model.addAttribute("foodNames", foodTypes);
		return "foodnames";
	}

	@RequestMapping(value = "/admin/foodnames/create", method = RequestMethod.GET)
	public String createFoodType(Model model) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodNameForm entity = new FoodNameForm();
		List<FoodType> fTypes = foodTypeService.findAll();
		model.addAttribute("fTypes", fTypes);
		model.addAttribute("entity", entity);
		return "createFoodName";
	}

	@RequestMapping(value = "/admin/foodnames/save", method = RequestMethod.POST)
	public String saveFoodType(Model model, @ModelAttribute("entity") FoodNameForm entity) {
		FoodName p = new FoodName();
		p.setFoodName(entity.getFoodName());
		p.setFoodType(entity.getFoodType());
		p.setLimit(entity.getLimit());
		foodNameService.save(p);
		return "redirect:/admin/foodnames";
	}

	@RequestMapping(value = "/admin/foodnames/edit", method = RequestMethod.GET)
	public String editFoodType(Model model, @RequestParam("pId") Long pId) {

		String name = Utilities.getLoggedInUserName(model);
		model.addAttribute("login", name);
		FoodNameForm foodNameForm = new FoodNameForm(foodNameService.findById(pId).get());
		model.addAttribute("entity", foodNameForm);
		List<FoodType> fTypes = foodTypeService.findAll();
		model.addAttribute("fTypes", fTypes);
		return "editFoodName";
	}

	@RequestMapping(value = "/admin/foodnames/update", method = RequestMethod.POST)
	public String updateFoodType(Model model, @ModelAttribute("entity") FoodNameForm entity) {
		FoodName p = foodNameService.findById(entity.getId()).get();
		p.setId(entity.getId());
		p.setFoodName(entity.getFoodName());
		p.setFoodType(entity.getFoodType());
		p.setLimit(entity.getLimit());
		foodNameService.save(p);
		return "redirect:/admin/foodnames";
	}

	@RequestMapping(value = "/admin/foodnames/delete", method = RequestMethod.GET)
	public String updateFoodType(Model model, @RequestParam("pId") Long pId) {
		for (OrderHasFoodItem item : fItemsService.findByFoodName(pId)) {
			fItemsService.delete(item);
		}
		foodNameService.deleteById(pId);
		return "redirect:/admin/foodnames";
	}

}
