package com.ae.tech.ProcessMenu.entity.DTO;

import com.ae.tech.ProcessMenu.entity.users.UserRole;

public record RegisterDTO( String email, String password, String age, String firstName, String lastName,
		String phoneNumber, String DDD, UserRole role, String cep, String logradouro, String bairro, String uf,
		String complemento) {

}
