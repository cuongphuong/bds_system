package com.sys.pp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

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

	/**
	 * Read by page
	 * 
	 * @return JSON List<Category>
	 */
	@ResponseBody
	@RequestMapping(path = "/get/{page}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<Category> loadData(@PathVariable Integer page) {
		if (null == page) {
			return new ArrayList<>();
		}
		return categoryService.findByPageNumber(page);
	}
	
	/**
	 * Read one
	 * 
	 * @return JSON Category
	 */
	@ResponseBody
	@RequestMapping(path = "/get/one/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public Optional<Category> getById(@PathVariable Integer id) {
		return categoryService.findById(id);
	}

	/**
	 * Delete by key
	 * @param Integer id
	 * 
	 * @return none
	 */
	@ResponseBody
	@RequestMapping(path = "/delete/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	public void deleteById(@PathVariable Integer id) {
		categoryService.removeById(id);
	}

	/**
	 * Find parent category
	 * 
	 * @return JSON List<Category>
	 */
	@ResponseBody
	@RequestMapping(path = "/parent", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<Category> findByParentCategory() {
		return categoryService.findByParentCategory();
	}

	
	/**
	 * Post a category
	 * @param Map<String, String> paramater
	 * Key map:
	 * * categoryName
	 * * categoryIdParent
	 * 
	 * @return status, obj added
	 */
	@ResponseBody
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json; charset=UTF-8")
	public Object saveCategory(@RequestBody Map<String, String> paramater) {
		// validate data
		Map<String, String> errors = this.validate(paramater);
		Map<String, Object> result = new HashMap<>();
		result.put("status", true);

		if (!errors.isEmpty()) {
			result.put("status", false);
			result.put("data", errors);
			return result;
		}

		try {
			Category category = this.createObject(paramater);

			categoryService.save(category);
			result.put("data", category);
			return result;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBJECT_EXISTS");
		}
	}

	/**
	 * Update a category
	 * @param Map<String, String> paramater
	 * Key map:
	 * * categoryName
	 * * categoryIdParent
	 * 
	 * @return status, obj added
	 */
	@ResponseBody
	@PostMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json; charset=UTF-8")
	public Object updateCategory(@RequestBody Map<String, String> paramater, @PathVariable Integer id) {
		// validate data
		Map<String, String> errors = this.validate(paramater);
		Map<String, Object> result = new HashMap<>();
		result.put("status", true);

		if (!errors.isEmpty()) {
			result.put("status", false);
			result.put("data", errors);
			return result;
		}

		try {
			Category category = this.createObject(paramater);
			category.setCategoryId(id);

			categoryService.save(category);
			result.put("data", category);
			return result;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OBJECT_EXISTS");
		}
	}

	private Category createObject(Map<String, String> paramater) {
		// convert to object
		Category category = new Category();
		category.setCategoryName(paramater.get("categoryName"));

		return category;
	}

	private Map<String, String> validate(Map<String, String> paramater) {
		Map<String, String> errors = new HashMap<>();
		if (null == paramater.get("categoryName")) {
			errors.put("validate_name", "Tên danh mục không được để trống");
		}

		if (null != paramater.get("categoryName") && paramater.get("categoryName").length() < 5) {
			errors.put("validate_name", "Tên danh mục phải lớn hơn 5 kí tự");
		}
		return errors;
	}
}
