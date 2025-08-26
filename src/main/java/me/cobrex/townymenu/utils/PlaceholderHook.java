package me.cobrex.townymenu.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderHook extends PlaceholderExpansion {

	public PlaceholderHook() {
	}

	@Override
	public String getIdentifier() {
		return "townymenu";
	}

	@Override
	public String getAuthor() {
		return "cobrex1";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String onPlaceholderRequest(Player p, String params) {
		if (params.equalsIgnoreCase("has_town")) {
			Resident res = TownyAPI.getInstance().getResident(p);
			return res != null && res.hasTown() ? "yes" : "no";
		}
		return null;
	}

	public static String parsePlaceholders(Player player, String input) {
		if (player == null || input == null) return input;

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			return PlaceholderAPI.setPlaceholders(player, input);
		}
		return input;
	}

}
