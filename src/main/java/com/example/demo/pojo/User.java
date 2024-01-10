package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
	private static final long serialVersionUID = 6746491305739206157L;
	private String userName;
	private String password;
	private String ipAddress;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ipAddress, password, userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(ipAddress, other.ipAddress) && Objects.equals(password, other.password)
				&& Objects.equals(userName, other.userName);
	}
	
	
	
}
