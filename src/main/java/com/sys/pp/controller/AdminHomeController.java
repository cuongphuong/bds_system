package com.sys.pp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.service.StatisticalService;
import com.sys.pp.util.DateUtil;

@Controller
@RequestMapping("admin")
public class AdminHomeController {
	@Autowired
	StatisticalService statisticalService;

	@GetMapping("")
	public String home(Model model, Principal principal) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		model.addAttribute("priceByMonth", formatter.format(statisticalService.countPrice(true)) + " VND");
		model.addAttribute("allPrice", formatter.format(statisticalService.countPrice(false)) + " VND");
		model.addAttribute("userByMonth", formatter.format(statisticalService.countUser(false)));
		model.addAttribute("allUser", formatter.format(statisticalService.countUser(false)));
		return "layouts/admin/home";
	}
	
	@ResponseBody
	@GetMapping("chart-data")
	private Map<String, Object> makeDataList() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		List<String> labelList = new ArrayList<String>();
		for (int i = 7; i > 0; i--) {
			Date d = DateUtil.getMoveDay(-i);
			BigDecimal tmp = statisticalService.getPriceOfDay(d);
			if (tmp == null ) {
				list.add(BigDecimal.ZERO);
			} else {
				list.add(tmp);
			}
			
			labelList.add(DateUtil.convertToString(d));
		}
		
		result.put("data", list);
		result.put("label", labelList);
		return result;
	}
}
