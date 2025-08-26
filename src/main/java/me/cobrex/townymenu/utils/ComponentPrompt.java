package me.cobrex.townymenu.utils;

import me.cobrex.townymenu.TownyMenuPlugin;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public abstract class ComponentPrompt extends StringPrompt {

	public static void startPrompt(Player player, ComponentPrompt prompt) {

		ConversationFactory factory = new ConversationFactory(TownyMenuPlugin.instance)
				.withFirstPrompt(prompt)
				.withLocalEcho(false)
				.withPrefix(context -> "")
				.withEscapeSequence(Localization.cancel(player))
				.thatExcludesNonPlayersWithMessage("Console cannot use this.");

		Conversation conversation = factory.buildConversation(player);
		conversation.begin();
	}

	@Override
	public String getPromptText(ConversationContext context) {
		Player player = (Player) context.getForWhom();
		MessageUtils.send(player, getPromptMessage(context));
		return "";
	}

	public void show(Player player) {
//		System.out.println("[DEBUG CompPrompt 17] show() called");
		ComponentPrompt.startPrompt(player, this);
	}

	protected Player getPlayer(ConversationContext context) {
		Object forWhom = context.getForWhom();
		if (forWhom instanceof Player) {
			return (Player) forWhom;
		}
		throw new IllegalStateException("Prompt was not started by a player.");
	}

	protected abstract String getPromptMessage(ConversationContext context);
}