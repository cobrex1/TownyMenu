package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class PlotNotForSalePrompt extends ComponentPrompt {

	private final TownBlock townBlock;
	private final Player player;

	public PlotNotForSalePrompt(Player player, TownBlock townBlock) {
		this.player = player;
		this.townBlock = townBlock;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.PlotConversables.NotForSale.PROMPT;
	}

	@Override
	public Prompt accept(ConversationContext context, String input) {
		if (input == null || input.isBlank()) {
			MessageUtils.send(player, Localization.Error.INVALID);
			return Prompt.END_OF_CONVERSATION;
		}

		String trimmedInput = input.trim();
		String expectedCancel = Localization.cancel(player);
		String expectedConfirm = Localization.confirm(player);

		if (!trimmedInput.equalsIgnoreCase(expectedCancel) &&
				!trimmedInput.equalsIgnoreCase(expectedConfirm)) {
			MessageUtils.send(player, Localization.Error.INVALID);
			return Prompt.END_OF_CONVERSATION;
		}

		if (!player.hasPermission("towny.command.plot.notforsale")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
//			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return Prompt.END_OF_CONVERSATION;
		}

		if (trimmedInput.equalsIgnoreCase(expectedCancel)) {
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("plot nfs");

		MessageUtils.send(player, Localization.PlotConversables.NotForSale.RESPONSE);

		return Prompt.END_OF_CONVERSATION;
	}
}
