package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.ItemConfigReadFactory;
import com.locydragon.rli.factory.ParticleConfigReaderFactory;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ListBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ParticleConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Particles//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredParticle();
		genDefault();
		int total = 0;
		L:for (File exists : new File(dir).listFiles()) {
			if (exists.getName().endsWith(".yml")) {
				try {
					YamlConfiguration config = new YamlConfiguration();
					config.load(exists);
				} catch (Throwable e) {
					for (OfflinePlayer p : Bukkit.getOperators()) {
						if (p.isOnline()) {
							p.getPlayer().sendMessage(Colors.color("&cRli加载错误：在文件 &e" + exists.getName()));
						}
					}
					Bukkit.getLogger().info(Colors.color("&cRli加载错误：在文件 &e" + exists.getName()));
					continue L;
				}
				total += ParticleConfigReaderFactory
						.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("Successfully load " + total + " particles in RLI!");
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("成功加载了 " + total + " 个粒子效果于 RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleParticle.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleParticle.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);
				//TODO HEADCIRCLE
				config.set("HeadCircle.origin", "(0,2,0)");
				config.set("HeadCircle.head", true);
				config.set("HeadCircle.rotate", "(0.8,0,1,0)");
				config.set("HeadCircle.amount", 5);
				config.set("HeadCircle.offsetX", 0);
				config.set("HeadCircle.offsetY", 0);
				config.set("HeadCircle.offsetZ", 0);
				config.set("HeadCircle.speed", 0);
				config.set("HeadCircle.range", 10);
				config.set("HeadCircle.circlePrecision", 1);
				config.set("HeadCircle.Effect", ListBuilder.buildList("circle ~ centre=(0,0,0);r=1;type=FLAME"));
				//TODO X
				config.set("X.origin", "(0,1,0)");
				config.set("X.amount", 5);
				config.set("X.offsetX", 0);
				config.set("X.offsetY", 0);
				config.set("X.offsetZ", 0);
				config.set("X.speed", 0);
				config.set("X.range", 10);
				config.set("X.circlePrecision", 5);
				config.set("X.precision", 0.1);
				config.set("X.Effect", ListBuilder.buildList("circle ~ centre=(0,0,0);r=2.82842;type=SMOKE"
				, "delay ~ 50", "line ~ A=(2,0,2);B=(-2,0,-2);type=FLAME", "delay ~ 150", "line ~ A=(-2,0,2);B=(2,0,-2);type=HAPPY_VILLAGER"));
				//TODO FUNCTION
				config.set("Function.origin", "(0,0.5,0)");
				config.set("Function.amount", 5);
				config.set("Function.offsetX", 0);
				config.set("Function.offsetY", 0);
				config.set("Function.offsetZ", 0);
				config.set("Function.speed", 0);
				config.set("Function.range", 10);
				config.set("Function.precision", 0.1);
				config.set("Function.Effect", ListBuilder.buildList("f ~ start=-2;end=2;ex=x*x+0.5;symmetric=true;type=HAPPY_VILLAGER",
						"f ~ start=-2;end=2;ex=(0-1)*(x*x+0.5);symmetric=true;type=HAPPY_VILLAGER", "f ~ start=-2;end=2;ex=x*x+0.5;symmetric=false;type=HAPPY_VILLAGER",
						"f ~ start=-2;end=2;ex=(0-1)*(x*x+0.5);symmetric=false;type=HAPPY_VILLAGER"));
				//TODO STAR
				config.set("Star.origin", "(0,3,0)");
				config.set("Star.head", true);
				config.set("Star.rotate", "(0.35,0,1,0)");
				config.set("Star.amount", 5);
				config.set("Star.offsetX", 0);
				config.set("Star.offsetY", 0);
				config.set("Star.offsetZ", 0);
				config.set("Star.speed", 0);
				config.set("Star.range", 10);
				config.set("Star.circlePrecision", 1);
				config.set("Star.precision", 0.1);
				config.set("Star.Effect", ListBuilder.buildList("circle ~ centre=(0,0,0);r=4;type=REDSTONE;color=#2E8B57", "line ~ A=(0,0,4);B=(-2.74,0,-2.91);type=REDSTONE;color=#F08080",
						"delay ~ 100", "line ~ A=(0,0,4);B=(2.76,0,-2.89);type=REDSTONE;color=#F08080", "delay ~ 100", "line ~ A=(2.76,0,-2.89);B=(-3.69,0,1.54);type=REDSTONE;color=#F08080",
						"delay ~ 100", "line ~ A=(-3.69,0,1.54);B=(3.71,0,1.48);type=REDSTONE;color=#F08080", "delay ~ 100", "line ~ A=(-2.74,0,-2.91);B=(3.71,0,1.48);type=REDSTONE;color=#F08080"));
				//TODO FLOWER
				config.set("Flower.origin", "(0,3,0)");
				config.set("Flower.amount", 5);
				config.set("Flower.offsetX", 0);
				config.set("Flower.offsetY", 0);
				config.set("Flower.offsetZ", 0);
				config.set("Flower.speed", 0);
				config.set("Flower.range", 10);
				config.set("Flower.precision", 0.1);
				config.set("Flower.Effect", ListBuilder.buildList("f ~ start=-3;end=3;ex=(((324-36*x*x)/9)&0.5)+2;symmetric=true;type=HAPPY_VILLAGER",
						"f ~ start=-3;end=3;ex=((0-1)*((324-36*x*x)/9)&0.5)-2;symmetric=true;type=HAPPY_VILLAGER",
						"f ~ start=-3;end=3;ex=((0-1)*((324-36*x*x)/9)&0.5)-2;symmetric=false;type=HAPPY_VILLAGER",
						"f ~ start=-3;end=3;ex=(((324-36*x*x)/9)&0.5)+2;symmetric=false;type=HAPPY_VILLAGER"));
				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
