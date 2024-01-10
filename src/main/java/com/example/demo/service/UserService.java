package com.example.demo.service;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserResponse;

public interface UserService {
	
	public UserResponse validateUserCreds(User user) throws Exception;

}
