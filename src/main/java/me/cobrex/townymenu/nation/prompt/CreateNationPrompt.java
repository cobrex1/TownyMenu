package me.cobrex.townymenu.nation.prompt;

import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class CreateNationPrompt extends ComponentPrompt {

	private final Player player;

	public CreateNationPrompt(Player player) {
		this.player = player;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.JoinCreateNationMenu.CREATE_OWN_NATION;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (input == null || input.isBlank()) {
			MessageUtils.send(player, "&4Nation name cannot be empty.");
			return END_OF_CONVERSATION;
		}

		if (input.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("n new " + input);
		return Prompt.END_OF_CONVERSATION;
	}
}
