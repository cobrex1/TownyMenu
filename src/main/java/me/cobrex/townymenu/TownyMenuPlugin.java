package me.cobrex.townymenu;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import me.cobrex.townymenu.bstats.Metrics;
import me.cobrex.townymenu.commands.ChunkviewCommand;
import me.cobrex.townymenu.commands.ChunkviewParticleCommand;
import me.cobrex.townymenu.config.ConfigUtil;
import me.cobrex.townymenu.listeners.ChunkViewListener;
import me.cobrex.townymenu.listeners.MenuListener;
import me.cobrex.townymenu.commands.NationMenuCommand;
import me.cobrex.townymenu.commands.PlotMenuCommand;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.commands.TownMenuCommand;
import me.cobrex.townymenu.utils.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TownyMenuPlugin extends JavaPlugin {

	public static TownyMenuPlugin instance;

	public static final List<Player> viewers = new ArrayList<>();
	public static final List<Location> viewerslocs = new ArrayList<>();

	@Override

	public void onEnable() {
		instance = this;

		try {
			ConfigUtil.loadConfig(this);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load settings.yml", e);
		}

		Settings.init(this);
		Localization.load(this);
		Localization.init();
		Bukkit.getLogger().info("Settings.COLOR_MODE = " + Settings.COLOR_MODE);

		getCommand("townmenu").setExecutor(new TownMenuCommand());
		getCommand("townmenu").setTabCompleter(new TownMenuCommand());
		getCommand("plotmenu").setExecutor(new PlotMenuCommand());
		getCommand("nationmenu").setExecutor(new NationMenuCommand(this));
		getCommand("chunkview").setExecutor(new ChunkviewCommand());
		getCommand("chunkviewparticle").setExecutor(new ChunkviewParticleCommand());

		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getServer().getPluginManager().registerEvents(new ChunkViewListener(), this);


		TownyEconomyHandler.initialize(Towny.getPlugin());

		Metrics metrics = new Metrics(this, 16084);

		metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

		if (hasHDB())
			logHooked("HeadDatabase");

		if (hasTowny())
			logHooked("Towny");

		if (hasPlaceholderAPI()) {
			logHooked("PlaceholderAPI");
			new PlaceholderHook().register();
		}

		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TownyMenu]" + ChatColor.WHITE + "Economy Setting: " + Settings.ECONOMY_ENABLED);
		getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "TownyMenu by Cobrex has been enabled.");
	}

	public void reload() throws IOException {
		ConfigUtil.loadConfig(this);
		Settings.init(this);
		Localization.load(this);

		getLogger().info("TownyMenu configuration reloaded.");
	}

	@Override
	public void onDisable() {
		if (ConfigUtil.getConfig() != null) {
			ConfigUtil.getConfig().save();
		}
		Localization.save();

		Bukkit.getScheduler().cancelTasks(this);

		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TownyMenu] " + ChatColor.WHITE + "Plugin has been disabled");
	}

	private void logHooked(String pluginName) {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[TownyMenu] " + ChatColor.WHITE + pluginName + " Hooked!");
	}

	public boolean hasHDB() {
		return Bukkit.getPluginManager().isPluginEnabled("HeadDatabase");
	}

	public boolean hasTowny() {
		return Bukkit.getPluginManager().isPluginEnabled("Towny");
	}

	public boolean hasPlaceholderAPI() {
		return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
	}

}

//Original creator and ideas for this plugin: Tolmikarc.