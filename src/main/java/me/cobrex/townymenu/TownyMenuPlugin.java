package me.cobrex.townymenu;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import me.cobrex.townymenu.bstats.Metrics;
import me.cobrex.townymenu.plot.command.PlotMenuCommand;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.command.TownMenuCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TownyMenuPlugin extends SimplePlugin {

	public static ArrayList<Player> viewers = new ArrayList<>();

	public static ArrayList<Location> viewerslocs = new ArrayList<>();

	@Override
	protected void onPluginStart() {

		Common.log("Enabling Towny Menu maintained by Cobrex");
		Common.log("for TownyAdvanced");

		Common.setTellPrefix("");

		registerCommand(new TownMenuCommand());
		registerCommand(new PlotMenuCommand());

		TownyEconomyHandler.initialize(Towny.getPlugin());

		int pluginId = 16084; // <-- Replace with the id of your plugin!
		Metrics metrics = new Metrics(this, pluginId);

		// Optional: Add custom charts
		metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

	}

	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(Settings.class, Localization.class);
	}

}
