package com.locydragon.rli.editor.listeners;

import com.locydragon.rli.editor.EditorMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;
import java.util.Objects;

public class MainMenuListener implements Listener {
	public static EditorMenu MAIN_MENU = null;
	public static HashMap<HumanEntity,EditorMenu> subMenuMap = new HashMap<>();
    /**
	@EventHandler
	public void onMainMenuClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		if (e.getInventory().equals(MAIN_MENU.targetInventory())) {
			e.setCancelled(true);
			if (!e.getWhoClicked().isOp()) {
				return;
			}
			if (MAIN_MENU.whenClick(e.getSlot()) != null) {
				EditorMenu menuTarget = MAIN_MENU.whenClick(e.getSlot());
				subMenuMap.put(e.getWhoClicked(), menuTarget);
				e.getWhoClicked().closeInventory();
				menuTarget.open((Player) menuTarget);
			}
		}
	}

	@EventHandler
	public void onSubMenuClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		if (!e.getWhoClicked().isOp()) {
			return;
		}
		if (subMenuMap.keySet().contains(e.getWhoClicked())) {
			EditorMenu menu = subMenuMap.get(e.getWhoClicked());
			if (!e.getInventory().equals(menu.targetInventory())) {
				subMenuMap.remove(e.getWhoClicked());
			} else {
				if (menu.whenClick(e.getSlot()) != null) {
					EditorMenu menuTarget = menu.whenClick(e.getSlot());
					subMenuMap.put(e.getWhoClicked(), menuTarget);
					e.getWhoClicked().closeInventory();
					menuTarget.open((Player)e.getWhoClicked());
				}
			}
		}
	}
	**/
}
