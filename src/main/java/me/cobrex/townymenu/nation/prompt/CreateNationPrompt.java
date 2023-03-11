package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.fo.conversation.SimplePrompt;

import javax.annotation.Nullable;

public class CreateNationPrompt extends SimplePrompt {

	Resident resident;

	public CreateNationPrompt(Player player) {
		super(false);
	}

	@Override
	public boolean isModal() {
		return false;
	}


	@Override
	protected String getPrompt(ConversationContext context) {
		return Localization.JoinCreateNationMenu.CREATE_OWN_NATION;
	}

	@Nullable
	@Override
	protected @org.jetbrains.annotations.Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		getPlayer(context).performCommand("n new " + (input));
		return null;
	}
}

