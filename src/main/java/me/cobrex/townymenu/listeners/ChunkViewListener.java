package me.cobrex.townymenu.listeners;

import lombok.Getter;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Settings;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


public class ChunkViewListener implements Listener {

	@Getter
	private static final ChunkViewListener instance = new ChunkViewListener();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
//		System.out.println("Joined player: " + event.getPlayer().getName());
	}

	@EventHandler
	public void onToggle(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();

		if (!TownyMenuPlugin.viewers.contains(player)) return;

		int index = TownyMenuPlugin.viewers.indexOf(player);
		Location previousLoc = TownyMenuPlugin.viewerslocs.get(index);
		Chunk chunk = previousLoc.getChunk();

		if (!player.getLocation().getBlock().equals(previousLoc.getBlock())) {
			Material chunkViewMaterial = Settings.getChunkViewMaterial();

			for (int y = 0; y < 127; y++) {
				for (int i = 0; i < 15; i++) {
					Location corner1 = chunk.getBlock(i, y, 0).getLocation();
					Location corner2 = chunk.getBlock(15, y, i).getLocation();
					Location corner3 = chunk.getBlock(15 - i, y, 15).getLocation();
					Location corner4 = chunk.getBlock(0, y, 15 - i).getLocation();


					player.sendBlockChange(corner1, Material.AIR.createBlockData());
					player.sendBlockChange(corner2, Material.AIR.createBlockData());
					player.sendBlockChange(corner3, Material.AIR.createBlockData());
					player.sendBlockChange(corner4, Material.AIR.createBlockData());

					Location below1 = corner1.clone().add(0, -1, 0);
					player.sendBlockChange(below1, below1.getBlock().getBlockData());
					Location below2 = corner2.clone().add(0, -1, 0);
					player.sendBlockChange(below2, below2.getBlock().getBlockData());
					Location below3 = corner3.clone().add(0, -1, 0);
					player.sendBlockChange(below3, below3.getBlock().getBlockData());
					Location below4 = corner4.clone().add(0, -1, 0);
					player.sendBlockChange(below4, below4.getBlock().getBlockData());
				}
			}

			TownyMenuPlugin.viewers.remove(index);
			TownyMenuPlugin.viewerslocs.remove(index);
		}
	}
}
