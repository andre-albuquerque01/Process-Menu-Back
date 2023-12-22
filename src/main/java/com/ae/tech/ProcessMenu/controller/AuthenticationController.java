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

import com.ae.tech.ProcessMenu.entity.DTO.AuthenticationDTO;
import com.ae.tech.ProcessMenu.entity.DTO.LoginResponseDTO;
import com.ae.tech.ProcessMenu.entity.DTO.RegisterDTO;
import com.ae.tech.ProcessMenu.entity.users.AddressUser;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.infra.security.TokenService;
import com.ae.tech.ProcessMenu.repositorio.AddressUserRepository;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;
import com.ae.tech.ProcessMenu.services.MailSenderService;
import com.ae.tech.ProcessMenu.services.RandomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	private AddressUserRepository addressUserRepository;

	@Autowired
	public SimpleMailMessage template;

	@Autowired
	private MailSenderService mailService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
			var auth = this.authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((User) auth.getPrincipal());
			var id = ((User) auth.getPrincipal()).getId().toString();
			return ResponseEntity.ok(new LoginResponseDTO(token, id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data) {
		try {
			if (this.userRepository.findByEmail(data.email()) != null)
				return ResponseEntity.badRequest().build();

			String encrytedPassword = new BCryptPasswordEncoder().encode(data.password());
			User newUser = new User(data.email(), encrytedPassword, data.cpf(), data.birthday(), data.firstName(),
					data.lastName(), data.phoneNumber(), data.DDD(), data.role(), data.addressUser(), data.ativo(),
					data.termsService());
			this.addressUserRepository.save(data.addressUser());
			newUser.setAddressUser(data.addressUser());
			this.userRepository.save(newUser);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/recover")
	public ResponseEntity<User> recover(@RequestBody @Valid Map<String, String> requestBody) {
		try {
			String email = requestBody.get("email");
			UserDetails userDetails = userRepository.findByEmail(email);

			if (userDetails == null) {
				return ResponseEntity.badRequest().build();
			}

			var newRandom = RandomService.generateRandomAlphaNumeric(9);
			String encrytedPassword = new BCryptPasswordEncoder().encode(newRandom);

			User userToUpdate = (User) userDetails;
			userToUpdate.setPassword(encrytedPassword);
			userRepository.save(userToUpdate);

			String text = String.format(template.getText());
			String subject = "Recover password";

			mailService.sendNewMail(email, subject, text + newRandom + "\n\n\nAtenciosamente\nEquipe de segurança.");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getOneUser(@PathVariable(value = "id") String id) {
		try {
			Optional<User> userData = userRepository.findById(id);
			if (userData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(userData.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") String id, @Valid @RequestBody User data) {
		try {
			Optional<User> userData = userRepository.findById(id);
			if (userData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			String encrytedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
			User _User = userData.get();
			if (_User.getAtivo() && new BCryptPasswordEncoder().matches(data.getPassword(), _User.getPassword())) {
				_User.setFirstName(data.getFirstName());
				_User.setLastName(data.getLastName());
				_User.setEmail(data.getEmail());
				_User.setPassword(encrytedPassword);
				_User.setBirthday(data.getBirthday());
				_User.setCpf(data.getCpf());
				_User.setDDD(data.getDDD());
				_User.setPhoneNumber(data.getPhoneNumber());
				_User.setAddressUser(new AddressUser());
				AddressUser userAddress = _User.getAddressUser();
				userAddress.setCep(data.getAddressUser().getCep());
				userAddress.setLogradouro(data.getAddressUser().getLogradouro());
				userAddress.setBairro(data.getAddressUser().getBairro());
				userAddress.setUf(data.getAddressUser().getUf());
				userAddress.setComplemento(data.getAddressUser().getComplemento());
				_User = userRepository.save(_User);
				return ResponseEntity.ok().build();
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PatchMapping("/usersPass/{id}")
	public ResponseEntity<User> updateUserPassword(@PathVariable(value = "id") String id, @Valid @RequestBody User data) {
		try {
			Optional<User> userData = userRepository.findById(id);
			if (userData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			String encrytedNewPassword = new BCryptPasswordEncoder().encode(data.getNewPassword());
			User _User = userData.get();
			if (_User.getAtivo() && new BCryptPasswordEncoder().matches(data.getPassword(), _User.getPassword())) {
				_User.setPassword(encrytedNewPassword);
				_User = userRepository.save(_User);
				return ResponseEntity.ok().build();
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	// Desnecessário
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		return ResponseEntity.ok().build();
	}

}
