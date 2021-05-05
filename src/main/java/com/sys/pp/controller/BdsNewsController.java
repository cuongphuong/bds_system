package com.sys.pp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/news")
public class BdsNewsController {

	@GetMapping("")
	public String viewAll() {
		return "layouts/admin/news-list";
	}
}
