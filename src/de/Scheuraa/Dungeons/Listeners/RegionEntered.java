package de.Scheuraa.Dungeons.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.connorlinfoot.titleapi.TitleAPI;

import de.Scheuraa.Dungeons.Schematics.Schematic;
import de.Scheuraa.Dungeons.Utils.Var;
import net.raidstone.wgevents.events.RegionEnteredEvent;

public class RegionEntered implements Listener{
	
	@EventHandler
	public void onRegionEntered(RegionEnteredEvent e) {
		Player p = e.getPlayer();
		String name = e.getRegionName();
		int endofname = name.lastIndexOf("-");
		name = name.subSequence(0, endofname).toString();
		Schematic schem = Var.getSchemByName(name);
		if(schem!=null) {
			TitleAPI.sendTitle(p, 2, 20,2, schem.getPrefix()+schem.getName(),schem.getSubName());
		}
		
	}

}
