package me.cobrex.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlotSetTypePrompt extends ComponentPrompt {

	private final List<String> plotTypes = Arrays.asList(
			"arena", "bank", "default", "embassy", "farm", "inn", "jail", "shop", "wilds"
	);

	private final TownBlock townBlock;
	private final Player player;

	public PlotSetTypePrompt(Player player, TownBlock townBlock) {
		this.player = player;
		this.townBlock = townBlock;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		String options = String.join(", ", plotTypes);
		return Localization.PlotConversables.SetType.PROMPT.replace("{options}", options);
	}

	protected boolean isInputValid(ConversationContext context, String input) {
		String lowerInput = input.toLowerCase();
		return plotTypes.contains(lowerInput) || input.equalsIgnoreCase(Localization.cancel(player));
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (!isInputValid(context, input)) {
			String options = String.join(", ", plotTypes);
			MessageUtils.send(player, Localization.PlotConversables.SetType.INVALID.replace("{options}", options));
			return this;
		}

		if (input.equalsIgnoreCase(Localization.cancel(player)) || !player.hasPermission("towny.command.plot.set." + input.toLowerCase())) {
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("plot set " + input);
		MessageUtils.send(player, Localization.PlotConversables.SetType.RESPONSE.replace("{input}", input));
		return Prompt.END_OF_CONVERSATION;
	}
}
