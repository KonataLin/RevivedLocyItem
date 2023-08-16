package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.ItemConfigReadFactory;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ListBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.entity.Player;


import java.io.File;
import java.io.IOException;

public class ItemConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Items//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredItem();
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
					}
					Bukkit.getLogger().info(Colors.color("&cRli加载错误：在文件 &e" + exists.getName()));
					continue L;
				}
				total += ItemConfigReadFactory.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("Successfully load " + total + " items in RLI!");
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("成功加载了 " + total + " 个特殊物品于 RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleItem.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleItem.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);
				config.set("ExampleItem.name", "&aTest Item");
				config.set("ExampleItem.id", 283);
				config.set("ExampleItem.lore", ListBuilder.buildList(
						"&b&m=======================================", "&bThis is a test item!", "    &7---- which has been created on rli.",
						"&7Left_Click: Damage the entity far away!",
						"&7Right_Click: Do something magical!", "&b&m======================================="
				));
				config.set("ExampleItem.Options", ListBuilder.buildList("MOVE_SPEED ~ 0.15", "UNBREAKABLE ~ true", "HIDE_UNBREAKABLE ~ true"
				, "HIDE_Attributes ~ true", "MAX_HEALTH ~ 3.5"));
				config.set("ExampleItem.Enchantment", ListBuilder.buildList("DAMAGE_ALL ~ 8"));
				config.set("ExampleItem.Skills", ListBuilder.buildList("skill ~ name=ExampleSkillReach @LEFT",
						"msg ~ msg=&7Slurp~ @LEFT",
						"skill ~ name=ExampleSkill @RIGHT"));
				config.set("ExampleItem.Hit", ListBuilder.buildList("line ~ t=FLAME",
						"particle ~ n=HeadCircle @target",
						"lightning ~ @target"));
				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
