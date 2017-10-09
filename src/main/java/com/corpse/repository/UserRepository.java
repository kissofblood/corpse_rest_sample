package com.corpse.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.corpse.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	@Modifying
	@Query("UPDATE User SET password=:password WHERE username=:username")
	int updateByPassword(@Param("username") String username, @Param("password") String password);

	@Modifying
	@Query("UPDATE User SET isAdmin=:isAdmin WHERE username=:username")
	int updateByIsAdmin(@Param("username") String username, @Param("isAdmin") boolean isAdmin);
}
