package com.sys.pp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.model.Category;
import com.sys.pp.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	public String viewAll() {
		return "layouts/admin/category-list";
	}

	@ResponseBody
	@RequestMapping(path = "/get/{page}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<Category> loadData(@PathVariable Integer page) {
		if (null == page) {
			return new ArrayList<>();
		}
		return categoryService.findByPageNumber(page);
	}

	@ResponseBody
	@RequestMapping(path = "/delete/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	public List<Category> deleteById(@PathVariable Integer id) {
		categoryService.removeById(id);
		return categoryService.findByPageNumber(0);
	}
	
	@ResponseBody
	@RequestMapping(path = "/get/parent", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<Category> findByParentCategory() {
		return categoryService.findByParentCategory();
	}
}
