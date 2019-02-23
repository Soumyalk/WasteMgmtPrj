package com.wastemgnt.service.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderPantryHasFoodItem;

public interface OrderPantryHasFoodItemService extends JpaRepository<OrderPantryHasFoodItem, Long> {

	@Query("select u from OrderPantryHasFoodItem u where u.order= :order")
	public List<OrderPantryHasFoodItem> getListByOrder(@Param("order") Long order);

	@Query("select u from OrderPantryHasFoodItem u where u.order= :order and u.foodItem= :foodItem")
	public List<OrderPantryHasFoodItem> getListByOrderAndFoodItemAndAddress(@Param("order") Long order, @Param("foodItem") Long foodItem);

}
