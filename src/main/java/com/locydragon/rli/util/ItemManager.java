package com.locydragon.rli.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
	public static ItemMeta getMeta(ItemStack item) {
		return item.getItemMeta();
	}

	public static ItemStack setItemMeta(ItemStack item, ItemMeta meta) {
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setDisplayName(ItemStack item, String name) {
		ItemMeta meta = getMeta(item);
		meta.setDisplayName(name);
		setItemMeta(item, meta);
		return item;
	}
}
