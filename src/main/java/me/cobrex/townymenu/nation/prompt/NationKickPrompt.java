package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class NationKickPrompt extends ComponentPrompt {

	private final Town town;

	public NationKickPrompt(Town town) {
		this.town = town;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_Kick.PROMPT.replace("{town}", town.getName());
	}

	@Override
	public Prompt accept(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		String lowerInput = input.toLowerCase();

		if (!player.hasPermission("towny.command.nation.kick")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		if (lowerInput.equals(Localization.confirm(player))) {
			town.removeNation();
			MessageUtils.send(player, Localization.NationConversables.Nation_Kick.RESPONSE.replace("{town}", town.getName()));
		} else {
			MessageUtils.send(player, Localization.Error.INVALID);
//			MessageUtils.send(player, MessageFormatter.format(Localization.Error.INVALID, player));
			return Prompt.END_OF_CONVERSATION;
		}

		return Prompt.END_OF_CONVERSATION;
	}
}
