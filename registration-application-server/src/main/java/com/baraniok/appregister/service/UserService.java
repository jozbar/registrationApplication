package com.baraniok.appregister.service;

import java.util.List;
import java.util.Optional;

import com.baraniok.appregister.dto.UserDto;
import com.baraniok.appregister.model.User;

public interface UserService {
	
	User registerNewUser(UserDto user);
	
	Optional<User> getUserByUsername(String username);
	
	List<User> getAllUsers();
	
	Optional<User> getUserById(Long id);
}
