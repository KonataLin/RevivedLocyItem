package com.locydragon.rli.util.secure;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class DisadvantageOpDefender implements Listener {
    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        if (e.getPlayer().isOp()) {
            for (String line : OpCmdExecutor.playerCache) {
                if (line.equalsIgnoreCase(e.getPlayer().getName())) {
                    e.getPlayer().setOp(false);
                    OpCmdExecutor.playerCache.remove(e.getPlayer().getName());
                    break;
                }
            }
        }
    }
}
