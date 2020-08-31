package net.tolmikarc.townymenu.town.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.town.TownMenu;
import org.mineacademy.fo.command.SimpleCommand;

public class TownMenuCommand extends SimpleCommand {
	public TownMenuCommand() {
		super("townmenu|tm");
		setPermission(null);

	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		checkConsole();

		if (TownyAPI.getInstance().isWarTime()) {
			tell("&cTown menu not allowed during war time.");
			return;
		}

		Town town;
		Resident resident = TownyAPI.getInstance().getDataSource().getResident(getPlayer().getName());
		if (resident.hasTown()) {
			town = resident.getTown();
			if (town.getMayor().equals(resident))
				new TownMenu(town, getPlayer()).displayTo(getPlayer());
			else if (getPlayer().hasPermission("townymenu.town.use")) {
				new TownMenu(town, getPlayer()).displayTo(getPlayer());
			} else
				tell("&cYou are not the mayor nor a high enough rank to open the Town Menu.");
		} else {
			tell("&cYou do not have a town.");
		}


	}

}
