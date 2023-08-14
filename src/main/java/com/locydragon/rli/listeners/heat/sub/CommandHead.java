package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.util.secure.OpCmdExecutor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CommandHead implements Listener  {
    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("command")) {
            String cmdType = e.getOption().getValue("NONE", "type", "t", "ty");
            String command = e.getOption().getValue("NONE", "command", "cmd", "c");
            command = PlaceholderAPI.setPlaceholders(e.getPlayer(), command);
            if (cmdType.equalsIgnoreCase("NONE")) {
                if (!command.startsWith("/")) {
                    e.getPlayer().chat("/" + command);
                } else {
                    e.getPlayer().chat(command);
                }
                return;
            }
            if (cmdType.equalsIgnoreCase("player")) {
                if (!command.startsWith("/")) {
                    e.getPlayer().chat("/" + command);
                } else {
                    e.getPlayer().chat(command);
                }
                return;
            }
            if (cmdType.equalsIgnoreCase("op")) {
                OpCmdExecutor.execute(e.getPlayer(), command);
                return;
            }
            if (cmdType.equalsIgnoreCase("console")) {
                if (command.startsWith("/")) {
                    command = command.replace("/", "");
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }
}
