package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.nms.NpcUtils;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.particle.ParticleExpression;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ReachExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("reach")) {
			double damage = ExpressionHelper.run(e.getPlayer(),
					e.getOption().getValue("1", "damage", "d", "da", "dg"));
			double range = Double.valueOf(e.getOption().getValue("5", "range", "r"));
			LivingEntity target = (LivingEntity) ParticleExpression.getCursorTarget(e.getPlayer(), range);
			if (target == null) {
				return;
			}
			if (NpcUtils.isNPC(target) || target.getType() == EntityType.ARMOR_STAND) {
				return;
			}
			if (target != null) {
				ItemCauseDamageEvent event = new ItemCauseDamageEvent(e.getPlayer(), e.getEventType()
						, e.getOption(), e.getOnUseItem(), damage, target);
				Bukkit.getPluginManager().callEvent(event);


				EntityDamageByEntityEvent newEvent = new EntityDamageByEntityEvent(e.getPlayer(), target
						, EntityDamageEvent.DamageCause.ENTITY_ATTACK, event.getDamage());
				OptionDamage.moTVector.add(newEvent.hashCode());
				Bukkit.getPluginManager().callEvent(newEvent);
				if (!newEvent.isCancelled()) {
					target.setMetadata("rli-damage"
							, new FixedMetadataValue(RevivedLocyItem.instance, true));
					target.damage(newEvent.getDamage());
					target.removeMetadata("rli-damage", RevivedLocyItem.instance);
					target.setLastDamageCause(newEvent);
				}
			}
		}
	}
}
