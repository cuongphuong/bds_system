package com.sys.pp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistical")
public class StatisticalController {
	
	@RequestMapping(path = "")
	public String view(Model model, Principal principal) {
		return "layouts/admin/statistical";
	}
}
