package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.util.Colors;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HelpCmd implements SubCmd {
    @Override
    public void runSubCommand(Player commandSender, String[] arguments) {
        if (!commandSender.hasPermission("RevivedLocyItem.admin")) {
            return;
        }
        commandSender.sendMessage(Colors.color(""));
        commandSender.sendMessage(Colors.color(""));
        commandSender.sendMessage(Colors.color(""));
        commandSender.sendMessage(Colors.color("&6你可以输入以下指令:"));
        commandSender.sendMessage(Colors.color("&b/rli item [物品ID名称] &7——来获得一个RPG物品吧!"));
        commandSender.sendMessage(Colors.color("&b/rli list &7——获取所有可用的物品吧!"));
        commandSender.sendMessage(Colors.color("&b/rli particle [粒子效果名称] &7——在你的脚下演示一个粒子效果吧!"));
        commandSender.sendMessage(Colors.color("&b/rli skill [技能组名称] &7——播放一个技能组吧!"));
        commandSender.sendMessage(Colors.color("&b/rli version &7——查看当前插件版本吧!"));
        commandSender.sendMessage(Colors.color("&b/rli reload &7——重载插件吧!"));
        commandSender.sendMessage(Colors.color("&b/rli as [物品ID名称] &7——&c将手上的物品赋予LocyItem的特征"));
        commandSender.sendMessage(Colors.color("&8&lTip: &b查看Rli官方Wiki获取更多…… &3&l↓↓↓"));
        commandSender.sendMessage(Colors.color(""));
        TextComponent message = new TextComponent(Colors.color("&f[&6&l点我跳转&f]"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://locydragon.github.io/wiki/#/"));
        message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("访问官方Wiki").create()));
        commandSender.spigot().sendMessage(message);
    }

    @Override
    public String tellMeCmdPrefix() {
        return "help";
    }
}
