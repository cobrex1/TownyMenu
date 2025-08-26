package me.cobrex.townymenu.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.town.JoinTownMenu;
import me.cobrex.townymenu.town.TownMenu;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TownMenuCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("This command can only be run by a player.");
			return true;
		}

		Resident resident = TownyAPI.getInstance().getResident(player.getName());
		if (resident == null) {
			player.sendMessage("Could not retrieve your Towny data.");
			return true;
		}

		if (args.length == 0) {
			if (resident.hasTown()) {
				Town town = null;
				try {
					town = resident.getTown();
				} catch (NotRegisteredException e) {
					throw new RuntimeException(e);
				}
				boolean isMayor = town.getMayor().equals(resident);
				boolean hasPermission = player.hasPermission("townymenu.town.use");
				boolean mayorHasNode = isMayor && town.getMayor().hasPermissionNode("townymenu.town.use");

				if (mayorHasNode || hasPermission) {
					try {
						MenuManager.openMenu(player, new TownMenu(player, town));
					} catch (NotRegisteredException e) {
						throw new RuntimeException(e);
					}
				} else {
					player.sendMessage(Localization.Error.NO_PERMISSION);
				}
			} else {
				MenuManager.openMenu(player, new JoinTownMenu(player));
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			if (player.hasPermission("townmenu.reload") || player.isOp()) {
				try {
					TownyMenuPlugin.instance.reload();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				player.sendMessage(Localization.reloaded(player));
			} else {
				player.sendMessage(Localization.Error.NO_PERMISSION);
			}
			return true;
		}

		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			return List.of("reload").stream()
					.filter(sub -> sub.startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
