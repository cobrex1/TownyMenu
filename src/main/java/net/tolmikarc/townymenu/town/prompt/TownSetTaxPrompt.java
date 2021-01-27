package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
import net.tolmikarc.townymenu.settings.Localization;
import net.tolmikarc.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownSetTaxPrompt extends SimplePrompt {

	Town town;

	public TownSetTaxPrompt(Town town) {
		super(false);

		this.town = town;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Tax.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if (Valid.isInteger(input))
			if (town.isTaxPercentage())
				return (0 <= Integer.parseInt(input) && Integer.parseInt(input) < TownySettings.getMaxTownTaxPercent());
			else
				return (Integer.parseInt(input) < TownySettings.getMaxTownTax() && Integer.parseInt(input) >= 0);

		else return input.equals(Localization.CANCEL);
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		if (town.isTaxPercentage())
			return Localization.TownConversables.Tax.INVALID_PERCENT.replace("{max_percent}", String.valueOf(TownySettings.getMaxTownTaxPercent()));
		else
			return Localization.TownConversables.Tax.INVALID_AMOUNT.replace("{max_amount}", String.valueOf(TownySettings.getMaxTownTax()));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.set.taxes") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		town.setTaxes(Integer.parseInt(input));
		TownyAPI.getInstance().getDataSource().saveTown(town);
		tell(town.isTaxPercentage() ? Localization.TownConversables.Tax.RESPONSE_PERCENT.replace("{input}", input) : Localization.TownConversables.Tax.RESPONSE_AMOUNT.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));

		return null;
	}
}
