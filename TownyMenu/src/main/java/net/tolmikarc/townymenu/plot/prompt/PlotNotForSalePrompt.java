package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class PlotNotForSalePrompt extends SimplePrompt {

	TownBlock townBlock;

	public PlotNotForSalePrompt(TownBlock townBlock) {
		super(false);

		this.townBlock = townBlock;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&3Would you like to set this plot not for sale? Type &aconfirm &3or &cdeny&6:";
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
		if (!getPlayer(context).hasPermission("towny.command.plot.notforsale")) {
			tell("&cYou do not have permission to set plots not for sale.");
			return null;
		}
		townBlock.setPlotPrice(-1);
		tell("&cPlot set not for sale.");
		return null;
	}
}
