package com.locydragon.rli.util;

public class LangReader {
	String info;

	public LangReader(String info) {
		this.info = info.trim();
	}

	public String headValue() {
		if (!info.contains("~")) {
			return new String();
		}
		return info.split("~", 2)[0].trim();
	}

	public String value() {
		if (!info.contains("~")) {
			return info;
		}
		return info.split("~", 2)[1].trim();
	}

	public boolean illegal() {
		return info.split("~", 2).length == 2;
	}
}
