package com.wastemgnt.service.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService 	{
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userServiceImpl;
    
	private static final Logger logger = LogManager.getLogger(SecurityServiceImpl.class);
    
    @Override
    public boolean login(String loginId, String pwd) {
    	UserDetails userDetails = userServiceImpl.loadUserByUsername(loginId);
    	BCryptPasswordEncoder n = new BCryptPasswordEncoder();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
        		new UsernamePasswordAuthenticationToken(userDetails,  n.encode(pwd), userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
        	
        	logger.info(" User authenitcation token----->" + usernamePasswordAuthenticationToken.getPrincipal() );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        logger.info(" User authenitcation token----->" + usernamePasswordAuthenticationToken.getPrincipal() );
    	return false;
    }


}
