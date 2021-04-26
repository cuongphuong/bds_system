package com.sys.pp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.repo.UserRepository;
import com.sys.pp.service.ProvinceService;

@Controller
@RequestMapping("")
public class HomePageController {

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	UserRepository userRepository;

	@GetMapping("")
	public String login(Model model) {
		model.addAttribute("province_id_list", provinceService.findAll());
		return "layouts/user/index";
	}

	@RequestMapping(path = "/view/{id}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}
}
