package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.EmptyTownException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownKickPrompt extends SimplePrompt {

	Resident resident;

	public TownKickPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&cAre you sure you would like to kick &b" + resident.getName() + " &cfrom your town? Type &bconfirm &cor &4cancel &cnow:";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals("confirm") || input.toLowerCase().equals("cancel");
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.kick"))
			return null;

		try {
			Town town = resident.getTown();
			town.removeResident(resident);
			TownyAPI.getInstance().getDataSource().saveTown(town);
			tell("&3Successfully kicked &b" + resident.getName() + " &3from your town.");
		} catch (EmptyTownException | NotRegisteredException e) {
			e.printStackTrace();
		}


		return null;
	}
}
