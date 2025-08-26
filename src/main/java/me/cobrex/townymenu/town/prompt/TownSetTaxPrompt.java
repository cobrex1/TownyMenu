package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TownSetTaxPrompt extends ComponentPrompt {

	private final Town town;
	private final Player player;

	public TownSetTaxPrompt(Town town, Player player) {
		this.town = town;
		this.player = player;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Tax.PROMPT;
	}

	@Override
	public boolean blocksForInput(ConversationContext context) {
		return false;
	}

	@Override
	public @Nullable Prompt acceptInput(@NotNull ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.town.set.taxes") || input.equalsIgnoreCase(Localization.cancel(player)))
			return null;

		if (!isValidInput(input)) {
			if (town.isTaxPercentage()) {
				MessageUtils.send(player,
						Localization.TownConversables.Tax.INVALID_PERCENT
								.replace("{max_percent}", String.valueOf(TownySettings.getMaxTownTaxPercent())));
			} else {
				MessageUtils.send(player,
						Localization.TownConversables.Tax.INVALID_AMOUNT
								.replace("{max_amount}", String.valueOf(TownySettings.getMaxTownTax())));
			}
			return this;
		}

		player.performCommand("town set taxes " + input);

		String message = town.isTaxPercentage()
				? Localization.TownConversables.Tax.RESPONSE_PERCENT.replace("{input}", input)
				: Localization.TownConversables.Tax.RESPONSE_AMOUNT
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input);

		MessageUtils.send(player, message);
		return null;
	}

	private boolean isValidInput(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return input.equalsIgnoreCase(Localization.cancel(player));
		}

		int value = Integer.parseInt(input);
		if (town.isTaxPercentage())
			return value >= 0 && value < TownySettings.getMaxTownTaxPercent();
		else
			return value >= 0 && value <= TownySettings.getMaxTownTax();
	}
}
