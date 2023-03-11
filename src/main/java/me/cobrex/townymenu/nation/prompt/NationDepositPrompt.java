package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.model.HookManager;

public class NationDepositPrompt extends SimplePrompt {

	Nation nation;

	public NationDepositPrompt(Nation nation) {
		super(false);

		this.nation = nation;

	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Deposit.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return ((Valid.isInteger(input) && ((HookManager.getBalance(getPlayer(context)) - Integer.parseInt(input)) > 0))) || input.equalsIgnoreCase(Localization.CANCEL);
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.NationConversables.Nation_Deposit.INVALID;
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.nation.deposit") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("nation deposit " + (input));
		tell(Localization.NationConversables.Nation_Deposit.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));

		return null;
	}
}
