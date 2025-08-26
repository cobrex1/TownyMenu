package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownGiveMayorPrompt extends ComponentPrompt {

	private final Resident resident;

	public TownGiveMayorPrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.TownConversables.Mayor.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.town.set.mayor")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		input = input.trim().toLowerCase();
		if (input.equals(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (input.equals(Localization.confirm(player))) {
			Town town = resident.getTownOrNull();

			if (town == null) {
				MessageUtils.send(player, Localization.Error.NO_TOWN);
				return Prompt.END_OF_CONVERSATION;
			}

			town.setMayor(resident);

			MessageUtils.send(player, Localization.TownConversables.Mayor.RESPONSE
					.replace("{player}", resident.getName()));

			TownyAPI.getInstance().getDataSource().saveTown(town);
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		} else {
			MessageUtils.send(player, Localization.Error.INVALID);
			return this;
		}

		return Prompt.END_OF_CONVERSATION;
	}
}
