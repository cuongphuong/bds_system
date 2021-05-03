package com.sys.pp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.model.Category;
import com.sys.pp.model.Province;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("search")
public class SearchController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	private ProvinceService provinceService;

	/**
	 * Load search view
	 * 
	 * @return view
	 */
	@RequestMapping(path = "")
	public String view(Model model, Principal principal) {
		// String email = principal.getName();
		model.addAttribute("categorys", this.getRealEstateByCategory());
		model.addAttribute("provinces", this.makeProvinceList());

		return "layouts/user/search";
	}

	
	private List<KeyValue> makeProvinceList() {
		List<Province> provinceList = provinceService.findAll();
		provinceList = provinceList.stream().limit(15).collect(Collectors.toList());

		List<KeyValue> result = new ArrayList<>();
		for (Province item : provinceList) {
			KeyValue obj = new KeyValue();
			obj.setKey(String.valueOf(item.getId()));
			obj.setValue(item.getName());
			result.add(obj);
		}
		return result;
	}

	private List<KeyValue> getRealEstateByCategory() {
		List<Category> categoryList = categoryRepository.findAll();

		List<KeyValue> result = new ArrayList<>();
		for (Category item : categoryList) {
			KeyValue obj = new KeyValue();
			obj.setKey(String.valueOf(item.getCategoryId()));
			obj.setValue(item.getCategoryName());
			obj.setValue1(String.format("/bds/category/%s/%s", item.getCategoryId(),
					StringUtils.toSlug(String.format("Bất dộng sản %s", item.getCategoryName()))));
			result.add(obj);
		}
		return result;
	}
}
