package com.ae.tech.ProcessMenu.entity.dto;

import com.ae.tech.ProcessMenu.entity.users.AddressUser;
import com.ae.tech.ProcessMenu.entity.users.UserRole;

public record RegisterDTO(String email, String password, String confirmpassword, String cpf, String birthday, String firstName, String lastName,
		String phoneNumber, String ddd, UserRole role, AddressUser addressUser, Boolean ativo, Boolean termsService) {
}