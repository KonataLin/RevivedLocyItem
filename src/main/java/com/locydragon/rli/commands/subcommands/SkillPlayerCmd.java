package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.SkillUnit;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class SkillPlayerCmd implements SubCmd {
    @Override
    public void runSubCommand(Player commandSender, String[] arguments) {
        if (!commandSender.hasPermission("RevivedLocyItem.admin")) {
            return;
        }
        if (arguments.length == 2) {
            if (LocyItemAPI.hasSkillUnit(arguments[1])) {
                SkillUnit unit = LocyItemAPI.getSkillUnit(arguments[1]);
                try {
                    unit.run(commandSender, null);
                } catch (Exception e) {
                    commandSender.sendMessage(Colors.color("&c当前技能组不能被指令执行.&f错误代码: (" + e.getMessage() + ")"));
                }
            } else {
                String msg = ConfigLinker.readConfigLang
                        ("NotFound", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
                commandSender.sendMessage(msg);
            }
        } else {
            String msg = ConfigLinker.readConfigLang
                    ("Skill", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
            commandSender.sendMessage(msg);
        }
    }

    @Override
    public String tellMeCmdPrefix() {
        return "skill";
    }
}
