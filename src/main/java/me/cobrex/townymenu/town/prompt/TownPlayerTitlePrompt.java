package me.cobrex.townymenu.town.prompt;


import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageFormatter;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownPlayerTitlePrompt extends ComponentPrompt {

	private final Resident resident;

	public TownPlayerTitlePrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Title.PROMPT
				.replace("{player}", resident.getName());
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, @NotNull String input) {
		Player player = getPlayer(context);
		String trimmed = input.trim();

		if (!player.hasPermission("towny.command.town.set.title")) {
			MessageUtils.send(player, MessageFormatter.format(Localization.Error.NO_PERMISSION, player));
			return Prompt.END_OF_CONVERSATION;
		}

		if (trimmed.equalsIgnoreCase(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (trimmed.length() >= 10) {
			MessageUtils.send(player, Localization.Error.INVALID);
			return this;
		}

		resident.setTitle(trimmed);

		try {
			if (resident.hasTown()) {
				TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
			}
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}

		MessageUtils.send(player, Localization.TownConversables.Title.RESPONSE
				.replace("{player}", resident.getName())
				.replace("{input}", trimmed));

		return Prompt.END_OF_CONVERSATION;
	}
}
