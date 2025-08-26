package me.cobrex.townymenu.config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigMigrator {

	public static void migrate(CommentedConfiguration config, Logger log) {
		Map<String, Object> migratedValues = new LinkedHashMap<>();

		for (String key : config.getKeys(true)) {
			if (config.isConfigurationSection(key)) continue;

			Object raw = config.get(key);
			Object value = coerceScalar(raw);
			String normalized = normalizePath(key);
			String newPath = mapOldToNew(normalized);

			migratedValues.putIfAbsent(newPath, value);
		}

		for (String rootKey : new HashSet<>(config.getKeys(false))) {
			config.set(rootKey, null);
		}

		for (ConfigNodes node : ConfigNodes.values()) {
			String path = node.getPath().toLowerCase(Locale.ROOT);

			Object value = migratedValues.get(path);
			if (value == null) value = node.getDefault();

			config.set(path, value);
		}

		log.info("[ConfigMigrator] Migrated settings.yml: normalized keys, preserved user values, filled defaults.");
	}

	private static Object coerceScalar(Object raw) {
		if (!(raw instanceof String s)) return raw;

		String t = s.trim();

		if (t.equalsIgnoreCase("true")) return true;
		if (t.equalsIgnoreCase("false")) return false;
		if (t.matches("^-?\\d+$")) {
			try { return Integer.parseInt(t); } catch (NumberFormatException ignored) {}
		}

		if (t.matches("^-?\\d+\\.\\d+$")) {
			try { return Double.parseDouble(t); } catch (NumberFormatException ignored) {}
		}
		return s;
	}

	private static String normalizePath(String path) {
		String[] segs = path.split("\\.");
		for (int i = 0; i < segs.length; i++) {
			segs[i] = segs[i].trim().replace(' ', '_').toLowerCase(Locale.ROOT);
		}
		return String.join(".", segs);
	}

	private static String mapOldToNew(String p) {

		if (p.equals("prefix")) return "prefix";
		if (p.equals("locale")) return "locale";
		if (p.equals("economy")) return "economy";
		if (p.equals("money_symbol")) return "money_symbol";
		if (p.equals("log_lag_over_milis")) return "log_lag_over_mills";
		if (p.equals("chunk_view")) return "chunk_view";
		if (p.equals("chunk_view_particle")) return "chunk_view_partilce";

		if (p.equals("find_town")) return "find_town_button.item";
		if (p.equals("create_town")) return "create_town_button.item";

		if (looksNewAlready(p)) return p;

		String base = p;
		String child = null;
		int dot = p.lastIndexOf('.');
		if (dot > 0) {
			base  = p.substring(0, dot);
			child = p.substring(dot + 1);
		}

		String leaf = (child == null || child.isEmpty()) ? "item" : child;

		Map<String,String> townMain = Map.ofEntries(
				Map.entry("toggle_menu", "town_toggle_button"),
				Map.entry("resident_list", "town_resident_list_button"),
				Map.entry("permissions_menu", "town_permissions_menu_button"),
				Map.entry("economy_menu", "town_economy_menu_button"),
				Map.entry("plot_menu", "town_plot_menu_button"),
				Map.entry("settings_menu", "town_settings_menu_button"),
				Map.entry("invite_menu", "town_invite_menu_button"),
				Map.entry("extra_info", "town_extra_info_button"),
				Map.entry("town_info_icon", "town_info_button")
		);
		if (townMain.containsKey(base)) {
			return townMain.get(base) + "." + leaf;
		}

		if (base.startsWith("filler_")) {
			return base + "." + leaf;
		}

		if (base.equals("back_button")) {
			return "back_button." + leaf;
		}

		if (base.startsWith("toggle_")) {
			return "town_" + base + "_button." + leaf;
		}

		if (base.startsWith("resident_")) {
			if (base.equals("resident_mayor")) return "resident_set_mayor_button." + leaf;
			return base + "_button." + leaf;
		}

		if (base.equals("build") || base.equals("break") || base.equals("item_use") || base.equals("switch")) {
			return base + "_info." + leaf;
		}

		if (base.matches("^(resident|nation|ally|outsider)_(build|break|item_use|switch)$")) {
			return base + "_button." + leaf;
		}

		if (base.equals("reset_all")) return "reset_all_button." + leaf;
		if (base.equals("all_on")) return "all_on_button." + leaf;
		if (base.equals("reset_to_town_perm")) return "reset_to_town_perms_button." + leaf;

		if (base.equals("town_balance")) return "town_balance_button." + leaf;
		if (base.equals("deposit")) return "town_deposit_button." + leaf;
		if (base.equals("withdraw")) return "town_withdraw_button." + leaf;
		if (base.equals("set_tax")) return "town_set_tax_button." + leaf;

		if (base.equals("set_town_spawn"))  return "town_set_spawn_button." + leaf;
		if (base.equals("set_home_block"))  return "town_set_home_block_button." + leaf;
		if (base.equals("set_town_board"))  return "town_set_board_button." + leaf;
		if (base.equals("set_town_name"))   return "town_set_name_button." + leaf;

		if (base.equals("town_claim_info")) return "town_claim_info." + leaf;
		if (base.equals("extra_commands")) return "town_extra_commands_info." + leaf;

		if (base.startsWith("set_nation_")) {
			return "nation_" + base.substring("set_".length()) + "_button." + leaf;
		}

		if (base.startsWith("nation_") && !base.contains("_button")) {
			return base + "_button." + leaf;
		}

		if (base.matches("^plot_(toggle|permissions|admin|friend)_menu$")) {
			return base + "_button." + leaf;
		}

		if (base.startsWith("plot_toggle_")) {
			return base + "_button." + leaf;
		}

		if (base.matches("^plot_(build|break|item_use|switch)$")) {
			return base + "_button." + leaf;
		}

		if (base.matches("^plot_(resident|nation|ally|outsider)_(build|break|item_use|switch)$")) {
			return base + "_button." + leaf;
		}

		if (base.matches("^plot_admin_(for_sale|not_for_sale|set_plot_type|evict_res)$")) {
			return base + "_button." + leaf;
		}

		if (child == null) return base + ".item";
		return p;
	}

	private static boolean looksNewAlready(String p) {

		if (p.startsWith("find_town_button") || p.startsWith("create_town_button")) return true;
		if (p.startsWith("find_nation_button") || p.startsWith("create_nation_button")) return true;
		if (p.contains("_button.")) return true;
		if (p.endsWith("_menu_size")) return true;
		if (p.startsWith("filler_")) return true;
		if (p.equals("back_button.item") || p.equals("back_button.custommodeldata")) return true;
		if (p.equals("chunk_view") || p.equals("chunk_view_partilce")) return true;
		if (p.equals("money_symbol") || p.equals("economy") || p.equals("locale") || p.equals("prefix")) return true;
		return false;
	}
}
