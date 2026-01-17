package me.cobrex.townymenu.town.prompt;


import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

//public class TownCreatePrompt extends BasePrompt {
public class TownCreatePrompt extends ComponentPrompt {

	private final Player player;

	public TownCreatePrompt(Player player) {
		this.player = player;
	}

	@Override
	protected String getPromptMessage (ConversationContext context) {
		return Localization.JoinCreateMenu.CREATE_OWN_TOWN;
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, String input) {
		if (input == null || input.isBlank()) {
			MessageUtils.send(player, "&4Town name cannot be empty.");
			return END_OF_CONVERSATION;
		}

		if (input.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("t new " + input);
		return END_OF_CONVERSATION;
	}
}
