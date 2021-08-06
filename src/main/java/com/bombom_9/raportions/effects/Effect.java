package com.bombom_9.raportions.effects;

import org.bukkit.entity.Player;

public interface Effect extends Runnable {
	public Effect incur(Player... p);
	
	public boolean isCancelled();
	public void cancelToggle();
	public void close();
	public void run();
	
	public com.bombom_9.raportions.Effect getType();
}