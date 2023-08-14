package com.locydragon.rli.util.particle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedParticle;
import com.locydragon.rli.RevivedLocyItem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleEffectForAbove13 {
    public static void display(ParticleEffect effect
            , float offsetX, float offsetY, float offsetZ,
                               float speed, int amount, Location center, double range, String effectName, int R, int G, int B) {
        PacketContainer container =
                RevivedLocyItem.manager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
        if (ParticleEffect.ParticlePacket.getVersion() < 15) {
            container.getFloat().write(0, (float) center.getX());
            container.getFloat().write(1, (float) center.getY());
            container.getFloat().write(2, (float) center.getZ());
            container.getFloat().write(3, offsetX);
            container.getFloat().write(4, offsetY);
            container.getFloat().write(5, offsetZ);
            container.getFloat().write(6, speed);
            container.getIntegers().write(0, amount);
            container.getBooleans().write(0, true);
            if (R >= 0 && G >= 0 && B >= 0) {
                container.getNewParticles().
                        write(0, WrappedParticle.create
                                (Particle.valueOf(stripe12ToAbove13(effectName.toUpperCase()))
                                        , new Particle.DustOptions
                                                (Color.fromRGB(R, G, B), 1f)));
            } else {
                container.getNewParticles().
                        write(0, WrappedParticle.create
                                (Particle.valueOf(stripe12ToAbove13(effectName.toUpperCase()))
                                        , null));
            }
            String worldName = center.getWorld().getName();
            double squared = 256 * 256;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if ((player.getWorld().getName().equals(worldName)) &&
                        (player.getLocation().distanceSquared(center) <= squared)) {
                    RevivedLocyItem.manager.sendServerPacket(player, container);
                }
            }
        } else {
            container.getDoubles().write(0, center.getX());
            container.getDoubles().write(1, center.getY());
            container.getDoubles().write(2, center.getZ());
            container.getFloat().write(0, offsetX);
            container.getFloat().write(1, offsetY);
            container.getFloat().write(2, offsetZ);
            container.getFloat().write(3, speed);
            container.getIntegers().write(0, amount);
            container.getBooleans().write(0, true);
            if (R >= 0 && G >= 0 && B >= 0) {
                container.getNewParticles().
                        write(0, WrappedParticle.create
                                (Particle.valueOf(stripe12ToAbove13(effectName.toUpperCase()))
                                        , new Particle.DustOptions
                                                (Color.fromRGB(R, G, B), 1f)));
            } else {
                container.getNewParticles().
                        write(0, WrappedParticle.create
                                (Particle.valueOf(stripe12ToAbove13(effectName.toUpperCase()))
                                        , null));
            }
            String worldName = center.getWorld().getName();

            double squared = 256 * 256;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if ((player.getWorld().getName().equals(worldName)) &&
                        (player.getLocation().distanceSquared(center) <= squared)) {
                    RevivedLocyItem.manager.sendServerPacket(player, container);
                }
            }
        }
    }

    public static String stripe12ToAbove13(String input) {
        if (input.equalsIgnoreCase("BUBBLE")) {
            return "BUBBLE_POP";
        } else if (input.equalsIgnoreCase("SPLASH")) {
            return "WATER_SPLASH";
        } else if (input.equalsIgnoreCase("WAKE")) {
            return "WATER_WAKE";
        } else if (input.equalsIgnoreCase("MAGIC_CRIT")) {
            return "CRIT_MAGIC";
        } else if (input.equalsIgnoreCase("SMOKE")) {
            return "SMOKE_NORMAL";
        } else if (input.equalsIgnoreCase("WITCH_MAGIC")) {
            return "SPELL_WITCH";
        } else if (input.equalsIgnoreCase("ANGRY_VILLAGER")) {
            return "VILLAGER_ANGRY";
        } else if (input.equalsIgnoreCase("HAPPY_VILLAGER")) {
            return "VILLAGER_HAPPY";
        } else if (input.equalsIgnoreCase("FOOTSTEP")) {
            return "VILLAGER_HAPPY";
        } else if (input.equalsIgnoreCase("SWEEP")) {
            return "SWEEP_ATTACK";
        } else if (input.equalsIgnoreCase("BARRIER")) {
            return "VILLAGER_HAPPY";
        }
        return input;
    }
}
