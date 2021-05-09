package com.sys.pp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.constant.GemRealtyConst.Formality;
import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.Users;
import com.sys.pp.repo.BDSNewRepository;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.ContactRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.FavouriteRepository;
import com.sys.pp.repo.ProjectRepository;
import com.sys.pp.repo.ProvinceRepository;
import com.sys.pp.repo.StreetRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.repo.WardRepository;
import com.sys.pp.service.BDSNewService;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.util.StringUtils;

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
	@Autowired
	FavouriteRepository favouriteRepository;

	@GetMapping("")
	public String view(Model model, Principal principal) {

		Users user = null;
		try {
			String email = principal.getName();
			user = userRepository.findByEmailAddress(email);

			if (StringUtils.isNullOrEmpty(email) || user == null)
				throw new Exception();
		} catch (Exception e) {
			LOGGER.warn("Chưa đăng nhập");
		}

		model.addAttribute("province_id_list", provinceService.findAll());
		model.addAttribute("category_list", categoryRepository.findAll());
		model.addAttribute("posts", this.getNewPost(user, favouriteRepository));
		model.addAttribute("highlight_posts", this.getHighlightPost(user, favouriteRepository));
		model.addAttribute("buy_category", GemRealtyService.getCategoryList(categoryRepository, Formality.BUY));
		model.addAttribute("rent_category", GemRealtyService.getCategoryList(categoryRepository, Formality.RENT));

		List<String> formality = new ArrayList<>();
		formality.add(Formality.BUY.toString());
		formality.add(Formality.SELL.toString());
		model.addAttribute("buy_countall", categoryRepository.countAllByFormatly(formality));
		model.addAttribute("buy_countallinday", categoryRepository.countAllByFormatlyInday(formality));

		formality = new ArrayList<>();
		formality.add(Formality.RENT.toString());
		formality.add(Formality.LEASE.toString());
		model.addAttribute("rent_countall", categoryRepository.countAllByFormatly(formality));
		model.addAttribute("rent_countallinday", categoryRepository.countAllByFormatlyInday(formality));

		return "layouts/user/index";
	}

	@RequestMapping(path = "/view/{id}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}

	@RequestMapping(path = "/about")
	public String about() {
		return "layouts/user/about";
	}

	@RequestMapping(path = "/favourite")
	public String favourite(Model model) {
		PostInfomation info = new PostInfomation();
		info.setMoreByCategory(GemRealtyService.getRealEstateByCategory(categoryRepository));
		model.addAttribute("infomation", info);
		return "layouts/user/favourite";
	}

	private List<PostInfomation> getNewPost(Users user, FavouriteRepository favouriteRepository) {
		List<BdsNew> posts = bDSNewService.findByPageNumber(0);
		posts = posts.stream().filter(p -> p.getDeleteFlg() == 0 && p.getStatusFlg() == 1).collect(Collectors.toList());

		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}

		List<PostInfomation> results = GemRealtyService.makePostCardList(userId, favouriteRepository, posts,
				districtRepository, provinceRepository, categoryRepository);
		return results;
	}

	private List<List<PostInfomation>> getHighlightPost(Users user, FavouriteRepository favouriteRepository) {

		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}

		List<BdsNew> posts = bDSNewRepository.findHighlightPost();
		List<List<PostInfomation>> results = GemRealtyService.makeHighlightPost(userId, favouriteRepository, posts,
				districtRepository, provinceRepository, categoryRepository);
		return results;
	}
}
