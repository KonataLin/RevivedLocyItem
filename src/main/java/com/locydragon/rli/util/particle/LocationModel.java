package com.locydragon.rli.util.particle;

public class LocationModel {
	public double x;
	public double y;
	public double z;
	public LocationModel(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public LocationModel(org.bukkit.Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
	}

	public LocationModel(String code) {
		code = code.trim().replace("(", "").replace(")", "");
		this.x = Double.valueOf(code.split(",")[0]);
		this.y = Double.valueOf(code.split(",")[1]);
		this.z = Double.valueOf(code.split(",")[2]);
	}

	public LocationModel clone() {
		return new LocationModel(this.x, this.y, this.z);
	}
}
