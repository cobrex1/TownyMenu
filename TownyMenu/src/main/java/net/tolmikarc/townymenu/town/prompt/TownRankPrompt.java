package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.permissions.TownyPerms;
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
		return "&3Type in the rank you would like to give to &b" + resident.getName() + " &3now: &b(Options: " + Common.join(TownyPerms.getTownRanks(), ", ") + ") &3Type &ccancel &3to cancel or &cremove &3to remove their rank.";
	}


	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cThis player either already has this rank or the rank does not exist. (Options: " + Common.join(TownyPerms.getTownRanks(), ", ") + ") &cType &c&lcancel &cto cancel or remove to remove their ranks.";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (TownyPerms.getTownRanks().contains(input) && !resident.hasTownRank(input)) || (input.toLowerCase().equals("cancel") || input.toLowerCase().equals("remove"));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (input.toLowerCase().equals("cancel")) {
			tell("&cCancelled prompt.");
			return null;
		} else if (input.toLowerCase().equals("remove")) {
			for (String rank : TownyPerms.getTownRanks()) {
				if (resident.hasTownRank(rank)) {
					try {
						resident.removeTownRank(rank);
						TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
					} catch (NotRegisteredException e) {
						e.printStackTrace();
					}
				}
			}
			tell("&cRemoved all ranks from resident " + resident.getName());
		} else {
			try {
				resident.addTownRank(input);
				TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
				tell("&aSuccessfully set " + resident.getName() + "'s &arank to " + input);
			} catch (AlreadyRegisteredException | NotRegisteredException e) {
				e.printStackTrace();
			}
		}


		return null;
	}
}
