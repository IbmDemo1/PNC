package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojo.IpServiceResponse;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserResponse;
import com.example.demo.service.UserService;  

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	RestTemplate restTemplate;

	UserService userService;
	
	
	@BeforeEach
	public void setUp() {
		userService = new UserServiceImpl();
		restTemplate = mock(RestTemplate.class);
		ReflectionTestUtils.setField(userService, "restTemplate", restTemplate);
	}
	
	@Test
	public void testValidateUser() throws Exception {
		
		IpServiceResponse response = new IpServiceResponse();
		response.setStatus("success");
		response.setCountry("Canada");
		
		ResponseEntity<IpServiceResponse> responseEntity = new ResponseEntity<IpServiceResponse>(response, HttpStatus.OK);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		when(restTemplate.exchange(Mockito.eq("http://ip-api.com/json/10.22.22"),Mockito.eq(HttpMethod.GET), Mockito.eq(entity), Mockito.eq(IpServiceResponse.class)))
				.thenReturn(responseEntity);		
		User user = new User();	
		user.setIpAddress("10.22.22");
		user.setPassword("Kas_hhh12");
		user.setUserName("test1");
		
		UserResponse userResponse = userService.validateUserCreds(user);
		assertEquals(userResponse.getWelcomeMessage(), "Welcome "+user.getUserName());
		
	}
	
	@Test
	public void testValidateUserWithOtherThanCanada() throws Exception {
		try {
		IpServiceResponse response = new IpServiceResponse();
		response.setStatus("fail");
		response.setCountry("");
		
		ResponseEntity<IpServiceResponse> responseEntity = new ResponseEntity<IpServiceResponse>(response, HttpStatus.OK);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		when(restTemplate.exchange(Mockito.eq("http://ip-api.com/json/10.22.22"),Mockito.eq(HttpMethod.GET), Mockito.eq(entity), Mockito.eq(IpServiceResponse.class)))
				.thenReturn(responseEntity);		
		User user = new User();	
		user.setIpAddress("10.22.22");
		user.setPassword("Kas_hhh12");
		user.setUserName("test1");
		
		UserResponse userResponse = userService.validateUserCreds(user);		
		}catch(Exception e) {
			assertEquals(e.getMessage(),"Users from Canada are only eligilbe to register");
		}
		
	}
	
	@Test
	public void testValidateUserWithInvalidPassword() throws Exception {
		try {		
		User user = new User();	
		user.setIpAddress("10.22.22");
		user.setPassword("test");
		user.setUserName("test1");
		
		UserResponse userResponse = userService.validateUserCreds(user);
		} catch(Exception e) {
			assertEquals(e.getMessage(),"Password must contain greater than 8 chars, 1 number, 1 capital letter, 1 special char in _#$%.");
		}	
	}
	
	
	@Test
	public void testValidateUserWithEmptyValues() throws Exception {
		try {		
		User user = new User();	
		user.setIpAddress("");
		user.setPassword("test");
		user.setUserName("test1");
		
		UserResponse userResponse = userService.validateUserCreds(user);
		} catch(Exception e) {
			assertEquals(e.getMessage(),"Please provide username, password, ipaddress");
		}	
	}
	
	

}
