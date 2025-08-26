package me.cobrex.townymenu.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cobrex.townymenu.settings.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MessageFormatter {

	private static final Pattern HEX_PATTERN_AMP = Pattern.compile("&#([A-Fa-f0-9]{6})");
	private static final Pattern HEX_PATTERN_HASH = Pattern.compile("#([A-Fa-f0-9]{6})");

	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
	private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

	public static Settings.ColorMode COLOR_MODE = Settings.COLOR_MODE;

	public static Component formatComponent(String input) {
		return formatComponent(input, null);
	}

	public static Component formatComponent(String input, Player player) {
		if (input == null || input.isBlank())
			return Component.empty();

		if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			input = PlaceholderAPI.setPlaceholders(player, input);
		} else if (player == null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			input = PlaceholderAPI.setPlaceholders(null, input);
		}

		if (!containsColorCode(input) && !input.contains("<") && !input.contains(">")) {
			input = "<gray>" + input;
		}

		if (!input.contains("<#")) {
			input = HEX_PATTERN_AMP.matcher(input).replaceAll(match -> "<#" + match.group(1) + ">");
			input = HEX_PATTERN_HASH.matcher(input).replaceAll(match -> "<#" + match.group(1) + ">");
		}

		try {
			Component comp;
			if (COLOR_MODE == Settings.ColorMode.MINIMESSAGE) {
				String mmInput = legacyToMiniMessage(input);
				comp = MINI_MESSAGE.deserialize(mmInput);
			} else {
				String legacy = org.bukkit.ChatColor.translateAlternateColorCodes('&', input);
				comp = LEGACY_SERIALIZER.deserialize(legacy);
			}
			comp = comp.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
			return comp;

		} catch (Exception ex) {
			Bukkit.getLogger().warning("[MessageFormatter] Failed to parse MiniMessage: " + input);
			ex.printStackTrace();
			String legacy = org.bukkit.ChatColor.translateAlternateColorCodes('&', input);
			Component comp = LEGACY_SERIALIZER.deserialize(legacy);
			return comp.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
		}
	}

	public static String format(String input, Player player) {
		if (input == null || input.isBlank()) return "";

		if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			input = PlaceholderAPI.setPlaceholders(player, input);
		} else if (player == null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			input = PlaceholderAPI.setPlaceholders(null, input);
		}

		if (!containsColorCode(input) && !input.contains("<") && !input.contains(">")) {
			input = COLOR_MODE == Settings.ColorMode.MINIMESSAGE ? "<gray>" + input : "&7" + input;
		}

		input = HEX_PATTERN_AMP.matcher(input).replaceAll(match -> "<#" + match.group(1) + ">");
		input = HEX_PATTERN_HASH.matcher(input).replaceAll(match -> "<#" + match.group(1) + ">");

		if (COLOR_MODE == Settings.ColorMode.MINIMESSAGE) {
			try {
				String mmInput = legacyToMiniMessage(input);

				Component comp = MINI_MESSAGE.deserialize(mmInput);

				return LegacyComponentSerializer.legacySection().serialize(comp);
			} catch (Exception e) {
				Bukkit.getLogger().warning("[MessageFormatter] Failed to parse MiniMessage in format(): " + input);
				e.printStackTrace();

				return org.bukkit.ChatColor.translateAlternateColorCodes('&', input);
			}
		}

		String hexProcessed = input.replace('&', '§');
		return hexProcessed;
	}

	public static List<String> formatList(List<String> inputList, Player player) {
		if (inputList == null) return List.of();
		return inputList.stream().map(s -> format(s, player)).collect(Collectors.toList());
	}

	public static List<String> formatList(List<String> inputList) {
		return formatList(inputList, null);
	}

	private static boolean containsColorCode(String input) {
		return input.contains("&") || input.contains("§") || input.contains("&#") || input.contains("#");
	}

	private static String legacyToMiniMessage(String input) {
		if (input == null) return "";

		Map<Character, String> codeMap = Map.ofEntries(
				Map.entry('0', "black"),
				Map.entry('1', "dark_blue"),
				Map.entry('2', "dark_green"),
				Map.entry('3', "dark_aqua"),
				Map.entry('4', "dark_red"),
				Map.entry('5', "dark_purple"),
				Map.entry('6', "gold"),
				Map.entry('7', "gray"),
				Map.entry('8', "dark_gray"),
				Map.entry('9', "blue"),
				Map.entry('a', "green"),
				Map.entry('b', "aqua"),
				Map.entry('c', "red"),
				Map.entry('d', "light_purple"),
				Map.entry('e', "yellow"),
				Map.entry('f', "white"),
				Map.entry('r', "/reset"),
				Map.entry('l', "bold"),
				Map.entry('o', "italic"),
				Map.entry('n', "underline"),
				Map.entry('m', "strikethrough")
		);

		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if ((c == '&' || c == '§') && i + 1 < chars.length) {
				char code = Character.toLowerCase(chars[i + 1]);
				i++;
				String tag = codeMap.get(code);
				if (tag != null) {
					if (tag.equals("/reset")) {
						builder.append("</reset>");
					} else {
						builder.append('<').append(tag).append('>');
					}
				} else {
					builder.append(c).append(code);
				}
			} else {
				builder.append(c);
			}
		}

		return builder.toString();
	}

	public static Component formatComponent(String input, Player player, boolean stripItalics) {
		Component comp = formatComponent(input, player);
		if (stripItalics) {
			comp = comp.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
		}
		return comp;
	}
}
