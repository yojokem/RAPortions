package com.bombom_9.raportions.sectors;

import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.bombom_9.raportions.Logger1;
import com.bombom_9.raportions.Manager;
import com.google.common.collect.Lists;

import net.md_5.bungee.api.ChatColor;

public class SectorManager extends Manager {
	protected List<Sector> sectors = Lists.newArrayList();
	
	protected SectorCommandListener scl;

	public SectorManager(int order, Plugin plugin) {
		super(order, plugin);
		init("SectorManager");
		scl = new SectorCommandListener();
	}
	
	public void add(Sector s) {
		s.setManager(this);
		s.enterServer(this.getPlugin().getServer());
		if(sectors.parallelStream().anyMatch(x -> x.getSectorName().contentEquals(s.getSectorName())))
			sectors.add(s);
	}
	
	public void remove(Sector s) {
		sectors.removeIf(x -> x.getSectorName().contentEquals(s.getSectorName()));
	}
	
	public void save() {
		
	}
	
	@Override
	public String getDescription() {
		return "섹터를 관리하는 관리자 객체입니다.";
	}
	
	protected Plugin getPlugin() {
		return this.pl;
	}
	
	private static class SectorCommandListener implements Listener, Logger1 {

		@Override
		public String getPrefix() {
			return "[SectorManager-" + ChatColor.YELLOW + ChatColor.UNDERLINE + "SCL" + ChatColor.RESET + "]";
		}

		@Override
		public String getSuffix() {
			return "";
		}
		
	}
}