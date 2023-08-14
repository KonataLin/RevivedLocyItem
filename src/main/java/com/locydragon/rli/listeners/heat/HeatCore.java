package com.locydragon.rli.listeners.heat;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HeatCore extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private LocyItem heatItem;
    private Entity who;
    private String type;
    private OptionReader reader;

    public HeatCore(Player player, Entity who, LocyItem item, String type, OptionReader value) {
        this.heatItem = item;
        this.player = player;
        this.who = who;
        this.type = type;
        this.reader = value;
    }

    public LocyItem getItem () {
        return this.heatItem;
    }

    public Entity getOnEntity() {
        return this.who;
    }

    public String getSkillType() {
        return this.type;
    }

    public OptionReader getOption() {
        return this.reader;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
