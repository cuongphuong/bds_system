package com.sys.pp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.controller.custommodel.StatisticalResult;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.NewsTypeRepository;
import com.sys.pp.repo.ProjectRepository;
import com.sys.pp.service.ProvinceService;
import com.sys.pp.service.StatisticalService;
import com.sys.pp.service.StatisticalService.Type;
import com.sys.pp.util.DateUtil;

@Controller
@RequestMapping("/admin/statistical")
public class StatisticalController {

	@Autowired
	StatisticalService statisticalService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	NewsTypeRepository newsTypeRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@RequestMapping(path = "")
	public String view(Model model, Principal principal) {
		return "layouts/admin/statistical";
	}

	@ResponseBody
	@PostMapping(path = "/province", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json; charset=UTF-8")
	public List<StatisticalResult> province(@RequestBody Map<String, String> paramater) {
		Type type = this.getTypeById(paramater.get("type"));
		if (null == type) {
			return new ArrayList<StatisticalResult>();
		}

		List<StatisticalResult> result = new ArrayList<StatisticalResult>();
		switch (type) {
		case NEW_TYPE:
			result = this.statisticalByNewType(paramater, type);
			break;
		case BDS_CATEGORY:
			result = this.statisticalByCategory(paramater, type);
			break;
		case PROVINCE:
			result = this.statisticalByProvince(paramater, type);
			break;
		case PROJECT:
			result = this.statisticalByProject(paramater, type);
			break;
		}
		return result;
	}
	
	private List<StatisticalResult> statisticalByProject(Map<String, String> paramater, Type type) {
		Date dateFrom = DateUtil.convertFromString(paramater.get("dateFrom"), DateUtil.DDMMYYYY_FORMAT);
		Date dateTo = DateUtil.convertFromString(paramater.get("dateTo"), DateUtil.DDMMYYYY_FORMAT);
		String[] formalitys = paramater.get("formality").split("_");
		List<StatisticalResult> data = statisticalService.statistical(dateFrom, dateTo, Arrays.asList(formalitys),
				type);

		List<StatisticalResult> result = new ArrayList<StatisticalResult>();
		for (StatisticalResult item : data) {
			StatisticalResult newItem = new StatisticalResult();

			String typeName = projectRepository.findById(Integer.valueOf(item.getLabel())).get().getName();
			newItem.setLabel(typeName);
			newItem.setNewsNumber(item.getNewsNumber());
			newItem.setPrice(item.getPrice());
			newItem.setVat(item.getVat());
			newItem.setFinalPrice(item.getFinalPrice());
			result.add(newItem);
		}
		return result;
	}
	
	private List<StatisticalResult> statisticalByCategory(Map<String, String> paramater, Type type) {
		Date dateFrom = DateUtil.convertFromString(paramater.get("dateFrom"), DateUtil.DDMMYYYY_FORMAT);
		Date dateTo = DateUtil.convertFromString(paramater.get("dateTo"), DateUtil.DDMMYYYY_FORMAT);
		String[] formalitys = paramater.get("formality").split("_");
		List<StatisticalResult> data = statisticalService.statistical(dateFrom, dateTo, Arrays.asList(formalitys),
				type);

		List<StatisticalResult> result = new ArrayList<StatisticalResult>();
		for (StatisticalResult item : data) {
			StatisticalResult newItem = new StatisticalResult();

			String typeName = categoryRepository.findById(Integer.valueOf(item.getLabel())).get().getCategoryName();
			newItem.setLabel(typeName);
			newItem.setNewsNumber(item.getNewsNumber());
			newItem.setPrice(item.getPrice());
			newItem.setVat(item.getVat());
			newItem.setFinalPrice(item.getFinalPrice());
			result.add(newItem);
		}
		return result;
	}

	private List<StatisticalResult> statisticalByNewType(Map<String, String> paramater, Type type) {
		Date dateFrom = DateUtil.convertFromString(paramater.get("dateFrom"), DateUtil.DDMMYYYY_FORMAT);
		Date dateTo = DateUtil.convertFromString(paramater.get("dateTo"), DateUtil.DDMMYYYY_FORMAT);
		String[] formalitys = paramater.get("formality").split("_");
		List<StatisticalResult> data = statisticalService.statistical(dateFrom, dateTo, Arrays.asList(formalitys),
				type);

		List<StatisticalResult> result = new ArrayList<StatisticalResult>();
		for (StatisticalResult item : data) {
			StatisticalResult newItem = new StatisticalResult();

			String typeName = newsTypeRepository.findByLevel(Integer.valueOf(item.getLabel())).getName();
			newItem.setLabel(typeName);
			newItem.setNewsNumber(item.getNewsNumber());
			newItem.setPrice(item.getPrice());
			newItem.setVat(item.getVat());
			newItem.setFinalPrice(item.getFinalPrice());
			result.add(newItem);
		}
		return result;
	}

	private List<StatisticalResult> statisticalByProvince(Map<String, String> paramater, Type type) {
		Date dateFrom = DateUtil.convertFromString(paramater.get("dateFrom"), DateUtil.DDMMYYYY_FORMAT);
		Date dateTo = DateUtil.convertFromString(paramater.get("dateTo"), DateUtil.DDMMYYYY_FORMAT);
		String[] formalitys = paramater.get("formality").split("_");

		List<StatisticalResult> data = statisticalService.statistical(dateFrom, dateTo, Arrays.asList(formalitys),
				type);

		List<StatisticalResult> result = new ArrayList<StatisticalResult>();
		for (StatisticalResult item : data) {
			StatisticalResult newItem = new StatisticalResult();

			String provinceName = provinceService.findById(Integer.valueOf(item.getLabel())).getName();
			newItem.setLabel(provinceName);
			newItem.setNewsNumber(item.getNewsNumber());
			newItem.setPrice(item.getPrice());
			newItem.setVat(item.getVat());
			newItem.setFinalPrice(item.getFinalPrice());
			result.add(newItem);
		}
		return result;
	}

	private Type getTypeById(String id) {
		return Arrays.asList(Type.values()).stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
	}

}
