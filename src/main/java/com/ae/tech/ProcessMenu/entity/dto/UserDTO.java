package com.ae.tech.ProcessMenu.entity.dto;

import com.ae.tech.ProcessMenu.entity.users.AddressUser;
import com.ae.tech.ProcessMenu.entity.users.UserRole;

public class UserDTO {
	private String email;
	private String password;
	private String confirmpassword;
	private String cpf;
	private String birthday;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String ddd;
	private UserRole role;
	private AddressUser addressUser;
	private Boolean ativo;
	private Boolean termsService;
	private String token;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public AddressUser getAddressUser() {
		return addressUser;
	}

	public void setAddressUser(AddressUser addressUser) {
		this.addressUser = addressUser;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getTermsService() {
		return termsService;
	}

	public void setTermsService(Boolean termsService) {
		this.termsService = termsService;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
