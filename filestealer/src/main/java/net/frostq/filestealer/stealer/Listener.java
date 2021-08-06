package net.frostq.filestealer.stealer;

public interface Listener {
	public void update(ThiefThread t, int cursor);
	public default void setListener(ThiefThread t) {
		t.listener = this;
	}
	
	public int getCurrentCursor();
	public float getCurrentStatistics();
	public String getCurrentPercentage();
	public String getCurrentStatisticsInStr();
	public Logger getLogger();
	
	public static interface MULTI extends Listener {
		
		@Override
		public default void update(ThiefThread t, int cursor) {
			update(null, t, cursor);
		}
		
		public void update(Stealer s, ThiefThread t, int cursor);
		public default void setListener(ThiefThread t) {
			t.listener = this;
		}
		
		public int getCurrentCursor();
		public float getCurrentStatistics();
		public String getCurrentPercentage();
		public String getCurrentStatisticsInStr();
		public Logger getLogger();
	}
}