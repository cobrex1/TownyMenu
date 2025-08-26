package me.cobrex.townymenu.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.nation.JoinNationMenu;
import me.cobrex.townymenu.nation.NationMainMenu;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class NationMenuCommand implements CommandExecutor, TabCompleter {

	private final TownyMenuPlugin plugin;

	public NationMenuCommand(TownyMenuPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("This command can only be run by a player.");
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (player.hasPermission("townmenu.reload") || player.isOp()) {
				plugin.reloadConfig();
				plugin.getLogger().info("TownMenu configuration reloaded.");
				player.sendMessage(Localization.reloaded(player));
			} else {
				player.sendMessage(Localization.Error.NO_PERMISSION);
			}
			return true;
		}

		Resident resident = TownyAPI.getInstance().getResident(player);
		if (resident == null) {
			player.sendMessage(Localization.Error.NO_TOWN);
			return true;
		}

		if (resident.hasTown()) {
			Town town = resident.getTownOrNull();
			if (town != null && resident.hasNation()) {
				Nation nation = town.getNationOrNull();

				if (nation != null) {
					boolean isKing = nation.getKing().equals(resident);
					boolean hasPermission = player.hasPermission("townymenu.nation.use")
							|| (town.getMayor().equals(resident) && isKing);

					if (hasPermission) {
						try {
							MenuManager.openMenu(player, new NationMainMenu(player));
						} catch (NotRegisteredException e) {
							throw new RuntimeException(e);
						}
					} else {
						player.sendMessage(Localization.Error.NO_PERMISSION);
					}
					return true;
				}
			}
		}

		MenuManager.openMenu(player, new JoinNationMenu(player, resident));
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
												@NotNull Command command,
												@NotNull String alias,
												@NotNull String[] args) {
		if (args.length == 1 && sender.hasPermission("townmenu.reload")) {
			return Collections.singletonList("reload");
		}

		return Collections.emptyList();
	}
}
