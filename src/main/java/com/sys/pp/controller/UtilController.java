package com.sys.pp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.model.District;
import com.sys.pp.model.Project;
import com.sys.pp.model.Street;
import com.sys.pp.model.Ward;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.ProjectRepository;
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

}
