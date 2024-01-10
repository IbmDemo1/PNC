package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserResponse;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name="User Credentials Controller", description="User Credentials Controller")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "User Created Successfully", content= @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description = "Bad Request", content= @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "5XX", description = "Internal Server Error", content= @Content(mediaType = "application/json")),
	})
	@PostMapping(path="/api/validateUserCreds", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateUserCredentials(@RequestBody User user) {
		UserResponse userResponse = null;
		try {
			logger.info("Recieved User for Validation:", user);
			userResponse= userService.validateUserCreds(user);
		} catch(Exception e) {
			logger.error("Problem occured while processing your request",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		return ResponseEntity.ok(userResponse);
	}
	
	

}
