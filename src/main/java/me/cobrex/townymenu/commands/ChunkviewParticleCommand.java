package me.cobrex.townymenu.commands;

import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.config.CommentedConfiguration;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkviewParticleCommand implements CommandExecutor {

	private static CommentedConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to send this command!");

			return true;
		}

		Player player = (Player) sender;

		if (!sender.hasPermission("chunkviewparticle.view")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return true;
		}

		String particleName = Settings.CHUNK_VIEW_PARTICLE;
//		Bukkit.getLogger().info("[DEBUG] Particle from Settings.CHUNK_VIEW_PARTICLE: " + particleName);

		Particle particle;
		try {
			particle = Particle.valueOf(particleName);
		} catch (IllegalArgumentException e) {
			player.sendMessage(ChatColor.RED + "Invalid particle type configured: " + particleName);
			return true;
		}

			new BukkitRunnable() {
				int count = 0;

				@Override
				public void run() {
					if (count++ >= 5) {
						cancel();
						return;
					}

					TownyMenuPlugin.viewers.add(player);
					TownyMenuPlugin.viewerslocs.add(player.getLocation());

					Chunk chunk = player.getLocation().getChunk();
					int baseY = player.getLocation().getBlockY();

					for (int y = baseY; y < baseY + 10; y++) {
						for (int i = 0; i <= 15; i++) {
							Location corner1 = chunk.getBlock(i, y, 0).getLocation().add(1, 0, 0);
							Location corner2 = chunk.getBlock(15, y, i).getLocation().add(1, 0, 1);
							Location corner3 = chunk.getBlock(15 - i, y, 15).getLocation().add(0, 0, 1);
							Location corner4 = chunk.getBlock(0, y, 15 - i).getLocation();

							if (corner1.getBlock().getType() == Material.AIR) player.spawnParticle(particle, corner1, 1);
							if (corner2.getBlock().getType() == Material.AIR) player.spawnParticle(particle, corner2, 1);
							if (corner3.getBlock().getType() == Material.AIR) player.spawnParticle(particle, corner3, 1);
							if (corner4.getBlock().getType() == Material.AIR) player.spawnParticle(particle, corner4, 1);
						}
					}
				}
			}.runTaskTimer(TownyMenuPlugin.instance, 0L, 60L);

		MessageUtils.send(player, Localization.ChunkView.PARTICLES);
		return true;
	}
}
