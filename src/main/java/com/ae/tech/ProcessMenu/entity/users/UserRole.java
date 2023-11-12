package com.ae.tech.ProcessMenu.entity.users;

public enum UserRole {
	ADMIN("admin"),
	USER("user"),
	FUNCIONARIO("funcionario");
	
	private String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
