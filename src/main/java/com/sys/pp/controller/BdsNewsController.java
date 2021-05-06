package com.sys.pp.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.constant.Names;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.DetailNew;
import com.sys.pp.model.District;
import com.sys.pp.model.Street;
import com.sys.pp.model.Ward;
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
import com.sys.pp.util.DateUtil;
import com.sys.pp.util.NumberUtils;
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("/admin/news")
public class BdsNewsController {

	@Autowired
	private BDSNewService bdsNewService;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
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
	public String viewAll() {
		return "layouts/admin/news-list";
	}

	/**
	 * Update status_flg = 1
	 * 
	 * @return JSON List<Category>
	 */
	@PostMapping("approved/{ids}")
	@ResponseBody
	public String approved(@PathVariable String ids) {
		String[] idArr = ids.split("t");
		List<String> idList = Arrays.asList(idArr);

		for (String id : idList) {
			if (!NumberUtils.isNumeric(id)) {
				continue;
			}

			Optional<BdsNew> item = bDSNewRepository.findById(Integer.valueOf(id));
			if (item.isPresent()) {
				BdsNew bdsNew = item.get();

				if (bdsNew.getDeleteFlg() == 1 || bdsNew.getStatusFlg() == 1) {
					continue;
				}

				bdsNew.setStatusFlg(Names.FLAG_ON);
				bDSNewRepository.save(bdsNew);
			}
		}
		return "OK";
	}

	/**
	 * Update status_flg = 1
	 * 
	 * @return JSON List<Category>
	 */
	@PostMapping("cancel/{ids}")
	@ResponseBody
	public String cancel(@PathVariable String ids) {
		String[] idArr = ids.split("t");
		List<String> idList = Arrays.asList(idArr);

		for (String id : idList) {
			if (!NumberUtils.isNumeric(id)) {
				continue;
			}

			Optional<BdsNew> item = bDSNewRepository.findById(Integer.valueOf(id));
			if (item.isPresent()) {
				BdsNew bdsNew = item.get();

				if (bdsNew.getDeleteFlg() == 1 || bdsNew.getStatusFlg() == 1) {
					continue;
				}

				bdsNew.setDeleteFlg(Names.FLAG_ON);
				bDSNewRepository.save(bdsNew);
			}
		}
		return "OK";
	}

	/**
	 * Read by page
	 * 
	 * @return JSON List<Category>
	 */
	@ResponseBody
	@RequestMapping(path = "/get/{page}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<PostInfomation> loadData(@PathVariable Integer page,
			@RequestParam(required = false, name = "filter") String filter) {
		if (null == page) {
			return new ArrayList<>();
		}

		List<BdsNew> bdsNewList;
		if (!StringUtils.isNullOrEmpty(filter) && "notapproved".equals(filter)) {
			bdsNewList = bdsNewService.findNotApprovedByPageNumber(page);
		} else {
			bdsNewList = bdsNewService.findByPageNumber(page);
		}

		List<PostInfomation> result = this.makeAPostInfomation(bdsNewList);

		result = result.stream().sorted((x1, x2) -> x2.getNewsId().compareTo(x1.getNewsId()))
				.collect(Collectors.toList());

		return result;
	}

	private List<PostInfomation> makeAPostInfomation(List<BdsNew> bdsNewList) {
		List<PostInfomation> list = new ArrayList<>();
		for (BdsNew item : bdsNewList) {
			list.add(this.makeAnItem(item));
		}
		return list;
	}

	/**
	 * Delete by key
	 * 
	 * @param Integer id
	 * 
	 * @return none
	 */
	@ResponseBody
	@RequestMapping(path = "/delete/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable Integer id) {
		bDSNewRepository.deleteById(id);
	}

	private PostInfomation makeAnItem(BdsNew item) {
		DetailNew detail = item.getDetailNew();
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		PostInfomation post = new PostInfomation();
		post.setTitle(item.getTitle());
		post.setCreateAt(item.getCreateAt());
		post.setAcreage(detail.getAcreage() != 0 ? formatter.format(detail.getAcreage()) + "m²" : "--");
		BigDecimal price = detail.getPrice();
		post.setPrice(price != null && price.compareTo(BigDecimal.ZERO) != 0
				? formatter.format(detail.getPrice()) + " " + GemRealtyConst.getUnitFromId(detail.getUnit())
				: "Thỏa thuận");
		post.setPricePost(String.format("%s VND", formatter.format(item.getPrice())));

		if (!StringUtils.isNullOrEmpty(StringUtils.nullToEmpty(detail.getProjectId()))) {
			String projectName = projectRepository.findById(detail.getProjectId()).get().getName();
			post.setProjectId(!StringUtils.isNullOrEmpty(detail.getProjectId().toString())
					? String.format("Dự án %s", projectName)
					: StringUtils.EMPTY);
		} else {
			post.setProjectId("--");
		}

		post.setCategoryId(this.makeCategory(item));
		post.setNewsId(String.valueOf(item.getNewsId()));
		post.setAddress(this.makeAddress(item));
		post.setStatusFlg(String.valueOf(item.getStatusFlg()));
		post.setStartDate(DateUtil.convertDDMMYYYYString(item.getStartDate()));
		post.setEndDate(DateUtil.convertDDMMYYYYString(item.getEndDate()));
		if (item.getStatusFlg() == 1) {
			post.setApproved(true);
		}

		if (item.getDeleteFlg() == 1) {
			post.setCanceled(true);
		}

		return post;
	}

	private String makeCategory(BdsNew news) {
		List<String> result = new ArrayList<String>();
		result.add(GemRealtyConst.getFormalityFromId(news.getDetailNew().getFormality()));
		result.add(categoryRepository.findById(news.getCategoryId()).get().getCategoryName());

		return String.join(" -> ", result);
	}

	private String makeAddress(BdsNew news) {
		StringBuilder address = new StringBuilder();
		DetailNew detail = news.getDetailNew();

		if (detail.getStreetId() != null) {
			Street streetObj = streetRepository.findById(detail.getStreetId()).get();
			String street = streetObj.getPrefix() + " " + streetObj.getName();
			address.append(String.format("%s, ", street));
		}

		if (detail.getWardId() != null) {
			Ward wardObj = wardRepository.findById(detail.getWardId()).get();
			String ward = wardObj.getPrefix() + " " + wardObj.getName();
			address.append(String.format("%s, ", ward));
		}

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
