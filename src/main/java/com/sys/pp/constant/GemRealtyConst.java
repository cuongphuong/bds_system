package com.sys.pp.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sys.pp.controller.custommodel.KeyValue;
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
