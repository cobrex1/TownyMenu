package me.cobrex.townymenu.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cobrex.townymenu.settings.Settings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

	private static final MiniMessage mini = MiniMessage.miniMessage();
	private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacyAmpersand();
	private static final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
	private static final int DEFAULT_TIMEOUT_SECONDS = 30;

	private static final boolean IS_PAPER;

	static {
		boolean isPaper = false;
		try {
			Player.class.getMethod("sendMessage", Component.class);
			isPaper = true;
		} catch (NoSuchMethodException ignored) {
			isPaper = false;
		}
		IS_PAPER = isPaper;
	}

	private static boolean looksLikeMiniMessage(String msg) {
		return msg.contains("<") && msg.contains(">");
	}

	public static Component format(Player player, String raw) {
		if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			raw = PlaceholderAPI.setPlaceholders(player, raw);
		}

		if (!raw.startsWith(Settings.PREFIX)) {
			raw = Settings.PREFIX + raw;
		}

		String hexProcessed = applyHex(raw);

		return switch (Settings.COLOR_MODE) {
			case MINIMESSAGE -> {
				if (!looksLikeMiniMessage(hexProcessed)) {
					Component legacyComponent = legacy.deserialize(hexProcessed);
					String converted = mini.serialize(legacyComponent);
					yield mini.deserialize(converted);
				}
				yield mini.deserialize(hexProcessed);
			}
			case LEGACY -> legacy.deserialize(hexProcessed);
		};
	}

	public static void send(Player player, String raw) {
		if (player == null || raw == null || raw.isEmpty()) return;

		Component message = format(player, raw);

		if (IS_PAPER) {
			player.sendMessage(message);
		} else {
			player.sendMessage(legacy.serialize(message));
		}
	}

	public static void startConversation(Plugin plugin, Player player, Prompt prompt) {
		new ConversationFactory(plugin)
				.withFirstPrompt(prompt)
				.withLocalEcho(false)
				.withEscapeSequence("cancel")
				.withTimeout(DEFAULT_TIMEOUT_SECONDS)
				.thatExcludesNonPlayersWithMessage("Console cannot use this.")
				.buildConversation(player)
				.begin();
	}

	private static String applyHex(String message) {
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			String hexCode = matcher.group(1);
			matcher.appendReplacement(buffer, "&x"
					+ "&" + hexCode.charAt(0)
					+ "&" + hexCode.charAt(1)
					+ "&" + hexCode.charAt(2)
					+ "&" + hexCode.charAt(3)
					+ "&" + hexCode.charAt(4)
					+ "&" + hexCode.charAt(5));
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	public static String getFormattedDateShort(long epochMillis) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
				.withZone(ZoneId.systemDefault());
		return formatter.format(Instant.ofEpochMilli(epochMillis));
	}
}