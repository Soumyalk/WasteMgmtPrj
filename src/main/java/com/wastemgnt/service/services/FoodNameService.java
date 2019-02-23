package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.FoodName;

public interface FoodNameService extends JpaRepository<FoodName, Long> {

}
