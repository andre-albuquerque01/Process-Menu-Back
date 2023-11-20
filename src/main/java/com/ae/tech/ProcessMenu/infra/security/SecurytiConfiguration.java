package com.ae.tech.ProcessMenu.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurytiConfiguration {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
						.requestMatchers(HttpMethod.PUT, "/auth/users/{id}").authenticated()
						
						.requestMatchers(HttpMethod.GET, "/product/").permitAll()
						.requestMatchers(HttpMethod.GET, "/product/searchProduct/{name}").permitAll()
						.requestMatchers(HttpMethod.GET, "/product/searchCategory/{category}").permitAll()
						.requestMatchers(HttpMethod.POST, "/product/register").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/product/alt/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/product/del/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/product/like/{id}").authenticated()
						
						.requestMatchers(HttpMethod.GET, "/order/").hasRole("USER")
						.requestMatchers(HttpMethod.GET, "/order/searchOrder/{number}").hasRole("USER")
						.requestMatchers(HttpMethod.POST, "/order/insert").hasRole("USER")
						.requestMatchers(HttpMethod.PATCH, "/order/alt/{id}").hasRole("USER")
						.requestMatchers(HttpMethod.DELETE, "/order/del/{id}").hasRole("USER")
						
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
