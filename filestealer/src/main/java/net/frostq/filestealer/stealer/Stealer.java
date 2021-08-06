package net.frostq.filestealer.stealer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JFrame;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.frostq.filestealer.FileStealer;

public class Stealer implements Listener, Logger.Adapter {
	private Path cursor;
	private List<TargetContainer> targets = Lists.newArrayList(); // if cursor is directory
	
	protected final String outputPastes;
	private final int threads;
	
	private final Set<Stealer> stealers = Sets.newHashSet();
	
	/**
	 * Stealer 생성
	 * <code>dir</code>이/가 디렉토리에 해당하지 않는 파일일 경우 그 파일이 포함된 디렉토리로 간주함.
	 * @param dir Directory {@link Path}
	 * @param threads Count of threads
	 * @param parent Parent {@link Stealer}
	 */
	public Stealer(Path dir, int threads, Stealer parent, Stealer topParent) {
		if(Files.isRegularFile(dir)) dir = dir.getParent();
		
		if(Files.notExists(dir)) {
			log("This Stealer[" + this.hashCode() + "] object is thrown out because the given directory does not exist.");
			this.threads = -1;
			this.outputPastes = "//////";
			started = true;
			return;
		}
		
		this.cursor = dir;
		this.threads = threads <= 0 ? 2 : threads;
		Stealer topParentt = topParent != null ? topParent : this;
		Stealer parentOne = parent != null ? parent : topParent;
		Stealer thisthis = this;
		outputPastes = "/" + thisthis.getCursor().subpath(topParentt.getCursor().getNameCount() - 1, thisthis.getCursor().getNameCount()).toString().replace('\\', '/');
		
		try {
			Files.list(getCursor()).iterator().forEachRemaining(new Consumer<Path>() {
				public void accept(Path t) {
					if(Files.isDirectory(t)) {
						Stealer s = new Stealer(t, -1, parentOne, topParentt);
						s.setOutputDirectory(forOutput);
						stealers.add(s);
						if(topParentt != thisthis) topParentt.stealers.add(s);
					} else {
						TargetContainer tc = new TargetContainer(thisthis, t);
						targets.add(tc);
						if(topParentt != thisthis) topParentt.targets.add(tc);
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.init();
	}
	
	public Stealer(Path p) {
		if(Files.isRegularFile(p))
			this.cursor = p;
		this.threads = 1;
		
		outputPastes = isDirectory() ? getCursor().getName(getCursor().getNameCount() - 1) + "/" : "/";
		getOutputPath();
		this.init();
	}
	
	private void init() {
		FileStealer.instances.add(this);
		
		Collections.sort(this.targets, new Comparator<TargetContainer>() {
			public int compare(TargetContainer t1, TargetContainer t2) {
				Path o1 = t1.getFile();
				Path o2 = t2.getFile();
				
				try {
					if(Files.isDirectory(o1) || Files.isRegularFile(o2))
						return -1;
					else if(Files.isDirectory(o2) || Files.isRegularFile(o1))
						return 1;
					else if(Files.size(o1) > Files.size(o2))
						return 1;
					else if(Files.size(o1) == Files.size(o2))
						return 0;
					else if(Files.size(o1) < Files.size(o2))
						return -1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return 0;
			}
		});
		
		log(getOutputPastes());
		log("Loaded; for " + getTargetsCount() + " file(s).");
		if(l == null) setListener(this);
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
	
	public Path getOutputPath() {
		Path p = Paths.get(forOutput + this.outputPastes);
		
		if(!Files.exists(p))
			try {
				Files.createDirectories(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return p;
	}
	
	private boolean started = false;
	
	public void start() {
		if(started) return;
		started = true;
		
		List<ThiefThread> threads = Lists.newArrayListWithCapacity(this.threads);
		
		if(isForDivision()) {
			int division = getTargetsCount() > 2 ? this.threads : (getTargetsCount() % 16 == 0 ? 2 : getTargetsCount() % 16);
			int size = getTargetsCount();
			
			int pointer = 0, pointer2 = -1;
			
			log("Processes are divided into " + division + " threads.");
			getLogger().setThreadsOut(division);
			
			for(int i = 0; i < division; i++) {
				ThiefThread t = new ThiefThread();
				List<TargetContainer> containers = Lists.newArrayList();
				pointer2 = i++ == division ? (size - 1) : (int) ((i+1) * Math.floor(size / division));
				for(int j = pointer; j <= pointer2; j++)
					containers.add(targets.get(j));
				pointer = pointer2 + 1;
				
				t.targets = containers;
				threads.add(t);
			}
		} else {
			log("Single thread running.");
			ThiefThread t = new ThiefThread();
			t.targets = targets;
			threads.add(t);
		}
		
		threads.forEach(t -> {
			log("A thread has been launched.");
			t.setListener(getListener());
			t.start();
		});
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
	public void update(ThiefThread t, int cursor) {
		cursorMapping.put(t, cursor);
		
		log(getCurrentPercentage());
	}
	
	public int getCurrentCursor() {
		return cursorMapping.values().parallelStream().mapToInt(x -> x).sum() + 1;
	}
	
	public float getCurrentStatistics() {
		return ((float) getCurrentCursor()) / ((float) getTargetsCount());
	}
	
	public String getCurrentPercentage() {
		return String.format("%.2f", getCurrentStatistics() * 100.0) + "%";
	}
	
	public String getCurrentStatisticsInStr() {
		return getCurrentCursor() + " of " + getTargetsCount();
	}
	
	public boolean isForDivision() {
		return isDirectory() ? (
				getCenteredCapacity() >= 384 * (long) Math.pow(2, 10) ||
				getMiddleCapacity() >= 448 * (long) Math.pow(2, 10) ||
				getAveragedCapacity() >= 200 * getTargetsCount() * (long) Math.pow(2, 10)
				) || getTargetsCount() > 1000
				:
				
					false;
	}
	
	public long getCenteredCapacity() {
		int l = Math.round(getTargetsCount() / 2);
		
		try {
			return getTargetsCount() % 2 == 0 ? Files.size(targets.get(l).getFile()) : Files.size(targets.get(l + 1).getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public long getMiddleCapacity() {
		try {
			return (Files.size(targets.get(0).getFile()) + Files.size(targets.get(getTargetsCount() - 1).getFile())) / 2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public long getAveragedCapacity() {
		try {
			return Files
					.walk(getCursor())
					.filter(p -> p.toFile().isFile())
					.mapToLong(p -> p.toFile().length()).sum() / getTargetsCount();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public String getOutputPastes() {
		return this.outputPastes;
	}
	
	protected boolean isDirectory() {
		return getCursor().toFile().isDirectory();
	}
	
	public int getTargetsCount() {
		return targets.size();
	}
	
	public Path getCursor() {
		return this.cursor;
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