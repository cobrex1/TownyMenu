package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import net.tolmikarc.townymenu.settings.Localization;
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
		return Localization.TownConversables.Title.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.length() < 10;
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {

		if (!getPlayer(context).hasPermission("towny.command.town.set.title") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;


		resident.setTitle(input);
		try {
			TownyAPI.getInstance().getDataSource().saveTown(resident.getTown());
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}

		tell(Localization.TownConversables.Title.RESPONSE.replace("{player}", resident.getName()).replace("{input}", input));

		return null;
	}
}
