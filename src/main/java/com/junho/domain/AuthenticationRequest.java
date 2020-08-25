package com.junho.domain;

import java.io.Serializable;

// 인증 요청에 대항 양식이다.
public class AuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	
	// JSON Parsing 을 위한 기본생성자가 필요하다.
	public AuthenticationRequest()
	{

	}

	public AuthenticationRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}