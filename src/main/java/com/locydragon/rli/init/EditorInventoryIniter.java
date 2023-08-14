package com.locydragon.rli.init;

import com.locydragon.rli.editor.EditorMenu;
import com.locydragon.rli.editor.MenuTag;
import com.locydragon.rli.editor.listeners.MainMenuListener;
import com.locydragon.rli.util.ConfigLinker;
import com.locydragon.rli.util.InventoryManager;
import com.locydragon.rli.util.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EditorInventoryIniter {

	public static void initMainInventory() {
		/**
		Inventory mainInventory = Bukkit.createInventory(null, 54,
				ConfigLinker.readConfigLang("Editor", null, null));
		ItemStack itemSpliter = new ItemStack(Material.STAINED_GLASS_PANE);
		itemSpliter.setDurability((short)0);
		ItemManager.setDisplayName(itemSpliter, new String());
		InventoryManager.fillWith(mainInventory, itemSpliter);
		MainMenuListener.MAIN_MENU = new EditorMenu(mainInventory, MenuTag.MAIN, "MAIN_INVENTORY");
		 **/
	}
}
