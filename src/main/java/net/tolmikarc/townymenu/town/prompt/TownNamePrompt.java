package net.tolmikarc.townymenu.town.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import net.tolmikarc.townymenu.settings.Localization;
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
	protected String getPrompt(ConversationContext ctx) {
		return Localization.TownConversables.Name.PROMPT.replace("{town}", town.getName()).replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		LagCatcher.start("load-all-town-names");
		List<String> allTownNames = new ArrayList<>();
		for (Town town : TownyAPI.getInstance().getDataSource().getTowns())
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


		try {
			if (town.getAccount().canPayFromHoldings(TownySettings.getTownRenameCost())) {
				TownyAPI.getInstance().getDataSource().renameTown(town, input);
				town.getAccount().withdraw(TownySettings.getTownRenameCost(), "Renaming town.");

				tell(Localization.TownConversables.Name.RESPONSE.replace("{input}", input));
				TownyAPI.getInstance().getDataSource().saveTown(town);
			} else
				tell(Localization.TownConversables.Name.RETURN);
		} catch (NotRegisteredException | AlreadyRegisteredException e) {
			e.printStackTrace();
		}


		return null;
	}
}
