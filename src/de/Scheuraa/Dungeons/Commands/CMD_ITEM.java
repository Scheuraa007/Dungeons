package de.Scheuraa.Dungeons.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.Scheuraa.Dungeons.Items.ChestItem;
import de.Scheuraa.Dungeons.Schematics.Schematic;
import de.Scheuraa.Dungeons.Utils.ReadConfig;
import de.Scheuraa.Dungeons.Utils.Var;

public class CMD_ITEM implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("dungeons.admin")) {
				if(args.length ==2 ) {
					String name = args[0];
					int prob = Integer.parseInt(args[1]);
					Schematic schem = Var.getSchemByName(name.toLowerCase());
					if(schem == null) {
						p.sendMessage("§cDieses Dungeon gibt es nicht");
						return false;
					}
					ItemStack stack = p.getItemInHand();
					if(stack == null || stack.getType()==Material.AIR) {
						p.sendMessage("§cDu musst ein Item in deiner Hand halten.");
						return false;
					}
					ChestItem item = new ChestItem(schem.getMan(), stack, prob);
					schem.getMan().addItem(item);
					ReadConfig.createConfigLocation(stack, prob, name, Var.cfgFile, Var.cfg);
					p.sendMessage("§aItem wurde zum Loot hinzugefügt");
					return true;
					
				}else {
					p.sendMessage("§cBitte nutze /item <name> <chance>");
					return false;
				}
			}
		}
		return false;
	}
	
	

}
