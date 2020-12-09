package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.permissions.TownyPerms;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownRankPrompt extends SimplePrompt {

	// TODO fix

	Resident resident;

	public TownRankPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Rank.PROMPT.replace("{player}", resident.getName()).replace("{ranks}", Common.join(TownyPerms.getTownRanks(), ", "));
	}


	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.TownConversables.Rank.INVALID.replace("{ranks}", Common.join(TownyPerms.getTownRanks(), ", "));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (TownyPerms.getTownRanks().contains(input) && !resident.hasTownRank(input)) || (input.toLowerCase().equals(Localization.CANCEL) || input.toLowerCase().equals(Localization.TownConversables.Rank.REMOVE));
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (input.toLowerCase().equals(Localization.CANCEL)) {
			return null;
		} else if (input.toLowerCase().equals(Localization.TownConversables.Rank.REMOVE)) {
			for (String rank : TownyPerms.getTownRanks()) {
				if (resident.hasTownRank(rank)) {
					try {
						resident.removeTownRank(rank);
					} catch (NotRegisteredException e) {
						e.printStackTrace();
					}
				}
			}
			tell(Localization.TownConversables.Rank.REMOVED_ALL.replace("{player}", resident.getName()));
		} else {
			try {
				resident.addTownRank(input);
				tell(Localization.TownConversables.Rank.RESPONSE.replace("{player}", resident.getName()).replace("{input}", input));
			} catch (AlreadyRegisteredException e) {
				e.printStackTrace();
			}
		}
		TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
		TownyAPI.getInstance().getDataSource().saveResident(resident);


		return null;
	}
}
