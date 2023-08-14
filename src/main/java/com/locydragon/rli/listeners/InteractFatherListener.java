package com.locydragon.rli.listeners;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.util.OptionReader;
import com.locydragon.rli.util.Pack;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.Map;

public class InteractFatherListener implements Listener {
	Executor pool = Executors.newCachedThreadPool();
	public static final String KEY_WORD_RIGHT = "RIGHT";
	public static final String KEY_WORD_LEFT = "LEFT";

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && LocyItemAPI.isLocyItem(e.getItem())) {
			LocyItem item = LocyItemAPI.asLocyItem(e.getItem());
			pool.execute(() -> {
				List<Pack<String,OptionReader>> executedEntry = new ArrayList<>();
				for (Map.Entry<String, List<OptionReader>> entry : item.getSkillsMap().entrySet()) {
					for (OptionReader reader : entry.getValue()) {
						if (reader.getWhen() != null && reader.getWhen().equalsIgnoreCase(KEY_WORD_RIGHT) && (e.getAction() == Action.RIGHT_CLICK_AIR
						|| e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
							Pack<String,OptionReader> pack = new Pack<>();
							pack.setKey(entry.getKey());
							pack.setValue(reader);
							executedEntry.add(pack);
							continue;
						} else if (reader.getWhen() != null && reader.getWhen().equalsIgnoreCase(KEY_WORD_LEFT) && (e.getAction() == Action.LEFT_CLICK_AIR
								|| e.getAction() == Action.LEFT_CLICK_BLOCK)) {
							Pack<String,OptionReader> pack = new Pack<>();
							pack.setKey(entry.getKey());
							pack.setValue(reader);
							executedEntry.add(pack);
							continue;
						}
					}
				}
				Bukkit.getScheduler().runTask(RevivedLocyItem.instance, () -> {
					for (Pack<String,OptionReader> pack : executedEntry) {
						Bukkit.getPluginManager().callEvent(new SkillExecuteEvent(e.getPlayer(), pack.getKey(), pack.getValue(), item));
					}
				});
			});
		}
	}
}
