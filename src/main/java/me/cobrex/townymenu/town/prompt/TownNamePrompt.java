package me.cobrex.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Town;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.debug.LagCatcher;

import java.util.ArrayList;
import java.util.List;

public class TownNamePrompt extends SimplePrompt {

	Town town;

	public TownNamePrompt(Town town) {
		super(false);

		this.town = town;

	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Name.PROMPT.replace("{town}", town.getName()).replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		LagCatcher.start("load-all-town-names");
		List<String> allTownNames = new ArrayList<>();
		for (Town town : TownyAPI.getInstance().getTowns())
//		for (Town town : TownyAPI.getInstance().getDataSource().getTowns())
			allTownNames.add(town.getName());
		LagCatcher.end("load-all-town-names");
		return ((input.length() < TownySettings.getMaxNameLength() && !allTownNames.contains(input)) || input.equalsIgnoreCase(Localization.CANCEL));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.TownConversables.Name.INVALID.replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.town.set.name") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("town set name " + (input));
		return null;
	}
}
