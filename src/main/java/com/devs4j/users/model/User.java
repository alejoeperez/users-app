package com.devs4j.users.model;

public class User {

	private String nickName;
	private String username;
	private String password;
	
	public User() {}
	
	public User(String nickName, String username, String password) {
		super();
		this.nickName = nickName;
		this.username = username;
		this.password = password;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [nickName=" + nickName + ", username=" + username + ", password=" + password + "]";
	}
	
	
}
