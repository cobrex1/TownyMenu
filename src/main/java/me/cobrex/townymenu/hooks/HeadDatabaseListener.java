package me.cobrex.townymenu.hooks;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.cobrex.townymenu.TownyMenuPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class HeadDatabaseListener implements Listener {

	private final TownyMenuPlugin plugin;

	public HeadDatabaseListener(final TownyMenuPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDatabaseLoad(DatabaseLoadEvent event) {
		// Load a new instance of the Head Database if this event fires.
		plugin.hdb = new HeadDatabaseHook();
		plugin.hdb.setHdbApi(new HeadDatabaseAPI());
	}
}
