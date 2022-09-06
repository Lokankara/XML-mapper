package com.xml.parser.util;

public enum XML {
	CARS("Cars"), CAR("Car"), MODEL("Model"), COLOR("Color"), WHEEL("Wheel"),
	POSITION("Position"), TIRE("Tire"), DIAMETER("Diameter"),
	ENGINE("Engine"), POWER("Power"), RUN("Run"), FUEL("Fuel"),
	TYPE("Type"), PRICE("Price"), BRAND("Brand"), ID("id");

	private final String value;

	public String value() {
		return value;
	}

	XML(String value) {
		this.value = value.intern();
	}
}
