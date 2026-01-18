package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NationSetTaxPrompt extends ComponentPrompt {

	private final Nation nation;

	public NationSetTaxPrompt(Nation nation) {
		this.nation = nation;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_Tax.PROMPT;
	}

	@Override
	public boolean blocksForInput(ConversationContext context) {
		return true;
	}

	@Override
	public Prompt accept(@NotNull ConversationContext context, @NotNull String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.nation.set.taxes")) { // || input.equalsIgnoreCase(Localization.cancel(player))) {
			return END_OF_CONVERSATION;
		}

		int amount;
		try {
			amount = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			sendInvalidInput(player);
			return this;
		}

		if (nation.isTaxPercentage()) {
			int maxPercent = (int) TownySettings.getMaxNationTaxPercent();
			if (amount < 0 || amount >= maxPercent) {
				MessageUtils.send(player, Localization.NationConversables.Nation_Tax.INVALID_PERCENT
						.replace("{max_percent}", String.valueOf(maxPercent)));
				return this;
			}
		} else {
			int maxAmount = (int) TownySettings.getMaxNationTax();
			if (amount < 0 || amount >= maxAmount) {
				MessageUtils.send(player, Localization.NationConversables.Nation_Tax.INVALID_AMOUNT
						.replace("{max_amount}", String.valueOf(maxAmount)));
				return this;
			}
		}

		player.performCommand("nation set taxes " + amount);

		String response = nation.isTaxPercentage()
				? Localization.NationConversables.Nation_Tax.RESPONSE_PERCENT.replace("{input}", input)
				: Localization.TownConversables.Tax.RESPONSE_AMOUNT
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input);

		MessageUtils.send(player, response);
		return END_OF_CONVERSATION;
	}

	private void sendInvalidInput(Player player) {
		String message = nation.isTaxPercentage()
				? Localization.NationConversables.Nation_Tax.INVALID_PERCENT.replace("{max_percent}", String.valueOf(TownySettings.getMaxNationTaxPercent()))
				: Localization.NationConversables.Nation_Tax.INVALID_AMOUNT.replace("{max_amount}", String.valueOf(TownySettings.getMaxNationTax()));
		MessageUtils.send(player, message);
	}
}
