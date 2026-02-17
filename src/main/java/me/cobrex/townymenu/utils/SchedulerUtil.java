package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.TownyMenuPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SchedulerUtil {

	private SchedulerUtil() {}

	public static void run(Player player, Runnable task) {
		if (isFolia()) {
			player.getScheduler().run(
					TownyMenuPlugin.instance,
					scheduledTask -> task.run(),
					null
			);
		} else {
			Bukkit.getScheduler().runTask(
					TownyMenuPlugin.instance,
					task
			);
		}
	}

	public static void runLater(Player player, Runnable task, long ticks) {
		if (isFolia()) {
			player.getScheduler().runDelayed(
					TownyMenuPlugin.instance,
					scheduledTask -> task.run(),
					null,
					ticks
			);
		} else {
			Bukkit.getScheduler().runTaskLater(
					TownyMenuPlugin.instance,
					task,
					ticks
			);
		}
	}

	public static void runTimer(Player player, Runnable task, long delay, long period, int maxRuns) {

		if (isFolia()) {

			final int[] count = {0};

			player.getScheduler().runAtFixedRate(
					TownyMenuPlugin.instance,
					scheduledTask -> {

						if (count[0]++ >= maxRuns) {
							scheduledTask.cancel();
							return;
						}

						task.run();
					},
					null,
					delay,
					period
			);

		} else {

			final int[] count = {0};

			Bukkit.getScheduler().runTaskTimer(
					TownyMenuPlugin.instance,
					scheduledTask -> {

						if (count[0]++ >= maxRuns) {
							scheduledTask.cancel();
							return;
						}

						task.run();
					},
					delay,
					period
			);
		}
	}


	private static boolean isFolia() {
		try {
			Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
			return true;
		} catch (ClassNotFoundException ignored) {
			return false;
		}
	}
}
