package me.cobrex.townymenu;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import me.cobrex.townymenu.bstats.Metrics;
import me.cobrex.townymenu.chunkview.ChunkviewParticleCommand;
import me.cobrex.townymenu.nation.NationMenuCommand;
import me.cobrex.townymenu.plot.command.PlotMenuCommand;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.town.command.TownMenuCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.model.HookManager;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TownyMenuPlugin extends SimplePlugin {

	public static ArrayList<Player> viewers = new ArrayList<>();

	public static ArrayList<Location> viewerslocs = new ArrayList<>();
	public static Plugin instance;

	@Override
	protected void onPluginStart() {
	}

	@Override
	protected void onReloadablesStart() {
		Valid.checkBoolean(HookManager.isPlaceholderAPILoaded(), "You need to install PlaceholderAPI to the server, if you want to support placeholders.");
		
		instance = this;
		Common.log("Enabling Towny Menu maintained by Cobrex");
		Common.log("for Towny");

		Common.setTellPrefix("");

		registerCommand(new TownMenuCommand());
		registerCommand(new PlotMenuCommand());
		registerCommand(new NationMenuCommand());
		registerCommand(new ChunkviewParticleCommand());

		TownyEconomyHandler.initialize(Towny.getPlugin());

		int pluginId = 16084; // <-- Replace with the id of your plugin!
		Metrics metrics = new Metrics(this, pluginId);

		// Optional: Add custom charts
		metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

		if (hasHDB()) getServer().getConsoleSender().
				sendMessage(ChatColor.DARK_GREEN + "[TownyMenu] " + ChatColor.WHITE + "HeadDatabase Hooked!");

		if (hasTowny()) getServer().getConsoleSender().
				sendMessage(ChatColor.DARK_GREEN + "[TownyMenu] " + ChatColor.WHITE + "Towny Hooked!");

		if (hasPlaceholderAPI()) getServer().getConsoleSender().
				sendMessage(ChatColor.DARK_GREEN + "[TownyMenu] " + ChatColor.WHITE + "PlaceholderAPI Hooked!");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return super.onTabComplete(sender, command, alias, args);
	}

	public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(Settings.class, Localization.class);
	}

	public boolean hasHDB() {
		return Bukkit.getServer().getPluginManager().getPlugin("HeadDatabase") != null;
	}

	public boolean hasTowny() {
		return Bukkit.getServer().getPluginManager().getPlugin("Towny") != null;
	}

	public boolean hasPlaceholderAPI() {
		return Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
	}
}
