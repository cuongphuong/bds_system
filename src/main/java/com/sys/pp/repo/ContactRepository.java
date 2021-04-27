package com.sys.pp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.pp.model.Contact;
import com.sys.pp.model.ContactPK;

@Repository("ContactRepository")
public interface ContactRepository extends JpaRepository<Contact, ContactPK> {
	@Query(value = "SELECT c.* FROM contacts c inner join users u on (c.user_id = u.user_id) WHERE u.email = :email", nativeQuery = true)
	List<Contact> findContactListByEmail(String email);
	
	@Query(value = "SELECT c.ind FROM contacts c WHERE c.user_id = :userId order by c.ind desc LIMIT 1", nativeQuery = true)
	Integer getMaxIndByUserId(String userId);
}
