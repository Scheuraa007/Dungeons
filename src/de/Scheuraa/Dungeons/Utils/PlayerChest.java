package de.Scheuraa.Dungeons.Utils;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerChest {
	Player p;
	Chest c;
	Inventory inv;
	public PlayerChest(Player p, Chest c,Inventory inv) {
		this.p = p;
		this.c = c;
		this.inv=inv;
		Var.playerchests.add(this);
	}
	
	public Player getP() {
		return p;
	}
	public Chest getC() {
		return c;
	}
	public Inventory getInv() {
		return inv;
	}
	
	

}
