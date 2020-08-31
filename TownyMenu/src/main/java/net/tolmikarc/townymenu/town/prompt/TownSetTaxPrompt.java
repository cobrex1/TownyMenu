package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
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
		return "&6Type in the amount you would like to set as a tax for your town: &c(Type cancel to exit prompt)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if (Valid.isInteger(input))
			if (town.isTaxPercentage())
				return (0 < Integer.parseInt(input) && Integer.parseInt(input) < TownySettings.getMaxTownTaxPercent());
			else
				return (Integer.parseInt(input) < TownySettings.getMaxTownTax());
		return false;
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		if (town.isTaxPercentage())
			return "&cPlease enter only a whole number between 0 and " + TownySettings.getMaxTownTaxPercent() + " for your tax percent.";
		else
			return "&cPlease enter a whole number between 0 and " + TownySettings.getMaxTownTax() + " for your tax amount.";
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.set.taxes"))
			return null;

		town.setTaxes(Integer.parseInt(input));
		TownyAPI.getInstance().getDataSource().saveTown(town);
		tell(town.isTaxPercentage() ? "&aTax percent set to " + input : "&aTax amount set to " + input);

		return null;
	}
}
