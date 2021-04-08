package com.sys.pp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sys.pp.constant.Names;
import com.sys.pp.model.Category;
import com.sys.pp.repo.CategoryRepository;

@Service("categoryService")
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRes;
	
 	
	public List<Category> findByPageNumber(int pageNumber) {
		Sort sortable = Sort.by("categoryId").ascending();
	    PageRequest pageable = PageRequest.of(pageNumber, Names.DEFAULT_PAGE_NUMBER, sortable);
	    Page<Category> page = categoryRes.findAll(pageable);
		return page.toList();
	}
	
	public void removeById(Integer categoryId) {
		if (null == categoryId) {
			return;
		}

		categoryRes.deleteById(categoryId);
	}
	
	public List<Category> findByParentCategory(){
		return categoryRes.findByParentCategory();
	}
}
