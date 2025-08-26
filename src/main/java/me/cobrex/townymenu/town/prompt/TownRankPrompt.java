package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.permissions.TownyPerms;
import me.cobrex.townymenu.settings.Localization;
import me.cobrex.townymenu.utils.ComponentPrompt;
import me.cobrex.townymenu.utils.MessageUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TownRankPrompt extends ComponentPrompt {

	private final Resident resident;

	public TownRankPrompt(Resident resident) {
		this.resident = resident;
	}

	@Override
	protected String getPromptMessage(ConversationContext context) {
		String ranks = String.join(", ", TownyPerms.getTownRanks());
		return Localization.TownConversables.Rank.PROMPT
				.replace("{player}", resident.getName())
				.replace("{ranks}", ranks);
	}

	@Override
	public Prompt acceptInput(@NotNull ConversationContext context, String input) {
		Player player = (Player) context.getForWhom();

		if (!player.hasPermission("towny.command.town.rank"))
			return END_OF_CONVERSATION;

		input = input.trim();

		if (input.equalsIgnoreCase(Localization.cancel(player)))
			return END_OF_CONVERSATION;

		if (input.equalsIgnoreCase(Localization.TownConversables.Rank.REMOVE)) {
			for (String rank : TownyPerms.getTownRanks()) {
				if (resident.hasTownRank(rank))
					resident.removeTownRank(rank);
			}
			MessageUtils.send(player, Localization.TownConversables.Rank.REMOVED_ALL.replace("{player}", resident.getName()));
		}

		else if (isValidRank(input)) {
			if (resident.hasTownRank(input)) {
				MessageUtils.send(player, Localization.TownConversables.Rank.ALREADY_HAS_RANK
						.replace("{player}", resident.getName())
						.replace("{input}", input));
				return this;
			}

			resident.addTownRank(input);
			MessageUtils.send(player, Localization.TownConversables.Rank.RESPONSE
					.replace("{player}", resident.getName())
					.replace("{input}", input));
		}
		else {
			String ranks = String.join(", ", TownyPerms.getTownRanks());
			MessageUtils.send(player, Localization.TownConversables.Rank.INVALID.replace("{ranks}", ranks));
			return this;
		}

		try {
			TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
		} catch (NotRegisteredException e) {
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
