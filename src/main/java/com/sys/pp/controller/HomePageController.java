package com.sys.pp.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.DetailNew;
import com.sys.pp.model.District;
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
import com.sys.pp.util.FileUtil;
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

	@GetMapping("")
	public String login(Model model) {

		model.addAttribute("province_id_list", provinceService.findAll());
		model.addAttribute("posts", this.getNewPost());
		return "layouts/user/index";
	}

	@RequestMapping(path = "/view/{id}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}

	private List<PostInfomation> getNewPost() {
		List<BdsNew> posts = bDSNewService.findByPageNumber(0);
		List<PostInfomation> results = new ArrayList<>();
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		for (BdsNew item : posts) {
			PostInfomation post = new PostInfomation();
			// Title
			String title = item.getTitle().toUpperCase();
			if (title.length() > 50)
				title = title.substring(0, 50) + "...";
			post.setTitle(title);
			// Diện tích
			post.setAcreage(
					item.getDetailNew().getAcreage() != 0 ? formatter.format(item.getDetailNew().getAcreage()) + "m²"
							: "--");
			// Giá tiền
			BigDecimal price = item.getDetailNew().getPrice();
			post.setPrice(price != null && price.compareTo(BigDecimal.ZERO) != 0
					? formatter.format(item.getDetailNew().getPrice()) + " "
							+ GemRealtyConst.getUnitFromId(item.getDetailNew().getUnit())
					: "Thỏa thuận");
			// Hình ảnh
			try {
				final String CHARATER = "multi-file";
				String imageUrlAction = item.getDetailNew().getImages();
				String basePath = imageUrlAction.substring(imageUrlAction.indexOf(CHARATER) + CHARATER.length());
				String fullPath = String.format("%s%s%s", GemRealtyConst.DEFAULT_IMAGE_FOLDER, File.separator,
						basePath);
				File f = new File(fullPath);
				if (f.exists() && f.isDirectory() && !FileUtil.isEmpty(Path.of(fullPath))) {
					List<List<String>> images = GemRealtyService.makeImagesLinkList(item.getDetailNew().getImages());
					post.setThumnail(images.get(0).get(0));
				} else {
					throw new Exception("Not existed image");
				}
			} catch (Exception e) {
				post.setThumnail("/image/no_image.jpg");
				LOGGER.info("Image not avaiable.", e);
			}
			// Địa chỉ
			post.setAddress(this.makeAddress(item));
			// Url
			String url = String.format(GemRealtyConst.BASE_FINISH_URL, item.getNewsId(),
					StringUtils.toSlug(item.getTitle()));
			post.setUrlPost(url);

			results.add(post);
		}

		return results;
	}

	private String makeAddress(BdsNew news) {
		StringBuilder address = new StringBuilder();
		DetailNew detail = news.getDetailNew();

//		if (detail.getProjectId() != null) {
//			String project = projectRepository.findById(detail.getProjectId()).get().getName();
//			address.append(String.format("Dự án %s, ", project));
//		}
//
//		if (detail.getStreetId() != null) {
//			Street streetObj = streetRepository.findById(detail.getStreetId()).get();
//			String street = streetObj.getPrefix() + " " + streetObj.getName();
//			address.append(String.format("%s, ", street));
//		}

//		if (detail.getWardId() != null) {
//			Ward wardObj = wardRepository.findById(detail.getWardId()).get();
//			String ward = wardObj.getPrefix() + " " + wardObj.getName();
//			address.append(String.format("%s, ", ward));
//		}

		if (detail.getDistrictId() != null) {
			District districtObj = districtRepository.findById(detail.getDistrictId()).get();
			String district = districtObj.getPrefix() + " " + districtObj.getName();
			address.append(String.format("%s, ", district));
		}

		if (detail.getProvinceId() != null) {
			String province = provinceRepository.findById(detail.getProvinceId()).get().getName();
			address.append(String.format("%s", province));
		}

		return address.toString();
	}
}
