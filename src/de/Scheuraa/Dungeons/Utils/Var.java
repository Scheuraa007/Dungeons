package de.Scheuraa.Dungeons.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import de.Scheuraa.Dungeons.Schematics.Schematic;

public class Var {
	
	private static HashMap<String, Schematic> schematicsbyName = new HashMap<String, Schematic>();
	private static ArrayList<Schematic> schems = new ArrayList<Schematic>();
	public static Stufe magisch, selten, episch, legendaer;
	public static ArrayList<Stufe> stufen = new ArrayList<Stufe>();
	public static File cfgFile = new File("plugins//Dungeons//items.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
	public static ArrayList<PlayerChest> playerchests = new ArrayList<PlayerChest>();
	
	
	public static void loadStufen() {
		magisch = new Stufe("Magisch", "§2", 30,2,4);
		stufen.add(magisch);
		selten = new Stufe("Selten", "§9", 20,3,4);
		stufen.add(selten);
		episch = new Stufe("Episch", "§5", 10,4,6);
		stufen.add(episch);
		legendaer = new Stufe("Legendär", "§6", 3,5,6);
		stufen.add(legendaer);
	}
	
	public static void addSchematic(String name, Schematic schem) {
		schematicsbyName.put(name.toLowerCase(), schem);
		schems.add(schem);
	}
	
	public static Schematic getSchemByName(String name) {
		return schematicsbyName.get(name);
	}
	
	public static ArrayList<Schematic> getSchematics() {
		return schems;
	}
	
	public static ArrayList<Schematic> getSchemsByStufe(Stufe stufe){
		ArrayList<Schematic> schems = new ArrayList<Schematic>();
		for(Schematic schem : Var.schems) {
			if(schem.getStufe()==stufe) {
				schems.add(schem);
			}
		}
		return schems;
	}

}
