package com.ae.tech.ProcessMenu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ae.tech.ProcessMenu.entity.dto.RegisterDTO;
import com.ae.tech.ProcessMenu.entity.dto.UserDTO;
import com.ae.tech.ProcessMenu.entity.users.AddressUser;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.entity.users.UserRole;
import com.ae.tech.ProcessMenu.repositorio.AddressUserRepository;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressUserRepository addressUserRepository;

	public UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setEmail(user.getEmail());
		userDTO.setCpf(user.getCpf());
		userDTO.setBirthday(user.getBirthday());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setDdd(user.getDDD());
		userDTO.setAddressUser(user.getAddressUser());
		return userDTO;
	}

	public void registerUser(RegisterDTO data) throws Exception {
		if (this.userRepository.findByEmail(data.email()) != null || !data.password().equals(data.confirmpassword()))
			throw new Exception("Email existente");

		var role = UserRole.USER;
		String encrytedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.email(), encrytedPassword, data.cpf(), data.birthday(), data.firstName(),
				data.lastName(), data.phoneNumber(), data.ddd(), role, data.addressUser(), true, data.termsService());
		this.addressUserRepository.save(data.addressUser());
		newUser.setAddressUser(data.addressUser());
		this.userRepository.save(newUser);
	}

	public User updateuser(String id, RegisterDTO data) throws Exception {
		Optional<User> userData = userRepository.findById(id);
		if (userData.isEmpty()) {
			throw new Exception("usuário não localizado");
		}

		User _User = userData.get();
		if (_User.getAtivo() && new BCryptPasswordEncoder().matches(data.password(), _User.getPassword())
				&& _User.getToken().equals(data.token())
				&& (_User.getEmail().equals(data.email()) || this.userRepository.findByEmail(data.email()) == null)) {
			_User.setFirstName(data.firstName());
			_User.setLastName(data.firstName());
			_User.setEmail(data.email());
			_User.setBirthday(data.birthday());
			_User.setCpf(data.cpf());
			_User.setDDD(data.ddd());
			_User.setPhoneNumber(data.phoneNumber());
			_User.setAddressUser(new AddressUser());
			AddressUser userAddress = _User.getAddressUser();
			userAddress.setCep(data.addressUser().getCep());
			userAddress.setLogradouro(data.addressUser().getLogradouro());
			userAddress.setBairro(data.addressUser().getBairro());
			userAddress.setUf(data.addressUser().getUf());
			userAddress.setComplemento(data.addressUser().getComplemento());
			_User = userRepository.save(_User);
			return _User;
		} else {
			throw new Exception("Update não foi realizado");
		}
	}

	public User updatePassword(String id, RegisterDTO data) throws Exception {
		Optional<User> userData = userRepository.findById(id);
		if (userData.isEmpty()) {
			throw new Exception("usuário não localizado");
		}
		String encrytedNewPassword = new BCryptPasswordEncoder().encode(data.confirmpassword());
		User _User = userData.get();
		if (_User.getAtivo() && new BCryptPasswordEncoder().matches(data.password(), _User.getPassword())) {
			_User.setPassword(encrytedNewPassword);
			_User = userRepository.save(_User);
			return _User;
		} else {
			throw new Exception("Update não foi realizado");
		}
	}

}
