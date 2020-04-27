package com.baraniok.appregister.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baraniok.appregister.dto.UserDto;
import com.baraniok.appregister.model.User;
import com.baraniok.appregister.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserResource {

	private final UserService userService;
	
	public UserResource(UserService userService) {
		this.userService = userService;
	}
	

	@PostMapping(value = "/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User addUser(@Valid @RequestBody UserDto userDto) {
		return userService.registerNewUser(userDto);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		if(users.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		model.addAttribute(users);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		Optional<User> optUser = userService.getUserById(id);
		return optUser
				.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	}
	
}
