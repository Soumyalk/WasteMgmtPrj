package com.wastemgnt.service.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wastemgnt.service.entity.Order;
import com.wastemgnt.service.entity.OrderHasFoodItem;

@Repository("orderService")
public interface OrderService extends JpaRepository<Order, Long>{

	
	@Query("select u from Order u where u.assignee= :assignee")
    public List<Order> getAssigneeOrders(@Param("assignee") Long assignee);
	
	@Query("select u from Order u where u.updatedOn >= :updatedOn and u.updatedBy = :updatedBy")
	List<Order> findByDate(@Param("updatedOn") Date createdOn,@Param("updatedBy") String updatedBy);
}
