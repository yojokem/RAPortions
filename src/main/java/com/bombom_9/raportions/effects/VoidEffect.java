package com.bombom_9.raportions.effects;

import java.io.Serializable;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class VoidEffect implements Effect, Serializable {
	/**
	 * VoidEffect for saving data of Sectors
	 */
	private static final long serialVersionUID = 12L;
	private volatile boolean cancelled = false, closed = false;
	protected final com.bombom_9.raportions.Effect TYPE = com.bombom_9.raportions.Effect.VOID;
	
	protected static VoidEffect instance = new VoidEffect();
	private Stream<Player> targets;
	
	protected VoidEffect(Player... p) {
		this.targets = Lists.newArrayList(p).stream();
	}
	
	public static void call(Player... p) {
		instance.incur(p);
	}

	@Override
	public VoidEffect incur(Player... p) {
		VoidEffect newOne = new VoidEffect(p);
		newOne.run();
		return newOne;
	}

	@Override
	public void cancelToggle() {
		if(!closed) cancelled = cancelled ? false : cancelled;
		else cancelled = true;
	}
	
	@Override
	public boolean isCancelled() {
		return closed || cancelled;
	}
	
	public void close() {
		closed = true;
		cancelled = false;
	}

	@Override
	public com.bombom_9.raportions.Effect getType() {
		return com.bombom_9.raportions.Effect.VOID;
	}

	@Override
	public void run() {
		targets.forEach(x -> {
			x.sendTitle("SubINFO", "VoidEffect is continuous", 1, 2, 1);
		});
	}
}