package com.sys.pp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.pp.model.BdsNew;
import com.sys.pp.service.BDSNewService;

@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	BDSNewService newService;
	
	/**
	 * Post a news
	 * @param Map<String, String> paramater
	 * Key map:
	 * * category_id
	 * * title // Tiêu đề
	 * * formality // hình thức
	 * * province_id // tỉnh
	 * * district_id // huyện
	 * * ward_id // xã
	 * * street_id // đường
	 * * project_id // dự án
	 * * time (house)
	 * * contact_ind
	 * * price // Giá
	 * * unit // đơn vị tính
	 * 
	 * * -- detail
	 * * direction // hướng
	 * * front_width // mặt tiền
	 * * entrance_width // chiều rộng lối vào
	 * * floors_num // số tầng
	 * * room_num  // số phòng ngủ
	 * * furniture // dồ nội thất
	 * * toilet_num // số nhà vệ sinh
	 * * juridical_info // thông tin pháp lý
	 * * images
	 * * lat
	 * * lng
	 * * description // mổ tả
	 * * acreage // diện tích
	 * 
	 * * -- contact new
	 * * contact_name // tên liên hệ
	 * * phone
	 * * diachi
	 * @return status
	 */
	@ResponseBody
	@RequestMapping(path = "/save", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public BdsNew addNews(@RequestBody Map<String, String> paramater) {
		
		
		return new BdsNew();
	}
	
	
	

	/**
	 * Get by page number
	 * @param page int
	 * @return JSON Array
	 */
	@ResponseBody
	@RequestMapping(path = "/get/{page}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public List<BdsNew> getNews(@PathVariable Integer page) {
		if (null == page) {
			return new ArrayList<>();
		}
		return newService.findByPageNumber(page);
	}
}
