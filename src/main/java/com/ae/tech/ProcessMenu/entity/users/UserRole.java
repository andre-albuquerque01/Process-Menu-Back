package com.ae.tech.ProcessMenu.entity.users;

public enum UserRole {
	ADMIN("ADMIN"),
	USER("USER"),
	FUNCIONARIO("FUNCIONARIO");
	
	private String role;
	
	UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
