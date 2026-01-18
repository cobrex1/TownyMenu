package me.cobrex.townymenu.town.prompt;


import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

;

public class TownNamePrompt extends ComponentPrompt {

	private final Town town;

	public TownNamePrompt(Town town) {
		this.town = town;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Name.PROMPT
				.replace("{town}", town.getName())
				.replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	public Prompt accept(@NotNull ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		String trimmed = input.trim();

		if (!player.hasPermission("towny.command.town.set.name")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return Prompt.END_OF_CONVERSATION;
		}

		int maxLength = TownySettings.getMaxNameLength();
		List<String> allTownNames = TownyAPI.getInstance().getTowns().stream()
				.map(Town::getName)
				.collect(Collectors.toList());

		if (trimmed.length() >= maxLength || allTownNames.contains(trimmed)) {
			MessageUtils.send(player, Localization.TownConversables.Name.INVALID
					.replace("{max_length}", String.valueOf(maxLength)));
			return this;
		}

		player.performCommand("town set name " + trimmed);
		MessageUtils.send(player, Localization.TownConversables.Name.RESPONSE
		.replace("{input}", trimmed));
		return Prompt.END_OF_CONVERSATION;
	}
}
