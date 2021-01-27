package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.object.Resident;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownKickPrompt extends SimplePrompt {

	Resident resident;

	public TownKickPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Kick.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals(Localization.CONFIRM) || input.toLowerCase().equals(Localization.CANCEL);
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.kick"))
			return null;

		if (input.toLowerCase().equals(Localization.CONFIRM)) {
			resident.removeTown();
			tell(Localization.TownConversables.Kick.RESPONSE.replace("{player}", resident.getName()));
		}


		return null;
	}
}
