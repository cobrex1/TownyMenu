package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.conversation.SimplePrompt;

public class TownGiveMayorPrompt extends SimplePrompt {

	Resident resident;

	public TownGiveMayorPrompt(Resident resident) {
		super(false);
		this.resident = resident;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Mayor.PROMPT.replace("{player}", resident.getName());
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return input.toLowerCase().equals(Localization.CONFIRM) || input.toLowerCase().equals(Localization.CANCEL);
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.set.mayor"))
			return null;

		if (input.toLowerCase().equals(Localization.CONFIRM)) {
			Town town = resident.getTown();
			town.setMayor(resident);
			Common.tell(getPlayer(context), Localization.TownConversables.Mayor.RESPONSE.replace("{player}", resident.getName()));
			TownyAPI.getInstance().getDataSource().saveTown(town);
			TownyAPI.getInstance().getDataSource().saveResident(resident);
		}

		return null;
	}
}
