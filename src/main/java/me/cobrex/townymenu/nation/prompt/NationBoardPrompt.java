package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class NationBoardPrompt extends SimplePrompt {

	Nation nation;

	public NationBoardPrompt(Nation nation) {
		super(false);

		this.nation = nation;

	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Board.PROMPT;
	}


	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.nation.set.board"))
			return null;

		getPlayer(context).performCommand("nation set board " + (input));
		return null;
	}
}

