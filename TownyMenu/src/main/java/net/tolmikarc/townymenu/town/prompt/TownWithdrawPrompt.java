package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.object.Town;
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
		return "&6Type in the amount you would like to deposit in your town: &c(Type cancel to exit prompt)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		try {
			return (Valid.isInteger(input) && (town.getAccount().canPayFromHoldings(Integer.parseInt(input))));
		} catch (EconomyException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cPlease enter only whole numbers and make sure you have enough money to deposit.";
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		try {
			HookManager.deposit(getPlayer(context), Integer.parseInt(input));
			town.getAccount().pay(Integer.parseInt(input), "Withdrawn from menu.");
			TownyAPI.getInstance().getDataSource().saveTown(town);
			tell("&aWithdrew " + input + " from your town account.");
		} catch (EconomyException e) {
			e.printStackTrace();
		}

		return null;
	}
}
