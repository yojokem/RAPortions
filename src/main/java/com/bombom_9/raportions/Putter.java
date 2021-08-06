package com.bombom_9.raportions;

import java.io.Serializable;

import org.bukkit.Server;

public interface Putter extends Serializable {
	public String getIdentifier();
	public void enterServer(Server s);
}