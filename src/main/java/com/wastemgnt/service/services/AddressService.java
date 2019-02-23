package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.Address;


public interface AddressService extends JpaRepository<Address, Long> {
	
}
