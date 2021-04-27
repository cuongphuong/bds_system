package com.sys.pp.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sys.pp.controller.custommodel.KeyValue;

public class GemRealtyConst {
	public static String DEFAULT_IMAGE_FOLDER = "D:\\GemRealty";

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

	public enum Unit {
		MILLION("Triệu"), BILLION("Tỷ");

		String name;

		Unit(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
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
			resultLst.add(new KeyValue(item.toString(), item.getName()));
		}
		return resultLst;
	}

}
