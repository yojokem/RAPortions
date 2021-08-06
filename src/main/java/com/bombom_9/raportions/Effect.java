package com.bombom_9.raportions;

public enum Effect {
	DAMAGE(1),
	TELEPORT(2),
	VOID(0);
	
	private int key;
	
	Effect(int key) {
		this.key = key;
	}
	
	public int key() {
		return this.key;
	}
	
	public boolean validTo(Effect e) {
		return this.equals(e);
	}
}