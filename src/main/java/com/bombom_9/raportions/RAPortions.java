package com.bombom_9.raportions;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.bombom_9.raportions.sectors.SectorManager;
import com.bombom_9.raportions.sectors.test.TestSector;

public class RAPortions extends JavaPlugin implements Logger1 {
	public static final String VERSION = "0.0.1";
	
	protected SectorManager sctm;
	
	@Override
	public void onLoad() {
		println("Loading on.");
		sctm = new SectorManager(1, this);
		println("Loaded once.");
	}
	
	@Override
	public void onEnable() {
		println("Enabling.");
		//Attach to SectorManager and runtime
		TestSector ts = new TestSector();
		ts.init(this);
		sctm.add(ts);
		println("Enabled! " + VERSION);
	}
	
	@Override
	public void onDisable() {
		println("Disabling..");
		println("Good-bye!");
	}

	@Override
	public String getPrefix() {
		return ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.UNDERLINE + "RAPortions" + ChatColor.RESET + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
	}

	@Override
	public String getSuffix() {
		return ChatColor.RESET + "" + ChatColor.GOLD + "*" + ChatColor.RESET;
	}
}