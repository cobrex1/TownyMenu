package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.settings.Settings;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NationDepositPrompt extends ComponentPrompt {

	private final Nation nation;
	private final Economy economy;

	public NationDepositPrompt(Nation nation, Economy economy) {
		this.nation = nation;
		this.economy = economy;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_Deposit.PROMPT;
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, @NotNull String input) {
		Player player = (Player) context.getForWhom();

		if (input.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (!isInteger(input)) {
			MessageUtils.send(player, Localization.NationConversables.Nation_Deposit.INVALID);
			return this;
		}

		if (!player.hasPermission("towny.command.nation.deposit")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		int amount;
		try {
			amount = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			player.sendMessage(Localization.NationConversables.Nation_Deposit.INVALID);
			return this;
		}

		double balance = economy.getBalance(player);
		if (amount <= 0 || balance < amount) {
			MessageUtils.send(player, Localization.NationConversables.Nation_Deposit.INVALID);
			return this;
		}

		player.performCommand("nation deposit " + amount);

		MessageUtils.send(player, Localization.NationConversables.Nation_Deposit.RESPONSE
				.replace("{money_symbol}", Settings.MONEY_SYMBOL)
				.replace("{input}", input));

		return Prompt.END_OF_CONVERSATION;
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}