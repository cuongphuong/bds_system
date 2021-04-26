package com.sys.pp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.pp.model.Province;
import com.sys.pp.repo.ProvinceRepository;

@Service("ProvinceService")
public class ProvinceService {

	@Autowired
	private ProvinceRepository provinceRepo;

	public List<Province> findAll() {
		return provinceRepo.findAll();
	}

	public Province findById(Integer id) {

		Optional<Province> pro = provinceRepo.findById(id);
		if (pro.isPresent()) {
			return pro.get();
		}
		return null;
	}
}
