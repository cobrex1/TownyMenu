package net.tolmikarc.townymenu.plot.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.plot.PlotMenu;
import org.mineacademy.fo.command.SimpleCommand;

public class PlotMenuCommand extends SimpleCommand {
	public PlotMenuCommand() {
		super("plotmenu|plm");
		setPermission(null);

	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		if (TownyAPI.getInstance().isWarTime()) {
			tell("&cPlot menu not allowed during war time.");
			return;
		}

		TownBlock townBlock;
	  Town town;
		Resident resident = TownyAPI.getInstance().getDataSource().getResident(getPlayer().getName());
		if (TownyAPI.getInstance().getTownBlock(getPlayer().getLocation()) != null) {
			townBlock = TownyAPI.getInstance().getTownBlock(getPlayer().getLocation());
			town = townBlock.getTown();
			if (town.getMayor().equals(resident) || getPlayer().hasPermission("townymenu.admin.plot"))
				new PlotMenu(townBlock).displayTo(getPlayer());
			else if (townBlock.hasResident() && townBlock.isOwner(resident)) {
				new PlotMenu(townBlock).displayTo(getPlayer());
			} else
				tell("&cYou do not own this plot.");
		} else {
			tell("&cYou are not in a town.");
		}


	}

}
