package me.cobrex.townymenu.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigUtil {

	private static CommentedConfiguration config;
	private static File configFile;

	public static void loadConfig(JavaPlugin plugin) throws IOException {
		File dataFolder = plugin.getDataFolder();
		if (!dataFolder.exists()) dataFolder.mkdirs();

		configFile = new File(dataFolder, "settings.yml");
		config = new CommentedConfiguration(configFile.toPath());
		config.load();

		ConfigMigrator.migrate(config, plugin.getLogger());

		Set<String> parentBases = new HashSet<>();
		for (ConfigNodes n : ConfigNodes.values()) {
			String p = n.getPath();
			int idx = p.lastIndexOf('.');
			if (idx > 0) parentBases.add(p.substring(0, idx));
		}

		for (ConfigNodes node : ConfigNodes.values()) {
			String path = node.getPath();
			Object def  = node.getDefault();

			boolean isParent = parentBases.contains(path);
			boolean isEmptyParent = isParent && (def instanceof String) && ((String) def).isEmpty();

			if (!config.contains(path)) {
				if (isEmptyParent) {
					continue;
				}
				config.set(path, def);
			}
		}

		for (ConfigNodes node : ConfigNodes.values()) {
			String[] commentsArr = node.getComments();
			if (commentsArr == null) continue;

			List<String> filtered = new ArrayList<>();
			for (String s : commentsArr) {
				if (s != null && !s.trim().isEmpty()) {
					filtered.add(s);
				}
			}
			if (!filtered.isEmpty()) {
				config.setComments(node.getPath(), filtered);
			}
		}

		try {
			config.save();
		} catch (Exception e) {
			plugin.getLogger().severe("Failed to save settings.yml: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getString(ConfigNodes node) {
		Object value = config.get(node.getRoot());
		return value != null ? String.valueOf(value) : String.valueOf(node.getDefault());
	}

	public static int getInt(ConfigNodes node) {
		Object value = config.get(node.getRoot());
		if (value instanceof Number) return ((Number) value).intValue();
		try {
			return Integer.parseInt(String.valueOf(value));
		} catch (NumberFormatException e) {
			return Integer.parseInt(String.valueOf(node.getDefault()));
		}
	}

	public static boolean getBoolean(ConfigNodes node) {
		Object value = config.get(node.getRoot());
		return Boolean.parseBoolean(String.valueOf(value != null ? value : node.getDefault()));
	}

	public static double getDouble(ConfigNodes node) {
		Object value = config.get(node.getRoot());
		if (value instanceof Number) return ((Number) value).doubleValue();
		try {
			return Double.parseDouble(String.valueOf(value));
		} catch (NumberFormatException e) {
			return Double.parseDouble(String.valueOf(node.getDefault()));
		}
	}

	public static CommentedConfiguration getConfig() {
		return config;
	}
}

