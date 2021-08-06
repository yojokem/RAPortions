package com.bombom_9.raportions.effects.endeavors;

import java.util.List;

import org.bukkit.Location;

import com.google.common.collect.Lists;

public abstract class LocationTargeter {
	private List<Location> locations;
	protected Orientation orient;
	
	LocationTargeter(Orientation orient, Location... lcs) {
		this.locations = Lists.newArrayList(lcs);
		this.orient = orient;
	}
	
	public List<Location> getTargetedOnes() {
		return this.locations;
	}
	
	public static enum Orientation {
		ONEtoONE, ONE, SET, inOrder
	}
}