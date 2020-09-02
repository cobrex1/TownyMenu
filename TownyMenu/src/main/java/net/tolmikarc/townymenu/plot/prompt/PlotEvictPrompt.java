package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
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
		return "&cWould you like to evict the resident of this plot? Type &aconfirm &cor deny:";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (input.equalsIgnoreCase("confirm") || input.equalsIgnoreCase("deny"));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cPlease enter either confirm or deny";
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.evict")) {
			tell("&cYou do not have permission to evict people from this plot.");
			return null;
		}
		if (!townBlock.hasResident()) {
			tell("&cPlot does not have any residents.");
			return null;
		}

		townBlock.setResident(null);
		townBlock.setPlotPrice(-1);

		tell("&cPlot resident evicted.");
		return null;
	}
}
