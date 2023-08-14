package com.locydragon.rli.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {
	public static Inventory fillWith(Inventory inventory, ItemStack item) {
		for (int i = 0;i < inventory.getSize();i++) {
			inventory.setItem(i, item);
		}
		return inventory;
	}
}
