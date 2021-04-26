package com.sys.pp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.model.District;
import com.sys.pp.repo.DistrictRepository;

@Service("DistrictService")
public class DistrictService {
	
	@Autowired
	DistrictRepository districtRepos;
	
	public District findById(Integer id) {
		Optional<District> pro = districtRepos.findById(id);
		if (pro.isPresent()) {
			return pro.get();
		}
		return null;
	}
}
