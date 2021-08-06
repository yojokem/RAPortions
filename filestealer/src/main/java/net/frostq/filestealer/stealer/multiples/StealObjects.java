package net.frostq.filestealer.stealer.multiples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.frostq.filestealer.stealer.Listener;
import net.frostq.filestealer.stealer.Logger;
import net.frostq.filestealer.stealer.Stealer;
import net.frostq.filestealer.stealer.ThiefThread;

public class StealObjects implements Runnable, Listener.MULTI, Logger.Adapter {
	private List<Stealer> stealers = Lists.newArrayList();
	private boolean activated = false;
	
	protected StealObjects(Path output, Stealer... stealers) {
		if(stealers.length == 0)
			activated = false;
		
		this.stealers.addAll(Arrays.asList(stealers));
		this.init(output);
	}
	
	public StealObjects(Path output, Path... paths) {
		if(paths.length == 0)
			activated = false;
		
		for(Path p : paths) {
			this.stealers.add(new Stealer(p, 3, null, null));
		}
		this.init(output);
	}
	
	private void init(Path output) {
		for(Stealer s : stealers) {
			reduceOverlaps(s);
			s.setOutputDirectory(output);
		}
	}
	
	public int getFileCount() {
		return stealers.parallelStream().mapToInt(x -> x.getTargetsCount()).sum();
	}
	
	public boolean reduceOverlaps(Stealer s) {
		if(stealers.indexOf(s) != stealers.lastIndexOf(s)) {
			stealers.remove(stealers.lastIndexOf(s));
			return reduceOverlaps(s);
		} else return true;
	}
	
	protected Path forOutput;
	
	public void setOutputDirectory(Path directory) {
		if(directory == null) return;
		if(Files.isRegularFile(directory)) directory = directory.getParent();
		
		if(!Files.exists(directory))
			try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		forOutput = directory;
		stealers.forEach(x -> x.setOutputDirectory(forOutput));
		log("Output Directory is set to " + directory);
	}
	
	public Path getOutputDirectory() {
		return this.forOutput;
	}
	
	private Listener l;
	public void setListener(Listener l) {
		this.l = l;
	}
	public Listener getListener() {
		return this.l;
	}
	
	public Map<ThiefThread, Integer> cursorMapping = Maps.newHashMap();
	
	@Override
	public void update(Stealer s, ThiefThread t, int cursor) {
		cursorMapping.put(t, cursor);
		
		log(getCurrentPercentage());
	}
	
	public int getCurrentCursor() {
		return cursorMapping.values().parallelStream().mapToInt(x -> x).sum() + 1;
	}
	
	public float getCurrentStatistics() {
		return ((float) getCurrentCursor()) / ((float) getFileCount());
	}
	
	public String getCurrentPercentage() {
		return String.format("%.2f", getCurrentStatistics() * 100.0) + "%";
	}
	
	public String getCurrentStatisticsInStr() {
		return getCurrentCursor() + " of " + getFileCount();
	}
	
	@Override
	public void run() {
		if(activated) {
			for(final Stealer s : stealers) {
				Thread t = new Thread(() -> {
					s.startConsole(f);
					
					lis = s.getListener();
					s.setListener(f);
					
					s.start();
				});
			}
		}
	}
	
	private boolean consoleLogging = false;
	
	public void startConsole(final JFrame f) {
		if(f != null) this.consoleLogging = true;
	}
	
	@Override
	public void log(String text) {
		if(consoleLogging)
			getLogger().update(getClass().getSimpleName() + "-" + hashCode() + " | " + text.trim());
	}

	@Override
	public Logger getLogger() {
		Logger x = this.getListener().getLogger();
		return x != null ? x : new Logger() {
			@Override
			public void update(String text) {
				System.out.println(text);
			}

			@Override
			public void setThreadsOut(int threads) { }
		};
	}
}