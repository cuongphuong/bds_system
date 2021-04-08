package com.sys.pp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Users;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<Users, Integer> {
	
	@Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
	Users findByEmailAddress(String emailAddress);
}
