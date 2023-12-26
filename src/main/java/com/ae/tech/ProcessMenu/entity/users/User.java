package com.ae.tech.ProcessMenu.entity.users;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Document(collection = "User")
public class User implements UserDetails {

	@Id
	private String id;

	private String email;

	private String password;

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

	private String newPassword;

	private String token;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		if (this.role == UserRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"),
					new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
		else if (this.role == UserRole.FUNCIONARIO)
			return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_FUNCIONARIO"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public User(String email, String password, String cpf, String birthday, String firstName, String lastName,
			String phoneNumber, String ddd, UserRole role, AddressUser addressUser, Boolean ativo,
			Boolean termsService) {
		this.email = email;
		this.password = password;
		this.cpf = cpf;
		this.birthday = birthday;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.ddd = ddd;
		this.role = role;
		this.addressUser = addressUser;
		this.ativo = ativo;
		this.termsService = termsService;
	}

	public User() {
	}

	public User(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", cpf=" + cpf + ", birthday="
				+ birthday + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", DDD=" + ddd + ", role=" + role + ", addressUser=" + addressUser + ", ativo=" + ativo
				+ ", termsService=" + termsService + "]";
	}

	public AddressUser getAddressUser() {
		return addressUser;
	}

	public void setAddressUser(AddressUser addressUser) {
		this.addressUser = addressUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
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

	public String getDDD() {
		return ddd;
	}

	public void setDDD(String ddd) {
		this.ddd = ddd;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
