package com.sys.pp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.repo.UserRepository;

@Controller
@RequestMapping("view")
public class ViewPostController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(path = "/bds/{id}/{slug}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}
}
