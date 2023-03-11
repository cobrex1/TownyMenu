package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class NationKickPrompt extends SimplePrompt {

	Town town;

	public NationKickPrompt(Town town) {
		super(false);
		this.town = town;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Kick.PROMPT.replace("{town}", town.getName());
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals(Localization.CONFIRM) || input.toLowerCase().equals(Localization.CANCEL);
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.nation.kick"))
			return null;

		if (input.toLowerCase().equals(Localization.CONFIRM)) {
			town.removeNation();
			tell(Localization.NationConversables.Nation_Kick.RESPONSE.replace("{town}", town.getName()));
		}

		return null;
	}
}
