package com.ae.tech.ProcessMenu.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.users.AddressUser;

public interface AddressUserRepository extends MongoRepository<AddressUser, String> {

}
