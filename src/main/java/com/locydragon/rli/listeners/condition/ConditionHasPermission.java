package com.locydragon.rli.listeners.condition;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.listeners.Condition;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;

public class ConditionHasPermission implements Condition {

    @Override
    public boolean check(Player player, String head, OptionReader value, LocyItem onItem) {
        if (head.equalsIgnoreCase("permission")
                || head.equalsIgnoreCase("perm")) {
            return player.hasPermission(value.getValue("null", "value", "v"));
        } else {
            return true;
        }
    }
}
