package com.locydragon.rli.util.particle;

/**坐标模型类
 * @author LocyDragon
 * @version 1.3.3
 */
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

	/**通过三个参数，如(1,2,3)创建LocationModel实例
	 * @param code 参数
	 */
	public LocationModel(String code) {
		code = code.trim().replace("(", "").replace(")", "");
		this.x = Double.valueOf(code.split(",")[0]);
		this.y = Double.valueOf(code.split(",")[1]);
		this.z = Double.valueOf(code.split(",")[2]);
	}

	/**完全克隆本LocationModel对象
	 */
	public LocationModel clone() {
		return new LocationModel(this.x, this.y, this.z);
	}
}
