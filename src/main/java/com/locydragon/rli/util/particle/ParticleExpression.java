package com.locydragon.rli.util.particle;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.util.Calculator;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class ParticleExpression {
	public static List<LocationModel> buildLine(Location locA, Location locB, double precision) {
		List<LocationModel> save = new ArrayList<>();
		org.bukkit.util.Vector vectorAB = locB.clone().subtract(locA).toVector();
		double vectorLength = vectorAB.length();
		vectorAB.normalize();
		for (double i = 0; i < vectorLength; i +=precision) {
			org.bukkit.util.Vector vector = vectorAB.clone().multiply(i);
			locA.add(vector);
			save.add(new LocationModel(locA));
			locA.subtract(vector);
		}
		return save;
	}

	public static Set<LocationModel> sendParticleCircle(Location loc, double radius, double precisionExtra) {
		Set<LocationModel> result = new HashSet<>();
		for (double degree = 0; degree < 360; degree += precisionExtra) {
			double offsetX = Math.sin(degree/180D * Math.PI) * radius;
			double offsetZ = Math.cos(degree/180D * Math.PI) * radius;
			Location result1 = loc.clone();
			result1.add(offsetX, 0D, offsetZ);
			result.add(new LocationModel(result1));
		}
		return result;
	}

	public static List<LocationModel> asFunction(String expression, double start, double end, double precision, LocationModel centre, boolean symmetric,
												 boolean x_Solid, boolean z_Solid) {
		int errorTime = 0;
		List<LocationModel> save = new ArrayList<>();
		for (BigDecimal begin = new BigDecimal(String.valueOf(start))
			 ; begin.compareTo(new BigDecimal(end)) == -1; begin = begin.add(new BigDecimal(precision))) {
			String value;
			if (begin.compareTo(new BigDecimal(0)) == -1) {
				value = "(0-" + String.valueOf(Math.abs(begin.setScale(10,
						BigDecimal.ROUND_HALF_UP).doubleValue())) + ")";
			} else {
				value = String.valueOf(begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			try {
				double output = new Calculator()
						.calculateWithOutPlayer(expression.replace("x", value));
				if (symmetric) {
					save.add(new LocationModel(output, centre.y, begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()));
				} else if (x_Solid) {
					save.add(new LocationModel(centre.x, output, begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()));
				} else if (z_Solid) {
					save.add(new LocationModel(begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue(), output, centre.x));
				} else {
					save.add(new LocationModel(begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue(), centre.y, output));
				}
			} catch (NumberFormatException | ArithmeticException e) {
				errorTime++;
				continue;
			}
		}
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().
				info("[Particle]Error point times: " +errorTime + "(This number should not be too large)");
		return save;
	}

	public static Entity getCursorTarget(Player p, double range){
		Block block;
		Entity target;
		Iterator<Entity> entities;
		Location loc = p.getEyeLocation();
		org.bukkit.util.Vector vec = loc.getDirection().multiply(0.15);
		while((range -= 0.1) > 0 && ((block = loc.getWorld().getBlockAt(loc)).isLiquid() || block.isEmpty())){
			org.bukkit.Location loc2 = loc.add(vec);
			List<Entity> entityFind = new ArrayList<>();
			for (Entity entity : loc2.getChunk().getEntities()) {
				org.bukkit.Location entityLoc = entity.getLocation();
				if (entity instanceof LivingEntity &&
						entityLoc.getBlockX() == loc2.getBlockX() && (entityLoc.getBlockY() + 1 == loc2.getBlockY()
						|| entityLoc.getBlockY() == loc2.getBlockY())
						&& entityLoc.getBlockZ() == loc2.getBlockZ()) {
					entityFind.add(entity);
				}
			}
			entities = entityFind.iterator();
			while(entities.hasNext()){
				if((target = entities.next()) != p){
					return target;
				}
			}
		}
		return null;
	}
}
