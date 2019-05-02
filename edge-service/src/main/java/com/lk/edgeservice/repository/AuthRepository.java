package com.lk.edgeservice.repository;

import com.lk.edgeservice.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<Account,Long> {

        public Optional<Account> findByEmail(String email);

}
