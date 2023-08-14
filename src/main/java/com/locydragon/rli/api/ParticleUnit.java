package com.locydragon.rli.api;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.util.*;
import com.locydragon.rli.util.enums.JobType;
import com.locydragon.rli.util.particle.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ParticleUnit {
	public static Executor executor = Executors.newCachedThreadPool();

	public float offsetX = 0;
	public float offsetY = 0;
	public float offsetZ = 0;
	public float speed = 0.2F;
	public int amount = 3;
	public int range = 255;
	public String name;
	public LocationModel centre;
	public double precision = 0.2;
	public double cireclePrecision = 10.0;
	public boolean headCoordinateSystem = false;
	public RotationModel deltaRotation;

	private Vector<ParticleJob> jobList = new Vector<>();

	public ParticleUnit(String name, LocationModel origin) {
		this.name = name;
		this.centre = origin;
	}

	public void load(List<String> effects) {
		for (String obj : effects) {
			LangReader line = new LangReader(obj);
			if (!line.illegal()) {
				continue;
			}
			if (line.headValue().equalsIgnoreCase("delay")) {
				ParticleJob job = new ParticleJob(JobType.DELAY, null);
				job.setExtra(line.value());
				jobList.add(job);
			} else {
				OptionReader reader = new OptionReader(line.value());
				if (line.headValue().equalsIgnoreCase("circle")) {
					jobList.add(new ParticleJob(JobType.DRAW_CIRCLE, reader));
				} else if (line.headValue().equalsIgnoreCase("line")) {
					jobList.add(new ParticleJob(JobType.DRAW_LINE, reader));
				} else if (line.headValue().equalsIgnoreCase("f")) {
					jobList.add(new ParticleJob(JobType.DRAW_FUNCTION, reader));
				}
			}
		}
		calculate();
	}

	private void calculate() {
		Vector<ParticleJob> newVector = new Vector<>();
		for (ParticleJob job : this.jobList) {
			if (job.getType() == JobType.DELAY) {
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_CIRCLE) {
				LocationModel centre = new LocationModel(job.getOptions().getValue(null, "centre", "c"));
				org.bukkit.Location fake = new
						Location(Bukkit.getWorlds().get(0), centre.x, centre.y, centre.z);
				for (LocationModel result : ParticleExpression.sendParticleCircle(fake,
						Double.valueOf(job.getOptions().getValue(null,"radius", "r")), this.cireclePrecision)) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				job.effectTypeString = job.getOptions().getValue(null, "type", "t");
				String color = job.getOptions().getValue(null, "color", "c");
				if (color != null) {
					int[] colorRGB = ArithHelper.hex2RGB(color);
					job.R = colorRGB[0];
					job.G = colorRGB[1];
					job.B = colorRGB[2];
				}
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_LINE) {
				LocationModel A = new LocationModel(job.getOptions().getValue(null,"A", "a"));
				LocationModel B = new LocationModel(job.getOptions().getValue(null,"B", "b"));
				org.bukkit.Location fakeA = new
						Location(Bukkit.getWorlds().get(0), A.x, A.y, A.z);
				org.bukkit.Location fakeB = new
						Location(Bukkit.getWorlds().get(0),B.x, B.y,B.z);
				for (LocationModel result : ParticleExpression.buildLine(fakeA, fakeB, this.precision)) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				job.effectTypeString = job.getOptions().getValue(null, "type", "t");
				String color = job.getOptions().getValue(null, "color", "c");
				if (color != null) {
					int[] colorRGB = ArithHelper.hex2RGB(color);
					job.R = colorRGB[0];
					job.G = colorRGB[1];
					job.B = colorRGB[2];
				}
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_FUNCTION) {
				double start = Double.valueOf(job.getOptions().getValue(null, "start", "s", "begin"));
				double end = Double.valueOf(job.getOptions().getValue(null,"end", "e", "stop"));
				String expression = job.getOptions().getValue(null,"expression", "ex");
				for (LocationModel result : ParticleExpression.asFunction(expression, start, end, this.precision, this.centre,
						Boolean.valueOf(job.getOptions().getValue("false","symmetric", "s")),
								Boolean.valueOf(job.getOptions().getValue("false","solidX", "sx")),
										Boolean.valueOf(job.getOptions().getValue("false","solidZ", "sz")))) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				job.effectTypeString = job.getOptions().getValue(null, "type", "t");
				String color = job.getOptions().getValue(null, "color", "c");
				if (color != null) {
					int[] colorRGB = ArithHelper.hex2RGB(color);
					job.R = colorRGB[0];
					job.G = colorRGB[1];
					job.B = colorRGB[2];
				}
				newVector.add(job);
			}
		}
		this.jobList = newVector;
	}

	public void draw(org.bukkit.Location centre, org.bukkit.util.Vector eyeLoc) {
		org.bukkit.util.Vector[] vectorsBasis = VectorCalculator2D3D
				.calculateBasisVector(centre, eyeLoc, this.headCoordinateSystem);
		executor.execute(() -> {
			for (ParticleJob job : this.jobList) {
				if (job.getType() == JobType.DELAY) {
					try {
						Thread.sleep(Long.valueOf(job.getExtraValue().trim()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					for (LocationModel model : job.result()) {
						org.bukkit.Location clone = centre.clone();
						org.bukkit.util.Vector addtionX = vectorsBasis[0].clone().multiply(model.x);
						org.bukkit.util.Vector addtionY = vectorsBasis[1].clone().multiply(model.z);
						org.bukkit.util.Vector addtionZ = vectorsBasis[2].clone().multiply(model.y);
						org.bukkit.util.Vector addtion = addtionX.add(addtionY).add(addtionZ);
						org.bukkit.util.Vector nowALocation = null;
						if (deltaRotation.zita != 0) {
							double[] rotatedLocation = VectorCalculator2D3D.rotation3D(new double[] {addtion.getX(), addtion.getZ()
									, addtion.getY()},
									deltaRotation.zita, deltaRotation.x, deltaRotation.y, deltaRotation.z);
							/**
							double[] rotatedLocation = VectorCalculator2D3D
									.rotateOnSphericalCoordinateSystem(addtion.getX(), addtion.getZ(), addtion.getY()
											, deltaRotation.zita, deltaRotation.phi);
							 **/
							nowALocation
									= new org.bukkit.util.Vector(clone.getX(), clone.getY(), clone.getZ())
									.add((new org.bukkit.util.Vector(rotatedLocation[0], rotatedLocation[2], rotatedLocation[1])));
						} else {
							nowALocation
									= new org.bukkit.util.Vector(clone.getX(), clone.getY(), clone.getZ()).add((addtion));
						}
						clone = nowALocation.toLocation(clone.getWorld());
						if (!RevivedLocyItem.enableOverParticleLib) {
							if (job.R >= 0 && job.G >= 0 && job.B >= 0) {
								job.effect.display((float)job.R / 255F, (float)job.G / 255F, (float)job.B / 255F
										, 1, 0, clone, this.range);
							} else {
								job.effect.display(this.offsetX, this.offsetY, this.offsetZ
										, this.speed, this.amount, clone, this.range);
							}
						} else {
							if (job.R >= 0 && job.G >= 0 && job.B >= 0) {
								ParticleEffectForAbove13.display(job.effect,
										(float)job.R / 255F, (float)job.G / 255F, (float)job.B / 255F
										, 1, 0,
										clone, this.range, job.effectTypeString, job.R, job.G, job.B);
							} else {
								ParticleEffectForAbove13.display(job.effect,
										this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount,
										clone, this.range, job.effectTypeString, job.R, job.G, job.B);
							}
						}
					}
				}
			}
		});
	}
}
