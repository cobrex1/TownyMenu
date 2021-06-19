package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
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

public class TownDepositPrompt extends SimplePrompt {

	Town town;

	public TownDepositPrompt(Town town) {
		super(false);

		this.town = town;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Deposit.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return ((Valid.isInteger(input) && ((HookManager.getBalance(getPlayer(context)) - Integer.parseInt(input)) > 0))) || input.equalsIgnoreCase(Localization.CANCEL);
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.TownConversables.Deposit.INVALID;
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.deposit") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		HookManager.withdraw(getPlayer(context), Integer.parseInt(input));
		Resident res = TownyAPI.getInstance().getResident(getPlayer(context).getUniqueId());
		if (res == null) {
			return null;
		}
		res.getAccount().payTo(Integer.parseInt(input), town, "Deposit money from menu.");
		TownyAPI.getInstance().getDataSource().saveTown(town);
		tell(Localization.TownConversables.Deposit.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));

		return null;
	}
}
