package me.cobrex.townymenu.utils;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class HeadDatabaseUtil {

	public static class HeadDataUtil {

		public static ItemStack getHead(String ID) {
			HeadDatabaseAPI api = new HeadDatabaseAPI();
			ItemStack material;
			try {
				material = api.getItemHead(ID);
			} catch (NullPointerException nullpointer) {
				material = new ItemStack(Objects.requireNonNull(Material.getMaterial("BARRIER")));
				return material;
			}
			return material;
		}

		public static ItemStack createItem(String material) {

			try {
				if (material.contains("hdb-"))
					if (hasHDB())
						return HeadDataUtil.getHead(material.replace("hdb-", ""));
				return new ItemStack(Objects.requireNonNull(Material.matchMaterial(material)));
			} catch (Exception e) {
				return new ItemStack(Objects.requireNonNull(Material.matchMaterial("BARRIER")));
			}
		}

		public static boolean hasHDB() {
			return Bukkit.getServer().getPluginManager().getPlugin("HeadDatabase") != null;
		}
	}
}
