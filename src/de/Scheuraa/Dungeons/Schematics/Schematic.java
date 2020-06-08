package de.Scheuraa.Dungeons.Schematics;

import java.util.ArrayList;

import de.Scheuraa.Dungeons.Items.ChestItem;
import de.Scheuraa.Dungeons.Items.ChestItems;
import de.Scheuraa.Dungeons.Utils.ReadConfig;
import de.Scheuraa.Dungeons.Utils.Stufe;
import de.Scheuraa.Dungeons.Utils.Var;

public class Schematic {
	private String name;
	private Stufe stufe;
	private ChestItems man;

	public Schematic(String name, Stufe stufe) {
		Var.addSchematic(name, this);
		this.name = name;
		this.stufe = stufe;
		this.man = new ChestItems(stufe.getMin(), stufe.getMax(), new ArrayList<ChestItem>());
		loadMan();
	}

	private void loadMan() {
		ReadConfig.getChestItems(name.toLowerCase(), Var.cfg);
	}

	public ChestItems getMan() {
		return man;
	}

	public String getName() {
		return name;
	}

	public int getChance() {
		return stufe.getChance();
	}

	public String getPrefix() {
		return stufe.getPrefix();
	}

	public String getSubName() {
		return stufe.getPrefix() + stufe.getName();
	}

	public Stufe getStufe() {
		return stufe;
	}

}
