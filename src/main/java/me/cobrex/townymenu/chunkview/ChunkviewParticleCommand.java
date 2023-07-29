package me.cobrex.townymenu.chunkview;

import lombok.SneakyThrows;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Settings;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.command.SimpleCommand;

public class ChunkviewParticleCommand extends SimpleCommand {

	public ChunkviewParticleCommand() {
		super("chunkviewparticle|chunkvp|cvp");
		setDescription("View the boarder of the chunk with particles");
		setPermission(null);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		Player player = getPlayer();
		checkPerm("chunkviewparticle.view");

		new BukkitRunnable() {
			int count = 0;

			@Override
			public void run() {
				if (count >= 5) {
					cancel();
				}
				count++;

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
				for (i = player.getLocation().getBlockY(); i < (player.getLocation().getBlockY() + 10); i++) {
					int i2 = 0;
					for (i2 = 0; i2 < 15; i2++) {
						corner1 = chunk.getBlock(i2, i, 0).getLocation().add(1, 0, 0);
						corner2 = chunk.getBlock(15, i, i2).getLocation().add(1, 0, 1);
						corner3 = chunk.getBlock(15 - i2, i, 15).getLocation().add(0, 0, 1);
						corner4 = chunk.getBlock(0, i, 15 - i2).getLocation();
						if (corner1.getBlock().getType() == Material.AIR)
							player.spawnParticle(Particle.valueOf(String.valueOf(Settings.CHUNK_VIEW_PARTICLE)), corner1, 1);
						if (corner2.getBlock().getType() == Material.AIR)
							player.spawnParticle(Particle.valueOf(String.valueOf(Settings.CHUNK_VIEW_PARTICLE)), corner2, 1);
						if (corner3.getBlock().getType() == Material.AIR)
							player.spawnParticle(Particle.valueOf(String.valueOf(Settings.CHUNK_VIEW_PARTICLE)), corner3, 1);
						if (corner4.getBlock().getType() == Material.AIR)
							player.spawnParticle(Particle.valueOf(String.valueOf(Settings.CHUNK_VIEW_PARTICLE)), corner4, 1);
					}

				}
			}
		}.runTaskTimer(TownyMenuPlugin.instance, 0L, 60l);
	}

}
