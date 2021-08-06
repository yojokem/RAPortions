package net.frostq.filestealer.stealer;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
//import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
//import java.nio.file.attribute.UserPrincipal;
//import java.nio.file.attribute.UserPrincipalLookupService;

public class TargetContainer {
	private Stealer container;
	private Path file;
	private FileLock lock;
	protected FileChannel channel;
	
	public TargetContainer(Stealer container, Path p) {
		this.container = container;
		this.file = p;
		
		/*
		 * try { UserPrincipalLookupService lookupService =
		 * p.getFileSystem().getUserPrincipalLookupService(); this.file =
		 * Files.setOwner(p,
		 * lookupService.lookupPrincipalByName(System.getProperty("user.name"))); }
		 * catch (Exception e) { e.printStackTrace(); System.exit(0); }
		 */
	}
	
	protected void open(Path...p) throws IOException {
		lockRelease();
		if(this.channel == null)
			this.channel = FileChannel.open(p.length == 0 ? getFile() : p[0], StandardOpenOption.READ, StandardOpenOption.WRITE);
	}
	
	protected void lock() {
		try {
			this.lock = this.channel.lock();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void lockRelease() {
		if(this.lock != null)
			try {
				lock.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public Stealer getContainer() {
		return this.container;
	}
	
	public Path getFile() {
		return this.file;
	}
}