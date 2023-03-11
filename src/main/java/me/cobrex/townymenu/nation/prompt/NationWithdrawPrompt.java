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

public class NationWithdrawPrompt extends SimplePrompt {


	Nation nation;

	public NationWithdrawPrompt(Nation nation) {
		super(false);

		this.nation = nation;

	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Withdraw.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return ((Valid.isInteger(input) && (nation.getAccount().canPayFromHoldings(Integer.parseInt(input)))) || input.equalsIgnoreCase(Localization.CANCEL));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.NationConversables.Nation_Withdraw.INVALID;
	}

	@Override
	protected @Nullable
	Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.nation.withdraw") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("nation withdraw " + (input));
		tell(Localization.NationConversables.Nation_Withdraw.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));
		return null;
	}
}
