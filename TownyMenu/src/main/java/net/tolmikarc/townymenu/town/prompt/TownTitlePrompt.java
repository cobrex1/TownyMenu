package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownTitlePrompt extends SimplePrompt {

	Resident resident;

	public TownTitlePrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&3Type in the title you would like to give to &b" + resident.getName() + " &3now: (under 10 characters) &cType cancel to exit.";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.length() < 10;
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.set.title"))
			return null;


		resident.setTitle(input);
		try {
			TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}

		tell("&3Successfully set &b" + resident.getName() + "'s &3title to &b" + input);

		return null;
	}
}
