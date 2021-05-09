package com.sys.pp.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.sys.pp.constant.GemRealtyConst;
import com.sys.pp.constant.GemRealtyService;
import com.sys.pp.constant.Names;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.Contact;
import com.sys.pp.model.ContactPK;
import com.sys.pp.model.DetailNew;
import com.sys.pp.model.District;
import com.sys.pp.model.Province;
import com.sys.pp.model.Street;
import com.sys.pp.model.Users;
import com.sys.pp.model.Ward;
import com.sys.pp.repo.BDSNewRepository;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.ContactRepository;
import com.sys.pp.repo.DetailNewRepository;
import com.sys.pp.repo.NewsTypeRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.service.ContactService;
import com.sys.pp.service.DistrictService;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.service.StreetService;
import com.sys.pp.service.WardService;
import com.sys.pp.util.DateUtil;
import com.sys.pp.util.FileUtil;
import com.sys.pp.util.NumberUtils;
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("post")
public class PostController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewPostController.class);

	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	WardService wardService;
	@Autowired
	DistrictService districtService;
	@Autowired
	StreetService streetService;
	@Autowired
	DetailNewRepository detailNewRepos;
	@Autowired
	ContactService contactService;
	@Autowired
	BDSNewRepository bDSNewRepository;
	@Autowired
	NewsTypeRepository newsTypeRepository;

	/**
	 * Load form for add new bds
	 * 
	 * @return view
	 */
	@RequestMapping(path = "")
	public String view(Model model, Principal principal) {
		String email = principal.getName();

		model.addAttribute("user_credit", userRepository.findByEmailAddress(email).getCredit());
		model.addAttribute("contact_list", contactRepository.findContactListByEmail(email));
		model.addAttribute("formalitys", GemRealtyConst.createFormalityList());
		model.addAttribute("province_id_list", provinceService.findAll());
		model.addAttribute("category_list", categoryRepository.findAll());
		model.addAttribute("unit_listxx", GemRealtyConst.getUnitList());
		model.addAttribute("direction_list", GemRealtyConst.getDirectionList());
		model.addAttribute("news_type", newsTypeRepository.findAll());

		model.addAttribute("actionUpload", this.makeUploadAction());
		return "layouts/user/post-news";
	}
	
	@ResponseBody
	@RequestMapping(path = "/preview", method = RequestMethod.POST)
	public Map<String, Object> preview(Principal principal, @RequestBody Map<String, String> paramater) {
		Map<String, Object> result = new HashMap<>();
		result.put("status", true);

		// Save image upload
		String destFolderPath = this.createImageFolderPath(paramater.get("image"));
		File destFolder = new File(destFolderPath);
		try {
			FileUtils.forceMkdir(destFolder);

			// move file from tmp to upload folder
			String tmpFolderStr = this.makeTmpFolder(paramater.get("image"));
			File tmpFolder = new File(tmpFolderStr);

			File folderCheck = new File(tmpFolderStr);

			if (folderCheck.exists() && folderCheck.isDirectory()) {
				if (!FileUtil.isEmpty(Path.of(tmpFolderStr))) {
					org.apache.commons.io.FileUtils.copyDirectory(tmpFolder, destFolder);
				}
			}
		} catch (IOException e) {
			LOGGER.error("UPLOAD IMAGES FAIL", e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload hình ảnh lỗi");
		}

		Contact contact = null;
		try {
			// create contact
			String email = principal.getName();
			Users user = userRepository.findByEmailAddress(email);
			if ("new".equals(paramater.get("contact_method"))) {
				contact = this.createContact(paramater);
				ContactPK pk = new ContactPK();
				pk.setUserId(user.getUserId());
				pk.setInd(contactService.registNewInd(user.getUserId()));
				contact.setId(pk);
			} else {
				ContactPK pk = new ContactPK();
				pk.setUserId(user.getUserId());
				pk.setInd(Integer.valueOf(paramater.get("contact_ind").substring(5)));
				Optional<Contact> contactOpt = contactRepository.findById(pk);
				if (contactOpt.isPresent()) {
					contact = contactOpt.get();
				}
			}

			// Tạo tin
			BdsNew bdsNew = this.createNews(paramater, user.getUserId());

			// Tạo chi tiết tin
			DetailNew detail = this.createDetailNew(paramater);
			detail.setNewsId(bdsNew.getNewsId());
			detail.setContactInd(contact.getId().getInd());

			String finishUrl = String.format(GemRealtyConst.BASE_FINISH_URL, bdsNew.getNewsId(),
					StringUtils.toSlug(bdsNew.getTitle()));
			
			

			
			
			
			
			result.put("data", finishUrl);
			return result;
		} catch (Exception ex) {
			LOGGER.error("SAVE NEWS ERROR", ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lưu thông tin lỗi");
		}
	}
	
	
	
	
	
	
	

	@ResponseBody
	@RequestMapping(path = "/validate", method = RequestMethod.POST)
	public Map<String, Object> validateToSave(Principal principal, @RequestBody Map<String, String> paramater) {
		// validate data
		Map<String, Object> errors = this.validate(paramater);
		Map<String, Object> result = new HashMap<>();

		if (!errors.isEmpty()) {
			result.put("status", false);
			result.put("data", errors);
			return result;
		}
		result.put("status", true);
		return result;
	}

	@ResponseBody
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(Principal principal, @RequestBody Map<String, String> paramater) {
		// validate data
		Map<String, Object> errors = this.validate(paramater);
		Map<String, Object> result = new HashMap<>();
		result.put("status", true);

		if (!errors.isEmpty()) {
			result.put("status", false);
			result.put("data", errors);
			return result;
		}

		// Save image upload
		String destFolderPath = this.createImageFolderPath(paramater.get("image"));
		File destFolder = new File(destFolderPath);
		try {
			FileUtils.forceMkdir(destFolder);

			// move file from tmp to upload folder
			String tmpFolderStr = this.makeTmpFolder(paramater.get("image"));
			File tmpFolder = new File(tmpFolderStr);

			File folderCheck = new File(tmpFolderStr);

			if (folderCheck.exists() && folderCheck.isDirectory()) {
				if (!FileUtil.isEmpty(Path.of(tmpFolderStr))) {
					org.apache.commons.io.FileUtils.copyDirectory(tmpFolder, destFolder);
				}
			}
		} catch (IOException e) {
			LOGGER.error("UPLOAD IMAGES FAIL", e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload hình ảnh lỗi");
		}

		Contact contact = null;
		try {
			// create contact
			String email = principal.getName();
			Users user = userRepository.findByEmailAddress(email);
			if ("new".equals(paramater.get("contact_method"))) {
				contact = this.createContact(paramater);
				ContactPK pk = new ContactPK();
				pk.setUserId(user.getUserId());
				pk.setInd(contactService.registNewInd(user.getUserId()));
				contact.setId(pk);
				contactRepository.save(contact);
			} else {
				ContactPK pk = new ContactPK();
				pk.setUserId(user.getUserId());
				pk.setInd(Integer.valueOf(paramater.get("contact_ind").substring(5)));
				Optional<Contact> contactOpt = contactRepository.findById(pk);
				if (contactOpt.isPresent()) {
					contact = contactOpt.get();
				}
			}

			// Tạo tin
			BdsNew bdsNew = this.createNews(paramater, user.getUserId());
			bDSNewRepository.save(bdsNew);
			// Tạo chi tiết tin
			DetailNew detail = this.createDetailNew(paramater);
			detail.setNewsId(bdsNew.getNewsId());
			detail.setContactInd(contact.getId().getInd());
			detailNewRepos.save(detail);

			String finishUrl = String.format(GemRealtyConst.BASE_FINISH_URL, bdsNew.getNewsId(),
					StringUtils.toSlug(bdsNew.getTitle()));
			result.put("data", finishUrl);
			return result;
		} catch (Exception ex) {
			LOGGER.error("SAVE NEWS ERROR", ex);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lưu thông tin lỗi");
		}
	}

	private String makeTmpFolder(String string) {
		String[] actionArr = string.split("/");
		String dateFolder = actionArr[3];
		String macFolder = actionArr[4];
		String timeFolder = actionArr[5];

		StringBuilder uploadAction = new StringBuilder();
		uploadAction.append(GemRealtyConst.DEFAULT_IMAGE_FOLDER_TEMP);
		uploadAction.append(File.separator);
		uploadAction.append(dateFolder);
		uploadAction.append(File.separator);
		uploadAction.append(macFolder);
		uploadAction.append(File.separator);
		uploadAction.append(timeFolder);
		return uploadAction.toString();
	}

	private String createImageFolderPath(String string) {
		String[] actionArr = string.split("/");
		String dateFolder = actionArr[3];
		String macFolder = actionArr[4];
		String timeFolder = actionArr[5];

		// create folder save image
		StringBuilder path = new StringBuilder();
		path.append(GemRealtyConst.DEFAULT_IMAGE_FOLDER);
		path.append(File.separator);
		path.append(dateFolder);
		path.append(File.separator);
		path.append(macFolder);
		path.append(File.separator);
		path.append(timeFolder);

		return path.toString();
	}

	private BdsNew createNews(Map<String, String> paramater, String userId) {
		BdsNew bdsNew = new BdsNew();

		// required
		bdsNew.setCategoryId(Integer.valueOf(paramater.get("category_id")));
		bdsNew.setTitle(paramater.get("title"));
		bdsNew.setStartDate(DateUtil.convertFromString(paramater.get("startDate")));
		bdsNew.setEndDate(DateUtil.convertFromString(paramater.get("endDate")));
		bdsNew.setCreateAt(new Date());
		bdsNew.setCreateBy(userId);
		bdsNew.setStatusFlg(Names.FLAG_OFF);
		bdsNew.setDeleteFlg(Names.FLAG_OFF);
		bdsNew.setLevel(Integer.valueOf(paramater.get("newsType")));

		bdsNew.setPrice(GemRealtyService.getPriceByCategory(newsTypeRepository,
				Integer.valueOf(paramater.get("newsType")), DateUtil.convertFromString(paramater.get("startDate")),
				DateUtil.convertFromString(paramater.get("endDate"))));
		return bdsNew;
	}

	private DetailNew createDetailNew(Map<String, String> paramater) {
		DetailNew detail = new DetailNew();
		// required
		detail.setFormality(paramater.get("formality"));

		// not require
		if (!StringUtils.isNullOrEmpty(paramater.get("province_id"))) {
			detail.setProvinceId(Integer.valueOf(paramater.get("province_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("district_id"))) {
			detail.setDistrictId(Integer.valueOf(paramater.get("district_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("ward_id"))) {
			detail.setWardId(Integer.valueOf(paramater.get("ward_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("street_id"))) {
			detail.setStreetId(Integer.valueOf(paramater.get("street_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("street_id"))) {
			detail.setStreetId(Integer.valueOf(paramater.get("street_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("project_id"))) {
			detail.setProjectId(Integer.valueOf(paramater.get("project_id")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("direction"))) {
			detail.setDirection(paramater.get("direction"));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("front_width"))) {
			detail.setFrontWidth(Double.valueOf(paramater.get("front_width")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("entrance_width"))) {
			detail.setEntranceWidth(Double.valueOf(paramater.get("entrance_width")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("floors_num"))) {
			detail.setFloorsNum(Integer.valueOf(paramater.get("floors_num")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("room_num"))) {
			detail.setRoomNum(Integer.valueOf(paramater.get("room_num")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("furniture"))) {
			detail.setFurniture(paramater.get("furniture"));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("toilet_num"))) {
			detail.setToiletNum(Integer.valueOf(paramater.get("toilet_num")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("juridical_info"))) {
			detail.setJuridicalInfo(paramater.get("juridical_info"));
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("acreage"))) {
			detail.setAcreage(Double.valueOf(paramater.get("acreage")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("price"))) {
			detail.setPrice(new BigDecimal(paramater.get("price")));
			detail.setUnit(Integer.valueOf(paramater.get("unit")));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("description"))) {
			detail.setDescription(paramater.get("description"));
		}
		if (!StringUtils.isNullOrEmpty(paramater.get("video"))) {
			detail.setVideoUrl(paramater.get("video"));
		}

		// image
		detail.setImages(paramater.get("image"));
		return detail;
	}

	private Contact createContact(Map<String, String> paramater) {
		Contact contact = new Contact();
		contact.setContactName(paramater.get("contact_name"));
		contact.setDiaChi(paramater.get("diachi"));
		contact.setEmail(paramater.get("email"));
		contact.setPhone(paramater.get("phone"));
		return contact;
	}

	/**
	 * Tính toán lại giá tiền của bài đăng khi user theo đổi này đăng
	 * 
	 * @return Map<String, Object> * status: boolean * price: number
	 */
	@ResponseBody
	@RequestMapping(path = "/calcalator/{newsType}")
	public Map<String, Object> caculatorPrice(@PathVariable Integer newsType,
			@RequestBody Map<String, String> paramater) {
		Map<String, Object> result = new HashMap<>();
		result.put("status", false);

		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date startDate = DateUtil.convertFromString(paramater.get("startDate"));
		Date endDate = DateUtil.convertFromString(paramater.get("endDate"));

		if (newsType == null || startDate == null || endDate == null || startDate.before(cal.getTime())
				|| endDate.before(cal.getTime()) || (endDate.getTime() - startDate.getTime()) == 0) {
			return result;
		}

		result.put("status", true);
		result.put("data", GemRealtyService.getPriceByCategory(newsTypeRepository, newsType, startDate, endDate));
		return result;
	}

	/**
	 * Add thêm số dư user đã thực hiện nạp
	 * 
	 * @return String ~ price added
	 */
	@ResponseBody
	@RequestMapping(value = "/add-credit", method = RequestMethod.POST)
	public String addCredit(@RequestBody Map<String, String> paramater, Principal principal) {
		if (!NumberUtils.isNumeric(paramater.get("credit"))) {
			return "false";
		}

		int creditAdd = Integer.valueOf(paramater.get("credit"));
		String email = principal.getName();

		Users user = userRepository.findByEmailAddress(email);
		user.setCredit(user.getCredit().add(BigDecimal.valueOf(creditAdd)));
		userRepository.save(user);
		return user.getCredit().toString();
	}

	/**
	 * Create đường dẩn thư mục tạm chứa hình ảnh upload cho bài đăng
	 * 
	 * @return String action path
	 */
	private String makeUploadAction() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder macAddress = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			StringBuilder uploadAction = new StringBuilder();
			uploadAction.append("/upload/multi-file/");
			uploadAction.append(DateUtil.convertToString(new Date()));
			uploadAction.append("/");
			uploadAction.append(macAddress.toString());
			uploadAction.append("/");
			uploadAction.append(new Date().getTime());

			return uploadAction.toString();
		} catch (Exception e) {
			return null;
		}
	}

	private Map<String, Object> validate(Map<String, String> paramater) {
		Map<String, Object> errors = new HashMap<>();
		// validate tiêu đề bài viết
		if (StringUtils.isNullOrEmpty(paramater.get("title"))) {
			errors.put("validate_title", "Tiêu đề bài viết không được để trống");
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("title"))
				&& (paramater.get("title").length() < 30 || paramater.get("title").length() > 90)) {
			errors.put("validate_title", "Tiêu đề bài viết từ 30 đến 90 kí tự.");
		}

		// hình thức
		if (StringUtils.isNullOrEmpty(paramater.get("formality"))) {
			errors.put("validate_formality", "Vui lòng chọn 1 loại hình thức bài đăng.");
		}

		if (StringUtils.isNullOrEmpty(paramater.get("province_id"))) {
			errors.put("validate_province_id", "Địa điểm chính xác là bắt buộc.");
		}

		// Tỉnh thành
		Province province = null;
		if (!StringUtils.isNullOrEmpty(paramater.get("province_id"))
				&& !NumberUtils.isNumeric(paramater.get("province_id"))) {
			errors.put("validate_province_id", "Kiểm tra lại tỉnh thành đã chọn.");
		} else if (!StringUtils.isNullOrEmpty(paramater.get("province_id"))) {
			province = provinceService.findById(Integer.valueOf(paramater.get("province_id")));
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("province_id")) && province == null) {
			errors.put("validate_province_id", "Tỉnh thành đã chọn không tồn tại.");
		}

		// validate quận huyện
		if (StringUtils.isNullOrEmpty(paramater.get("district_id"))) {
			errors.put("validate_district_id", "Địa điểm chính xác là bắt buộc.");
		}

		District district = null;
		if (!StringUtils.isNullOrEmpty(paramater.get("district_id"))
				&& !NumberUtils.isNumeric(paramater.get("district_id"))) {
			errors.put("validate_district_id", "Kiểm tra lại quận huyện đã chọn.");
		} else if (!StringUtils.isNullOrEmpty(paramater.get("district_id"))) {
			district = districtService.findById(Integer.valueOf(paramater.get("district_id")));
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("district_id")) && district == null) {
			errors.put("validate_district_id", "Quận huyện đã chọn không tồn tại.");
		}

		if (province != null && district != null && district.getProvinceId() != province.getId()) {
			errors.put("validate_district_id", "Quận huyện đã chọn không thuộc tỉnh.");
		}

		// validate xã phường
		if (StringUtils.isNullOrEmpty(paramater.get("ward_id"))) {
			errors.put("validate_ward_id", "Địa điểm chính xác là bắt buộc.");
		}

		Ward ward = null;
		if (!StringUtils.isNullOrEmpty(paramater.get("ward_id")) && !NumberUtils.isNumeric(paramater.get("ward_id"))) {
			errors.put("validate_ward_id", "Kiểm tra lại xã phường đã chọn.");
		} else if (!StringUtils.isNullOrEmpty(paramater.get("ward_id"))) {
			ward = wardService.findById(Integer.valueOf(paramater.get("ward_id")));
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("ward_id")) && ward == null) {
			errors.put("validate_ward_id", "Xã phường đã chọn không tồn tại.");
		}

		if (district != null && ward != null && ward.getDistrictId() != district.getId()) {
			errors.put("validate_ward_id", "Xã phường đã chọn không thuộc quận huyện.");
		}

		// validate đường
		Street street = null;
		if (!StringUtils.isNullOrEmpty(paramater.get("street_id"))
				&& !NumberUtils.isNumeric(paramater.get("street_id"))) {
			errors.put("validate_ward_id", "Kiểm tra lại xã phường đã chọn.");
		} else if (!StringUtils.isNullOrEmpty(paramater.get("street_id"))) {
			street = streetService.findById(Integer.valueOf(paramater.get("street_id")));
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("street_id")) && street == null) {
			errors.put("validate_street_id", "Vui lòng kiểm tra lại tuyến đường.");
		}

		if (district != null && street != null && street.getDistrictId() != district.getId()) {
			errors.put("validate_ward_id", "Tuyến đường này không thuộc quận huyện.");
		}
		// giá bán
		if (!StringUtils.isNullOrEmpty(paramater.get("price")) && !NumberUtils.isNumeric(paramater.get("price"))) {
			errors.put("validate_price", "Kiểm tra lại giá tiền.");
		}
		// danh mục
		if (StringUtils.isNullOrEmpty(paramater.get("category_id"))) {
			errors.put("validate_category_id", "Không được để trống danh mục bài đăng.");
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("category_id"))
				&& !categoryRepository.findById(Integer.valueOf(paramater.get("category_id"))).isPresent()) {
			errors.put("validate_category_id", "Kiểm tra lại danh mục bài đăng.");
		}
		// diện tích
		if (StringUtils.isNullOrEmpty(paramater.get("acreage"))) {
			errors.put("validate_acreage", "Diện tích không được bỏ trống.");
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("acreage")) && !NumberUtils.isNumeric(paramater.get("acreage"))) {
			errors.put("validate_acreage", "Kiểm tra lại diện tích.");
		}
		// youtube link
		if (!StringUtils.isNullOrEmpty(paramater.get("video")) && !StringUtils.isYoutubeUrl(paramater.get("video"))) {
			errors.put("validate_video", "Kiểm tra lại liên kết được nhập.");
		}
		// Diện tích mặt trước
		if (!StringUtils.isNullOrEmpty(paramater.get("front_width"))
				&& !NumberUtils.isNumeric(paramater.get("front_width"))) {
			errors.put("validate_front_width", "Kiểm tra lại diện tích mặt trước.");
		}
		// Số tầng
		if (!StringUtils.isNullOrEmpty(paramater.get("floors_num"))
				&& !NumberUtils.isNumeric(paramater.get("floors_num"))) {
			errors.put("validate_floors_num", "Kiểm tra lại số tầng.");
		}
		// Số toilet
		if (!StringUtils.isNullOrEmpty(paramater.get("toilet_num"))
				&& !NumberUtils.isNumeric(paramater.get("toilet_num"))) {
			errors.put("validate_toilet_num", "Kiểm tra lại số toilet.");
		}
		// Chiefu rộng lối vào
		if (!StringUtils.isNullOrEmpty(paramater.get("entrance_width"))
				&& !NumberUtils.isNumeric(paramater.get("entrance_width"))) {
			errors.put("validate_toilet_num", "Kiểm tra lại chiều rộng lối vào.");
		}
		// Số phòng ngủ
		if (!StringUtils.isNullOrEmpty(paramater.get("room_num"))
				&& !NumberUtils.isNumeric(paramater.get("room_num"))) {
			errors.put("validate_toilet_num", "Kiểm tra lại số phòng ngủ.");
		}
		// schedule
		if (!DateUtil.validateDate(paramater.get("startDate")) || !DateUtil.validateDate(paramater.get("endDate"))) {
			errors.put("validate_date", "Kiểm tra lịch trình bài đăng.");
		}

		if ("new".equals(paramater.get("contact_method"))) {
			if (!StringUtils.isNullOrEmpty(paramater.get("email"))
					&& !StringUtils.isValidEmail(paramater.get("email"))) {
				errors.put("validate_email", "Email không hợp lệ");
			}

			if (StringUtils.isNullOrEmpty(paramater.get("phone"))) {
				errors.put("validate_phone", "Số điện thoại không được để trống");
			}

			if (!StringUtils.isNullOrEmpty(paramater.get("phone"))
					&& !StringUtils.isValidPhone(paramater.get("phone"))) {
				errors.put("validate_phone", "Số điện thoại không đúng định dạng");
			}
		}

		return errors;
	}
}
