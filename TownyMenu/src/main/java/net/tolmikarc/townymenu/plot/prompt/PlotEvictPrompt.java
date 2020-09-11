package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import net.tolmikarc.townymenu.settings.Localization;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class PlotEvictPrompt extends SimplePrompt {

	TownBlock townBlock;

	public PlotEvictPrompt(TownBlock townBlock) {
		super(false);

		this.townBlock = townBlock;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return Localization.PlotConversables.Evict.PROMPT;
	}


	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (input.equalsIgnoreCase(Localization.CANCEL) || input.equalsIgnoreCase(Localization.CONFIRM));
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.evict") || input.equalsIgnoreCase(Localization.CANCEL)) {
			return null;
		}
		if (!townBlock.hasResident()) {
			tell(Localization.PlotConversables.Evict.INVALID);
			return null;
		}

		townBlock.setResident(null);
		townBlock.setPlotPrice(-1);

		tell(Localization.PlotConversables.Evict.RESPONSE);
		return null;
	}
}
