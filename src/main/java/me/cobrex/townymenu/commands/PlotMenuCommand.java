package me.cobrex.townymenu.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.plot.PlotMenu;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class PlotMenuCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("This command can only be run by a player.");
			return true;
		}

		Resident resident = TownyAPI.getInstance().getResident(player.getName());
		if (resident == null) {
			player.sendMessage(Localization.Error.NO_TOWN);
			return true;
		}

		TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
		if (townBlock == null) {
			player.sendMessage(Localization.Error.NO_TOWN);
			return true;
		}

		try {
			Town town = townBlock.getTown();
			boolean isMayor = town.getMayor().equals(resident);
			boolean isPlotOwner = townBlock.hasResident() && townBlock.isOwner(resident);
			boolean hasAdminPermission = player.hasPermission("townymenu.admin.plot");

			if (isMayor || hasAdminPermission || isPlotOwner) {
				MenuManager.openMenu(player, new PlotMenu(player, townBlock));
			} else {
				player.sendMessage(Localization.Error.MUST_BE_IN_OWN_PLOT);
			}
		} catch (Exception e) {
			player.sendMessage("An error occurred while opening the plot menu.");
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Collections.emptyList();
	}
}
