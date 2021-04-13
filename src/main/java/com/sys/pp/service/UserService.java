package com.sys.pp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sys.pp.constant.Names;
import com.sys.pp.model.Users;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.util.StringUtils;

@Service("userService")
public class UserService {
	private static String FREFIX_USER = "US";

	@Autowired
	UserRepository userRes;

	public Optional<Users> findById(String id) {
		return userRes.findById(id);
	}

	public Users findByEmailAddress(String email) {
		return userRes.findByEmailAddress(email);
	}

	public List<Users> findByPageNumber(int pageNumber) {
		Sort sortable = Sort.by("userId").descending();
		PageRequest pageable = PageRequest.of(pageNumber, Names.DEFAULT_PAGE_NUMBER, sortable);
		Page<Users> page = userRes.findAll(pageable);
		return page.toList();
	}

	public Users save(Users entity) {
		return userRes.save(entity);
	}

	public void removeById(String userId) {
		if (null == userId) {
			return;
		}

		userRes.deleteById(userId);
	}

	public String registNewId() {
		String maxId = userRes.findMaxId();
		if (StringUtils.isNullOrEmpty(maxId)) {
			return String.format("%s%08d", FREFIX_USER, 1);
		}

		String onlyNumberId = StringUtils.getNumberOfString(maxId);
		int id = Integer.valueOf(onlyNumberId);

		String newId = String.format("%s%08d", FREFIX_USER, id + 1);
		return newId;
	}
}
