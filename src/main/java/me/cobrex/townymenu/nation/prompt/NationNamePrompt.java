package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class NationNamePrompt extends ComponentPrompt {

	private final Player sender;
	private final Nation nation;

	public NationNamePrompt(Player sender, Nation nation) {
		this.sender = sender;
		this.nation = nation;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_Name.PROMPT
				.replace("{nation}", nation.getName())
				.replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		String trimmed = input.trim();

		if (!player.hasPermission("towny.command.nation.set.name")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return Prompt.END_OF_CONVERSATION;
		}


		if (trimmed.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		int maxLength = TownySettings.getMaxNameLength();
		List<String> allNationNames = TownyAPI.getInstance().getNations().stream()
				.map(Nation::getName)
				.collect(Collectors.toList());

		if (trimmed.length() >= maxLength || allNationNames.contains(trimmed)) {
			MessageUtils.send(player, Localization.NationConversables.Nation_Name.INVALID
					.replace("{max_length}", String.valueOf(maxLength)));
			return this;
		}

		sender.performCommand("nation set name " + input);
		MessageUtils.send(player, Localization.NationConversables.Nation_Name.RESPONSE
				.replace("{input}", trimmed));
		return Prompt.END_OF_CONVERSATION;
	}
}
