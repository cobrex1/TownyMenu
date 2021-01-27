package net.tolmikarc.townymenu.plot.prompt;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.event.TownBlockSettingsChangedEvent;
import com.palmergames.bukkit.towny.object.TownBlock;
import lombok.SneakyThrows;
import net.tolmikarc.townymenu.settings.Localization;
import net.tolmikarc.townymenu.settings.Settings;
import org.bukkit.Bukkit;
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
		return Localization.PlotConversables.ForSale.PROMPT;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return (Valid.isInteger(input) && (TownySettings.getMaxPlotPrice() > Integer.parseInt(input)));
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return Localization.PlotConversables.ForSale.INVALID.replace("{max_price}", String.valueOf(TownySettings.getMaxPlotPrice()));
	}

	@SneakyThrows
	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if (!getPlayer(context).hasPermission("towny.command.plot.forsale")) {
			return null;
		}

		townBlock.setPlotPrice(Integer.parseInt(input));
		townBlock.setChanged(true);
		TownBlockSettingsChangedEvent event = new TownBlockSettingsChangedEvent(townBlock);
		Bukkit.getServer().getPluginManager().callEvent(event);
		TownyAPI.getInstance().getDataSource().saveTownBlock(townBlock);
		TownyAPI.getInstance().getDataSource().saveTown(townBlock.getTown());
		tell(Localization.PlotConversables.ForSale.RESPONSE.replace("{money_symbol}", Settings.MONEY_SYMBOL).replace("{input}", input));
		return null;
	}
}
