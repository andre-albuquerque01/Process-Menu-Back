package com.ae.tech.ProcessMenu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ae.tech.ProcessMenu.entity.DTO.AuthenticationDTO;
import com.ae.tech.ProcessMenu.entity.DTO.LoginResponseDTO;
import com.ae.tech.ProcessMenu.entity.DTO.RegisterDTO;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.infra.security.TokenService;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		    var auth = this.authenticationManager.authenticate(usernamePassword);
		    
		    var token = tokenService.generateToken((User) auth.getPrincipal());
		    return ResponseEntity.ok(new LoginResponseDTO(token));
		} catch (Exception e) {
		    e.printStackTrace();
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if (this.repository.findByEmail(data.email()) != null)
			return ResponseEntity.badRequest().build();

		String encrytedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.email(), encrytedPassword, data.age(), data.firstName(), data.lastName(),
				data.phoneNumber(), data.DDD(), data.role(), data.cep(), data.logradouro(), data.bairro(), data.uf(),
				data.complemento());

		this.repository.save(newUser);

		return ResponseEntity.ok().build();
	}
}
