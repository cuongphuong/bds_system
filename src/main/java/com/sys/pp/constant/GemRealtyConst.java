package com.sys.pp.constant;

public class GemRealtyConst {
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
		EAST("Hướng đông"), //
		WEST("Hướng tây"), //
		SOUTH("Hướng nam"), //
		NORTH("Hướng bắc"), //
		NORTHEAST("Hướng đông bắc"), //
		NORTHWEST("Hướng đông bắc"), //
		SOUTHWEST("Hướng tây nam"), //
		SOUTHEAST("Hướng đông nam"); //

		String name;

		Direction(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

}
