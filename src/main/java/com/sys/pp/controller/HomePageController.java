package com.sys.pp.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.repo.BDSNewRepository;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.ContactRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.ProjectRepository;
import com.sys.pp.repo.ProvinceRepository;
import com.sys.pp.repo.StreetRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.repo.WardRepository;
import com.sys.pp.service.BDSNewService;
import com.sys.pp.service.ProvinceService;

@Controller
@RequestMapping("")
public class HomePageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

	@Autowired
	private ProvinceService provinceService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BDSNewService bDSNewService;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProvinceRepository provinceRepository;
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	WardRepository wardRepository;
	@Autowired
	StreetRepository streetRepository;
	@Autowired
	BDSNewRepository bDSNewRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("")
	public String login(Model model) {

		model.addAttribute("province_id_list", provinceService.findAll());
		model.addAttribute("posts", this.getNewPost());
		model.addAttribute("highlight_posts", this.getHighlightPost());
		return "layouts/user/index";
	}

	@RequestMapping(path = "/view/{id}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}

	private List<PostInfomation> getNewPost() {
		List<BdsNew> posts = bDSNewService.findByPageNumber(0);
		List<PostInfomation> results = GemRealtyService.makePostCardList(posts, districtRepository, provinceRepository);
		return results;
	}

	private List<List<PostInfomation>> getHighlightPost() {
		List<BdsNew> posts = bDSNewRepository.findHighlightPost();
		List<List<PostInfomation>> results = GemRealtyService.makeHighlightPost(posts, districtRepository,
				provinceRepository);
		return results;
	}
}
