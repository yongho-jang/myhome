package com.chagvv.myhome.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chagvv.myhome.model.Role;
import com.chagvv.myhome.model.User;
import com.chagvv.myhome.repository.UserRepository;

@Service
public class UserService {

	private UserRepository repository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository,PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User save(User user) {
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		user.setEnabled(true);
		
		Role role = new Role();
		role.setId(1);
		user.getRoles().add(role);
		
		return repository.save(user);
	}
}
