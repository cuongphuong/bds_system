package com.sys.pp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.model.Street;
import com.sys.pp.repo.StreetRepository;

@Service("StreetService")
public class StreetService {

	@Autowired
	StreetRepository streetRepos;

	public Street findById(Integer id) {
		Optional<Street> pro = streetRepos.findById(id);
		if (pro.isPresent()) {
			return pro.get();
		}
		return null;
	}
}
