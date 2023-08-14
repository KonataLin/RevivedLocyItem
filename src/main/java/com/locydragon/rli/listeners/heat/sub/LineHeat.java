package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.util.ArithHelper;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.ParticleEffect;
import com.locydragon.rli.util.particle.ParticleEffectForAbove13;
import com.locydragon.rli.util.particle.ParticleExpression;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LineHeat implements Listener {
    private static Executor executor = Executors.newCachedThreadPool();

    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("line")) {
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(e.getOnEntity().getWorld().getName())) {
                return;
            }
            String type = e.getOption().getValue("HAPPY_VILLAGER", "type", "t").toUpperCase();
            String color = e.getOption().getValue(null, "color", "c");
            int R = -1;
            int G = -1;
            int B = -1;
            if (color != null) {
                int[] colorRGB = ArithHelper.hex2RGB(color);
                R = colorRGB[0];
                G = colorRGB[1];
                B = colorRGB[2];
            }
            int finalR = R;
            int finalG = G;
            int finalB = B;
            executor.execute(() -> {
                List<Location> lineLocList = new ArrayList<>();
                for (LocationModel model : ParticleExpression.buildLine(e.getOnEntity().getLocation()
                        , e.getPlayer().getLocation(), 0.2)) {
                    Location where = new Location(e.getPlayer().getWorld(), model.x, model.y + 1, model.z);

                    if (!RevivedLocyItem.enableOverParticleLib) {
                        ParticleEffect.valueOf(type).display
                                (0, 0, 0, 0, 5, where, 10);
                    } else {
                        ParticleEffectForAbove13.display(ParticleEffect.valueOf(type),
                                0, 0, 0, 0, 5,
                                where, 10, type, finalR, finalG, finalB);
                    }
                }
            });
        }
    }
}
