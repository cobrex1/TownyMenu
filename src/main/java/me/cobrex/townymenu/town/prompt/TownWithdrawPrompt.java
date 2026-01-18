package me.cobrex.townymenu.town.prompt;

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

public class TownWithdrawPrompt extends ComponentPrompt {

	private final Town town;

	public TownWithdrawPrompt(Town town) {
		this.town = town;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Withdraw.PROMPT;
	}

	@Override
	public @Nullable Prompt accept(@NotNull ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		input = input.trim();

		if (!player.hasPermission("towny.command.town.withdraw")) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (!isValidAmount(input)) {
			MessageUtils.send(player, Localization.TownConversables.Withdraw.INVALID);
			return this;
		}

		player.performCommand("town withdraw " + input);
		MessageUtils.send(player, Localization.TownConversables.Withdraw.RESPONSE
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input));

		return END_OF_CONVERSATION;
	}

	private boolean isValidAmount(String input) {
		try {
			int amount = Integer.parseInt(input);
			return town.getAccount().canPayFromHoldings(amount);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}