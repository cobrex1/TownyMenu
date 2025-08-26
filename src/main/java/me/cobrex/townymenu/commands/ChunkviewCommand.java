package me.cobrex.townymenu.commands;

import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkviewCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to send this command!");
			return true;
		}

		Player player = (Player) sender;

		if (!sender.hasPermission("chunkview.view")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return true;
		}

		Material blockMaterial = Settings.getChunkViewMaterial();
//		Bukkit.getLogger().info("[DEBUG] ChunkView Material = " + blockMaterial.name());

		if (blockMaterial == null || !blockMaterial.isBlock()) {
			MessageUtils.send(player, "<red>Invalid block type configured for CHUNK_VIEW.");
			return true;
		}

		TownyMenuPlugin.viewers.add(player);
		TownyMenuPlugin.viewerslocs.add(player.getLocation());

		Chunk chunk = player.getLocation().getChunk();
		int baseY = player.getLocation().getBlockY();

		for (int i = 0; i < 15; i++) {
			Location loc1 = chunk.getBlock(i, baseY, 0).getLocation();
			Location loc2 = chunk.getBlock(15, baseY, i).getLocation();
			Location loc3 = chunk.getBlock(15 - i, baseY, 15).getLocation();
			Location loc4 = chunk.getBlock(0, baseY, 15 - i).getLocation();

			if (loc1.getBlock().getType() == Material.AIR)
				player.sendBlockChange(loc1, blockMaterial.createBlockData());
			if (loc2.getBlock().getType() == Material.AIR)
				player.sendBlockChange(loc2, blockMaterial.createBlockData());
			if (loc3.getBlock().getType() == Material.AIR)
				player.sendBlockChange(loc3, blockMaterial.createBlockData());
			if (loc4.getBlock().getType() == Material.AIR)
				player.sendBlockChange(loc4, blockMaterial.createBlockData());
		}

		MessageUtils.send(player, Localization.ChunkView.TOGGLE_REMOVE);
		return true;
	}
}
