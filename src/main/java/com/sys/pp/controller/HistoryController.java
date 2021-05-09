package com.sys.pp.controller;

import java.security.Principal;
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
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("/history")
public class HistoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);
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

	@GetMapping("/{pageNumber}")
	public String view(Model model, Principal principal, @PathVariable int pageNumber) {
		Users user = null;
		try {
			String email = principal.getName();
			user = userRepository.findByEmailAddress(email);

			if (StringUtils.isNullOrEmpty(email) || user == null)
				throw new Exception();
		} catch (Exception e) {
			LOGGER.warn("Chưa đăng nhập");
			return "layouts/user/history";
		}

		model.addAttribute("posts", this.getHistooryPost(user, favouriteRepository, pageNumber));
		return "layouts/user/history";
	}

	private List<PostInfomation> getHistooryPost(Users user, FavouriteRepository favouriteRepository, int pageNumber) {
		List<BdsNew> posts = bDSNewService.findByUserId(user.getUserId(), pageNumber);
		posts = posts.stream().filter(p -> p.getDeleteFlg() == 0 && p.getStatusFlg() == 1).collect(Collectors.toList());

		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}

		List<PostInfomation> results = GemRealtyService.makePostCardList(userId, favouriteRepository, posts,
				districtRepository, provinceRepository, categoryRepository);
		return results;
	}

}
