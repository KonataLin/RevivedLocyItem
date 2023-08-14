package com.locydragon.rli.listeners.heat;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HeatFatherListener implements Listener {
    private static Executor executor = Executors.newCachedThreadPool();

	@EventHandler
	public void onHeat(ItemCauseDamageEvent e) {
	    if (e.getOnUseItem() == null) { return; }
	    executor.execute(() -> {
           for (String line : e.getOnUseItem().getHeat()) {
               LangReader lang = new LangReader(line);
               OptionReader option = new OptionReader(lang.value());
               if (lang.headValue().equalsIgnoreCase("delay")) {
                   try {
                       Thread.sleep(Long.valueOf(option.getInfo()));
                   } catch (InterruptedException ex) {
                       ex.printStackTrace();
                   }
               } else {
                   Bukkit.getScheduler().runTask(RevivedLocyItem.instance, () -> {
                       Bukkit.getPluginManager()
                               .callEvent(new HeatCore(e.getPlayer(), e.getWho(), e.getOnUseItem(), lang.headValue(), option));
                   });
               }
           }
        });
	}
}
