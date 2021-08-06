package com.bombom_9.raportions;

import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public abstract class Manager implements Logger1 {
	protected int order;
	private String name;
	protected Plugin pl;
	
	public Manager(int order, Plugin p) {
		this.order = order;
		this.pl = p;
	}
	
	protected void init(String name) {
		setName(name);
		println("Initialized.");
		println("- " + getDescription());
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getPrefix() {
		return "[" + ChatColor.LIGHT_PURPLE + "RAPS" + ChatColor.RESET + "-" + ChatColor.AQUA + getName() + ChatColor.RESET + "]";
	}
	
	@Override
	public String getSuffix() {
		return "";
	}
	
	public abstract String getDescription();
}