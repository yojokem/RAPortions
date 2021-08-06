package com.bombom_9.raportions.sectors;

import com.bombom_9.raportions.Manager;
import com.bombom_9.raportions.Putter;

public interface Sector extends Putter {
	public String getSectorName();
	public void allow();
	public void disallow();
	
	public void setName(String r);
	public String getName();
	public void setManager(Manager m);
}