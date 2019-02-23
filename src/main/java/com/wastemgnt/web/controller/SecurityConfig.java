package com.wastemgnt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wastemgnt.service.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl userDetailsService;

	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/dashboard").access("hasAnyRole('ROLE_DASHBOARD')");
		// The pages does not require login
		//mapping between context url path and User role permissions.
		http.authorizeRequests().antMatchers("/startpage", "/login", "/logout").permitAll()
				.antMatchers("/admin/users/**", "/admin/userroles", "/admin/userroles/**", "/admin/permissions",
						"/admin/permissions/**")
				.access("hasAnyRole('ROLE_SYSADMIN', 'ROLE_USER_MGNT')")
				.antMatchers("/admin/foodtypes", "/admin/foodtypes/**", "/admin/foodnames", "/admin/foodnames/**")
				.access("hasAnyRole('ROLE_SYSADMIN', 'ROLE_FOOD')").antMatchers("/admin/address", "/admin/address/**")
				.access("hasAnyRole('ROLE_SYSADMIN', 'ROLE_ADDRESS')")
				.antMatchers("/admin/orders", "/admin/orders/detail", "/admin/orders/edit")
				.access("hasAnyRole('ROLE_SYSADMIN', 'ROLE_ORDER_MGNT')").antMatchers("/admin/orders/create")
				.access("hasAnyRole('ROLE_CREATE_ORDER')").antMatchers("/admin/myprofile")
				.access("hasAnyRole('ROLE_SYSADMIN', 'ROLE_USER_MGNT', 'ROLE_FOOD', 'ROLE_ORDER_MGNT')").and()
				.formLogin()
				// Submit URL of login page.
				.loginPage("/login")//
				.loginProcessingUrl("/login") // Submit URL ,after successful login it is redirected to dashboard, below is the configuratin.
				.defaultSuccessUrl("/dashboard")//
				.failureUrl("/login?error=true")// incase of failure, sending flag true
				.usernameParameter("username")// request parameters
				.passwordParameter("password")
				// Config for Logout Page
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManager).userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder());
	}

}
