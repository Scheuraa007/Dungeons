package de.Scheuraa.Dungeons.Items;

import java.util.ArrayList;

public class ChestItems {
	private int currentTicket=0;
	ArrayList<ChestItem> items = new ArrayList<ChestItem>();
	int min,max;
	
	public ChestItems(int min, int max,ArrayList<ChestItem> items) {
		this.min = min;
		this.max = max;
		this.items = items;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public ArrayList<ChestItem> getItems() {
		return items;
	}
	
	public void addItem(ChestItem item) {
		items.add(item);
	}
	
	
	public int getCurrentTicket() {
		return currentTicket;
	}
	
	public void setCurrentTicket(int currentTicket) {
		this.currentTicket = currentTicket;
	}

}
