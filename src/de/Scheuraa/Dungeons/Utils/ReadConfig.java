package de.Scheuraa.Dungeons.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import de.Scheuraa.Dungeons.Items.ChestItem;
import de.Scheuraa.Dungeons.Items.ChestItems;
import de.Scheuraa.Dungeons.Schematics.Schematic;

public class ReadConfig {
	
	public static void createConfigLocation(ItemStack item, int probability, String path, File file, YamlConfiguration cfg) {
		int anzItems =0;
		if(cfg.contains(path)) {
			anzItems = cfg.getInt(path+".anzItems");
		}else {
			cfg.set(path+".anzItems",anzItems);
		}
		cfg.set(path+"."+anzItems+".item", item);
		cfg.set(path +"." + anzItems+".prob", probability);
		anzItems++;
		cfg.set(path+".anzItems",anzItems);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getChestItems(String path, YamlConfiguration cfg){
		if(!cfg.contains(path)) {
			return;
		}
		int anzItems = cfg.getInt(path+".anzItems");
		ArrayList<ChestItem> items = new ArrayList<ChestItem>();
		for(int i=0;i<anzItems;i++) {
			ItemStack stack = cfg.getItemStack(path+"."+i+".item");
			int prob = cfg.getInt(path+"."+i+".prob");
			Schematic schem = Var.getSchemByName(path);
			ChestItem item = new ChestItem(schem.getMan(), stack, prob);
			schem.getMan().addItem(item);
		}
	}
	
}
