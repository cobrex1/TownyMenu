package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class PlotForSalePrompt extends ComponentPrompt {

	private final TownBlock townBlock;
	private final Player player;

	public PlotForSalePrompt(Player player, TownBlock townBlock) {
		this.player = player;
		this.townBlock = townBlock;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.PlotConversables.ForSale.PROMPT;
	}

	protected boolean isInputValid(ConversationContext context, String input) {
		if (!isInteger(input)) return false;

		int price = Integer.parseInt(input);
		return price <= TownySettings.getMaxPlotPrice();
	}

	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.PlotConversables.ForSale.INVALID
				.replace("{max_price}", String.valueOf(TownySettings.getMaxPlotPrice()));
	}

	@Override
	public Prompt accept(ConversationContext context, String input) {
		if (!isInputValid(context, input)) {
			player.sendMessage(getFailedValidationText(context, input));
			return this;
		}

		if (!player.hasPermission("towny.command.plot.forsale")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("plot fs " + input);
		MessageUtils.send(player, Localization.PlotConversables.ForSale.RESPONSE
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input));
		return Prompt.END_OF_CONVERSATION;
	}

	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException ignored) {
			return false;
		}
	}
}
