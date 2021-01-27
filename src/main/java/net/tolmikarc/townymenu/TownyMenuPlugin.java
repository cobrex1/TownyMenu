package net.tolmikarc.townymenu;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import net.tolmikarc.townymenu.plot.command.PlotMenuCommand;
import net.tolmikarc.townymenu.settings.Localization;
import net.tolmikarc.townymenu.settings.Settings;
import net.tolmikarc.townymenu.town.command.TownMenuCommand;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Arrays;
import java.util.List;

public class TownyMenuPlugin extends SimplePlugin {

	@Override
	protected void onPluginStart() {

		Common.log("Enabling Towny Menu by Tolmikarc");
		Common.log("for TownyAdvanced");


		Common.ADD_TELL_PREFIX = true;

		registerCommand(new TownMenuCommand());
		registerCommand(new PlotMenuCommand());


		TownyEconomyHandler.initialize(Towny.getPlugin());
	}

	@Override
	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(Settings.class, Localization.class);
	}
}
