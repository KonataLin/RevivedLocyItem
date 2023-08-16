package com.example.plugin;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExampleSkill implements Listener {
    @EventHandler
    public void onSkilExecute(SkillExecuteEvent e) {
        if (e.getEventType().equalsIgnoreCase("YourSkillName")) {
            String value1 = e.getOption().getValue(null, "value1");
            String value2 = e.getOption().getValue(null, "value2");
            //TODO Your Skill
        }
    }
}
