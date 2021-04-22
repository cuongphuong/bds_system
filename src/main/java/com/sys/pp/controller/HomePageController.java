package com.sys.pp.controller;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.constant.GemRealtyConst.Direction;
import com.sys.pp.constant.GemRealtyConst.Formality;
import com.sys.pp.constant.GemRealtyConst.Unit;
import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.model.Category;
import com.sys.pp.model.District;
import com.sys.pp.model.Project;
import com.sys.pp.model.Street;
import com.sys.pp.model.Users;
import com.sys.pp.model.Ward;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.ContactRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.ProjectRepository;
import com.sys.pp.repo.StreetRepository;
import com.sys.pp.repo.UserRepository;
import com.sys.pp.repo.WardRepository;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.util.DateUtil;
import com.sys.pp.util.NumberUtils;

@Controller
@RequestMapping("")
public class HomePageController {

	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private StreetRepository streetRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("")
	public String login() {
		return "layouts/user/index";
	}
	
	@RequestMapping(path = "/view/{id}")
	public String view(Model model, Principal principal, @PathVariable Integer id) {
		return "layouts/user/view";
	}

	/**
	 * Load form add new
	 * 
	 * @return
	 */
	@RequestMapping(path = "/post")
	public String loadForm(Model model, Principal principal) {
		String email = principal.getName();

		model.addAttribute("user_credit", userRepository.findByEmailAddress(email).getCredit());
		model.addAttribute("contact_list", contactRepository.findContactListByEmail(email));
		model.addAttribute("formalitys", this.getFormalityList());
		model.addAttribute("province_id_list", provinceService.findAll());
		model.addAttribute("category_list", categoryRepository.findAll());
		model.addAttribute("unit_listxx", this.getUnitList());
		model.addAttribute("direction_list", this.getDirectionList());

		model.addAttribute("actionUpload", this.makeUploadAction());
		return "layouts/user/post-news";
	}

	@ResponseBody
	@RequestMapping(path = "/calcalator/{categoryId}")
	public Map<String, Object> caculatorPrice(@PathVariable Integer categoryId,
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

		if (categoryId == null || startDate == null || endDate == null
				|| startDate.before(cal.getTime())
				|| endDate.before(cal.getTime()) 
				|| (endDate.getTime() - startDate.getTime()) == 0) {
			return result;
		}

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hourOfDate = calendar.get(Calendar.HOUR_OF_DAY);
		Optional<Category> categoryOptinal = categoryRepository.findById(categoryId);

		if (categoryOptinal.isPresent()) {
			BigDecimal g = categoryOptinal.get().getPrice();

			long diff = endDate.getTime() - startDate.getTime();
			long hour = diff / (60 * 60 * 1000);

			BigDecimal price = BigDecimal.valueOf(hour).multiply(g)
					.subtract(BigDecimal.valueOf(hourOfDate).multiply(g));

			result.put("status", true);
			result.put("data", price);
		}

		return result;
	}
	
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

	@ResponseBody
	@RequestMapping(path = "/get-district/{provinceId}")
	public List<District> loadDistrictsByProvinceId(@PathVariable Integer provinceId) {
		return districtRepository.findByProvinceId(provinceId);
	}

	@ResponseBody
	@RequestMapping(path = "/get-ward/{district}")
	public List<Ward> loadWardsByDistrictId(@PathVariable Integer district) {
		return wardRepository.findByDistrictId(district);
	}

	@ResponseBody
	@RequestMapping(path = "/get-street/{district}")
	public List<Street> loadStreetByDistrictId(@PathVariable Integer district) {
		return streetRepository.findStreetByDistrictId(district);
	}

	@ResponseBody
	@RequestMapping(path = "/get-project/{district}")
	public List<Project> loadProjectByDistrictId(@PathVariable Integer district) {
		return projectRepository.findProjectByDistrictId(district);
	}

	private List<KeyValue> getFormalityList() {
		List<Formality> list = Arrays.asList(Formality.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Formality item : list) {
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

	private List<KeyValue> getDirectionList() {
		List<Direction> list = Arrays.asList(Direction.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Direction item : list) {
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

	private List<KeyValue> getUnitList() {
		List<Unit> list = Arrays.asList(Unit.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Unit item : list) {
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

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
			uploadAction.append(macAddress.toString());
			uploadAction.append("/");
			uploadAction.append(new Date().getTime());

			return uploadAction.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
