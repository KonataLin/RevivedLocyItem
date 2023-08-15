package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.SkillConfigReaderFactory;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ListBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SkillConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Skills//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredSkill();
		genDefault();
		int total = 0;
		L:for (File exists : new File(dir).listFiles()) {
			if (exists.getName().endsWith(".yml")) {
				try {
					YamlConfiguration config = new YamlConfiguration();
					config.load(exists);
				} catch (Throwable e) {
					for (OfflinePlayer p : Bukkit.getOperators()) {
						if (p.isOnline()) {
							p.getPlayer().sendMessage(Colors.color("&cRli加载错误：在文件 &e" + exists.getName()));
						}
						Bukkit.getLogger().info(Colors.color("&cRli加载错误：在文件 &e" + exists.getName()));
					}
					continue L;
				}
				total += SkillConfigReaderFactory.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("Successfully load " + total + " skills in RLI!");
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("成功加载了 " + total + " 个技能于 RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleSkill.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleSkill.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);
				config.set("ExampleSkill.cooldown", 10);
				config.set("ExampleSkill.wait",
						"&7>>> &bPlease wait for {cd}s so that you can use skill again!");
				config.set("ExampleSkill.conditions",
						ListBuilder.buildList("random ~ value=0.75;fail=&cThis skill is executed 75% of the time",
								"compare ~ value=1 > 0;fail=&c&lThis will never happen.",
								"compare ~ value=Test equals test;fail=&c&lThis will never happen."));
				config.set("ExampleSkill.Skills", ListBuilder.buildList("msg ~ m=&7Chug! Chug!", "particle ~ name=Star"
				, "lightning ~", "delay ~ 1000", "launch ~ type=FireBall;d=15", "push ~ dp=-1.6;dh=0.8"));

				config.set("ExampleSkillReach.cooldown", 3);
				config.set("ExampleSkillReach.wait",
						"&7>>> &cPlease wait for {cd}s so that you can use skill again!");
				config.set("ExampleSkillReach.Skills", ListBuilder.buildList("reach ~ r=15;d=25"));

				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
