package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownKickPrompt extends ComponentPrompt {

	private final Resident resident;

	public TownKickPrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Kick.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, @NotNull String input) {
		Player player = resident.getPlayer();
		String lowered = input.trim().toLowerCase();

		if (!player.hasPermission("towny.command.town.kick")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return Prompt.END_OF_CONVERSATION;
		}

		if (resident.isMayor()) {
			MessageUtils.send(player, MessageFormatter.format(Localization.TownConversables.Kick.MAYOR, player));
			return Prompt.END_OF_CONVERSATION;
		}

		if (lowered.equals(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (lowered.equals(Localization.confirm(player))) {
			resident.removeTown();
			MessageUtils.send(player, MessageFormatter.format(Localization.TownConversables.Kick.RESPONSE.replace("{player}", resident.getName()), player));
		} else {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.INVALID, player));
			return Prompt.END_OF_CONVERSATION;
		}

		return Prompt.END_OF_CONVERSATION;
	}
}
