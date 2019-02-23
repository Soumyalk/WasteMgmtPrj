package com.wastemgnt.service.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wastemgnt.service.entity.Permission;
import com.wastemgnt.service.entity.User;
import com.wastemgnt.service.entity.UserRole;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;

	public User findByUserName(String userName) {
		User u = service.findByLoginId(userName);
		return u;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User u = service.findByLoginId(userName);
		if (null != u) {
			Set<GrantedAuthority> grantedPerms = new HashSet<>();
			for (UserRole role : u.getUserRoles()) {
				for (Permission permission : role.getPermissions()) {
					grantedPerms.add(new SimpleGrantedAuthority(permission.getPermission()));
				}
			}

			BCryptPasswordEncoder bCrpt = new BCryptPasswordEncoder();
			return new org.springframework.security.core.userdetails.User(u.getLoginId(), bCrpt.encode(u.getPassword()),
					u.isUserEnabled(), true, true, true, grantedPerms);
		} else {
			throw new UsernameNotFoundException("");
		}
	}

	public Set<User> getUsersByRole() {
		Set<User> users = new HashSet<>();
		for (User user : service.findAll()) {
			if (user.getUserRoles().stream().filter(role -> role.getRoleName().equals("DELIVERY_ASSOCIATE")).findFirst().isPresent()) {
				users.add(user);
			}
		}
		return users;
	}

}