package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Contact;

@Repository("ContactRepository")
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	@Query(value = "SELECT c.* FROM contacts c inner join users u on (c.user_id = u.user_id) WHERE u.email = :email", nativeQuery = true)
	List<Contact> findContactListByEmail(String email);
}
