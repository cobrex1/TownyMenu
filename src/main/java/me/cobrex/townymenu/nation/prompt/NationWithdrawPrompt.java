package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NationWithdrawPrompt extends ComponentPrompt {

	private final Nation nation;

	public NationWithdrawPrompt(Nation nation) {
		this.nation = nation;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_Withdraw.PROMPT;
	}

	@Override
	public Prompt accept(@NotNull ConversationContext context, @NotNull String input) {
		Player player = (Player) context.getForWhom();
		input = input.trim();

		if (!player.hasPermission("towny.command.nation.withdraw"))
			return Prompt.END_OF_CONVERSATION;

		if (!isValidAmount(input)) {
			MessageUtils.send(player, Localization.Error.INVALID);
			return this;
		}

		player.performCommand("nation withdraw " + input);

		String response = Localization.NationConversables.Nation_Withdraw.RESPONSE
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input);

		MessageUtils.send(player, response);
		return END_OF_CONVERSATION;
	}

	private boolean isValidAmount(String input) {
		try {
			int amount = Integer.parseInt(input);
			return nation.getAccount().canPayFromHoldings(amount);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
