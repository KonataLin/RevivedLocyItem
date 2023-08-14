package com.locydragon.rli.factory;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.old.MaterialOld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemConfigReadFactory {
	public static int readFile(FileConfiguration config) {
		int find = 0;
		for (String key : config.getKeys(false)) {
			LocyItem item = null;
			try {
				item = new LocyItem(key, config.getString(key + ".name")
						, returnMaterial(config.getString(key + ".id")));
			} catch (Exception e) {
				for (OfflinePlayer p : Bukkit.getOperators()) {
					if (p.isOnline()) {
						((Player)p).sendMessage(Colors.color("&cRli加载错误：在文件 &e" + config.getName()));
					}
				}
				continue;
			}
			find++;

			//TODO lore
			List<String> lores = config.getStringList(key + ".lore");
			if (lores != null && !lores.isEmpty()) {
				List<String> colorLore = new ArrayList<>();
				lores.forEach(x -> colorLore.add(Colors.color(x)));
				item.setLore(colorLore);
			}

			//TODO 附魔
			List<String> enchantment = config.getStringList(key + ".Enchantment");
			if (enchantment != null && !enchantment.isEmpty()) {
				for (String info : enchantment) {
					LangReader readerLine = new LangReader(info);
					if (!readerLine.illegal()) {
						RevivedLocyItem.instance.getLogger().info(ConfigLinker.readConfigLang("illegal", "{item}", key)
								.replace("{where}", "Enchantment"));
						continue;
					}
					try {
						Enchantment enchantMent = Enchantment.getByName(readerLine.headValue().toUpperCase());
						if (enchantMent == null) {
							RevivedLocyItem.instance.getLogger().info(ConfigLinker.readConfigLang("illegal", "{item}", key)
									.replace("{where}", "Enchantment"));
							continue;
						}
						if (!isInt(readerLine.value())) {
							RevivedLocyItem.instance.getLogger().info(ConfigLinker.readConfigLang("illegal", "{item}", key)
									.replace("{where}", "Enchantment"));
							continue;
						}
						item.enchantment(enchantMent, Integer.valueOf(readerLine.value()));
					} catch (Exception exc) {
						RevivedLocyItem.instance.getLogger().info(ConfigLinker.readConfigLang("illegal", "{item}", key)
								.replace("{where}", "Enchantment"));
						continue;
					}
				}
			}

			//TODO 技能
			List<String> skills = config.getStringList(key + ".Skills");
			if (skills != null && !skills.isEmpty()) {
				for (String obj : skills) {
					item.addSkill(obj);
				}
			}

			//TODO 属性
			OptionReaders.read(config.getStringList(key + ".Options"), item);

			for (String object : config.getStringList(key + ".Heat")) {
				item.putHeatAfter(object);
			}
			LocyItemAPI.registerItem(item);
		}
		return find;
	}

	public static Material returnMaterial(String info) {
		if (isInt(info)) {
			return Material.getMaterial(MaterialOld.getMaterial(Integer.valueOf(info)).toString());
		}
		return Material.getMaterial(info.toUpperCase());
	}

	public static boolean isInt(String i) {
		try {
			Integer.valueOf(i);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
