package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownBoardPrompt extends SimplePrompt {

	Town town;

	public TownBoardPrompt(Town town) {
		super(false);

		this.town = town;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Board.PROMPT;
	}


	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.set.board"))
			return null;

		getPlayer(context).performCommand("town set board " + (input));
		return null;
	}
}
