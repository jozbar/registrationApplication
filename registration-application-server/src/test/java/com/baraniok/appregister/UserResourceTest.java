package com.baraniok.appregister;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.baraniok.appregister.data.UserRepository;
import com.baraniok.appregister.dto.UserDto;
import com.baraniok.appregister.exception.UserAlreadyExistException;
import com.baraniok.appregister.model.User;
import com.baraniok.appregister.rest.UserResource;
import com.baraniok.appregister.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
@SessionAttributes("userDto")
class UserResourceTest {

	@Autowired
	MockMvc mvc;
	@MockBean
	UserService userService;
	@MockBean
	UserRepository userRepository;


	@Before
	public void setup() {

	}

	@Test
	public void addUser() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUsername("Iwona");
		userDto.setPassword("Iwona123");

		mvc.perform(post("/users/register")
				.contentType("application/json")
				.content(toJson(userDto)))
				.andExpect(status().isCreated());
	}

	@Test
	public void addUserWithWrongUsername() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUsername(";fd");
		userDto.setPassword("Djkjk67");

		mvc.perform(post("/users/register")
				.contentType("application/json")
				.content(toJson(userDto)))
				.andExpect(status().isBadRequest());
		
	}

	@Test
	public void addTwoUsersWithTheSameUsernameShouldResultAnError() throws Exception {

		UserDto userDto = new UserDto();
		userDto.setUsername("Iwona");
		userDto.setPassword("Iwona123");

		when(userService.registerNewUser(userDto)).thenThrow(new UserAlreadyExistException("User with such username already exist"));

		MvcResult mvcResult = mvc.perform(post("/users/register")
				.contentType("application/json")
				.content(toJson(userDto)))
				.andExpect(status().isConflict())
				.andReturn();

		assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("User with such username already exist");
	}

	@Test
	public void getAllUsersWithNoUsersDefinedShouldReturnNotFoundStatus() throws Exception {
		mvc.perform(get("/users"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getAllUsersShouldReturnDefinedUsers() throws Exception {
		List<User> users = createUsers();
		when(userService.getAllUsers()).thenReturn(users);

		ResultActions perform = mvc.perform(get("/users"));

		perform.andExpect(status().isOk())
				.andExpect(content()
						.string("[{\"id\":null,\"username\":\"Marysia\"},{\"id\":null,\"username\":\"Ala\"}]"));
	}

	private List<User> createUsers() {
		User user1 = new User("Marysia", "Hello123");
		User user2 = new User("Ala", "Hello1234");
		return List.of(user1, user2);
	}

	private String toJson(UserDto userDto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(userDto);
	}


}
