package com.baraniok.appregister.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baraniok.appregister.exception.UserAlreadyExistException;
import com.baraniok.appregister.data.UserRepository;
import com.baraniok.appregister.dto.UserDto;
import com.baraniok.appregister.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerNewUser(UserDto userDto) {
		Optional<User> optUser = userRepository.findByUsername(userDto.getUsername());
		if(optUser.isPresent()) {
			throw new UserAlreadyExistException("User with such username already exist");
		}

		return userRepository.save(convertToEntity(userDto));
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> getAllUsers() {
		return ((List<User>)userRepository.findAll());
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	private User convertToEntity(UserDto userDto) {
		return new User(userDto.getUsername(), userDto.getPassword());
	}
}
