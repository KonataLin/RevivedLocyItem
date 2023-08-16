package com.locydragon.rli.editor.optionreader;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class OptionDamage implements Listener {
	public static ConcurrentHashMap<String,String> IDDamageMap = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<UUID,Double> damageMap = new ConcurrentHashMap<>();
	public static Vector<Integer> moTVector = new Vector<>();

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (moTVector.contains(e.hashCode())) {
			return;
		}
		if (e.getDamager() instanceof Player) {
			Player player = (Player)e.getDamager();
			ItemStack hand = player.getItemInHand();
			if (LocyItemAPI.isLocyItem(hand)) {
				LocyItem item = LocyItemAPI.asLocyItem(hand);
				if (IDDamageMap.containsKey(item.getID())) {
					e.setDamage(ExpressionHelper.run(player, IDDamageMap.get(item.getID())));
				}
			}
		}
	}

	@EventHandler
	public void onLaunchX(ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player) {
			ItemStack item = ((Player) e.getEntity().getShooter()).getItemInHand();
			if (e.getEntity() instanceof Arrow && item.getType() == Material.BOW) {
				if (LocyItemAPI.isLocyItem(item)) {
					LocyItem model = LocyItemAPI.asLocyItem(item);
					if (IDDamageMap.containsKey(model.getID())) {
						damageMap.put(e.getEntity().getUniqueId(),
								ExpressionHelper.run((Player)e.getEntity().getShooter(), IDDamageMap.get(model.getID())));
					}
				}
			}
		}
	}

	@EventHandler
	public void onDamageByArrow(EntityDamageByEntityEvent e) {
		if (damageMap.containsKey(e.getEntity().getUniqueId())) {
			e.setDamage(damageMap.get(e.getEntity().getUniqueId()));
			damageMap.remove(e.getEntity().getUniqueId());
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (damageMap.containsKey(e.getEntity().getUniqueId())) {
			damageMap.remove(e.getEntity().getUniqueId());
		}
	}
}
