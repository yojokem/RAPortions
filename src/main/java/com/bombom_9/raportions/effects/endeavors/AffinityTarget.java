package com.bombom_9.raportions.effects.endeavors;

public abstract class AffinityTarget {
	private int affinity;
	
	int getAffinity() {
		return this.affinity;
	}
	abstract AffinityTarget setAffinity(int aff);
}