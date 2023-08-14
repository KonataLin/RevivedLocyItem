package com.locydragon.rli.factory;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.nms.ItemNBTSetGet;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OptionReaders implements Listener {
	public static void read(List<String> params, LocyItem item) {
		for (String object : params) {
			LangReader reader = new LangReader(object);
			if (!reader.illegal()) {
				Bukkit.getLogger().info("Something wrong with item: " +item.getID() + "'s Option!" + object);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_ENCHANTS") && reader.value().equalsIgnoreCase("true")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				item.getItem().setItemMeta(meta);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_ENCHANTS") && reader.value().equalsIgnoreCase("false")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
				item.getItem().setItemMeta(meta);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_UNBREAKABLE") && reader.value().equalsIgnoreCase("true")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				item.getItem().setItemMeta(meta);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_UNBREAKABLE") && reader.value().equalsIgnoreCase("false")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				item.getItem().setItemMeta(meta);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_ATTRIBUTES") && reader.value().equalsIgnoreCase("true")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				item.getItem().setItemMeta(meta);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_ATTRIBUTES") && reader.value().equalsIgnoreCase("false")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				item.getItem().setItemMeta(meta);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_POTION_EFFECTS") && reader.value().equalsIgnoreCase("true")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				item.getItem().setItemMeta(meta);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_POTION_EFFECTS") && reader.value().equalsIgnoreCase("false")) {
				ItemMeta meta = item.getItem().getItemMeta();
				meta.removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				item.getItem().setItemMeta(meta);
			}
			if (reader.headValue().equalsIgnoreCase("DAMAGE")) {
				String value = reader.value();
				OptionDamage.IDDamageMap.put(item.getID(), value);
			}
			if (reader.headValue().equalsIgnoreCase("UNBREAKABLE")) {
				if (!reader.value().equalsIgnoreCase("false")) {
					item.setBuildItem(ItemNBTSetGet.unbreakable(item.getItem()));
				}
			}

			if (ParticleEffect.ParticlePacket.getVersion() < 17) {
				if (reader.headValue().equalsIgnoreCase("ATTACK_SPEED")) {
					item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
							"generic.attackSpeed", Double.valueOf(reader.value())));
				}
				if (reader.headValue().equalsIgnoreCase("MAX_HEALTH")) {
					item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
							"generic.maxHealth", Double.valueOf(reader.value())));
				}
				if (reader.headValue().equalsIgnoreCase("MOVE_SPEED")) {
					item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
							"generic.movementSpeed", Double.valueOf(reader.value())));
				}
				if (reader.headValue().equalsIgnoreCase("ARMOR_VALUE")) {
					item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
							"generic.armor", Double.valueOf(reader.value())));
				}
				if (reader.headValue().equalsIgnoreCase("LUCK_VALUE")) {
					item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
							"generic.luck", Double.valueOf(reader.value())));
				}
			} else {
				if (reader.headValue().equalsIgnoreCase("ATTACK_SPEED")
				|| reader.headValue().equalsIgnoreCase("MAX_HEALTH")
						|| reader.headValue().equalsIgnoreCase("MOVE_SPEED")
						|| reader.headValue().equalsIgnoreCase("ARMOR_VALUE")
						|| reader.headValue().equalsIgnoreCase("LUCK_VALUE")) {
					OptionReadersOver17.buildOption(item, reader.headValue()
							, Double.valueOf(reader.value()));
				}
			}
		}
	}
}
