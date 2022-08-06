package me.cobrex.townymenu.join;

import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.fo.conversation.SimplePrompt;

import javax.annotation.Nullable;

public class CreateTownPrompt extends SimplePrompt {

	public CreateTownPrompt(Player player) {
		super(false);
	}

	@Override
	protected String getPrompt(ConversationContext context) {
		return Localization.JoinCreateMenu.CREATE_OWN_TOWN;
	}

	@Nullable
	@Override
	protected @org.jetbrains.annotations.Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		getPlayer(context).performCommand("t new " + (input));
		return null;
	}

}
