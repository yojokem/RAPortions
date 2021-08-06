package com.bombom_9.raportions.sectors;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class SectorI extends ISector implements Sector {
	/**
	 * SectorI
	 */
	private static final long serialVersionUID = -6910182131358500062L;

	@Override
	public String getIdentifier() {
		return this.getSectorName();
	}

	@Override
	public String getPrefix() {
		return "[SectorI]〔" + getIdentifier() + "〕";
	}

	@Override
	public String getSuffix() {
		return "℗";
	}

	@Override
	public void onAdd(CommandSender cs, Location l) {
		cs.sendMessage(getPrefix() + " " + "Added!");
		println("Added: " + l.getBlockX() + " - " + l.getBlockY() + " - " + l.getBlockZ());
	}

	@Override
	public void onRemove(CommandSender cs, Location l) {
		cs.sendMessage(getPrefix() + " " + "Removed!");
		println("Removed: " + l.getBlockX() + " - " + l.getBlockY() + " - " + l.getBlockZ());
	}

	@Override
	public void onUpdate(CommandSender cs, Location l) {
		cs.sendMessage(getPrefix() + " " + "Updated!");
		println("Updated: " + l.getBlockX() + " - " + l.getBlockY() + " - " + l.getBlockZ());
	}

	@Override
	public void enterServer(Server s) {
		(s != null ? s : this.getManager().getPlugin().getServer())
			.getPluginManager().registerEvents(this, this.getManager().getPlugin());
	}
}