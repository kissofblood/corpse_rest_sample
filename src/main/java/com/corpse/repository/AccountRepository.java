package com.corpse.repository;

import org.springframework.data.repository.CrudRepository;

import com.corpse.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
