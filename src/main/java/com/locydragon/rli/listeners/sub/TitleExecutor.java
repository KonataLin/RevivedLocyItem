package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.nms.Title;
import com.locydragon.rli.util.Colors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TitleExecutor implements Listener {
    @EventHandler
    public void onExecute(SkillExecuteEvent e) {
        if (e.getEventType().equalsIgnoreCase("title")) {
            try {
                e.getPlayer().sendTitle
                        (Colors.color(e.getOption().getValue("&c&l键入主标题", "title", "t")),
                                Colors.color(e.getOption().getValue("&c&l键入副标题", "subtitle", "s")),
                                Integer.valueOf(e.getOption().getValue("5", "in")),
                                Integer.valueOf(e.getOption().getValue("5", "stay")),
                                Integer.valueOf(e.getOption().getValue("5", "out")));
            } catch (Error ex) {
                Title.sendTitle(e.getPlayer(), Integer.valueOf(e.getOption().getValue("5", "in")),
                        Integer.valueOf(e.getOption().getValue("5", "stay")),
                        Integer.valueOf(e.getOption().getValue("5", "out")),
                                Colors.color(e.getOption().getValue("&c&l键入主标题", "title", "t")),
                                Colors.color(e.getOption().getValue("&c&l键入副标题", "subtitle", "s")));
            }
        }
    }
}
