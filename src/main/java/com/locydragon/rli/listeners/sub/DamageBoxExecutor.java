package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.nms.NpcUtils;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.VectorCalculator2D3D;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.RotationModel;
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

import javax.swing.text.html.Option;

public class DamageBoxExecutor implements Listener {
    @EventHandler
    public void onExecute(SkillExecuteEvent e) {
        if (e.getEventType().equalsIgnoreCase("box")) {
            double damage = ExpressionHelper.run(e.getPlayer(),
                    e.getOption().getValue("1", "damage", "d", "da", "dg"));
            boolean head = Boolean.valueOf
                    (e.getOption().getValue("false", "head", "h"));
            LocationModel locationModelTarget
                    = new LocationModel(e.getOption().getValue("(0,0,0)", "box", "b"));
            LocationModel locationModelSize
                    = new LocationModel(e.getOption().getValue("(1,1,1)", "size", "s"));
            RotationModel locationModelRotate
                    = new RotationModel(e.getOption().getValue("(0,0,0,0)", "rotate", "r"));

            org.bukkit.util.Vector[] vectorsBasis = VectorCalculator2D3D
                    .calculateBasisVector(e.getPlayer().getLocation()
                            .clone(), e.getPlayer().getEyeLocation().getDirection()
                            , head);
            org.bukkit.Location clone = e.getPlayer().getLocation().clone();
            org.bukkit.util.Vector addtionX = vectorsBasis[0].clone().multiply(locationModelTarget.x);
            org.bukkit.util.Vector addtionY = vectorsBasis[1].clone().multiply(locationModelTarget.z);
            org.bukkit.util.Vector addtionZ = vectorsBasis[2].clone().multiply(locationModelTarget.y);
            org.bukkit.util.Vector addtion = addtionX.add(addtionY).add(addtionZ);
            org.bukkit.util.Vector nowALocation = null;
            if (locationModelRotate.zita != 0) {
                double[] rotatedLocation = VectorCalculator2D3D.rotation3D(new double[] {addtion.getX(), addtion.getZ()
                                , addtion.getY()},
                        locationModelRotate.zita, locationModelRotate.x, locationModelRotate.y, locationModelRotate.z);
                nowALocation
                        = new org.bukkit.util.Vector(clone.getX(), clone.getY(), clone.getZ())
                        .add((new org.bukkit.util.Vector(rotatedLocation[0], rotatedLocation[2], rotatedLocation[1])));
            } else {
                nowALocation
                        = new org.bukkit.util.Vector(clone.getX(), clone.getY(), clone.getZ()).add((addtion));
            }
            clone = nowALocation.toLocation(clone.getWorld());

            for (Entity nearBy : clone.getWorld().getNearbyEntities
                    (clone, locationModelSize.x, locationModelSize.y, locationModelSize.z)) {
                if (!(nearBy instanceof LivingEntity)) {
                    continue;
                }
                LivingEntity entity = (LivingEntity)nearBy;
                if (NpcUtils.isNPC(entity) || entity.getType() == EntityType.ARMOR_STAND || entity.getType().equals(EntityType.ARMOR_STAND)) {
                    return;
                }
                ItemCauseDamageEvent event = new ItemCauseDamageEvent(e.getPlayer(), e.getEventType()
                        , e.getOption(), e.getOnUseItem(), damage, entity);
                Bukkit.getPluginManager().callEvent(event);
                if (damage != 0) {
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
