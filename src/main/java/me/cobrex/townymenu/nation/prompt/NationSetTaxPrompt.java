package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;

public class NationSetTaxPrompt extends SimplePrompt {


	Nation nation;

	public NationSetTaxPrompt(Nation nation) {
		super(false);

		this.nation = nation;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Tax.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		if (Valid.isInteger(input))
			if (nation.isTaxPercentage())
				return (0 <= Integer.parseInt(input) && Integer.parseInt(input) < TownySettings.getMaxNationTaxPercent());
			else
				return (Integer.parseInt(input) < TownySettings.getMaxNationTax() && Integer.parseInt(input) >= 0);

		else return input.equals(Localization.CANCEL);
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		if (nation.isTaxPercentage())
			return Localization.NationConversables.Nation_Tax.INVALID_PERCENT.replace("{max_percent}", String.valueOf(TownySettings.getMaxNationTaxPercent()));
		else
			return Localization.NationConversables.Nation_Tax.INVALID_AMOUNT.replace("{max_amount}", String.valueOf(TownySettings.getMaxNationTax()));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.nation.set.taxes") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("nation set taxes " + (input));
		tell(nation.isTaxPercentage() ? Localization.NationConversables.Nation_Tax.RESPONSE_PERCENT.replace("{input}", input) : Localization.TownConversables.Tax.RESPONSE_AMOUNT.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));

		return null;
	}
}

