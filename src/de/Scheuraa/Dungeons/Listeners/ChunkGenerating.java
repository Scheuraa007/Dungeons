package de.Scheuraa.Dungeons.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import de.Scheuraa.Dungeons.Main.Main;
import de.Scheuraa.Dungeons.Schematics.Schematic;
import de.Scheuraa.Dungeons.Utils.Stufe;
import de.Scheuraa.Dungeons.Utils.Var;

public class ChunkGenerating implements Listener {

	public static ArrayList<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
	private static int id = 0;

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {

		Chunk chunk = e.getChunk();
		if (e.isNewChunk()) {

			Random random = new Random();
			int randomZahl = random.nextInt(100000);
			int j = 0;
			Schematic toPaste = null;
			for(Stufe stu : Var.stufen) {
				if(randomZahl>=j&&randomZahl<j+stu.getChance()) {
					ArrayList<Schematic> schems = Var.getSchemsByStufe(stu);
					if(schems.size()>0) {
						int i  = random.nextInt(schems.size());
						toPaste = schems.get(i);
					}
					break;
				}
				j+=stu.getChance();
			}
			if (toPaste != null) {
				Block b = chunk.getBlock(0, 5, 0);
				Biome biom = b.getBiome();
				if (biom == Biome.COLD_OCEAN || biom == Biome.OCEAN || biom == Biome.DEEP_COLD_OCEAN
						|| biom == Biome.DEEP_FROZEN_OCEAN || biom == Biome.DEEP_LUKEWARM_OCEAN
						|| biom == Biome.DEEP_OCEAN || biom == Biome.DEEP_WARM_OCEAN || biom == Biome.FROZEN_OCEAN
						|| biom == Biome.LUKEWARM_OCEAN || biom == Biome.WARM_OCEAN) {
					return;
				}
				boolean foundLand = false;
				Block bb = e.getChunk().getBlock(0, e.getWorld().getHighestBlockYAt(b.getX(), b.getZ()), 0);
				int trys = 0;
				while (!foundLand) {
					if (trys >= 60)
						break;
					if (bb.getType() != Material.AIR && bb.getType() != Material.OAK_LEAVES
							&& bb.getType() != Material.JUNGLE_LEAVES && bb.getType() != Material.ACACIA_LEAVES
							&& bb.getType() != Material.DARK_OAK_LEAVES && bb.getType() != Material.BIRCH_LEAVES
							&& bb.getType() != Material.SPRUCE_LEAVES && bb.getType() != Material.OAK_LOG
							&& bb.getType() != Material.JUNGLE_LOG && bb.getType() != Material.ACACIA_LOG
							&& bb.getType() != Material.DARK_OAK_LOG && bb.getType() != Material.BIRCH_LOG
							&& bb.getType() != Material.SPRUCE_LOG) {
						foundLand = true;
					} else {
						bb = bb.getLocation().subtract(0, 1, 0).getBlock();
					}
					trys++;
				}
				double x = bb.getX();
				double z = bb.getZ();
				double y = bb.getY();
				Location loc = new Location(chunk.getWorld(), x, y, z);

				pasteSchem(toPaste.getName(), loc);
			}
		}

	}

	@SuppressWarnings("resource")
	private void pasteSchem(String name, Location loc) {
		File file = new File(Main.getPlugin().getDataFolder().getParent() + "/WorldEdit/schematics/" + name + ".schem");
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		Clipboard clipboard = null;
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
			try {
				clipboard = reader.read();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		com.sk89q.worldedit.world.World w = BukkitAdapter.adapt(loc.getWorld());
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(w, -1)) {
			ClipboardHolder holder = new ClipboardHolder(clipboard);
			Operation operation = holder.createPaste(editSession)
					.to(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ())).ignoreAirBlocks(false).build();
			try {
				Operations.complete(operation);
			} catch (WorldEditException ex) {
				ex.printStackTrace();
			}
			Region weregion = clipboard.getRegion();
			BlockVector3 clipBoardOffset = clipboard.getRegion().getMinimumPoint().subtract(clipboard.getOrigin());
			Vector3 realTo = Vector3.at(loc.getX(), loc.getY(), loc.getZ())
					.add(holder.getTransform().apply(clipBoardOffset.toVector3()));
			Vector3 max = realTo.add(holder.getTransform()
					.apply(weregion.getMaximumPoint().subtract(weregion.getMinimumPoint()).toVector3()));
			ProtectedRegion region = new ProtectedCuboidRegion(name + "-" + id, realTo.toBlockPoint(),
					max.toBlockPoint());
			Map<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();
			flags.put(Flags.BUILD, StateFlag.State.DENY);
			flags.put(Flags.BLOCK_BREAK, StateFlag.State.DENY);
			flags.put(Flags.BLOCK_PLACE, StateFlag.State.DENY);
			flags.put(Flags.CREEPER_EXPLOSION, StateFlag.State.DENY);
			region.setFlags(flags);
			RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
			RegionManager regionman = container.get(w);
			regionman.addRegion(region);
			regions.add(region);
			id++;
		}
		System.out.println("Pasted Schem at " + loc.toString());

	}

}
