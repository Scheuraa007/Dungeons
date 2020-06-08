package de.Scheuraa.Dungeons.Utils;

public class Stufe {
	
	private String name,prefix;
	private int chance,min,max;
	
	public Stufe(String name, String prefix, int chance,int min, int max) {
		this.name = name;
		this.prefix = prefix;
		this.chance = chance;
		this.min = min;
		this.max =max;
	}
	
	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getChance() {
		return chance;
	}
	
	
	
	

}
