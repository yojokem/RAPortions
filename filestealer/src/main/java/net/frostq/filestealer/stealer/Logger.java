package net.frostq.filestealer.stealer;

public interface Logger {
	public static interface Adapter {
		public void log(final String text);
		public Logger getLogger();
	}
	
	public void update(final String text);
	public void setThreadsOut(int threads);
}