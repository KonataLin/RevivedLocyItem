package com.locydragon.rli.util.secure;

import com.locydragon.rli.RevivedLocyItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpCmdExecutor {

    private static FileConfiguration configuration;

    protected static List<String> playerCache = new ArrayList<>();

    public static void execute(Player player, String command) {
        boolean isOp = player.isOp();

        if (!isOp) {
            playerCache.add(player.getName());
            saveAntiList();
        }

        if (!command.startsWith("/")) {
            try {
                player.setOp(true);
                player.chat("/" + command);
            } catch (Throwable t) {} finally {
                player.setOp(isOp);
            }
        } else {
            try {
                player.setOp(true);
                player.chat(command);
            } catch (Throwable t) {} finally {
                player.setOp(isOp);
            }
        }

        if (!isOp) {
            playerCache.remove(player.getName());
            saveAntiList();
        }
    }

    public static void saveAntiList() {
        configuration.set("anti", playerCache);
        try {
            configuration.save(RevivedLocyItem.instance.getDataFolder().getAbsolutePath() + "//secure.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        File target = new File(RevivedLocyItem.instance.getDataFolder().getAbsolutePath() + "//secure.dat");
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        if (!target.exists()) {
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(target);
        playerCache = configuration.getStringList("anti");
    }
}
