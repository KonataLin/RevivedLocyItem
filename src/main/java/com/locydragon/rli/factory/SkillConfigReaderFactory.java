package com.locydragon.rli.factory;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillUnit;
import com.locydragon.rli.util.Colors;
import org.bukkit.configuration.file.FileConfiguration;

public class SkillConfigReaderFactory {
	public static int readFile(FileConfiguration config) {
		int find = 0;
		for (String key : config.getKeys(false)) {
			find++;
			SkillUnit unit = new SkillUnit(key, config.getInt(key + ".cooldown"),
					Colors.color(config.getString(key + ".wait")));
			for (String skillLine : config.getStringList(key + ".Skills")) {
				unit.addSkill(skillLine);
			}
			for (String conditionLine : config.getStringList(key + ".conditions")) {
				unit.addCondition(conditionLine);
			}
			LocyItemAPI.registerSkillUnit(unit);
		}
		return find;
	}
}
