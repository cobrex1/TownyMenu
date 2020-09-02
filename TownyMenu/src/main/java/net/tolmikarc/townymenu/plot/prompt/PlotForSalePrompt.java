package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.TownBlock;
import net.tolmikarc.townymenu.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;

public class PlotForSalePrompt extends SimplePrompt {

	TownBlock townBlock;

	public PlotForSalePrompt(TownBlock townBlock) {
		super(false);

		this.townBlock = townBlock;

	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&3Type in the amount you would like to set this plot for sale: &c(Type cancel to exit prompt)";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (Valid.isInteger(input) && (TownySettings.getMaxPlotPrice() > Integer.parseInt(input)));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&cPlease enter only whole numbers and is less than the max plot price: " + TownySettings.getMaxPlotPrice();
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.forsale")) {
			tell("&cYou do not have permission to set plots for sale.");
			return null;
		}

		townBlock.setPlotPrice(Integer.parseInt(input));
		tell("&3Plot set price to &b" + Settings.MONEY_SYMBOL + input);
		return null;
	}
}
