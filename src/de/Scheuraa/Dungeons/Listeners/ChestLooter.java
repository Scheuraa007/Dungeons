package de.Scheuraa.Dungeons.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import de.Scheuraa.Dungeons.Schematics.Schematic;
import de.Scheuraa.Dungeons.Utils.PlayerChest;
import de.Scheuraa.Dungeons.Utils.Var;


public class ChestLooter implements Listener{
	
	@EventHandler
	public void onChest(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.CHEST){
				Block b = e.getClickedBlock();
				World w = BukkitAdapter.adapt(e.getPlayer().getWorld());
				RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
				RegionManager regions = container.get(w);
				ApplicableRegionSet set = regions.getApplicableRegions(BlockVector3.at(b.getX(), b.getY(), b.getZ()));
					if(set.size()>0) {
						Chest c = (Chest) b.getState();
						ProtectedRegion region = (ProtectedRegion) set.getRegions().toArray()[0];
						String name = region.getId();
						int endofname = name.lastIndexOf("-");
						name=name.subSequence(0, endofname).toString();
						Schematic schem = Var.getSchemByName(name);
						de.Scheuraa.Dungeons.Items.Chest ches = new de.Scheuraa.Dungeons.Items.Chest(schem.getMan());
						Inventory inv = ches.getInv();
						for(PlayerChest pc : Var.playerchests) {
							if(pc.getP()==e.getPlayer() && pc.getC().getBlock().getLocation().equals(c.getLocation())) {
								System.out.println("schonmal");
								e.getPlayer().openInventory(pc.getInv());
								e.setCancelled(true);
								return;
							}
						}
						e.getPlayer().openInventory(inv);
						new PlayerChest(e.getPlayer(), c, inv);
						e.setCancelled(true);
						
					}
				
			}
		}
	}

}
