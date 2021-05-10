package com.sys.pp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.constant.GemRealtyConst.AcreageScope;
import com.sys.pp.constant.GemRealtyConst.FontWidth;
import com.sys.pp.constant.GemRealtyConst.PriceScope;
import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.controller.custommodel.SearchCondition;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.Category;
import com.sys.pp.model.Province;
import com.sys.pp.model.Users;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.FavouriteRepository;
import com.sys.pp.repo.ProvinceRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.service.SearchService;
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("search")
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	SearchService service;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	ProvinceRepository provinceRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	FavouriteRepository favouriteRepository;

	/**
	 * Load search view
	 * 
	 * @return view
	 */
	@RequestMapping(path = "")
	public String view(Model model, Principal principal) {
		model.addAttribute("categorys", this.getRealEstateByCategory());
		model.addAttribute("provinces", this.makeProvinceList());

		return "layouts/user/search";
	}

	/**
	 * Search Result
	 * 
	 * @return json data
	 */
	@ResponseBody
	@PostMapping(path = "")
	public Map<String, Object> search(@RequestParam Map<String, String> allRequestParams, ModelMap model,
			Principal principal) {
		Users user = null;
		try {
			String email = principal.getName();
			user = userRepository.findByEmailAddress(email);

			if (StringUtils.isNullOrEmpty(email) || user == null)
				throw new Exception();
		} catch (Exception e) {
			LOGGER.warn("Chưa đăng nhập");
		}

		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", true);
		try {
			SearchCondition searchCondition = null;
			if (!StringUtils.isNullOrEmpty(allRequestParams.get("ctg"))) {
				searchCondition = new SearchCondition();
				if (allRequestParams.get("ctg") != null) {
					String[] categorys = allRequestParams.get("ctg").split("t");
					List<Integer> categoryList = Arrays.asList(categorys).stream().map(Integer::valueOf)
							.collect(Collectors.toList());
					searchCondition.setCategoryList(categoryList);
				}
			} else if (!StringUtils.isNullOrEmpty(allRequestParams.get("province"))) {
				searchCondition = new SearchCondition();
				if (allRequestParams.get("province") != null) {
					String[] provinces = allRequestParams.get("province").split("t");
					List<Integer> districtsList = Arrays.asList(provinces).stream().map(Integer::valueOf)
							.collect(Collectors.toList());
					searchCondition.setProvinceList(districtsList);
				}
			} else {
				searchCondition = this.makeSearchCondition(allRequestParams);
			}

			List<BdsNew> data = service.searchData(searchCondition);
			result.put("data", GemRealtyService.makePostCardList(userId, favouriteRepository, data, districtRepository,
					provinceRepository, categoryRepository));

			return result;
		} catch (Exception e) {
			result.put("status", false);
			return result;
		}
	}

	private SearchCondition makeSearchCondition(Map<String, String> allRequestParams) {
		SearchCondition searchCondition = new SearchCondition();

		if (allRequestParams.get("keyword") != null) {
			String keyword = allRequestParams.get("keyword");
			searchCondition.setKeyword(keyword);
		} else {
			searchCondition.setKeyword(StringUtils.EMPTY);
		}

		if (allRequestParams.get("formality") != null) {
			String[] formalitys = allRequestParams.get("formality").split("_");
			searchCondition.setFormalityList(Arrays.asList(formalitys));
		}

		if (allRequestParams.get("category") != null) {
			String[] categorys = allRequestParams.get("category").split("t");
			List<Integer> categoryList = Arrays.asList(categorys).stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			searchCondition.setCategoryList(categoryList);
		}

		if (allRequestParams.get("location") != null) {
			String location = allRequestParams.get("location");
			searchCondition.setLocation(Integer.valueOf(location));
		}

		if (allRequestParams.get("district") != null) {
			String[] districts = allRequestParams.get("district").split("t");
			List<Integer> districtsList = Arrays.asList(districts).stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			searchCondition.setDistrictList(districtsList);
		}

		if (allRequestParams.get("ward") != null) {
			String[] wards = allRequestParams.get("ward").split("t");
			List<Integer> wardList = Arrays.asList(wards).stream().map(Integer::valueOf).collect(Collectors.toList());
			searchCondition.setCategoryList(wardList);
		}

		if (allRequestParams.get("street") != null) {
			String[] streets = allRequestParams.get("street").split("t");
			List<Integer> streetList = Arrays.asList(streets).stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			searchCondition.setCategoryList(streetList);
		}

		if (allRequestParams.get("project") != null) {
			String[] projects = allRequestParams.get("project").split("t");
			List<Integer> projectList = Arrays.asList(projects).stream().map(Integer::valueOf)
					.collect(Collectors.toList());
			searchCondition.setCategoryList(projectList);
		}

		if (allRequestParams.get("price") != null) {
			String price = allRequestParams.get("price");
			PriceScope priceScope = GemRealtyConst.getPriceScopeById(Integer.valueOf(price));
			searchCondition.setPrice(priceScope);
		}

		if (allRequestParams.get("acreage") != null) {
			String acreage = allRequestParams.get("acreage");
			AcreageScope acreageScope = GemRealtyConst.getAcreageScopeById(Integer.valueOf(acreage));
			searchCondition.setAcreage(acreageScope);
		}

		if (allRequestParams.get("front") != null) {
			String[] fonts = allRequestParams.get("front").split("t");
			List<FontWidth> fontWidthList = Arrays.asList(fonts).stream()
					.map(p -> GemRealtyConst.getFontWidthFromId(Integer.valueOf(p))).collect(Collectors.toList());
			searchCondition.setFrontWidthList(fontWidthList);
		}

		if (allRequestParams.get("floor") != null) {
			String[] floors = allRequestParams.get("floor").split("t");
			searchCondition.setFloorList(
					Arrays.asList(floors).stream().map(p -> Integer.valueOf(p)).collect(Collectors.toList()));
		}

		if (allRequestParams.get("room") != null) {
			String[] rooms = allRequestParams.get("room").split("t");
			searchCondition.setRoomList(
					Arrays.asList(rooms).stream().map(p -> Integer.valueOf(p)).collect(Collectors.toList()));
		}

		if (allRequestParams.get("way") != null) {
			String[] ways = allRequestParams.get("way").split("t");
			searchCondition
					.setWayList(Arrays.asList(ways).stream().map(p -> Integer.valueOf(p)).collect(Collectors.toList()));
		}
		return searchCondition;
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
