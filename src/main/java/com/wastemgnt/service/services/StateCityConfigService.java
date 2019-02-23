package com.wastemgnt.service.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wastemgnt.service.entity.StateCityConfig;

public interface StateCityConfigService extends JpaRepository<StateCityConfig, Long> {

}
