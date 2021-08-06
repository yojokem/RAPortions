package com.bombom_9.raportions;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Storage {
	protected Path raw;
	private boolean valid = false;
	
	public Storage(Path p1) {
		setDirectory(p1);
	}
	
	public Storage setDirectory(Path p1) {
		if(!Files.isDirectory(p1)) return this;
		
		this.raw = p1;
		this.valid = true;
		return this;
	}
	
	public Path getDirectory() {
		return this.raw;
	}
	
	public Stream<Path> getLists() {
		if(!valid) return null;
		try {
			return Files.list(getDirectory());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public long put(InputStream is, Path file, OpenOption... opts) {
		if(!valid) return -1;
		try {
			return Files.copy(is, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	protected void put(Path file, Putter p, OpenOption... opts) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file, opts));
			oos.writeObject(p);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void put(Putter p, OpenOption... opts) {
		if(!valid) return;
		put(Paths.get(this.getDirectory() + "/" + p.getIdentifier()), p, opts);
	}
	
	public <T extends Putter> Putter stream(Class<T> d, Path p) {
		if(!valid) return null;
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(Files.newInputStream(p));
			T dp =  d.cast(ois.readObject());
			ois.close();
			return dp;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}