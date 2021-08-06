package com.bombom_9.raportions.sectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.bombom_9.raportions.Logger1;
import com.bombom_9.raportions.Manager;
import com.bombom_9.raportions.effects.Effect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class ISector implements Logger1, Sector, Listener {
	/**
	 * ISector belongs
	 * 14L
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> exceptions = Lists.newArrayList();
	private Map<Location, Double> locations = Maps.newLinkedHashMap();
	// effects Map에서 Value가 <code>true</code>인 경우, Inbound로 처리함. 
	private Map<Effect, Boolean> effects = Maps.newConcurrentMap();
	private SectorManager m;
	
	private String name;
	private boolean allowed; //enabled
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onOccur(PlayerMoveEvent e) {
		if(allowed) {
			if(!exceptions.contains(e.getPlayer())) {
				//순간 판단
				
				/*
				 * boolean inStart = locations.keySet().contains(e.getFrom()); boolean inEnd =
				 * locations.keySet().contains(e.getTo());
				 * 
				 * boolean inbound = inStart && inEnd, intoout = (inStart == true) && (inEnd ==
				 * false), outtoin = (inStart == false) && (inEnd == true), outbound = !inbound;
				 * 
				 * if(inbound || outtoin) { //is In effects.keySet().parallelStream() .filter(x
				 * -> effects.get(x)) .forEach(x -> x.incur(e.getPlayer())); } else if(outbound
				 * || intoout) { //is Out effects.keySet().parallelStream() .filter(x ->
				 * !effects.get(x)) .forEach(x -> x.incur(e.getPlayer())); }
				 */
			} else println("Exception occurred in Sector [" + name + "] within an exceptor " + e.getPlayer().getDisplayName());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().setHealth(e.getPlayer().getHealthScale());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onQuit(PlayerQuitEvent e) {
		e.getPlayer().setHealth(e.getPlayer().getHealthScale());
	}
	
	public void setManager(Manager m) {
		this.m = SectorManager.class.cast(m);
	}
	
	protected SectorManager getManager() {
		return this.m;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSectorName() {
		return "SCT-〈" + getName() + "〉";
	}
	
	@Override
	public void allow() {
		this.allowed = true;
	}
	
	public boolean isAllowed() {
		return this.allowed;
	}
	
	@Override
	public void disallow() {
		this.allowed = false;
	}
	
	public void add(CommandSender cs, Location l, double x) {
		if(l == null || locations.keySet().contains(l)) return;
		onUpdate(cs, l);
		
		if(l != null && !locations.keySet().contains(l))
			locations.put(l, x);
		
		onAdd(cs, l);
	}
	
	public void remove(CommandSender cs, Location l) {
		if(!locations.keySet().contains(l)) return;
		onUpdate(cs, l);
		
		locations.remove(l);
		
		onRemove(cs, l);
	}
	
	public Location get(int index) {
		return Lists.newArrayList(locations.keySet().iterator()).get(index);
	}
	
	public Stream<Location> get() {
		return locations.keySet().parallelStream();
	}
	
	public abstract void onAdd(CommandSender cs, Location l);
	public abstract void onRemove(CommandSender cs, Location l);
	public abstract void onUpdate(CommandSender cs, Location l);
}