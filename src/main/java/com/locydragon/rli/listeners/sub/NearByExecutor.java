package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.nms.NpcUtils;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;
import org.bukkit.plugin.Plugin;

public class NearByExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("near")) {
			String x = e.getOption().getValue("0", "x");
			String y = e.getOption().getValue("0", "y");
			String z = e.getOption().getValue("0", "z");
			String damage = e.getOption().getValue("0", "damage", "d", "da", "dg");
			double damageDouble = ExpressionHelper.run(e.getPlayer(), damage);
			for (Entity nearBy : e.getPlayer().getNearbyEntities(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z))) {
				if (nearBy instanceof LivingEntity && !NpcUtils.isNPC(nearBy)) {
					LivingEntity entity = (LivingEntity)nearBy;
					if (NpcUtils.isNPC(entity) || entity.getType() == EntityType.ARMOR_STAND || entity.getType().equals(EntityType.ARMOR_STAND)) {
						return;
					}
					ItemCauseDamageEvent event = new ItemCauseDamageEvent(e.getPlayer(), e.getEventType()
							, e.getOption(), e.getOnUseItem(), damageDouble, entity);
					Bukkit.getPluginManager().callEvent(event);
					if (damageDouble != 0) {
						EntityDamageByEntityEvent newEvent = new EntityDamageByEntityEvent(e.getPlayer(), entity
								, EntityDamageEvent.DamageCause.ENTITY_ATTACK, event.getDamage());
						//OptionDamage.moTVector.clear();
						OptionDamage.moTVector.add(newEvent.hashCode());
						Bukkit.getPluginManager().callEvent(newEvent);
						if (!newEvent.isCancelled()) {
							entity.setMetadata("rli-damage"
									, new FixedMetadataValue(RevivedLocyItem.instance, true));
							entity.damage(newEvent.getDamage());
							entity.removeMetadata("rli-damage", RevivedLocyItem.instance);
							entity.setLastDamageCause(newEvent);
						}
					}
				}
			}
		}
	}
}
