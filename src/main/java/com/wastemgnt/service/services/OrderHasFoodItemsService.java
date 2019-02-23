package com.wastemgnt.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.OrderHasFoodItem;

@Repository("orderhasFoodItemService")
public interface OrderHasFoodItemsService extends JpaRepository<OrderHasFoodItem, Long> {

	@Query("select u from OrderHasFoodItem u where u.order= :order")
	List<OrderHasFoodItem> findByOrderId(@Param("order") Long order);

	@Query("select u from OrderHasFoodItem u where u.createdOn >= :createdOn")
	List<OrderHasFoodItem> findByDate(@Param("createdOn") Date createdOn);

	@Query("select u from OrderHasFoodItem u where u.order= :order and u.foodName= :foodName")
	OrderHasFoodItem findByOrderIdAndFoodName(@Param("order") Long order, @Param("foodName") Long foodName);

	@Query("select u from OrderHasFoodItem u where u.foodName= :foodName")
	List<OrderHasFoodItem> findByFoodName(@Param("foodName") Long foodName);

}
