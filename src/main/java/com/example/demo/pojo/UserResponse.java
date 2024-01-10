package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Objects;

public class UserResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -257046928539093095L;
	String uuid;
	String welcomeMessage;
	String cityName;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cityName, uuid, welcomeMessage);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponse other = (UserResponse) obj;
		return Objects.equals(cityName, other.cityName)
				&& Objects.equals(uuid, other.uuid) && Objects.equals(welcomeMessage, other.welcomeMessage);
	}
	
	

}
