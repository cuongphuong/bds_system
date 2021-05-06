package com.sys.pp.constant;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.ListUtils;

import com.sys.pp.constant.GemRealtyConst.Formality;
import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.controller.custommodel.PostInfomation;
import com.sys.pp.model.BdsNew;
import com.sys.pp.model.Category;
import com.sys.pp.model.DetailNew;
import com.sys.pp.model.District;
import com.sys.pp.model.Favourite;
import com.sys.pp.model.FavouritePK;
import com.sys.pp.model.NewsType;
import com.sys.pp.repo.CategoryRepository;
import com.sys.pp.repo.DistrictRepository;
import com.sys.pp.repo.FavouriteRepository;
import com.sys.pp.repo.NewsTypeRepository;
import com.sys.pp.repo.ProvinceRepository;
import com.sys.pp.util.FileUtil;
import com.sys.pp.util.StringUtils;

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

	public static List<List<PostInfomation>> makeHighlightPost(String userId, FavouriteRepository favouriteRepository,
			List<BdsNew> posts, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		List<List<PostInfomation>> results = new ArrayList<>();
		List<PostInfomation> tmpList = new ArrayList<>();
		for (BdsNew item : posts) {
			PostInfomation post = makeAnItem(userId, favouriteRepository, item, formatter, districtRepository,
					provinceRepository);
			tmpList.add(post);

			if (tmpList.size() == 4) {
				results.add(tmpList);
				tmpList = new ArrayList<>();
			}
		}
		if (!ListUtils.isEmpty(tmpList))
			results.add(tmpList);
		return results;
	}

	public static List<PostInfomation> makePostCardList(String userId, FavouriteRepository favouriteRepository,
			List<BdsNew> posts, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		List<PostInfomation> results = new ArrayList<>();

		for (BdsNew item : posts) {
			PostInfomation post = makeAnItem(userId, favouriteRepository, item, formatter, districtRepository,
					provinceRepository);
			results.add(post);
		}
		return results;
	}

	private static PostInfomation makeAnItem(String userId, FavouriteRepository favouriteRepository, BdsNew item,
			DecimalFormat formatter, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
		PostInfomation post = new PostInfomation();

		// Title
		post.setTitle(item.getTitle());
		// Diện tích && Giá tiền
		String x1 = item.getDetailNew().getAcreage() != 0 ? formatter.format(item.getDetailNew().getAcreage()) + "m²"
				: "--";
		BigDecimal price = item.getDetailNew().getPrice();
		String x2 = price != null && price.compareTo(BigDecimal.ZERO) != 0
				? formatter.format(item.getDetailNew().getPrice()) + " "
						+ GemRealtyConst.getUnitFromId(item.getDetailNew().getUnit())
				: "Thỏa thuận";
		post.setDescription(String.format("%s · %s", x2, x1));

		// Hình ảnh
		try {
			final String CHARATER = "multi-file";
			String imageUrlAction = item.getDetailNew().getImages();
			String basePath = imageUrlAction.substring(imageUrlAction.indexOf(CHARATER) + CHARATER.length());
			String fullPath = String.format("%s%s%s", GemRealtyConst.DEFAULT_IMAGE_FOLDER, File.separator, basePath);
			File f = new File(fullPath);
			if (f.exists() && f.isDirectory() && !FileUtil.isEmpty(Path.of(fullPath))) {
				List<List<String>> images = GemRealtyService.makeImagesLinkList(item.getDetailNew().getImages());
				post.setThumnail(images.get(0).get(0));
			} else {
				throw new Exception("Not existed image");
			}
		} catch (Exception e) {
			post.setThumnail("/image/no_image.jpg");
			LOGGER.info("Image not avaiable.");
		}

		// Địa chỉ
		post.setAddress(makeAddress(item, districtRepository, provinceRepository));
		// Url
		String url = String.format(GemRealtyConst.BASE_FINISH_URL, item.getNewsId(),
				StringUtils.toSlug(item.getTitle()));
		post.setUrlPost(url);
		post.setLevel(item.getLevel());

		if (userId != null && favouriteRepository != null) {
			Optional<Favourite> liked = favouriteRepository.findById(new FavouritePK(userId, item.getNewsId()));
			if (liked.isPresent()) {
				post.setLiked(true);
			} else {
				post.setLiked(false);
			}
		}

		return post;
	}

	private static String makeAddress(BdsNew news, DistrictRepository districtRepository,
			ProvinceRepository provinceRepository) {
		StringBuilder address = new StringBuilder();
		DetailNew detail = news.getDetailNew();

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

	public static List<KeyValue> getRealEstateByCategory(CategoryRepository categoryRepository) {
		List<Category> categoryList = categoryRepository.findAll();

		List<KeyValue> result = new ArrayList<>();
		for (Category item : categoryList) {
			KeyValue obj = new KeyValue();
			obj.setKey(String.valueOf(item.getCategoryId()));
			obj.setValue(item.getCategoryName());
			obj.setValue1(String.format("/search?category=%s", item.getCategoryId()));
			result.add(obj);
		}
		return result;
	}

	public static List<KeyValue> getCategoryList(CategoryRepository categoryRepository,
			GemRealtyConst.Formality format) {
		List<Category> categoryList = categoryRepository.findAll();
		categoryList = categoryList.stream().limit(10).collect(Collectors.toList());

		List<KeyValue> result = new ArrayList<>();
		List<String> formality = new ArrayList<>();

		if (GemRealtyConst.Formality.BUY.equals(format)) {
			formality.add(Formality.BUY.toString());
			formality.add(Formality.SELL.toString());
		} else {
			formality.add(Formality.RENT.toString());
			formality.add(Formality.LEASE.toString());
		}

		for (Category item : categoryList) {
			KeyValue obj = new KeyValue();
			obj.setKey(String.valueOf(item.getCategoryId()));
			obj.setValue(item.getCategoryName());

			int count = categoryRepository.countByCategory(item.getCategoryId(), formality);
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			obj.setValue2(String.format("(%s)", formatter.format(count)));

			if (GemRealtyConst.Formality.BUY.equals(format)) {
				obj.setValue1(String.format("/search?formality=SELL_BUY&category=%s", item.getCategoryId()));
			} else {
				obj.setValue1(String.format("/search?formality=LEASE_RENT&category=%s", item.getCategoryId()));
			}

			result.add(obj);
		}
		return result;
	}
}
