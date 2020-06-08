package de.Scheuraa.Dungeons.Items;

import org.bukkit.inventory.ItemStack;

public class ChestItem {
	
	private ItemStack item;
	private int probability;
	
	private int minTicket, maxTicket;
	
	
	
	public ChestItem(ChestItems itemsclass,ItemStack item, int probability){
		this.item = item;
		this.probability = probability;
		this.minTicket = itemsclass.getCurrentTicket()+1;
		this.maxTicket = minTicket + probability;
		itemsclass.setCurrentTicket(maxTicket);
		
	}
	

	public ItemStack getItem() {
			return item;
	}

	public int getProbability() {
		return probability;
	}
	
	public int getMinTicket() {
		return minTicket;
	}
	 public int getMaxTicket() {
		return maxTicket;
	}

}
