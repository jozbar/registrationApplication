package com.baraniok.appregister.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.baraniok.appregister.validation.ValidPassword;
import lombok.Data;

@Data
public class UserDto {

	private Long id;
	
	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username accepts alpha-numeric values only")
	@Size(min = 5, message = "Username length should be at least 5 characters")
	private String username;
	
	@ValidPassword
	private String password;

}
