package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.object.Town;
import net.tolmikarc.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.model.HookManager;

public class TownDepositPrompt extends SimplePrompt {

	Town town;

	public TownDepositPrompt(Town town) {
		super(false);

		this.town = town;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&3Type in the amount you would like to deposit in your town: &c(Type cancel to exit prompt)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (Valid.isInteger(input) && ((HookManager.getBalance(getPlayer(context)) - Integer.parseInt(input)) > 0));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cPlease enter only whole numbers and make sure you have enough money to deposit.";
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.deposit"))
			return null;

		try {
			HookManager.withdraw(getPlayer(context), Integer.parseInt(input));
			town.getAccount().setBalance(town.getAccount().getHoldingBalance() + Integer.parseInt(input), "Deposit money from menu.");
			TownyAPI.getInstance().getDataSource().saveTown(town);
			tell("&3Deposited &b" + Settings.MONEY_SYMBOL + input + " &3into your town account.");
		} catch (EconomyException e) {
			e.printStackTrace();
		}

		return null;
	}
}
