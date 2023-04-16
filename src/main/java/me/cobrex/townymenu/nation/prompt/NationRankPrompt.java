package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.permissions.TownyPerms;
import lombok.SneakyThrows;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

public class NationRankPrompt extends SimplePrompt {


	// TODO fix

	Resident resident;

	public NationRankPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Rank.PROMPT.replace("{player}", resident.getName()).replace("{ranks}", Common.join(TownyPerms.getNationRanks(), ", "));
	}


	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.NationConversables.Nation_Rank.INVALID.replace("{ranks}", Common.join(TownyPerms.getNationRanks(), ", "));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (TownyPerms.getNationRanks().contains(input) && !resident.hasNationRank(input)) || (input.toLowerCase().equals(Localization.CANCEL) || input.toLowerCase().equals(Localization.NationConversables.Nation_Rank.REMOVE));
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (input.toLowerCase().equals(Localization.CANCEL)) {
			return null;
		} else if (input.toLowerCase().equals(Localization.NationConversables.Nation_Rank.REMOVE)) {
			for (String rank : TownyPerms.getNationRanks()) {
				if (resident.hasNationRank(rank)) {
					resident.removeNationRank(rank);
				}
			}
			tell(Localization.NationConversables.Nation_Rank.REMOVED_ALL.replace("{player}", resident.getName()));
		} else {
			resident.addNationRank(input);
			tell(Localization.NationConversables.Nation_Rank.RESPONSE.replace("{player}", resident.getName()).replace("{input}", input));
		}
		TownyAPI.getInstance().getDataSource().saveNation(resident.getNation());
		TownyAPI.getInstance().getDataSource().saveResident(resident);

		return null;
	}
}

