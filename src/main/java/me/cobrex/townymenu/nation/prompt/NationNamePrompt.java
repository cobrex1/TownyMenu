package me.cobrex.townymenu.nation.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Nation;
import me.cobrex.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.debug.LagCatcher;

import java.util.ArrayList;
import java.util.List;

public class NationNamePrompt extends SimplePrompt {

	Nation nation;

	public NationNamePrompt(Nation nation) {
		super(false);

		this.nation = nation;
	}

	@Override
	public boolean isModal() {
		return false;
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.NationConversables.Nation_Name.PROMPT.replace("{nation}", nation.getName()).replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		LagCatcher.start("load-all-town-names");
		List<String> allNationNames = new ArrayList<>();
		for (Nation nation : TownyAPI.getInstance().getNations())
			allNationNames.add(nation.getName());
		LagCatcher.end("load-all-town-names");
		return ((input.length() < TownySettings.getMaxNameLength() && !allNationNames.contains(input)) || input.equalsIgnoreCase(Localization.CANCEL));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.NationConversables.Nation_Name.INVALID.replace("{max_length}", String.valueOf(TownySettings.getMaxNameLength()));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.nation.set.name") || input.equalsIgnoreCase(Localization.CANCEL))
			return null;

		getPlayer(context).performCommand("nation set name " + (input));
		return null;
	}
}

