package com.locydragon.rli.listeners.condition;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.listeners.Condition;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.OptionReader;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class ConditionCompare implements Condition {

    @Override
    public boolean check(Player player, String head, OptionReader value, LocyItem onItem) {
        if (head.equalsIgnoreCase("compare")) {
            String param = value.getValue("1 > 0", "value", "v");
            if (param.contains(">")) {
                return ExpressionHelper.run(player, param.split(">", 2)[0])
                        > ExpressionHelper.run(player, param.split(">", 2)[1]);
            } else if (param.contains("<")) {
                return ExpressionHelper.run(player, param.split("<", 2)[0])
                        < ExpressionHelper.run(player, param.split("<", 2)[1]);
            } else if (param.contains("=")) {
                return ExpressionHelper.run(player, param.split("=", 2)[0])
                        == ExpressionHelper.run(player, param.split("=", 2)[1]);
            } else if (param.contains("equals")) {
                String AA = param.split("equals", 2)[0];
                String BB = param.split("equals", 2)[1];
                return (PlaceholderAPI.setPlaceholders(player, AA).trim())
                        .equalsIgnoreCase(PlaceholderAPI.setPlaceholders(player, BB).trim());
            }
            return true;
        } else {
            return true;
        }
    }
}
