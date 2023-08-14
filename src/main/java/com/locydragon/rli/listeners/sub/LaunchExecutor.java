package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.OptionReader;
import com.locydragon.rli.util.Pack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class LaunchExecutor implements Listener {
	public static final String NONE = "NONE";
	public static List<String> values = new ArrayList<>();
	public static HashMap<String,Class<? extends Projectile>> classMap = new HashMap<>();
	public static HashMap<UUID,Double> damageMap = new HashMap<>();
	public static HashMap<UUID,Pack<Player,LocyItem>> infoMap = new HashMap<>();
	public static HashMap<UUID,OptionReader> readerMap = new HashMap<>();

	static {
		String values = "AbstractArrow, Arrow, DragonFireball, Egg, EnderPearl, Fireball, FishHook, LargeFireball, LingeringPotion, LlamaSpit, ShulkerBullet, " +
				"SmallFireball, Snowball, SpectralArrow, SplashPotion, ThrownExpBottle, ThrownPotion, TippedArrow, Trident, WitherSkull";
		for (String each : values.split(",")) {
			LaunchExecutor.values.add(each.trim());
			try {
				classMap.put(each.trim(), (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + each.trim()));
			} catch (ClassNotFoundException e) {
				continue;
			}
		}
	}

	@EventHandler
	public void onRemove(EntityDeathEvent e) {
		if (damageMap.containsKey(e.getEntity().getUniqueId())) {
			damageMap.remove(e.getEntity().getUniqueId());
			infoMap.remove(e.getEntity().getUniqueId());
			readerMap.remove(e.getEntity().getUniqueId());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (damageMap.containsKey(e.getDamager().getUniqueId())) {
			e.setDamage(damageMap.get(e.getDamager().getUniqueId()));
			ItemCauseDamageEvent event = new ItemCauseDamageEvent(infoMap.get(e.getDamager().getUniqueId()).getKey(),
					"launch", readerMap.get(e.getDamager().getUniqueId()), infoMap.get(e.getDamager().getUniqueId()).getValue(),
					damageMap.get(e.getDamager().getUniqueId()), e.getEntity());
			Bukkit.getPluginManager().callEvent(event);
			e.setDamage(event.getDamage());
			damageMap.remove(e.getDamager().getUniqueId());
			infoMap.remove(e.getDamager().getUniqueId());
			readerMap.remove(e.getDamager().getUniqueId());
		}
	}

	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("launch")) {
			String damage = e.getOption().getValue(NONE, "damage", "d", "da", "dg");
			String type = e.getOption().getValue(NONE, "type", "t", "ty");
			if (type.equals(NONE)) {
				type = values.get(new Random().nextInt(values.size()) - 1);
			} else {
				for (String object : values) {
					if (object.equalsIgnoreCase(type)) {
						type = object;
					}
				}
			}
			if (!values.contains(type)) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type not found!——" + type);
				return;
			}
			if (classMap.getOrDefault(type, null) == null) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type version error.——" + type);
				return;
			}
			Projectile projectileLaunched = e.getPlayer().launchProjectile(classMap.get(type),
					e.getPlayer().getEyeLocation().getDirection());
			UUID entityUUID = projectileLaunched.getUniqueId();
			try {
				if (isNum(damage)) {
					damageMap.put(entityUUID, Double.valueOf(damage));
					Pack<Player,LocyItem> info = new Pack<>();
					info.setKey(e.getPlayer());
					info.setValue(e.getOnUseItem());
					infoMap.put(entityUUID, info);
					readerMap.put(entityUUID, e.getOption());
				} else {
					damageMap.put(entityUUID, ExpressionHelper.run(e.getPlayer(), damage));
					Pack<Player,LocyItem> info = new Pack<>();
					info.setKey(e.getPlayer());
					info.setValue(e.getOnUseItem());
					infoMap.put(entityUUID, info);
					readerMap.put(entityUUID, e.getOption());
				}
			} catch (Exception exc) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Expression error.——" + damage);
				return;
			}
		}
	}

	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
