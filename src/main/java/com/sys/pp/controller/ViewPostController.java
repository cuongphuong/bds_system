
package com.sys.pp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.Contact;
import com.sys.pp.model.ContactPK;
import com.sys.pp.model.DetailNew;
import com.sys.pp.model.District;
import com.sys.pp.model.Project;
import com.sys.pp.model.Street;
import com.sys.pp.model.Users;
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
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("view")
public class ViewPostController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewPostController.class);

	@Autowired
	UserRepository userRepository;
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

	@RequestMapping(path = "/bds/{id}/{slug}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		try {
			Optional<BdsNew> newsOpt = bDSNewRepository.findById(id);
			if (!newsOpt.isPresent()) {
				return null;
			}
			BdsNew news = newsOpt.get();
			PostInfomation info = new PostInfomation();
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			DetailNew detailNews = news.getDetailNew();

			List<List<String>> images = GemRealtyService.makeImagesLinkList(detailNews.getImages());
			if (images != null) {
				info.setImages(images);
				info.setHasImage(true);
			}
			info.setTitle(news.getTitle() + " - Gem Realty");
			info.setBreadcrumbItems(this.makeBreadcrumbs(news));
			info.setAddress(this.makeAddress(news));
			BigDecimal price = detailNews.getPrice();
			info.setPrice(price != null && price.compareTo(BigDecimal.ZERO) != 0
					? formatter.format(detailNews.getPrice()) + " " + GemRealtyConst.getUnitFromId(detailNews.getUnit())
					: "Thỏa thuận");
			info.setAcreage(detailNews.getAcreage() != 0 ? formatter.format(detailNews.getAcreage()) + "m²" : "--");
			info.setDescription(!StringUtils.isNullOrEmpty(detailNews.getDescription()) ? detailNews.getDescription()
					: "Không có thông tin mô tả");
			if (!StringUtils.isNullOrEmpty(detailNews.getVideoUrl())) {
				info.setVideoUrl(detailNews.getVideoUrl());
				info.setHasVideoUrl(true);
			} else {
				info.setHasVideoUrl(false);
			}

			if (!StringUtils.isNullOrEmpty(StringUtils.nullToEmpty(detailNews.getProjectId()))) {
				String projectName = projectRepository.findById(detailNews.getProjectId()).get().getName();
				info.setProjectId(!StringUtils.isNullOrEmpty(detailNews.getProjectId().toString())
						? String.format("Dự án %s", projectName)
						: StringUtils.EMPTY);
			}

			info.setDirection(GemRealtyConst.getDirectionFromId(detailNews.getDirection()));
			info.setFrontWidth(detailNews.getFrontWidth() != 0 ? formatter.format(detailNews.getFrontWidth()) + "m"
					: StringUtils.EMPTY);
			info.setEntranceWidth(
					detailNews.getEntranceWidth() != 0 ? formatter.format(detailNews.getEntranceWidth()) + "m"
							: StringUtils.EMPTY);
			info.setEntranceWidth(
					detailNews.getEntranceWidth() != 0 ? formatter.format(detailNews.getEntranceWidth()) + "m"
							: StringUtils.EMPTY);

			if (!StringUtils.isNullOrEmpty(StringUtils.nullToEmpty(detailNews.getFloorsNum()))) {
				info.setFloorsNum(formatter.format(detailNews.getFloorsNum()) + " (tầng)");
			}

			if (!StringUtils.isNullOrEmpty(StringUtils.nullToEmpty(detailNews.getToiletNum()))) {
				info.setToiletNum(formatter.format(detailNews.getToiletNum()) + " (phòng)");
			}

			if (!StringUtils.isNullOrEmpty(StringUtils.nullToEmpty(detailNews.getRoomNum()))) {
				info.setRoomNum(formatter.format(detailNews.getRoomNum()) + " (phòng)");
			}

			info.setFurniture(!StringUtils.isNullOrEmpty(detailNews.getFurniture()) ? detailNews.getFurniture() : "--");
			info.setJuridicalInfo(
					!StringUtils.isNullOrEmpty(detailNews.getJuridicalInfo()) ? detailNews.getJuridicalInfo() : "--");

			if (detailNews.getProjectId() != null) {
				Optional<Project> project = projectRepository.findById(detailNews.getProjectId());
				if (project.isPresent()) {
					info.setLat(project.get().getLat());
					info.setLng(project.get().getLng());
				}
			}

			ContactPK pk = new ContactPK();
			pk.setInd(detailNews.getContactInd());
			pk.setUserId(news.getCreateBy());
			Contact contact = contactRepository.findById(pk).get();

			info.setContactName(contact.getContactName());
			info.setDiaChi(contact.getDiaChi());
			info.setEmail(contact.getEmail());

			String phoneMask = "####.###.###";
			MaskFormatter maskFormatter = new MaskFormatter(phoneMask);
			maskFormatter.setValueContainsLiteralCharacters(false);
			String phoneFormat = maskFormatter.valueToString(contact.getPhone());
			info.setPhone(phoneFormat);

			info.setMoreBds(this.getRealEstateNearby(detailNews.getProvinceId()));
			info.setMoreByCategory(GemRealtyService.getRealEstateByCategory(categoryRepository));

			Users owner = null;
			if (principal != null) {
				String email = principal.getName();
				owner = userRepository.findByEmailAddress(email);
			}

			if (news.getStatusFlg() == 1) {
				info.setApproved(true);
			}

			if (news.getDeleteFlg() == 1) {
				info.setCanceled(true);
			}

			if (owner != null && owner.getUserId().equals(news.getCreateBy())) {
				info.setAccessByOwner(true);
			} else {
				info.setAccessByOwner(false);
			}

			Users user = userRepository.findById(news.getCreateBy()).get();
			model.addAttribute("user", user);
			model.addAttribute("infomation", info);

			return "layouts/user/view";
		} catch (Exception ex) {
			LOGGER.error("Xử lý hiển thị tin lỗi", ex);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi tải tin");
		}
	}

	private List<KeyValue> getRealEstateNearby(int provinceId) {
		List<District> districtList = districtRepository.findByProvinceId(provinceId);
		districtList = districtList.stream().limit(5).collect(Collectors.toList());

		List<KeyValue> result = new ArrayList<>();
		for (District item : districtList) {
			int countAll = bDSNewRepository.coutnNewsByDistrictId(item.getId());
			int countByMonth = bDSNewRepository.coutnNewsByDistrictIdOnMonth(item.getId());

			KeyValue obj = new KeyValue();

			obj.setKey(String.valueOf(item.getId()));
			obj.setValue(String.format("Bất động sản %s", item.getName()));
			obj.setValue1(String.valueOf(countAll));
			obj.setValue2(countByMonth != 0 ? String.format("Có %s bài viết được đăng trong tháng này.", countByMonth)
					: "Chưa có bài viết nào trong tháng này.");
			obj.setValue3(String.format("/search?location=%s&district=%s", item.getProvinceId(), item.getId()));

			result.add(obj);
		}
		return result;
	}

	private List<String> makeBreadcrumbs(BdsNew news) {
		List<String> result = new ArrayList<String>();
		result.add(GemRealtyConst.getFormalityFromId(news.getDetailNew().getFormality()));
		result.add(provinceRepository.findById(news.getDetailNew().getProvinceId()).get().getName());
		result.add(categoryRepository.findById(news.getCategoryId()).get().getCategoryName());

		if (news.getDetailNew().getProjectId() != null) {
			String project = projectRepository.findById(news.getDetailNew().getProjectId()).get().getName();
			result.add(String.format("Bất động sản dự án %s", project));
		} else {
			result.add(news.getTitle());
		}

		return result;
	}

	private String makeAddress(BdsNew news) {
		StringBuilder address = new StringBuilder();
		DetailNew detail = news.getDetailNew();

		if (detail.getProjectId() != null) {
			String project = projectRepository.findById(detail.getProjectId()).get().getName();
			address.append(String.format("Dự án %s, ", project));
		}

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
