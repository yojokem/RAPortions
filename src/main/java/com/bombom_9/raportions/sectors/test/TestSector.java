package com.bombom_9.raportions.sectors.test;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.bombom_9.raportions.sectors.SectorI;

public class TestSector extends SectorI {
	private Plugin p;
	
	public void init(Plugin p) {
		this.p = p;
		World over = p.getServer().getWorld("world");
		Location l = new Location(over, -51, 83, 167);
		
	}
}