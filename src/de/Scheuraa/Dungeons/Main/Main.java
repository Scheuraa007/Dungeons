package de.Scheuraa.Dungeons.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Scheuraa.Dungeons.Commands.CMD_ITEM;
import de.Scheuraa.Dungeons.Listeners.ChestLooter;
import de.Scheuraa.Dungeons.Listeners.ChunkGenerating;
import de.Scheuraa.Dungeons.Listeners.RegionEntered;
import de.Scheuraa.Dungeons.Schematics.Schematic;
import de.Scheuraa.Dungeons.Utils.ReadConfig;
import de.Scheuraa.Dungeons.Utils.Var;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		super.onEnable();
		Bukkit.getPluginManager().registerEvents(new ChunkGenerating(), this);
		Bukkit.getPluginManager().registerEvents(new RegionEntered(), this);
		Bukkit.getPluginManager().registerEvents(new ChestLooter(), this);
		this.getCommand("item").setExecutor(new CMD_ITEM());
		loadSchems();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	
	public static Main getPlugin() {
		return plugin;
	}
	
	
	
	private void loadSchems() {
		Var.loadStufen();
		new Schematic("Tempel", Var.magisch);
		new Schematic("Verlies", Var.selten);
		new Schematic("Gold", Var.episch);
		new Schematic("Dia", Var.legendaer);
	}
}
