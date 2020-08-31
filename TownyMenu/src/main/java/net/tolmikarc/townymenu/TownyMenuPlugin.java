package net.tolmikarc.townymenu;

import net.tolmikarc.townymenu.plot.command.PlotMenuCommand;
import net.tolmikarc.townymenu.town.command.TownMenuCommand;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;

public class TownyMenuPlugin extends SimplePlugin {

	@Override
	protected void onPluginStart() {
		Common.log("Enabling Towny Menu by Tolmikarc");


		registerCommand(new TownMenuCommand());
		registerCommand(new PlotMenuCommand());
	}
}
