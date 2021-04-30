package com.sys.pp.constant;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sys.pp.model.NewsType;
import com.sys.pp.repo.NewsTypeRepository;
import com.sys.pp.util.FileUtil;

public class GemRealtyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GemRealtyConst.class);

	public static BigDecimal getPriceByCategory(NewsTypeRepository newsTypeRepository, Integer newsType, Date startDate,
			Date endDate) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hourOfDate = calendar.get(Calendar.HOUR_OF_DAY);

		Optional<NewsType> newsTypeOptinal = newsTypeRepository.findById(newsType);

		if (newsTypeOptinal.isPresent()) {
			BigDecimal g = BigDecimal.valueOf(newsTypeOptinal.get().getPrice());

			long diff = endDate.getTime() - startDate.getTime();
			long hour = diff / (60 * 60 * 1000);

			BigDecimal price = BigDecimal.valueOf(hour).multiply(g)
					.subtract(BigDecimal.valueOf(hourOfDate).multiply(g));

			return price;
		}
		return BigDecimal.ZERO;
	}

	public static List<List<String>> makeImagesLinkList(String imageUrlAction) {
		final String CHARATER = "multi-file";
		String basePath = imageUrlAction.substring(imageUrlAction.indexOf(CHARATER) + CHARATER.length());

		StringBuilder fullPath = new StringBuilder();
		fullPath.append(GemRealtyConst.DEFAULT_IMAGE_FOLDER);
		fullPath.append(File.separator);
		fullPath.append(basePath);

		try {
			if (FileUtil.isEmpty(Path.of(fullPath.toString()))) {
				return null;
			}
			File[] files = new File(fullPath.toString()).listFiles();

			List<List<String>> results = new ArrayList<>();
			StringBuilder tmpPath = new StringBuilder();
			List<String> tmp = new ArrayList<>();
			for (File file : files) {
				if (file.isFile()) {
					tmpPath.setLength(0);
					tmpPath.append("/images");
					tmpPath.append(basePath + "/");
					tmpPath.append(file.getName());

					if (tmp.size() == 5) {
						results.add(tmp);
						tmp = new ArrayList<String>();
					}
					tmp.add(tmpPath.toString());
				}
			}
			results.add(tmp);
			return results;
		} catch (IOException e) {
			LOGGER.warn("Không tìm thấy đường dẩn tới thư mục hình ảnh.");
			return null;
		}
	}

}
