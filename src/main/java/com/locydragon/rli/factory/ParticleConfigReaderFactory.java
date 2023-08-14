package com.locydragon.rli.factory;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.api.ParticleUnit;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.RotationModel;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ParticleConfigReaderFactory {
	public static int readFile(FileConfiguration config) {
		int find = 0;
		for (String key : config.getKeys(false)) {
			find++;
			ParticleUnit unit = new ParticleUnit(key, new LocationModel
					(config.getString(key + ".origin", "(0,0,0)")));
			unit.amount = config.getInt(key + ".amount", 3);
			unit.offsetX = (float) config.getDouble(key + ".offsetX", 0);
			unit.offsetY = (float) config.getDouble(key + ".offsetY", 0);
			unit.offsetZ = (float) config.getDouble(key + ".offsetZ", 0);
			unit.speed = (float) config.getDouble(key + ".speed", 0.2);
			unit.range = config.getInt(key + ".range", 10);
			unit.cireclePrecision = config.getDouble(key + ".circlePrecision", 10);
			unit.precision = config.getDouble(key + ".precision", 0.2);
			unit.headCoordinateSystem = config.getBoolean(key + ".head", false);
			unit.deltaRotation = new RotationModel(config.getString(key + ".rotate", "(0,0,0,0)"));
			List<String> effectList = config.getStringList(key + ".Effect");
			if (effectList == null) {
				effectList = new ArrayList<>();
			}
			unit.load(effectList);
			LocyItemAPI.registerParticleEffect(unit);
		}
		return find;
	}
}
