package com.bombom_9.raportions.sectors.test;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.bombom_9.raportions.sectors.SectorI;

public class TestSector extends SectorI {
	/**
	 * TestSector
	 */
	private static final long serialVersionUID = 1406703194600905463L;
	private transient Plugin p;
	
	public void init(Plugin p) {
		setName("TestSector");
		println("Loaded");
		this.p = p;
		World over = p.getServer().getWorld("world");
		Location l = new Location(over, -51, 81, 167);
		Location l2 = new Location(over, -45, 90, 163);
		
		add(p.getServer().getConsoleSender(), l, l2);
	}
}