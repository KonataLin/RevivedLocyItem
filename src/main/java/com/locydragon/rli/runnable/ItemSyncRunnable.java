package com.locydragon.rli.runnable;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.init.ConfigMaster;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSyncRunnable extends BukkitRunnable {
	@Override
	public void run() {
		if (RevivedLocyItem.instance.getConfig().getInt("SyncDelay") < 0) {
			return;
		}
		for (Player online : Bukkit.getOnlinePlayers()) {
			int size = 0;
			for (int i = 0;i < online.getInventory().getSize();i++) {
				ItemStack locationItem = online.getInventory().getItem(i);
				if (locationItem == null) {
					continue;
				}
				ItemStack locationItemClone = locationItem.clone();
				if (LocyItemAPI.isLocyItem(locationItem)) {
					LocyItem model = LocyItemAPI.asLocyItem(locationItem);
					locationItemClone.setAmount(model.getItem().getAmount());
					locationItemClone.setDurability(model.getItem().getDurability());
					if (!locationItemClone.isSimilar(model.getItem())) {
						int amount = locationItem.getAmount();
						ItemStack clonedItem = model.getItem().clone();
						clonedItem.setAmount(amount);
						online.getInventory().setItem(i, clonedItem);
						size++;
					}
				}
			}
			if (size != 0 && RevivedLocyItem.configMaster.showConfig().getBoolean("InfoSync")) {
				String msg = ConfigLinker.readConfigLang
						("Sync", "{amount}", String.valueOf(size));
				online.sendMessage(msg);
				online.updateInventory();
			}
		}
	}
}
