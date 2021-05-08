package com.sys.pp.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sys.pp.controller.custommodel.KeyValue;
import com.sys.pp.controller.custommodel.LabelValue;
import com.sys.pp.util.StringUtils;

public class GemRealtyConst {
	public static String DEFAULT_IMAGE_FOLDER = "D:\\GemRealty";
	public static String DEFAULT_IMAGE_FOLDER_TEMP = "D:\\GemRealtyTemp";
	public static final String BASE_FINISH_URL = "/view/bds/%s/%s";

	public enum Formality {
		SELL("Bán"), LEASE("Cho thuê"), BUY("Mua"), RENT("Thuê");

		String name;

		Formality(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static String getFormalityFromId(String id) {
		return Arrays.asList(Formality.values()).stream().filter(x -> x.toString().equals(id)).map(x -> x.getName())
				.findFirst().get();
	}

	public enum Unit {
		MILLION(0, "Triệu"), BILLION(1, "Tỷ");

		String name;
		int id;

		Unit(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}

	public static String getUnitFromId(int id) {
		return Arrays.asList(Unit.values()).stream().filter(x -> x.getId() == id).map(x -> x.getName()).findFirst()
				.get();
	}

	public enum Direction {
		NONE("Không xác định"), //
		EAST("Đông"), //
		WEST("Tây"), //
		SOUTH("Nam"), //
		NORTH("Bắc"), //
		NORTHEAST("Đông bắc"), //
		NORTHWEST("Tây bắc"), //
		SOUTHWEST("Tây nam"), //
		SOUTHEAST("Đông nam"); //

		String name;

		Direction(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum SortResult {
		NEW(1, "Tin đăng mới nhất "), //
		OLD(2, "Tin đăng cũ nhất "), //
		PRICE_UP(3, "Giá tăng dần "), //
		PRICE_DOWN(4, "Giá giảm dần "), //
		ACREAGE_UP(5, "Diện tích tăng dần "), //
		ACREAGE_DOWN(6, "Diện tích giảm dần "), //
		A_Z(7, "Tiêu đề từ A - Z "), //
		Z_A(8, "Tiêu đề từ Z - A "); //

		int id;
		String label;

		SortResult(int id, String label) {
			this.id = id;
			this.label = label;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	public static SortResult getCortResultById(int id) {
		SortResult result = Arrays.asList(SortResult.values()).stream().filter(p -> p.getId() == id).findFirst()
				.orElse(null);
		return result;
	}

	public enum PriceScope {
		NONE(0, 0, 0, "Thỏa thuận"), //
		_50(1, 1, 50, "<= 50 triệu"), //
		_50_100(2, 50, 100, "50 - 100 triệu"), //
		_100_200(3, 100, 200, "100 - 200 triệu"), //
		_200_500(4, 200, 500, "200 - 500 triệu"), //
		_500_1M(5, 500, 1000, "500 triệu - 1 tỷ"), //
		_1M_2M(6, 1000, 2000, "1 - 2 tỷ"), //
		_2M_5M(7, 2000, 5000, "2 - 5 tỷ"), //
		_THAN_5M(8, 5000, -1, "> 5 tỷ"); //

		int id;
		int start;
		int end;
		String lable;

		PriceScope(int id, int start, int end, String lable) {
			this.id = id;
			this.start = start;
			this.end = end;
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
	}

	public static PriceScope getPriceScopeById(int id) {
		PriceScope result = Arrays.asList(PriceScope.values()).stream().filter(p -> p.getId() == id).findFirst()
				.orElse(null);
		return result;
	}

	public enum AcreageScope {
		LESS_20(0, 0, 20, "< 20 m²"), //
		_20_30(1, 21, 30, "20 - 30 m²"), ///
		_30_40(2, 31, 40, "30 - 40 m²"), //
		_40_50(3, 41, 50, "40 - 50 m²"), //
		_50_60(4, 50, 60, "50 - 60 m²"), //
		_60_80(5, 61, 80, "60 - 80 m²"), //
		_80_100(6, 81, 100, "80 - 100 m²"), //
		_100_130(7, 101, 130, "100 - 130 m²"), //
		_130_160(8, 131, 160, "130 - 160 m²"), //
		_160_200(9, 161, 200, "160 - 200 m²"), ///
		_200_250(10, 201, 250, "200 - 250 m²"), //
		_20_300(11, 251, 300, "250 - 300 m²"), //
		_300_400(12, 301, 400, "300 - 400 m²"), //
		_400_00(13, 401, 500, "400 - 500 m²"), //
		_500_700(14, 501, 700, "500 - 700 m²"), //
		_700_1M(15, 701, 1000, "700 - 1.000 m²"), //
		_1M_1M5(16, 1001, 1500, "1.000 - 1.500 m²"), //
		_1M_2M(17, 1501, 2000, "1.500 - 2.000 m²"), //
		_2M_3M(18, 2001, 3000, "2.000 - 3.000 m²"), //
		_3M_5M(19, 3001, 5000, "3.000 - 5.000 m²"), //
		_5M_7M(20, 5001, 7000, "5.000 - 7.000 m²"), //
		_7M_10M(21, 7001, 10000, "7.000 - 10.000 m²"), //
		_10M_20M(22, 10001, 20000, "10.000 - 20.000 m²"), //
		_20M_50M(23, 20001, 50000, "20.000 - 50.000 m²"), //
		_50M_100M(24, 50001, 100000, "50.000 - 100.000 m²"), //
		THAN_100M(25, 100001, -1, ">= 100.000 m²"); //

		int id;
		int start;
		int end;
		String lable;

		AcreageScope(int id, int start, int end, String lable) {
			this.id = id;
			this.start = start;
			this.end = end;
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
	}

	public static AcreageScope getAcreageScopeById(int id) {
		AcreageScope result = Arrays.asList(AcreageScope.values()).stream().filter(p -> p.getId() == id).findFirst()
				.orElse(null);
		return result;
	}

	public enum FontWidth {
		LESS_3(1, 0, 2, "< 3 m"), //
		_3_4(2, 3, 4, "3 - 4 m"), //
		_4_5(3, 4, 5, "4 - 5 m"), //
		_5_6(4, 5, 6, "5 - 6 m"), //
		_6_7(5, 6, 7, "6 - 7 m"), //
		_7_8(6, 7, 8, "7 - 8 m"), //
		_8_9(7, 8, 9, "8 - 9 m"), //
		_9_10(8, 9, 10, "9 - 10 m"), //
		_10_15(9, 10, 15, "10 - 15 m"), //
		_15_20(10, 15, 20, "15 - 20 m"), //
		_20_30(11, 20, 30, "20 - 30 m"), //
		_30_50(12, 30, 50, "30 - 50 m"), //
		_50_100(13, 50, 100, "50 - 100 m"), //
		THAN_100(14, 100, 99999999, ">= 100 m");//

		int id;
		int start;
		int end;
		String lable;

		FontWidth(int id, int start, int end, String lable) {
			this.id = id;
			this.start = start;
			this.end = end;
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
	}

	public static FontWidth getFontWidthFromId(int id) {
		return Arrays.asList(FontWidth.values()).stream().filter(x -> x.getId() == id).findFirst().orElse(null);
	}

	public static List<LabelValue> getPriceScope() {
		List<LabelValue> results = Arrays.asList(PriceScope.values()).stream().map(LabelValue::new)
				.collect(Collectors.toList());
		return results;
	}

	public static List<LabelValue> getFrontWidth() {
		List<LabelValue> results = Arrays.asList(FontWidth.values()).stream().map(LabelValue::new)
				.collect(Collectors.toList());
		return results;
	}

	public static List<LabelValue> getSortResults() {
		List<LabelValue> results = Arrays.asList(SortResult.values()).stream().map(LabelValue::new)
				.collect(Collectors.toList());
		return results;
	}

	public static List<LabelValue> getAcreageScope() {
		List<LabelValue> results = Arrays.asList(AcreageScope.values()).stream().map(LabelValue::new)
				.collect(Collectors.toList());
		return results;
	}

	public static String getDirectionFromId(String id) {
		return Arrays.asList(Direction.values()).stream().filter(x -> x.toString().equals(id)).map(x -> x.getName())
				.findFirst().orElse(StringUtils.EMPTY);
	}

	/**
	 * Create danh sách HÌNH THỨC bài post theo key - value
	 * 
	 * @return List<KeyValue>
	 */
	public static List<KeyValue> createFormalityList() {
		List<Formality> list = Arrays.asList(Formality.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Formality item : list) {
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

	/**
	 * Create danh sách HƯỚNG bài post theo key - value
	 * 
	 * @return List<KeyValue>
	 */
	public static List<KeyValue> getDirectionList() {
		List<Direction> list = Arrays.asList(Direction.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Direction item : list) {
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

	/**
	 * Create danh sách ĐƠN VỊ TÍNH bài post theo key - value
	 * 
	 * @return List<KeyValue>
	 */
	public static List<KeyValue> getUnitList() {
		List<Unit> list = Arrays.asList(Unit.values());
		List<KeyValue> resultLst = new ArrayList<>();

		for (Unit item : list) {
			resultLst.add(new KeyValue(String.valueOf(item.getId()), item.getName()));
		}
		return resultLst;
	}
}
