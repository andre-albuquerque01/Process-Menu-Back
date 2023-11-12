package com.ae.tech.ProcessMenu.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.ae.tech.ProcessMenu.entity.users.User;

public interface UserRepository extends MongoRepository<User, String>{
 UserDetails findByEmail(String email);
}
