package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class NationGiveKingPrompt extends ComponentPrompt {

		private final Resident resident;

	public NationGiveKingPrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		return Localization.NationConversables.Nation_King.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.nation.set.king")) {
			MessageUtils.send(player, Localization.Error.NO_PERMISSION);
			return Prompt.END_OF_CONVERSATION;
		}

		input = input.trim().toLowerCase();
		if (input.equals(Localization.cancel(player))) {
			return Prompt.END_OF_CONVERSATION;
		}

		if (input.equals(Localization.confirm(player))) {

			Nation nation = null;
			try {
				nation = resident.getNation();
			} catch (TownyException e) {
				throw new RuntimeException(e);
			}
			try {
				nation.setKing(resident);
			} catch (TownyException e) {
				throw new RuntimeException(e);
			}

			MessageUtils.send(player, Localization.NationConversables.Nation_King.RESPONSE
					.replace("{player}", resident.getName()));

			TownyAPI.getInstance().getDataSource().saveNation(nation);
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		} else {
			MessageUtils.send(player, Localization.Error.INVALID);
			return this;

		}

		return Prompt.END_OF_CONVERSATION;
	}
}
