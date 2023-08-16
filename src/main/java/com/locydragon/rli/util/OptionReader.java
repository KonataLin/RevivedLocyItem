package com.locydragon.rli.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**技能格式处理类
 * 用于格式化技能信息
 * @author LocyDragon
 * @version 1.3.3
 */
public class OptionReader {
	String info;
	List<String> values = new ArrayList<>();
	String when;
	/**以一条信息来读取并创建OptionReader
	 * @param info 输入的信息，如 value1=1;value2=2 @LEFT 输入后会被自动格式化
	 */
	public OptionReader(String info) {
		if (info.contains("@")) {
			this.info = info.trim().split("@")[0].trim();
			this.when = info.trim().split("@")[1].trim();
		} else {
			this.info = info.trim();
		}
		Arrays.stream(this.info.split(";")).forEach(x -> values.add(x));
	}

	/**获取所有信息
	 */
	public String getInfo() {
		return this.info;
	}

	/**读取value值
	 * @param defaultValue 如果不存在时默认的返回值
	 * @param key 可以输入很多个，代表别名，如value1、v1等
	 */
	public String getValue(String defaultValue, String... key) {
		List<String> asList = Arrays.asList(key);
		for (String obj : values) {
			if (asList.contains(obj.split("=", 2)[0])) {
				return obj.split("=", 2)[1];
			}
		}
		return defaultValue;
	}

	/**获取@符号后的信息，如LEFT、RIGHT
	 */
	public String getWhen() {
		return this.when;
	}
}
