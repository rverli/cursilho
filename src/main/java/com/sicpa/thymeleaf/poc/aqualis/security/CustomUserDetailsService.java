package com.sicpa.thymeleaf.poc.aqualis.security;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.RoleRepository;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.UserRepository;

/**
 * Service used by Spring to access credentials of logged users.
 * @author lrosa1
 *
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired(required = true)
	private UserRepository userRepository;

	@Autowired(required = true)
	private RoleRepository userRoleRepository;

	@Autowired 
	private HttpSession httpSession;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			
			httpSession.setAttribute("user", user.getId());
			
			List<String> userRoles = userRoleRepository.findUserRoleByUserAndUserIsActive(user.getId());
			return new CustomUserDetails(user, userRoles);
		}
	}
}
