package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SoundExecutor implements Listener {
    @EventHandler
    public void onExecute(SkillExecuteEvent e) {
        if (e.getEventType().equalsIgnoreCase("sound")) {
            String type = e.getOption().getValue("AMBIENT_CAVE", "type", "t");
            String level = e.getOption().getValue("1", "volume", "v");
            String pitch = e.getOption().getValue("1", "pitch", "p");
            try {
                Sound sound = Sound.valueOf(type);
                float levelF = (float)(double)Double.valueOf(level);
                float pitchF = (float)(double)Double.valueOf(pitch);
                e.getPlayer().playSound(e.getPlayer().getLocation(), sound, levelF, pitchF);
            } catch (Exception ex) {
                e.getPlayer().sendMessage("找不到声效：" + type + " 请检验ID是否正确");
            }
        }
    }
}
