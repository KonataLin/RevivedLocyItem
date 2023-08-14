package com.locydragon.rli.factory;

import com.locydragon.rli.api.LocyItem;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class OptionReadersOver17 {
    public static void buildOption(LocyItem item, String string, double value) {
        if (string.equalsIgnoreCase("ATTACK_SPEED")) {
            ItemStack i = item.getItem();
            ItemMeta meta = i.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                    new AttributeModifier(UUID.randomUUID().toString(), value, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
            i.setItemMeta(meta);
            item.setBuildItem(i);
        }
        if (string.equalsIgnoreCase("MAX_HEALTH")) {
            ItemStack i = item.getItem();
            ItemMeta meta = i.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,
                    new AttributeModifier(UUID.randomUUID().toString()
                            , value, AttributeModifier.Operation.ADD_NUMBER));
            i.setItemMeta(meta);
            item.setBuildItem(i);
        }
        if (string.equalsIgnoreCase("MOVE_SPEED")) {
            ItemStack i = item.getItem();
            ItemMeta meta = i.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
                    new AttributeModifier(UUID.randomUUID().toString()
                            , value, AttributeModifier.Operation.ADD_NUMBER));
            i.setItemMeta(meta);
            item.setBuildItem(i);
        }
        if (string.equalsIgnoreCase("ARMOR_VALUE")) {
            ItemStack i = item.getItem();
            ItemMeta meta = i.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                    new AttributeModifier(UUID.randomUUID().toString()
                            , value, AttributeModifier.Operation.ADD_NUMBER));
            i.setItemMeta(meta);
            item.setBuildItem(i);
        }
        if (string.equalsIgnoreCase("LUCK_VALUE")) {
            ItemStack i = item.getItem();
            ItemMeta meta = i.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_LUCK,
                    new AttributeModifier(UUID.randomUUID().toString()
                            , value, AttributeModifier.Operation.ADD_NUMBER));
            i.setItemMeta(meta);
            item.setBuildItem(i);
        }
    }
}
