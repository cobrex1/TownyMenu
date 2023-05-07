package me.cobrex.townymenu.plot.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import me.cobrex.townymenu.plot.PlotMenu;
import me.cobrex.townymenu.settings.Localization;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class PlotMenuCommand extends SimpleCommand {
	public PlotMenuCommand() {
		super("plotmenu|plm");
		setPermission(null);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

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
