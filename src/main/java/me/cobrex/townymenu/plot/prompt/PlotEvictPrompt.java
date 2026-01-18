package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class PlotEvictPrompt extends ComponentPrompt {

	private final TownBlock townBlock;
	private final Player player;

	public PlotEvictPrompt(Player player, TownBlock townBlock) {
		this.player = player;
		this.townBlock = townBlock;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.PlotConversables.Evict.PROMPT;
	}

	@Override
	public Prompt accept(ConversationContext context, String input) {
		if (!player.hasPermission("towny.command.plot.evict"))  {
			return Prompt.END_OF_CONVERSATION;
		}

		if (!isInputValid(context, input)) {
			MessageUtils.send(player, Localization.Error.INVALID);
			return this;
		}

		player.performCommand("plot evict");
		MessageUtils.send(player, Localization.PlotConversables.Evict.RESPONSE);
		return Prompt.END_OF_CONVERSATION;
	}

	protected boolean isInputValid(ConversationContext context, String input) {
		return input.equalsIgnoreCase(Localization.cancel(player)) || input.equalsIgnoreCase(Localization.confirm(player));
	}

	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.Error.INVALID;
	}
}
