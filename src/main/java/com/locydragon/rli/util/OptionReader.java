package com.locydragon.rli.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionReader {
	String info;
	List<String> values = new ArrayList<>();
	String when;
	public OptionReader(String info) {
		if (info.contains("@")) {
			this.info = info.trim().split("@")[0].trim();
			this.when = info.trim().split("@")[1].trim();
		} else {
			this.info = info.trim();
		}
		Arrays.stream(this.info.split(";")).forEach(x -> values.add(x));
	}

	public String getInfo() {
		return this.info;
	}

	public String getValue(String defaultValue, String... key) {
		List<String> asList = Arrays.asList(key);
		for (String obj : values) {
			if (asList.contains(obj.split("=", 2)[0])) {
				return obj.split("=", 2)[1];
			}
		}
		return defaultValue;
	}

	public String getWhen() {
		return this.when;
	}
}
