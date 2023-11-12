package com.ae.tech.ProcessMenu.entity.users;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Document(collection = "User")
public class User implements UserDetails {

	@Id
	private String id;

	@Email(message = "Email should be valid")
	private String email;

	private String password;

	@Min(value = 18, message = "Age should not be less than 18")
	@Max(value = 150, message = "Age should not be greater than 150")
	private String age;

	@NotNull(message = "First name cannot be null")
	private String firstName;

	@NotNull(message = "Last name cannot be null")
	private String lastName;

	@NotNull(message = "Phone number cannot be null")
	private String phoneNumber;

	@NotNull(message = "DDD cannot be null")
	private String DDD;

	private UserRole role;

	@NotNull(message = "Cep cannot be null")
	private String cep;

	@NotNull(message = "Logradouro cannot be null")
	private String logradouro;

	@NotNull(message = "Bairoo cannot be null")
	private String bairro;

	@NotNull(message = "UF cannot be null")
	private String uf;

	@NotNull(message = "Complemento cannot be null")
	private String complemento;

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

	public User() {
	}

	public User(String email, String password, String age, String firstName, String lastName,
			String phoneNumber, String DDD, UserRole role, String cep, String logradouro, String bairro, String uf,
			String complemento) {
		this.email = email;
		this.password = password;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.DDD = DDD;
		this.role = role;
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.uf = uf;
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", age=" + age + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", DDD=" + DDD + ", role="
				+ role + ", cep=" + cep + ", logradouro=" + logradouro + ", bairro=" + bairro + ", uf=" + uf
				+ ", complemento=" + complemento + "]";
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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
		return DDD;
	}

	public void setDDD(String dDD) {
		DDD = dDD;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
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
