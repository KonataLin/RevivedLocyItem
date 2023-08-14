package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillUnit;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class ItemListCmd implements SubCmd {
    @Override
    public void runSubCommand(Player commandSender, String[] arguments) {
        if (!commandSender.hasPermission("RevivedLocyItem.admin")) {
            return;
        }
        commandSender.sendMessage(Colors.color("&6当前可使用的Rli技能物品:"));
        for (String skillItem : LocyItemAPI.availableItems()) {
            commandSender.sendMessage(Colors.color("  &a● &b" + skillItem));
        }
        commandSender.sendMessage(Colors.color("&b使用 &5/rli item [物品名称] &b—— 来获得这个物品"));
        commandSender.sendMessage(Colors.color("&c&l若未检测到您的物品，请确保您已重载并确认配置文件书写格式正确."));
    }

    @Override
    public String tellMeCmdPrefix() {
        return "list";
    }
}
