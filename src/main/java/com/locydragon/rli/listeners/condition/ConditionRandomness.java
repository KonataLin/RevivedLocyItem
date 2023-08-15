package com.locydragon.rli.listeners.condition;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.listeners.Condition;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;

public class ConditionRandomness implements Condition {

    @Override
    public boolean check(Player player, String head, OptionReader value, LocyItem onItem) {
        if (head.equalsIgnoreCase("random")) {
            double valueRandom =
                    ExpressionHelper.run(player, value.getValue("1", "value", "v"));
            return Math.random() < valueRandom;
        } else {
            return true;
        }
    }
}
