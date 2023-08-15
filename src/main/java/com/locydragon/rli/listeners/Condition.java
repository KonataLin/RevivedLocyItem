package com.locydragon.rli.listeners;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;


public interface Condition {
    public boolean check(Player player, String head, OptionReader value, LocyItem onItem);
}
