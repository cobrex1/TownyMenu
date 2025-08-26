package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.permissions.TownyPerms;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class NationRankPrompt extends ComponentPrompt {

	private final Resident resident;

	public NationRankPrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		String ranks = String.join(", ", TownyPerms.getNationRanks());
		return Localization.NationConversables.Nation_Rank.PROMPT
				.replace("{player}", resident.getName())
				.replace("{ranks}", ranks);
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.town.rank"))
			return END_OF_CONVERSATION;

		input = input.trim();

		if (input.equalsIgnoreCase(Localization.cancel(player)))
			return END_OF_CONVERSATION;

		if (input.equalsIgnoreCase(Localization.NationConversables.Nation_Rank.REMOVE)) {
			for (String rank : TownyPerms.getTownRanks()) {
				if (resident.hasNationRank(rank))
					resident.removeNationRank(rank);
			}
			MessageUtils.send(player, Localization.NationConversables.Nation_Rank.REMOVED_ALL.replace("{player}", resident.getName()));
		}

		else if (isValidRank(input)) {
			if (resident.hasNationRank(input)) {
				MessageUtils.send(player, Localization.NationConversables.Nation_Rank.ALREADY_HAS_RANK
						.replace("{player}", resident.getName())
						.replace("{input}", input));
				return this;
			}

			resident.addNationRank(input);
			MessageUtils.send(player, Localization.NationConversables.Nation_Rank.RESPONSE
					.replace("{player}", resident.getName())
					.replace("{input}", input));
		} else {
			String ranks = String.join(", ", TownyPerms.getNationRanks());
			MessageUtils.send(player, Localization.TownConversables.Rank.INVALID.replace("{ranks}", ranks));
			return this;
		}

		try {
			TownyAPI.getInstance().getDataSource().saveNation(resident.getNation());
		} catch (TownyException e) {
			throw new RuntimeException(e);
		}
		TownyAPI.getInstance().getDataSource().saveResident(resident);
		return END_OF_CONVERSATION;
	}

	private boolean isValidRank(String input) {
		return TownyPerms.getTownRanks().stream()
				.anyMatch(rank -> rank.equalsIgnoreCase(input));
	}
}
