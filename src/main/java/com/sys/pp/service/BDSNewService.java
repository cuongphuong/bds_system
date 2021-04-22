package com.sys.pp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sys.pp.constant.Names;
import com.sys.pp.model.BdsNew;
import com.sys.pp.repo.BDSNewRepository;

@Service("BDSNewService")
public class BDSNewService {
	@Autowired
	private BDSNewRepository bDSNewRepository;
	
	public List<BdsNew> findByPageNumber(int pageNumber) {
		Sort sortable = Sort.by("categoryId").descending();
	    PageRequest pageable = PageRequest.of(pageNumber, Names.DEFAULT_PAGE_NUMBER_OF_HOME_NEWS, sortable);
	    Page<BdsNew> page = bDSNewRepository.findAll(pageable);
		return page.toList();
	}
}
