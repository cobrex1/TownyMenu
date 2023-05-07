package me.cobrex.townymenu.chunkview;

import lombok.Getter;
import lombok.SneakyThrows;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.Remain;

@AutoRegister
public final class ChunkviewCommand extends SimpleCommand {

	public ChunkviewCommand() {
		super("chunkview|chunkv|cv");
		setDescription("View the boarder of the chunk you are standing in");
		setPermission(null);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		Player player = getPlayer();
		checkPerm("chunkview.view");

		TownyMenuPlugin.viewers.add(player);
		TownyMenuPlugin.viewerslocs.add(player.getLocation());
		Chunk chunk = player.getLocation().getChunk();
		Location corner1;
		chunk.getBlock(0, 0, 0).getLocation();
		Location corner2;
		chunk.getBlock(15, 0, 0).getLocation();
		Location corner3;
		chunk.getBlock(0, 0, 15).getLocation();
		Location corner4;
		chunk.getBlock(15, 0, 15).getLocation();
		int i = player.getLocation().getBlockY();
		int i2 = 0;
		for (i2 = 0; i2 < 15; i2++) {
			corner1 = chunk.getBlock(i2, i, 0).getLocation();
			corner2 = chunk.getBlock(15, i, i2).getLocation();
			corner3 = chunk.getBlock(15 - i2, i, 15).getLocation();
			corner4 = chunk.getBlock(0, i, 15 - i2).getLocation();
			if (corner1.getBlock().getType() == Material.AIR)
				Remain.sendBlockChange(0, player, corner1, CompMaterial.SEA_LANTERN);
			if (corner2.getBlock().getType() == Material.AIR)
				Remain.sendBlockChange(0, player, corner2, CompMaterial.SEA_LANTERN);
			if (corner3.getBlock().getType() == Material.AIR)
				Remain.sendBlockChange(0, player, corner3, CompMaterial.SEA_LANTERN);
			if (corner4.getBlock().getType() == Material.AIR)
				Remain.sendBlockChange(0, player, corner4, CompMaterial.SEA_LANTERN);
		}

		player.sendMessage(ChatColor.GOLD + Localization.ChunkView.TOGGLE_REMOVE);
	}

	@AutoRegister
	public static final class PlayerListener implements Listener {

		@Getter
		private static final PlayerListener instance = new PlayerListener();

		public PlayerListener() {
		}

		@EventHandler
		public void onToggle(PlayerToggleSneakEvent event) {

			final Player player = event.getPlayer();
			if (TownyMenuPlugin.viewers.contains(player)) {
				Location loc = player.getLocation();
				World world = loc.getWorld();
				int index = TownyMenuPlugin.viewers.indexOf(player);
				Location prevloc = TownyMenuPlugin.viewerslocs.get(index);
				Chunk chunk = prevloc.getChunk();
				if (loc.getX() != ((Location) TownyMenuPlugin.viewerslocs.get(index)).getX() || loc.getY() != ((Location) TownyMenuPlugin.viewerslocs.get(index)).getY() || loc.getZ() != ((Location) TownyMenuPlugin.viewerslocs.get(index)).getZ()) {
					Location corner1;
					chunk.getBlock(0, 0, 0).getLocation();
					Location corner2;
					chunk.getBlock(15, 0, 0).getLocation();
					Location corner3;
					chunk.getBlock(0, 0, 15).getLocation();
					Location corner4;
					chunk.getBlock(15, 0, 15).getLocation();
					int i = 0;
					int i2 = 0;
					for (i = 0; i < 127; i++) {
						for (i2 = 0; i2 < 15; i2++) {
							corner1 = chunk.getBlock(i2, i, 0).getLocation();
							corner2 = chunk.getBlock(15, i, i2).getLocation();
							corner3 = chunk.getBlock(15 - i2, i, 15).getLocation();
							corner4 = chunk.getBlock(0, i, 15 - i2).getLocation();
							if (corner1.getBlock().getType() == Material.AIR)
								Remain.sendBlockChange(0, player, corner1, CompMaterial.AIR);
							if (corner2.getBlock().getType() == Material.AIR)
								Remain.sendBlockChange(0, player, corner2, CompMaterial.AIR);
							if (corner3.getBlock().getType() == Material.AIR)
								Remain.sendBlockChange(0, player, corner3, CompMaterial.AIR);
							if (corner4.getBlock().getType() == Material.AIR)
								Remain.sendBlockChange(0, player, corner4, CompMaterial.AIR);
						}
					}

					player.sendMessage(ChatColor.GOLD + Localization.ChunkView.REMOVED);
					TownyMenuPlugin.viewers.remove(player);
					TownyMenuPlugin.viewerslocs.remove(index);
				}
			}
		}
	}
}
