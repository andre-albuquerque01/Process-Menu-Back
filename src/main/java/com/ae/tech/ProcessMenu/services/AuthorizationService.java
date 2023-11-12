package com.ae.tech.ProcessMenu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ae.tech.ProcessMenu.repositorio.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	UserRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loginRepository.findByEmail(username);
	}

}
