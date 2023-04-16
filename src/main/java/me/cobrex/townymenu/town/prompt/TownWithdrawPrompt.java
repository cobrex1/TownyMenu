package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownWithdrawPrompt extends SimplePrompt {

	Town town;

	public TownWithdrawPrompt(Town town) {
		super(false);

		this.town = town;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Withdraw.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return ((Valid.isInteger(input) && (town.getAccount().canPayFromHoldings(Integer.parseInt(input)))) || input.equalsIgnoreCase(Localization.CANCEL));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.TownConversables.Withdraw.INVALID;
	}

	@Override
	protected @Nullable
	Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.withdraw") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("town withdraw " + (input));
		tell(Localization.TownConversables.Withdraw.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));
		return null;
	}
}
