package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.object.Town;
import net.tolmikarc.townymenu.settings.Localization;
import net.tolmikarc.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.model.HookManager;

public class TownWithdrawPrompt extends SimplePrompt {

	Town town;

	public TownWithdrawPrompt(Town town) {
		super(false);

		this.town = town;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Withdraw.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		try {
			return ((Valid.isInteger(input) && (town.getAccount().canPayFromHoldings(Integer.parseInt(input)))) || input.equalsIgnoreCase(Localization.CANCEL));
		} catch (EconomyException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.TownConversables.Withdraw.INVALID;
	}

	@Override
	protected @Nullable
	Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		try {
			HookManager.deposit(getPlayer(context), Integer.parseInt(input));
			town.getAccount().withdraw(Integer.parseInt(input), "Withdrawn from menu.");
			TownyAPI.getInstance().getDataSource().saveTown(town);
			tell(Localization.TownConversables.Withdraw.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));
		} catch (EconomyException e) {
			e.printStackTrace();
		}

		return null;
	}
}
