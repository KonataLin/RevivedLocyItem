package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.nms.ItemNBTSetGet;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AsItemCmd implements SubCmd {
    boolean canvas = false;

    @Override
    public void runSubCommand(Player commandSender, String[] arguments) {
        if (arguments.length == 2) {
            String itemName = arguments[1];
            LocyItem item = LocyItemAPI.getItem(itemName);
            if (item != null) {
                ItemStack itemHand = commandSender.getItemInHand();
                if (itemHand != null && itemHand.getType() != Material.AIR) {
                    if (LocyItemAPI.isLocyItem(itemHand)) {
                        commandSender.sendMessage(Colors.color("&b你手上的物品本来就是一个LocyItem."));
                        return;
                    } else {
                        if (!canvas && RevivedLocyItem.instance.getConfig().getInt("SyncDelay") > 0) {
                            commandSender.sendMessage(Colors.color("&c检测到您开启了物品同步……"));
                            commandSender.sendMessage(Colors.color("&7>>> &5&l使用本指令需要关闭物品同步，请问您真的需要这么做吗?"));
                            commandSender.sendMessage(Colors.color("&a再次输入指令确认关闭物品同步并设置!"));
                            canvas = true;
                            Bukkit.getScheduler().runTaskLater(RevivedLocyItem.instance, () -> {
                                canvas = false;
                            }, 200L);
                            return;
                        }
                        if (RevivedLocyItem.instance.getConfig().getInt("SyncDelay") > 0) {
                            RevivedLocyItem.instance.getConfig().set("SyncDelay", -1);
                            RevivedLocyItem.instance.saveConfig();
                        }
                        commandSender.
                                setItemInHand(ItemNBTSetGet.addPluginTag(itemHand, arguments[1]));
                        commandSender.sendMessage(Colors.color("&a设置成功."));
                        return;
                    }
                } else {
                    commandSender.sendMessage(Colors.color("&b你的手上什么都没有啊，起码拿个东西吧……"));
                }
            } else {
                String msg = ConfigLinker.readConfigLang
                        ("NotFound", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
                commandSender.sendMessage(msg);
            }
        } else {
            commandSender.sendMessage(Colors.color("&b/rli as [物品ID名称] &7——&c将手上的物品赋予LocyItem的特征"));
        }
    }

    @Override
    public String tellMeCmdPrefix() {
        return "as";
    }
}