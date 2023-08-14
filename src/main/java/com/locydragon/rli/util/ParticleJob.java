package com.locydragon.rli.util;

import com.locydragon.rli.util.enums.JobType;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class ParticleJob {
	private JobType type;
	private OptionReader reader;
	private String extraValue = null;
	private List<LocationModel> calculatedResult = new ArrayList<>();
	public ParticleEffect effect = ParticleEffect.FLAME;
	public String effectTypeString = "FLAME";
	public int R = -1;
	public int G = -1;
	public int B = -1;

	public ParticleJob(JobType type, OptionReader option) {
		this.type = type;
		this.reader = option;
	}

	public JobType getType() {
		return this.type;
	}

	public OptionReader getOptions() {
		return this.reader;
	}

	public void setExtra(String value) {
		this.extraValue = value;
	}

	public String getExtraValue() {
		return this.extraValue;
	}

	public void addResult(LocationModel model) {
		this.calculatedResult.add(model);
	}

	public List<LocationModel> result() {
		return this.calculatedResult;
	}

	public boolean isReady() {
		return !this.calculatedResult.isEmpty();
	}
}
