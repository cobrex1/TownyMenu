package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class TownBoardPrompt extends ComponentPrompt {

	private final Town town;
	private final Player player;

	public TownBoardPrompt(Player player, Town town) {
		System.out.println("[DEBUG] Constructed TownBoardPrompt for town: " + town.getName());
		this.player = player;
		this.town = town;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		Bukkit.getLogger().info("[DEBUG TBP] Showing prompt to " + player.getName());
		System.out.println("[DEBUG TBP] getPromptMessage called for board prompt.");
		System.out.println("[DEBUG TBP] Sending get message: " + Localization.TownConversables.Board.PROMPT);
		return Localization.TownConversables.Board.PROMPT;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();
		String trimmed = input.trim();
		System.out.println("[DEBUG TBP] acceptInput received: " + input);

		if (!player.hasPermission("towny.command.town.set.board")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		if (trimmed.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		player.performCommand("town set board " + input);
		System.out.println("[DEBUG TBP] Sending response message: " + Localization.TownConversables.Board.RESPONSE);
		MessageUtils.send(player, Localization.TownConversables.Board.RESPONSE);
		return Prompt.END_OF_CONVERSATION;
	}
}
