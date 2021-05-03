package com.sys.pp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.controller.custommodel.LabelValue;
import com.sys.pp.model.Category;
import com.sys.pp.model.District;
import com.sys.pp.model.Project;
import com.sys.pp.model.Street;
import com.sys.pp.model.Ward;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.ProjectRepository;
import com.sys.pp.repo.ProvinceRepository;
import com.sys.pp.repo.StreetRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.repo.WardRepository;

@Controller
@RequestMapping("util")
public class UtilController {
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private StreetRepository streetRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	ProvinceRepository provinceRepository;

	/**
	 * Lấy danh sách quận huyện theo ID của tỉnh thành
	 * 
	 * @return List<District> danh sách quận huyện
	 */
	@ResponseBody
	@RequestMapping(path = "/get-district/{provinceId}")
	public List<District> loadDistrictsByProvinceId(@PathVariable Integer provinceId) {
		return districtRepository.findByProvinceId(provinceId);
	}

	/**
	 * Lấy danh sách xã phường theo ID của quận huyện
	 * 
	 * @return List<Ward> danh sách xã phường
	 */
	@ResponseBody
	@RequestMapping(path = "/get-ward/{district}")
	public List<Ward> loadWardsByDistrictId(@PathVariable Integer district) {
		return wardRepository.findByDistrictId(district);
	}

	/**
	 * Lấy danh sách đường theo ID của quận huyện
	 * 
	 * @return List<Street> danh sách đường
	 */
	@ResponseBody
	@RequestMapping(path = "/get-street/{district}")
	public List<Street> loadStreetByDistrictId(@PathVariable Integer district) {
		return streetRepository.findStreetByDistrictId(district);
	}

	/**
	 * Lấy danh sách các dự án trong khu vực theo ID của quận huyện
	 * 
	 * @return List<Project> danh dự án
	 */
	@ResponseBody
	@RequestMapping(path = "/get-project/{district}")
	public List<Project> loadProjectByDistrictId(@PathVariable Integer district) {
		return projectRepository.findProjectByDistrictId(district);
	}

	/**
	 * Lấy danh sách hình thức bài đăng
	 * 
	 * @return List<Project> danh dự án lable - value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-formality-la_va")
	public List<LabelValue> loadFormalityLabelValue() {
		List<KeyValue> items = GemRealtyConst.createFormalityList();
		List<LabelValue> results = items.stream().map(LabelValue::new).collect(Collectors.toList());

		return results;
	}

	/**
	 * Lấy danh sách loại hình bất động sản
	 * 
	 * @return List<Project> danh loại hình bất động sản lable - value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-category-la_va")
	public List<LabelValue> loadCategoryListByLabelValue() {
		List<Category> items = categoryRepository.findAll();
		List<LabelValue> results = items.stream().map(LabelValue::new).collect(Collectors.toList());

		return results;
	}

	/**
	 * Lấy danh sách giá tiền theo mức
	 * 
	 * @return List<Project> danh sách giá tiền lable - value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-prices-scope-la_va")
	public List<LabelValue> loadPriceScope() {
		return GemRealtyConst.getPriceScope();
	}

	/**
	 * Lấy danh diên tích
	 * 
	 * @return List<Project> danh sách diện tích label - value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-acreage-scope-la_va")
	public List<LabelValue> loadAcreageScope() {
		return GemRealtyConst.getAcreageScope();
	}
	
	/**
	 * Lấy danh diên tích mặt trươsc
	 * 
	 * @return List<Project> danh sách diện tích mặt trước label - value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-front-width-scope-la_va")
	public List<LabelValue> loadFrontWidth() {
		return GemRealtyConst.getFrontWidth();
	}

	/**
	 * Lấy danh sách tỉnh
	 * 
	 * @return List<District> danh sách tỉnh thành phố lable value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-province_la_va")
	public List<LabelValue> loadProvince() {
		return provinceRepository.findAll().stream().map(LabelValue::new).collect(Collectors.toList());
	}

	/**
	 * Lấy danh sách quận huyện
	 * 
	 * @return List<District> danh sách quận huyện lable value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-district_la_va/{provinceId}")
	public List<LabelValue> loadDistrict(@PathVariable Integer provinceId) {
		return districtRepository.findByProvinceId(provinceId).stream().map(LabelValue::new)
				.collect(Collectors.toList());
	}

	/**
	 * Lấy danh sách xax phường
	 * 
	 * @return List<District> danh sách xã phường lable value
	 */
	@ResponseBody
	@RequestMapping(path = "/get-ward_la_va/{districts}")
	public List<LabelValue> loadWard(@PathVariable String districts) {
		List<Ward> results = new ArrayList<Ward>();
		String[] arr = districts.split(",");

		for (int i = 0; i < arr.length; i++) {
			List<Ward> tmp = wardRepository.findByDistrictId(Integer.valueOf(arr[i]));
			results.addAll(tmp);
		}

		return results.stream().map(LabelValue::new).collect(Collectors.toList());
	}

	/**
	 * Lấy danh sách đường theo ID của quận huyện
	 * 
	 * @return List<Street> danh sách đường
	 */
	@ResponseBody
	@RequestMapping(path = "/get-street_la_va/{districts}")
	public List<LabelValue> loadStreet(@PathVariable String districts) {
		List<Street> results = new ArrayList<>();
		String[] arr = districts.split(",");

		for (int i = 0; i < arr.length; i++) {
			List<Street> tmp = streetRepository.findStreetByDistrictId(Integer.valueOf(arr[i]));
			results.addAll(tmp);
		}

		return results.stream().map(LabelValue::new).collect(Collectors.toList());
	}

	/**
	 * Lấy danh sách các dự án trong khu vực theo ID của quận huyện
	 * 
	 * @return List<Project> danh dự án
	 */
	@ResponseBody
	@RequestMapping(path = "/get-project_la_va/{districts}")
	public List<LabelValue> loadProject(@PathVariable String districts) {
		List<Project> results = new ArrayList<>();
		String[] arr = districts.split(",");

		for (int i = 0; i < arr.length; i++) {
			List<Project> tmp = projectRepository.findProjectByDistrictId(Integer.valueOf(arr[i]));
			results.addAll(tmp);
		}
		return results.stream().map(LabelValue::new).collect(Collectors.toList());
	}
}
