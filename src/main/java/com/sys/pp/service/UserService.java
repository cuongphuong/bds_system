package com.sys.pp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.model.Users;
import com.sys.pp.repo.UserRepository;

@Service("userService")
public class UserService {

	@Autowired
	UserRepository userRes;

	public Optional<Users> findById(Integer id) {
		return userRes.findById(id);
	}

	public Users findByEmailAddress(String email) {
		return userRes.findByEmailAddress(email);
	}
}
