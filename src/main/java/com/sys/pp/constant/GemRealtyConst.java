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
		NORTHWEST("Đông bắc"), //
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

	public enum PriceScope {
		NONE(0, 0, 0, "Thỏa thuận"), //
		_50(1, -1, 50, "<= 50 triệu"), //
		_50_100(2, 51, 100, "50 - 100 triệu"), //
		_100_200(3, 100, 200, "100 - 200 triệu"), //
		_200_500(4, 201, 500, "200 - 500 triệu"), //
		_500_1M(5, 500, 1000, "500 triệu - 1 tỷ"), //
		_1M_2M(6, 1001, 2000, "1 - 2 tỷ"), //
		_2M_5M(7, 2001, 5000, "2 - 5 tỷ"), //
		_THAN_5M(8, 5001, -1, "> 5 tỷ"); //

		int id;
		BigDecimal start;
		BigDecimal end;
		String lable;

		PriceScope(int id, int start, int end, String lable) {
			this.id = id;
			this.start = BigDecimal.valueOf(start);
			this.end = BigDecimal.valueOf(end);
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public BigDecimal getStart() {
			return start;
		}

		public BigDecimal getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
	}

	public enum AcreageScope {
		LESS_20(0, 0, 0, "< 20 m²"), //
		_20_30(1, 0, 0, "20 - 30 m²"), ///
		_30_40(2, 0, 0, "30 - 40 m²"), //
		_40_50(3, 0, 0, "40 - 50 m²"), //
		_50_60(4, 0, 0, "50 - 60 m²"), //
		_60_80(5, 0, 0, "60 - 80 m²"), //
		_80_100(6, 0, 0, "80 - 100 m²"), //
		_100_130(7, 0, 0, "100 - 130 m²"), //
		_130_160(8, 0, 0, "130 - 160 m²"), //
		_160_200(9, 0, 0, "160 - 200 m²"), ///
		_200_250(10, 0, 0, "200 - 250 m²"), //
		_20_300(11, 0, 0, "250 - 300 m²"), //
		_300_400(12, 0, 0, "300 - 400 m²"), //
		_400_00(13, 0, 0, "400 - 500 m²"), //
		_500_700(14, 0, 0, "500 - 700 m²"), //
		_700_1M(15, 0, 0, "700 - 1.000 m²"), //
		_1M_1M5(16, 0, 0, "1.000 - 1.500 m²"), //
		_1M_2M(17, 0, 0, "1.500 - 2.000 m²"), //
		_2M_3M(18, 0, 0, "2.000 - 3.000 m²"), //
		_3M_5M(19, 0, 0, "3.000 - 5.000 m²"), //
		_5M_7M(20, 0, 0, "5.000 - 7.000 m²"), //
		_7M_10M(21, 0, 0, "7.000 - 10.000 m²"), //
		_10M_20M(22, 0, 0, "10.000 - 20.000 m²"), //
		_20M_50M(23, 0, 0, "20.000 - 50.000 m²"), //
		_50M_100M(24, 0, 0, "50.000 - 100.000 m²"), //
		THAN_100M(25, 0, 0, ">= 100.000 m²");//

		int id;
		BigDecimal start;
		BigDecimal end;
		String lable;

		AcreageScope(int id, int start, int end, String lable) {
			this.id = id;
			this.start = BigDecimal.valueOf(start);
			this.end = BigDecimal.valueOf(end);
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public BigDecimal getStart() {
			return start;
		}

		public BigDecimal getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
	}

	public enum FontWidth {
		LESS_3(1, 0, 0, "< 3 m"), //
		_3_4(2, 0, 0, "3 - 4 m"), //
		_4_5(3, 0, 0, "4 - 5 m"), //
		_5_6(4, 0, 0, "5 - 6 m"), //
		_6_7(5, 0, 0, "6 - 7 m"), //
		_7_8(6, 0, 0, "7 - 8 m"), //
		_8_9(7, 0, 0, "8 - 9 m"), //
		_9_10(8, 0, 0, "9 - 10 m"), //
		_10_15(9, 0, 0, "10 - 15 m"), //
		_15_20(10, 0, 0, "15 - 20 m"), //
		_20_30(11, 0, 0, "20 - 30 m"), //
		_30_50(12, 0, 0, "30 - 50 m"), //
		_50_100(13, 0, 0, "50 - 100 m"), //
		THAN_100(14, 0, 0, ">= 100 m");//

		int id;
		BigDecimal start;
		BigDecimal end;
		String lable;

		FontWidth(int id, int start, int end, String lable) {
			this.id = id;
			this.start = BigDecimal.valueOf(start);
			this.end = BigDecimal.valueOf(end);
			;
			this.lable = lable;
		}

		public int getId() {
			return id;
		}

		public BigDecimal getStart() {
			return start;
		}

		public BigDecimal getEnd() {
			return end;
		}

		public String getLable() {
			return lable;
		}
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
