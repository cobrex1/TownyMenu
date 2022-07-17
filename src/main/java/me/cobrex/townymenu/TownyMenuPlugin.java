package me.cobrex.townymenu;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.command.TownMenuCommand;
import me.cobrex.townymenu.plot.command.PlotMenuCommand;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.Arrays;
import java.util.List;

public class TownyMenuPlugin extends SimplePlugin {

	@Override
	protected void onPluginStart() {

		Common.log("Enabling Towny Menu maintained by Cobrex");
		Common.log("for TownyAdvanced");

		Common.setTellPrefix("");

		registerCommand(new TownMenuCommand());
		registerCommand(new PlotMenuCommand());


		TownyEconomyHandler.initialize(Towny.getPlugin());
	}

//	@Override

	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(Settings.class, Localization.class);
	}

}
