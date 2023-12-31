package com.ae.tech.ProcessMenu.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ae.tech.ProcessMenu.entity.dto.AuthenticationDTO;
import com.ae.tech.ProcessMenu.entity.dto.LoginResponseDTO;
import com.ae.tech.ProcessMenu.entity.dto.RegisterDTO;
import com.ae.tech.ProcessMenu.entity.dto.UserDTO;
import com.ae.tech.ProcessMenu.entity.users.AddressUser;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.entity.users.UserRole;
import com.ae.tech.ProcessMenu.infra.security.TokenService;
import com.ae.tech.ProcessMenu.repositorio.AddressUserRepository;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;
import com.ae.tech.ProcessMenu.services.MailSenderService;
import com.ae.tech.ProcessMenu.services.RandomService;
import com.ae.tech.ProcessMenu.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	public SimpleMailMessage template;

	@Autowired
	private MailSenderService mailService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((User) auth.getPrincipal());
			var user = ((User) auth.getPrincipal());
			user.setToken(token);
			this.userRepository.save(user);
			return ResponseEntity.ok(new LoginResponseDTO(token, user.getId().toString()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data) {
		try {
			userService.registerUser(data);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/recover")
	public ResponseEntity<User> recover(@RequestBody @Valid RegisterDTO data) {
		try {

			mailService.recoverPassword(data.email());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTO> getOneUser(@PathVariable(value = "id") String id) {
		try {
			Optional<User> userData = userRepository.findById(id);
			if (userData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				User user = userData.get();
				UserDTO userDTO = userService.convertToDTO(user);
				return ResponseEntity.ok(userDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") String id,
			@Valid @RequestBody RegisterDTO data) {
		try {
			userService.updateuser(id, data);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("/usersPass/{id}")
	public ResponseEntity<User> updateUserPassword(@PathVariable(value = "id") String id,
			@Valid @RequestBody RegisterDTO data) {
		try {
			userService.updatePassword(id, data);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Desnecessário
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		return ResponseEntity.ok().build();
	}

}
