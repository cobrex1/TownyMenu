package net.tolmikarc.townymenu.plot.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.plot.PlotMenu;
import net.tolmikarc.townymenu.settings.Localization;
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
			tell(Localization.Error.WAR_TIME);
			return;
		}

		Resident resident = TownyAPI.getInstance().getResident(getPlayer().getName());
		if (TownyAPI.getInstance().getTownBlock(getPlayer().getLocation()) != null) {
			TownBlock townBlock = TownyAPI.getInstance().getTownBlock(getPlayer().getLocation());
			Town town = townBlock.getTown();
			if (town.getMayor().equals(resident) || getPlayer().hasPermission("townymenu.admin.plot"))
				new PlotMenu(townBlock).displayTo(getPlayer());
			else if (townBlock.hasResident() && townBlock.isOwner(resident)) {
				new PlotMenu(townBlock).displayTo(getPlayer());
			} else
				tell(Localization.Error.MUST_BE_IN_OWN_PLOT);
		} else {
			tell(Localization.Error.NO_TOWN);
		}


	}

}
