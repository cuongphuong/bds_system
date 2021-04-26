package com.sys.pp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.model.Ward;
import com.sys.pp.repo.WardRepository;

@Service("WardService")
public class WardService {

	@Autowired
	WardRepository wardRepos;

	public List<Ward> findAll() {
		return wardRepos.findAll();
	}

	public Ward findById(Integer id) {

		Optional<Ward> pro = wardRepos.findById(id);
		if (pro.isPresent()) {
			return pro.get();
		}
		return null;
	}
}
