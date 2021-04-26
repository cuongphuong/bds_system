package com.sys.pp.constant;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.sys.pp.model.Category;
import com.sys.pp.repo.CategoryRepository;

public class GemRealtyService {
	
	public static BigDecimal getPriceByCategory(CategoryRepository categoryRepository, Integer categoryId,
			Date startDate, Date endDate) {
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

			return price;
		}
		return BigDecimal.ZERO;
	}
	
	
}
