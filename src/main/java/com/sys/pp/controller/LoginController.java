package com.sys.pp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.sys.pp.constant.Names;
import com.sys.pp.model.RolePK;
import com.sys.pp.model.Roles;
import com.sys.pp.model.Users;
import com.sys.pp.repo.RoleRepository;
import com.sys.pp.service.UserService;
import com.sys.pp.util.StringUtils;

@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("login")
	public String login() {
		return "layouts/admin/login";
	}

	@GetMapping("regist")
	public String regist() {
		return "layouts/user/regist";
	}

	@ResponseBody
	@RequestMapping(path = "regist-save", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, String> paramater) {
		// validate data
		Map<String, String> errors = this.validate(paramater, false);
		Map<String, Object> result = new HashMap<>();
		result.put("status", true);

		if (!errors.isEmpty()) {
			result.put("status", false);
			result.put("data", errors);
			return result;
		}

		try {
			// Save user
			Users user = this.createObject(paramater);
			userService.save(user);

			// Set role user
			RolePK pk = new RolePK();
			pk.setRole(Names.ROLES.ROLE_USER.toString());
			pk.setUserId(user.getUserId());
			Roles role = new Roles();
			role.setId(pk);
			roleRepository.save(role);

			result.put("data", user);
			return result;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FAIL TO CREATE USER");
		}
	}

	private Users createObject(Map<String, String> paramater) {
		// convert to object
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(paramater.get("signuppassword"));

		Users user = new Users();
		user.setUserId(userService.registNewId());
		user.setUserName(paramater.get("signupusername"));
		user.setPhone(paramater.get("signupphone"));
		user.setPass(hashedPassword);
		user.setEmail(paramater.get("signupemail"));
		user.setCredit(BigDecimal.ZERO);

		return user;
	}

	private Map<String, String> validate(Map<String, String> paramater, boolean isUpdate) {
		Map<String, String> errors = new HashMap<>();

		if (StringUtils.isNullOrEmpty(paramater.get("signuppassword"))) {
			errors.put("validate_signuppassword", "Mật khẩu không được để trống");
		}

		if (!paramater.get("signuppassword").equals(paramater.get("signupcpassword"))) {
			errors.put("validate_signupcpassword", "Mật khẩu xác nhận không chính xác");
		}

		if (StringUtils.isNullOrEmpty(paramater.get("signupusername"))) {
			errors.put("validate_signupusername", "Tên người dùng không được để trống");
		}

		if (null != paramater.get("signupusername") && paramater.get("signupusername").length() < 5) {
			errors.put("validate_signupusername", "Tên người dùng phải lớn hơn 5 kí tự");
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("signupemail"))
				&& !StringUtils.isValidEmail(paramater.get("signupemail"))) {
			errors.put("validate_signupemail", "Email không hợp lệ");
		}

		if (!isUpdate && userService.findByEmailAddress(paramater.get("signupemail")) != null) {
			errors.put("validate_signupemail", "Email đã tồn tại");
		}

		if (StringUtils.isNullOrEmpty(paramater.get("signupphone"))) {
			errors.put("validate_signupphone", "Số điện thoại không được để trống");
		}

		if (!StringUtils.isNullOrEmpty(paramater.get("signupphone"))
				&& !StringUtils.isValidPhone(paramater.get("signupphone"))) {
			errors.put("validate_signupphone", "Số điện thoại không đúng định dạng");
		}

		return errors;
	}
}
