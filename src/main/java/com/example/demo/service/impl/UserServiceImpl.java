package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojo.IpServiceResponse;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserResponse;
import com.example.demo.service.UserService;

import io.micrometer.common.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	RestTemplate restTemplate;

	@Override
	public UserResponse validateUserCreds(User user) throws Exception {
		logger.info("Started validateUserCreds");
		UserResponse userResponse = new UserResponse();
		if (!StringUtils.isBlank(user.getIpAddress()) && !StringUtils.isBlank(user.getPassword())
				&& !StringUtils.isBlank(user.getUserName())) {
			if (!isValidPassword(user.getPassword())) {
				throw new Exception(
						"Password must contain greater than 8 chars, 1 number, 1 capital letter, 1 special char in _#$%.");
			} else {

				IpServiceResponse response = validateIpAddress(user.getIpAddress());
				if (null != response && response.getStatus().equals("success")
						&& response.getCountry().equals("Canada")) {
					userResponse.setCityName(response.getCity());
					userResponse.setUuid(UUID.randomUUID().toString());
					userResponse.setWelcomeMessage("Welcome " + user.getUserName());
				} else {
					logger.error("Users from Canada are only eligilbe to register");
					throw new Exception("Users from Canada are only eligilbe to register");
				}

			}

		} else {
			logger.error("Please provide username, password, ipaddress");
			throw new Exception("Please provide username, password, ipaddress");
		}
		logger.info("User Created with data",userResponse);
		return userResponse;
	}

	private IpServiceResponse validateIpAddress(String ipAddress) throws Exception {
		logger.info("Started Validating IpAddress");
		IpServiceResponse response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			response = restTemplate
					.exchange("http://ip-api.com/json/" + ipAddress, HttpMethod.GET, entity, IpServiceResponse.class)
					.getBody();
		} catch (Exception e) {
			logger.error("Problem occured calling ip address service");
			throw new Exception("Problem occured calling ip address service");
		}

		return response;
	}

	private boolean isValidPassword(String password) {
		logger.info("Started Validating pasword");
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[_$%.])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(regex);

		if (password == null) {
			return false;
		}

		Matcher m = p.matcher(password);

		return m.matches();
	}

}
